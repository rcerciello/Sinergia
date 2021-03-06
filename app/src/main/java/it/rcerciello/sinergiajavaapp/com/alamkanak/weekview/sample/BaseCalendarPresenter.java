package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import it.rcerciello.sinergiajavaapp.GlobalUtils;
import it.rcerciello.sinergiajavaapp.data.managers.CalendarNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;
import it.rcerciello.sinergiajavaapp.utils.GeneralConstants;
import it.rcerciello.weekLibrary.weekview.WeekViewEvent;

public class BaseCalendarPresenter implements BaseCalendarContract.Presenter {

    private BaseCalendarContract.View mView;

    /**
     * @param view: view
     */
    BaseCalendarPresenter(@NonNull BaseCalendarContract.View view) {
        this.mView = view;
    }

    @Override
    public void deleteAppointment(String appointmentId, final WeekViewEvent event) {

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
    public void start() {

    }

    @Override
    public void getAllAppointments() {
        mView.showInProgress(true);
        CalendarNetworkLayer.getInstance().getAllAppointments(new APICallback<List<WeekViewEvent>>() {
            @Override
            public void onSuccess(List<WeekViewEvent> appointments) {
                if (appointments != null && appointments.size() > 0) {
                    divideAppointments(appointments);
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


    private void divideAppointments(List<WeekViewEvent> appointments) {
        List<WeekViewEvent> appointmentsLella = new ArrayList<>();
        List<WeekViewEvent> appointmentsMaria = new ArrayList<>();
        List<WeekViewEvent> appointmentsAnna = new ArrayList<>();

        for (WeekViewEvent appointment : appointments) {
            if (appointment.getId_staff().size() > 0) {
                for (int i = 0; i < appointment.getId_staff().size(); i++) {
                    switch (appointment.getId_staff().get(i)) {
                        case GeneralConstants.ID_LELLA:
                            appointmentsLella.add(appointment);
                            break;
                        case GeneralConstants.ID_ANNA:
                            appointmentsAnna.add(appointment);
                            break;
                        case GeneralConstants.ID_MARIA:
                            appointmentsMaria.add(appointment);
                            break;
                    }
                }
            }
        }
        mView.showMariaAppointments(appointmentsMaria);
        mView.showLellaAppointments(appointmentsLella);
        mView.showAnnaAppointments(appointmentsAnna);
    }
}
