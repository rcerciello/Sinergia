package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample;

import java.util.List;

import it.rcerciello.sinergiajavaapp.BasePresenter;
import it.rcerciello.sinergiajavaapp.BaseView;
import it.rcerciello.weekLibrary.weekview.WeekViewEvent;

public class BaseCalendarContract {

    public interface View extends BaseView<Presenter> {

        void showInProgress(boolean showOrHide);

        void removeEventFromCalendar(WeekViewEvent event);

        void showSnackbar(String message);

        void showMariaAppointments(List<WeekViewEvent> appointments);

        void showAnnaAppointments(List<WeekViewEvent> appointments);

        void showLellaAppointments(List<WeekViewEvent> appointments);


    }


    public interface Presenter extends BasePresenter {
        void deleteAppointment(String appointmentId, final WeekViewEvent event);

        void getAllAppointments();
    }
}
