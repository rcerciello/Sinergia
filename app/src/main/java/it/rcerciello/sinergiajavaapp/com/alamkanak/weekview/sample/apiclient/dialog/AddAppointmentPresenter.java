package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.dialog;

import android.support.annotation.NonNull;

import it.rcerciello.sinergiajavaapp.GlobalUtils;
import it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.AppointmentEvent;
import it.rcerciello.sinergiajavaapp.data.managers.CalendarNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;

public class AddAppointmentPresenter implements AddAppointmentContract.Presenter {

    private AddAppointmentContract.View mView;

    public AddAppointmentPresenter(@NonNull AddAppointmentContract.View view) {
        this.mView = view;
    }

    @Override
    public void addAppointment(AppointmentEvent event) {
        mView.showInProgress(true);
        CalendarNetworkLayer.getInstance().addAppointment(event, new APICallback<Boolean>() {
            @Override
            public void onSuccess(Boolean object) {
                mView.showInProgress(false);
                mView.dismissView();
            }

            @Override
            public void onFailure(String error) {
                mView.showInProgress(false);
                if (GlobalUtils.isNotNullAndNotEmpty(error)) {
                    mView.showSnackbar(error);
                }
            }

            @Override
            public void onSessionExpired() {
                mView.showInProgress(false);
            }

            @Override
            public void onFailure(boolean isFailure) {
                mView.showInProgress(false);
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void editAppointment(AppointmentEvent event) {
        mView.showInProgress(true);
        CalendarNetworkLayer.getInstance().editAppointment(event, new APICallback<Boolean>() {
            @Override
            public void onSuccess(Boolean object) {
                mView.showInProgress(false);
                mView.dismissView();
            }

            @Override
            public void onFailure(String error) {

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
