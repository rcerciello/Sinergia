package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.AddAppointment;

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

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.rcerciello.sinergiajavaapp.GlobalUtils;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.dialog.client.ClientSelectDialogFragment;
import it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.dialog.service.ServiceSelectDialogFragment;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.data.network.ApiClient;
import it.rcerciello.sinergiajavaapp.utils.GeneralConstants;
import it.rcerciello.weekLibrary.weekview.WeekViewEvent;


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

    private WeekViewEvent editableModel;

    private WeekViewEvent newWeekModel;

    private ServiceModel serviceModel;
    private ClientModel clientModel;

    int mHour;
    int mMinute;
    int mYear;
    int mMonth;
    int mDay;


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
                        clientId.setText(editableModel.getIdCliente());
                    }
                    if (GlobalUtils.isNotNullAndNotEmpty(editableModel.getIdCollaborator())) {
                        switch (editableModel.getIdCollaborator()) {
                            case GeneralConstants.ID_LELLA:
                                rbLella.performClick();
                                break;
                            case GeneralConstants.ID_MARIA:
                                rbMaria.performClick();
                                break;
                            case GeneralConstants.ID_ANNA:
                                rbAnna.performClick();
                                break;
                        }
                    }

                    if (GlobalUtils.isNotNullAndNotEmpty(String.valueOf(editableModel.getIdService()))) {
                        serviceId.setText(editableModel.getIdService());
                        newWeekModel.setIdService(editableModel.getIdService());
                    }

                }

            } else {
                String collaborator = getIntent().getStringExtra("collaboratorID");
                if (GlobalUtils.isNotNullAndNotEmpty(collaborator)) {
                    switch (collaborator) {
                        case GeneralConstants.ID_LELLA:
                            rbLella.performClick();
                            break;
                        case GeneralConstants.ID_MARIA:
                            rbMaria.performClick();
                            break;
                        case GeneralConstants.ID_ANNA:
                            rbAnna.performClick();
                            break;


                    }
                }
            }
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });

        serviceId.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (serviceId.getRight() - serviceId.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        ServiceSelectDialogFragment serviceSelectDialog = ServiceSelectDialogFragment.newInstance();
                        serviceSelectDialog.setServiceSelectedListener(new ServiceSelectDialogFragment.ServiceSelectedListener() {
                            @Override
                            public void onServiceSelected(ServiceModel model) {
                                serviceId.setText(model.getName());
                                newWeekModel.setIdService(model.getServiceIdentifier());
                                serviceModel = model;
                            }
                        });
                        serviceSelectDialog.show(getSupportFragmentManager(), "mobilePrefixSelectDialog");
                        return false;
                    }
                }
                return true;

            }
        });


        clientId.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (serviceId.getRight() - serviceId.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        ClientSelectDialogFragment serviceSelectDialog = ClientSelectDialogFragment.newInstance();
                        serviceSelectDialog.setClientSelectedListener(new ClientSelectDialogFragment.ClientSelectedListener() {
                            @Override
                            public void onClientSelected(ClientModel model) {
                                clientId.setText(model.getName() + " " + model.getSurname());
                                newWeekModel.setIdCliente(model.getId());
                                clientModel = model;
                            }
                        });
                        serviceSelectDialog.show(getSupportFragmentManager(), "mobilePrefixSelectDialog");
                        return false;
                    }
                }
                return true;

            }
        });
    }

    @OnClick(R.id.rbLella)
    public void clickLellaAction() {
        rbLella.setChecked(true);
        rbAnna.setChecked(false);
        rbMaria.setChecked(false);

        newWeekModel.setIdCollaborator(GeneralConstants.ID_LELLA);
    }

    @OnClick(R.id.rbMaria)
    public void clickLMariaAction() {
        rbLella.setChecked(false);
        rbAnna.setChecked(false);
        rbMaria.setChecked(true);
        newWeekModel.setIdCollaborator(GeneralConstants.ID_MARIA);
    }

    @OnClick(R.id.rbAnna)
    public void clickAnnaaAction() {
        rbLella.setChecked(false);
        rbAnna.setChecked(true);
        rbMaria.setChecked(false);

        newWeekModel.setIdCollaborator(GeneralConstants.ID_ANNA);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_changes, R.anim.slide_out_top);
    }


    @OnClick(R.id.btnOK)
    public void okAction() {
        if (serviceModel != null) {
            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, mHour);
            startTime.set(Calendar.MINUTE, mMinute);
            startTime.set(Calendar.MONTH, mMonth);
            startTime.set(Calendar.YEAR, mYear);
            Calendar endTime = (Calendar) startTime.clone();

            int hours = serviceModel.getDuration() / 60; //since both are ints, you get an int
            int minutes = serviceModel.getDuration() % 60;


            endTime.add(Calendar.MINUTE, minutes);
            endTime.add(Calendar.HOUR, hours);
            endTime.set(Calendar.MONTH, mMonth);

            newWeekModel.setStartTime(startTime);
            newWeekModel.setEndTime(endTime);
        }
        if (isEditable) {
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
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        etOraInizio.setText(hourOfDay + ":" + minute);

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    @OnClick(R.id.btnDatePicker)
    public void datePickerAction() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        etDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
