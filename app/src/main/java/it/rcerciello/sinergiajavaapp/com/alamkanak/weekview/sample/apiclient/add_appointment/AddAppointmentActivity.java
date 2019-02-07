package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.add_appointment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.rcerciello.sinergiajavaapp.GlobalUtils;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.dialog.client.ClientSelectDialogFragment;
import it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.dialog.service.ServiceSelectDialogFragment;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModelAdd;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;
import it.rcerciello.sinergiajavaapp.data.network.ApiClient;
import it.rcerciello.sinergiajavaapp.data.repository.SinergiaRepo;
import it.rcerciello.sinergiajavaapp.scene.clients.add_clients.AddClientsActivity;
import it.rcerciello.sinergiajavaapp.scene.services.add_service.AddServiceActivity;
import it.rcerciello.sinergiajavaapp.utils.GeneralConstants;
import it.rcerciello.weekLibrary.weekview.WeekViewEvent;
import timber.log.Timber;


public class AddAppointmentActivity extends AppCompatActivity implements AddAppointmentContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.etClientName)
    EditText clientId;

    @BindView(R.id.etServiceId)
    EditText serviceId;

    @BindView(R.id.etOraInizio)
    EditText etOraInizio;

    @BindView(R.id.etData)
    EditText etDate;

    @BindView(R.id.btnOK)
    Button btnOk;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.rbLella)
    RadioButton rbLella;

    @BindView(R.id.rbAnna)
    RadioButton rbAnna;

    @BindView(R.id.rbMaria)
    RadioButton rbMaria;

    @BindView(R.id.btnTimePicker)
    Button btnTimePicker;

    @BindView(R.id.btnDatePicker)
    Button btnDatePicker;

    @BindView(R.id.root)
    RelativeLayout root;

    @BindView(R.id.tvNuovoCliente)
    TextView tvNuovoCliente;

    @BindView(R.id.tvNuovoServizio)
    TextView tvNuovoServizio;

    private boolean isEditable = false;

    private AddAppointmentContract.Presenter mPresenter;
    private WeekViewEvent editableModel = new WeekViewEvent();
    private WeekViewEvent newWeekModel;
    private ServiceModel mServiceModel;
    private ClientModel mClientModel;
    private Calendar startTimeSelected;
    private boolean isClientFill = false, isServiceFill = false, isOperatorFill = true, isHourFill = true, isDateFill = true;
    private boolean isLellaChecked = false;
    private boolean isMariaChecked = false;
    private boolean isAnnaChecked = false;


    private Calendar startTime = GregorianCalendar.getInstance();
    int mHour;
    int mMinute;
    int mYear;
    int mMonth;
    int mDay;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        ButterKnife.bind(this);

        mPresenter = new AddAppointmentPresenter(this);
        newWeekModel = new WeekViewEvent();


        Intent i = getIntent();
        if (i != null) {
            isEditable = i.getBooleanExtra("isEditable", false);
            if (isEditable) {
                editableModel = ApiClient.getGson().fromJson(getIntent().getStringExtra("AppointmentModel"), WeekViewEvent.class);
                if (editableModel != null) {
                    newWeekModel = editableModel;
                    if (GlobalUtils.isNotNullAndNotEmpty(editableModel.getIdCliente())) {
                        newWeekModel.setIdCliente(editableModel.getIdCliente());

                        SinergiaRepo.getInstance().selectClientById(editableModel.getIdCliente(), new APICallback<ClientModel>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onSuccess(ClientModel object) {
                                clientId.setText(object.getName() + " " + object.getSurname());
                            }

                            @Override
                            public void onFailure(String error) {
                                Timber.d("errore select clients");
                            }

                            @Override
                            public void onSessionExpired() {

                            }

                            @Override
                            public void onFailure(boolean isFailure) {

                            }
                        });
                    }

                    if (editableModel.getStartTime() != null) {
                        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
                        calendar.setTime(editableModel.getStartTime().getTime());

                        etOraInizio.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
                        etDate.setText(calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR));

                        startTime = calendar;
                    }

                    for (int j = 0; j < editableModel.getId_staff().size(); j++) {
                        switch (editableModel.getId_staff().get(j)) {
                            case GeneralConstants.ID_LELLA:
                                isLellaChecked = false;
                                rbLella.performClick();
                                break;
                            case GeneralConstants.ID_MARIA:
                                isMariaChecked = false;
                                rbMaria.performClick();
                                break;
                            case GeneralConstants.ID_ANNA:
                                isAnnaChecked = false;
                                rbAnna.performClick();
                                break;
                        }
                    }

                    if (GlobalUtils.isNotNullAndNotEmpty(String.valueOf(editableModel.getId_service()))) {
                        newWeekModel.setId_service(editableModel.getId_service());
//                        SinergiaRepo.getInstance().selectServiceById(editableModel.getId_service(), new APICallback<ServiceModel>() {
//                            @Override
//                            public void onSuccess(ServiceModel object) {
//                                mServiceModel = object;
//                                serviceId.setText(object.getName());
//                            }
//
//                            @Override
//                            public void onFailure(String error) {
//                                Timber.e("Errore select service by id");
//                            }
//
//                            @Override
//                            public void onSessionExpired() {
//
//                            }
//
//                            @Override
//                            public void onFailure(boolean isFailure) {
//
//                            }
//                        });


                    }

                }

            } else {
                String collaborator = getIntent().getStringExtra("collaboratorID");
                Long d = getIntent().getLongExtra("time", -1);
                if (d != -1) {
                    Date date = new Date();
                    date.setTime(d);
                    startTimeSelected = GregorianCalendar.getInstance();
                    startTimeSelected.setTime(date);
                    etOraInizio.setText(startTimeSelected.get(Calendar.HOUR_OF_DAY) + ":" + startTimeSelected.get(Calendar.MINUTE));
                    etDate.setText(startTimeSelected.get(Calendar.DAY_OF_MONTH) + "-" + (startTimeSelected.get(Calendar.MONTH) + 1) + "-" + startTimeSelected.get(Calendar.YEAR));
                    startTime = startTimeSelected;
                }

                if (GlobalUtils.isNotNullAndNotEmpty(collaborator)) {
                    switch (collaborator) {
                        case GeneralConstants.ID_LELLA:
                            isLellaChecked = false;
                            rbLella.performClick();
                            break;
                        case GeneralConstants.ID_MARIA:
                            isMariaChecked = false;
                            rbMaria.performClick();
                            break;
                        case GeneralConstants.ID_ANNA:
                            isAnnaChecked = false;
                            rbAnna.performClick();
                            break;
                    }
                }
            }

            Timber.d("************************");
            Timber.d("isMariaChecked => " + isMariaChecked);
            Timber.d("isLellaChecked => " + isLellaChecked);
            Timber.d("isAnnaChecked => " + isAnnaChecked);


        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setOnTouchListener((View v, MotionEvent event) -> {
            v.requestFocusFromTouch();
            return false;
        });

        clientId.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (clientId.getRight() - clientId.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    ClientSelectDialogFragment serviceSelectDialog = ClientSelectDialogFragment.newInstance();
                    serviceSelectDialog.setClientSelectedListener(model -> {
                        clientId.setText(model.getName() + " " + model.getSurname());
                        newWeekModel.setIdCliente(model.getPrimaryKeyModel().getPrimaryKey());
                        mClientModel = model;
                        isClientFill = true;
                        if (areAllFieldfill()) {
                            btnOk.setVisibility(View.VISIBLE);
                        } else {
                            btnOk.setVisibility(View.GONE);
                        }
                    });
                    serviceSelectDialog.show(getSupportFragmentManager(), "ClientSelectDialog");
                    return false;
                }
            }
            return true;

        });

        serviceId.setOnTouchListener((v, event) -> {
            final int DRAWABLE_LEFT = 0;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (serviceId.getLeft() - serviceId.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                    newWeekModel.setId_service(new ArrayList<>());
                    serviceId.setText("");
                    isServiceFill = false;
                    if (areAllFieldfill()) {
                        btnOk.setVisibility(View.VISIBLE);
                    } else {
                        btnOk.setVisibility(View.GONE);
                    }
                    return false;
                }
            }
            return true;

        });
    }

    private boolean areAllFieldfill() {
        return isClientFill && isServiceFill && isOperatorFill && isHourFill && isDateFill;
    }

    public boolean checkIfServiceIdAlreadyExist(ServiceModel model) {
        if (newWeekModel.getId_service() != null && newWeekModel.getId_service().size() > 0) {

            for (String service : newWeekModel.getId_service()) {
                if (service.equalsIgnoreCase(model.getServicePrimaryKeyModel().getPrimaryKey())) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    @OnClick(R.id.btnAddService)
    public void addserviceAction() {
        ServiceSelectDialogFragment serviceSelectDialog = ServiceSelectDialogFragment.newInstance();
        serviceSelectDialog.setServiceSelectedListener(model -> {
            if (!checkIfServiceIdAlreadyExist(model)) {
                if (GlobalUtils.isNotNullAndNotEmpty(serviceId.getText().toString())) {
                    serviceId.setText(serviceId.getText().toString() + ", " + model.getName());
                } else {
                    serviceId.setText(model.getName());
                }

                if (newWeekModel.getId_service() == null) {
                    newWeekModel.setId_service(new ArrayList<>());
                }
                newWeekModel.getId_service().add(model.getServicePrimaryKeyModel().getPrimaryKey());
                Timber.d("Service ID SIZE => " + newWeekModel.getId_service().size());

                mServiceModel = model;

                isServiceFill = true;
                if (areAllFieldfill()) {
                    btnOk.setVisibility(View.VISIBLE);
                } else {
                    btnOk.setVisibility(View.GONE);
                }
            } else {
                Toast.makeText(this, "Il servizio è stato già aggiunto", Toast.LENGTH_LONG).show();
            }
        });
        serviceSelectDialog.show(getSupportFragmentManager(), "ServiceSelectDialog");
    }

    @OnClick(R.id.rbLella)
    public void clickLellaAction() {
        rbLella.setChecked(!isLellaChecked);
        isLellaChecked = !isLellaChecked;
        Timber.d("isMariaChecked => " + isMariaChecked);
        Timber.d("isLellaChecked => " + isLellaChecked);
        Timber.d("isAnnaChecked => " + isAnnaChecked);
    }

    @OnClick(R.id.rbMaria)
    public void clickLMariaAction() {
        rbMaria.setChecked(!isMariaChecked);
        isMariaChecked = !isMariaChecked;
        Timber.d("isMariaChecked => " + isMariaChecked);
        Timber.d("isLellaChecked => " + isLellaChecked);
        Timber.d("isAnnaChecked => " + isAnnaChecked);
    }

    @OnClick(R.id.rbAnna)
    public void clickAnnaaAction() {
        rbAnna.setChecked(!isAnnaChecked);
        isAnnaChecked = !isAnnaChecked;
        Timber.d("isMariaChecked => " + isMariaChecked);
        Timber.d("isLellaChecked => " + isLellaChecked);
        Timber.d("isAnnaChecked => " + isAnnaChecked);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_changes, R.anim.slide_out_top);
    }


    @OnClick(R.id.btnOK)
    public void okAction() {
        Calendar endTime = (Calendar) startTime.clone();
        int hours = mServiceModel.getDuration() / 60; //since both are ints, you get an int
        int minutes = mServiceModel.getDuration() % 60;
        endTime.add(Calendar.MINUTE, minutes);
        endTime.add(Calendar.HOUR, hours);
        newWeekModel.setStartTime(startTime);
        newWeekModel.setEndTime(endTime);


        List<String> staff = new ArrayList<>();
        if (isMariaChecked) {
            staff.add(GeneralConstants.ID_MARIA);
        }

        if (isAnnaChecked) {
            staff.add(GeneralConstants.ID_ANNA);
        }

        if (isLellaChecked) {
            staff.add(GeneralConstants.ID_LELLA);
        }
        newWeekModel.setId_staff(staff);


        if (isEditable) {
            newWeekModel.setAppointmentId(editableModel.getAppointmentId());
            mPresenter.editAppointment(newWeekModel);
        } else {
            mPresenter.addAppointment(newWeekModel);
        }

        Timber.d("MODELLO => " + newWeekModel.toString());
    }


    @Override
    public void showInProgress(boolean showOrHide) {
        if (showOrHide) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void dismissView() {
        onBackPressed();
        //Todo refresh calendar
    }


    @Override
    public void showSnackbar(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(AddAppointmentContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void logout() {

    }


    @OnClick(R.id.btnTimePicker)
    public void timePickerAction() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timePicker, selectedHour, selectedMinute) -> {
            etOraInizio.setText(selectedHour + ":" + selectedMinute);
            mHour = selectedHour;
            mMinute = selectedMinute;
            Calendar newDate = Calendar.getInstance();
            newDate.setTime(startTime.getTime());
            newDate.set(newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH), mHour, mMinute);
            Timber.d("START DATE CALENDAR =>\n" + newDate);
            startTime = newDate;
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }


    @OnClick(R.id.btnDatePicker)
    public void datePickerAction() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        Timber.d("** GIORNO ** " + mDay);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth, mHour, mMinute);
            Timber.d("START DATE CALENDAR =>\n" + newDate);
            startTime = newDate;
            etDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @OnClick(R.id.tvNuovoCliente)
    public void aggiungiNuovoCliente() {
        Intent i = new Intent(this, AddClientsActivity.class);
        i.putExtra("comeFromCalendar", true);
        startActivityForResult(i, 999);
    }

    @OnClick(R.id.tvNuovoServizio)
    public void aggiungiNuovoServizio() {
        Intent i = new Intent(this, AddServiceActivity.class);
        i.putExtra("comeFromCalendar", true);
        startActivityForResult(i, 999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == 999) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                String name = data.getStringExtra("name");
                Timber.d("NOME => " + name);
                // String data = ApiClient.getGson().toJson(bookmarksList.get(position));
                if (name.equalsIgnoreCase("Cliente")) {
                    ClientModelAdd clientModel = ApiClient.getGson().fromJson(data.getStringExtra("modello"), ClientModelAdd.class);
                    if (clientModel != null && GlobalUtils.isNotNullAndNotEmpty(clientModel.getName()) && GlobalUtils.isNotNullAndNotEmpty(clientModel.getSurname())) {
                        clientId.setText(clientModel.getName() + " " + clientModel.getSurname());
//                        Timber.d("mClientModel => " + clientModel);
//                        clientModel = model;
                    }
                } else if (name.equalsIgnoreCase("Servizio")) {
                    ServiceModel serviceModel = ApiClient.getGson().fromJson(data.getStringExtra("modello"), ServiceModel.class);
                    if (serviceModel != null && GlobalUtils.isNotNullAndNotEmpty(serviceModel.getName())) {
                        serviceId.setText(serviceModel.getName());
//                        newWeekModel.setId_service(serviceModel.getServicePrimaryKeyModel().getPrimaryKey());
                        mServiceModel = serviceModel;
                        Timber.d("mServiceModel => " + serviceModel);
                    }
                }
            }
        }
    }
}
