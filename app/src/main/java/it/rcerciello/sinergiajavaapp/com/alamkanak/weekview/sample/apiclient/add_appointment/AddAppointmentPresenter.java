package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.add_appointment;

import android.support.annotation.NonNull;

import it.rcerciello.sinergiajavaapp.GlobalUtils;
import it.rcerciello.sinergiajavaapp.data.managers.CalendarNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;
import it.rcerciello.weekLibrary.weekview.WeekViewEvent;
import timber.log.Timber;

class AddAppointmentPresenter implements AddAppointmentContract.Presenter {

    private AddAppointmentContract.View mView;
    /**
     * @param view: mview
     */
    AddAppointmentPresenter(@NonNull AddAppointmentContract.View view) {
        this.mView = view;
    }

    @Override
    public void addAppointment(WeekViewEvent event) {
        mView.showInProgress(true);
        Timber.i("Add Appointment request");
        CalendarNetworkLayer.getInstance().addAppointment(event, new APICallback<Boolean>() {
            @Override
            public void onSuccess(Boolean object) {
                Timber.i("Add Appointment on success");
                mView.showInProgress(false);
                mView.dismissView();
            }

            @Override
            public void onFailure(String error) {
                Timber.i("Add Appointment on failure");
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
    public void editAppointment(WeekViewEvent event) {
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
                mView.showInProgress(false);
            }
        });
    }
}
