package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.AppointmentEvent;
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
    EditText etData;


    @BindView(R.id.btnOK)
    Button btnOk;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    private boolean isEditable = false;
    private String name;
    private String endDate;
    private String startDate;
    private String id;

    private AddAppointmentContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        ButterKnife.bind(this);

        mPresenter = new AddAppointmentPresenter(this);

        Intent i = getIntent();
        if (i != null) {
            isEditable = i.getBooleanExtra("isEditable", false);
            Timber.e("isEditable", "" + isEditable);
            if (isEditable) {
//                name = i.getStringExtra("name");
//                Timber.e("name" , ""+name);
//                endDate = i.getStringExtra("endDate");
//                Timber.e("endDate" , ""+endDate);
//                startDate = i.getStringExtra("startDate");
//                Timber.e("startDate" , ""+startDate);
//                id = i.getStringExtra("id");
//                Timber.e("id" , ""+id);
//
//                if (isNonNullAndNotEmpty(id)) {
//                    clientId.setText(id);
//                }
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


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_changes, R.anim.slide_out_top);
    }


    @OnClick(R.id.btnOK)
    public void okAction() {
        if (isEditable) {
            mPresenter.editAppointment(new AppointmentEvent());
        } else {
            mPresenter.addAppointment(new AppointmentEvent());
        }
    }



    @OnClick(R.id.etOraInizio)
    public void oraAction() {
//        new TimePikerDialog(this).show();
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
}
