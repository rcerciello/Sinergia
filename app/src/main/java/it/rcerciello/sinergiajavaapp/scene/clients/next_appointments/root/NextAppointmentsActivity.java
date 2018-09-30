package it.rcerciello.sinergiajavaapp.scene.clients.next_appointments.root;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import it.rcerciello.sinergiajavaapp.GlobalUtils;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.SaveButton.CustomSaveButton;
import it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.add_appointment.AddAppointmentActivity;
import it.rcerciello.sinergiajavaapp.data.network.ApiClient;
import it.rcerciello.sinergiajavaapp.scene.clients.next_appointments.root.adapter.NextAppointmentsAdapter;
import it.rcerciello.weekLibrary.weekview.WeekViewEvent;

public class NextAppointmentsActivity extends AppCompatActivity implements NextAppointmentContract.View, CustomSaveButton.CustomSaveButtonInterface, NextAppointmentsAdapter.OnNextAppointmentInteractionListener {
    private Unbinder unbinder;
    private NextAppointmentsAdapter mAdapter;
    private NextAppointmentContract.Presenter mPresenter;

    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.root)
    ConstraintLayout root;

    @BindView(R.id.progress)
    ProgressBar progress;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_appointments);
        unbinder = ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        mPresenter = new NextAppointmentPresenter(this);
        list.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NextAppointmentsAdapter(item ->
                onAppointmentAction(item)
        );
        list.setAdapter(mAdapter);
        Intent i = getIntent();
        if (i != null) {
            String id = getIntent().getStringExtra("customerId");

            if (GlobalUtils.isNotNullAndNotEmpty(id)) {
                mPresenter.getAllAppointments(id);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void saveButtonClick() {

    }

    @Override
    public void onAppointmentAction(WeekViewEvent item) {
        Intent i = new Intent(this, AddAppointmentActivity.class);
        String data = ApiClient.getGson().toJson(item);
        i.putExtra("isEditable", true);
        i.putExtra("AppointmentModel", data);
        startActivity(i);

    }

    @Override
    public void showSnackbarError(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void closeView() {
        onBackPressed();
    }

    @Override
    public void showOrHideProgressBar(boolean showOrHide) {
        if (showOrHide) progress.setVisibility(View.VISIBLE);
        else progress.setVisibility(View.GONE);

    }

    @Override
    public void updateAdapterDataSource(List<WeekViewEvent> clients) {
        mAdapter.updateDataSoure(clients);

    }

    @Override
    public void setPresenter(NextAppointmentContract.Presenter presenter) {

    }

    @Override
    public void logout() {

    }
}
