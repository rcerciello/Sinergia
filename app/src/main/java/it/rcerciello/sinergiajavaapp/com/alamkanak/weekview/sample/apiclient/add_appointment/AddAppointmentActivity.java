package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.add_appointment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TimePicker;

import java.sql.Time;
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
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;
import it.rcerciello.sinergiajavaapp.data.network.ApiClient;
import it.rcerciello.sinergiajavaapp.data.repository.SinergiaRepo;
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

    private boolean isEditable = false;

    private AddAppointmentContract.Presenter mPresenter;
    private WeekViewEvent editableModel = new WeekViewEvent();
    private WeekViewEvent newWeekModel;
    private ServiceModel serviceModel;
    private ClientModel clientModel;
    private Calendar startTimeSelected;
    private boolean isLellaChecked = false;
    private boolean isMariaChecked = false;
    private boolean isAnnaChecked = false;


    private Calendar startTime = GregorianCalendar.getInstance();
    int mHour;
    int mMinute;
    int mYear;
    int mMonth;
    int mDay;


    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_NoActionBar);
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
                    }

                    for(int j  = 0 ; j < editableModel.getId_staff().size();j++)
                    {
                        switch (editableModel.getId_staff().get(j)) {
                            case GeneralConstants.ID_LELLA:
                                isLellaChecked = true;
                                rbLella.performClick();
                                break;
                            case GeneralConstants.ID_MARIA:
                                isMariaChecked = true;
                                rbMaria.performClick();
                                break;
                            case GeneralConstants.ID_ANNA:
                                isAnnaChecked = true;
                                rbAnna.performClick();
                                break;
                        }
                    }

                    if (GlobalUtils.isNotNullAndNotEmpty(String.valueOf(editableModel.getId_service()))) {
                        newWeekModel.setId_service(editableModel.getId_service());
                        SinergiaRepo.getInstance().selectServiceById(editableModel.getId_service(), new APICallback<ServiceModel>() {
                            @Override
                            public void onSuccess(ServiceModel object) {
                                serviceModel = object;
                                serviceId.setText(editableModel.getId_service());
                            }

                            @Override
                            public void onFailure(String error) {
                                Timber.e("Errore select service by id");
                            }

                            @Override
                            public void onSessionExpired() {

                            }

                            @Override
                            public void onFailure(boolean isFailure) {

                            }
                        });


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

//                if (GlobalUtils.isNotNullAndNotEmpty(collaborator)) {
//                    switch (collaborator) {
//                        case GeneralConstants.ID_LELLA:
//                            isLellaChecked =  false;
//                            rbLella.performClick();
//                            break;
//                        case GeneralConstants.ID_MARIA:
//                            isMariaChecked = false;
//                            rbMaria.performClick();
//                            break;
//                        case GeneralConstants.ID_ANNA:
//                            isMariaChecked = false;
//                            rbAnna.performClick();
//                            break;
//
//
//                    }
//                }
            }


        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setOnTouchListener((View v, MotionEvent event) -> {
            v.requestFocusFromTouch();
            return false;
        });

        serviceId.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (serviceId.getRight() - serviceId.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    ServiceSelectDialogFragment serviceSelectDialog = ServiceSelectDialogFragment.newInstance();
                    serviceSelectDialog.setServiceSelectedListener(model -> {
                        serviceId.setText(model.getName());
                        newWeekModel.setId_service(model.getServicePrimaryKeyModel().getPrimaryKey());
                        serviceModel = model;
                    });
                    serviceSelectDialog.show(getSupportFragmentManager(), "mobilePrefixSelectDialog");
                    return false;
                }
            }
            return true;

        });


        clientId.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (serviceId.getRight() - serviceId.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    ClientSelectDialogFragment serviceSelectDialog = ClientSelectDialogFragment.newInstance();
                    serviceSelectDialog.setClientSelectedListener(model -> {
                        clientId.setText(model.getName() + " " + model.getSurname());
                        newWeekModel.setIdCliente(model.getPrimaryKeyModel().getPrimaryKey());
                        clientModel = model;
                    });
                    serviceSelectDialog.show(getSupportFragmentManager(), "mobilePrefixSelectDialog");
                    return false;
                }
            }
            return true;

        });
    }

    @OnClick(R.id.rbLella)
    public void clickLellaAction() {
        rbLella.setChecked(!isLellaChecked);
        isLellaChecked = !isLellaChecked;
    }

    @OnClick(R.id.rbMaria)
    public void clickLMariaAction() {
        rbMaria.setChecked(!isMariaChecked);
        isMariaChecked = !isMariaChecked;
    }

    @OnClick(R.id.rbAnna)
    public void clickAnnaaAction() {
        rbAnna.setChecked(!isAnnaChecked);
        isAnnaChecked = !isAnnaChecked;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_changes, R.anim.slide_out_top);
    }


    @OnClick(R.id.btnOK)
    public void okAction() {
        Calendar endTime = (Calendar) startTime.clone();
        int hours = serviceModel.getDuration() / 60; //since both are ints, you get an int
        int minutes = serviceModel.getDuration() % 60;
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
}
