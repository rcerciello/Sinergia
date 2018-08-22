package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample;

import android.support.annotation.NonNull;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.rcerciello.sinergiajavaapp.GlobalUtils;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.AppointmentEvent;
import it.rcerciello.sinergiajavaapp.data.managers.CalendarNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;

public class BaseCalendarPresenter implements BaseCalendarContract.Presenter {

    private BaseCalendarContract.View mView;

    public BaseCalendarPresenter(@NonNull BaseCalendarContract.View view) {
        this.mView = view;
    }


    @Override
    public void deleteAppointment(String appointmentId, final AppointmentEvent event) {

        mView.showInProgress(true);
        CalendarNetworkLayer.getInstance().deleteAppointment(appointmentId, new APICallback<Boolean>() {
            @Override
            public void onSuccess(Boolean object) {
                mView.showInProgress(false);
                if (event != null) {
                    mView.removeEventFromCalendar(event);
                }
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
    public void editAppointment(AppointmentEvent event) {
        mView.showInProgress(true);
        CalendarNetworkLayer.getInstance().editAppointment(event, new APICallback<Boolean>() {
            @Override
            public void onSuccess(Boolean object) {
                mView.showInProgress(false);
                mView.refreshCalendar();
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
    public void addAppointment(AppointmentEvent event) {
        CalendarNetworkLayer.getInstance().addAppointment(event, new APICallback<Boolean>() {
            @Override
            public void onSuccess(Boolean object) {
                mView.showInProgress(false);
                mView.refreshCalendar();
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
    public void getAllAppointments() {
        mView.showInProgress(true);
        CalendarNetworkLayer.getInstance().getAllAppointments(new APICallback<List<WeekViewEvent>>() {
            @Override
            public void onSuccess(List<WeekViewEvent> appointments) {
                if (appointments != null && appointments.size() > 0) {

                    mView.showAllAppointments(appointments);
                }
                mView.showInProgress(false);
            }

            @Override
            public void onFailure(String error) {
                mView.showInProgress(false);
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

}
