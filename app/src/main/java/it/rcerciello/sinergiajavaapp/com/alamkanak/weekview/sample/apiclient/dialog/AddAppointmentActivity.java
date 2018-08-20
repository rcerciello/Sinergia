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
import android.widget.ScrollView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.rcerciello.sinergiajavaapp.R;
import timber.log.Timber;


public class AddAppointmentActivity extends AppCompatActivity {

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


    private boolean isEditable = false;
    private String name;
    private String endDate;
    private String startDate;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        ButterKnife.bind(this);

        Intent i = getIntent();
        if (i != null) {
            isEditable = i.getBooleanExtra("isEditable", false);
            Timber.e("isEditable" , ""+isEditable);
            if (isEditable) {
                name = i.getStringExtra("name");
                Timber.e("name" , ""+name);
                endDate = i.getStringExtra("endDate");
                Timber.e("endDate" , ""+endDate);
                startDate = i.getStringExtra("startDate");
                Timber.e("startDate" , ""+startDate);
                id = i.getStringExtra("id");
                Timber.e("id" , ""+id);

                if (isNonNullAndNotEmpty(id)) {
                    clientId.setText(id);
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


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_changes, R.anim.slide_out_top);
    }


    @OnClick(R.id.btnOK)
    public void okAction() {
        String clientIdText = clientId.getText().toString();
        String serviceIdText = serviceId.getText().toString();

        if (isNonNullAndNotEmpty((clientIdText)) && isNonNullAndNotEmpty(serviceIdText)) {
            //TODO Do API Call
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onBackPressed();
                }
            }, 1000);
            Toast.makeText(this, "Do APi Call", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "I campi non possono essere vuoti", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isNonNullAndNotEmpty(String value) {
        return value != null && !value.trim().equals("");
    }

    @OnClick(R.id.etOraInizio)
    public void oraAction() {
//        new TimePikerDialog(this).show();
    }
}
