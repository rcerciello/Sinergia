package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample;

import android.content.Context;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.List;

import it.rcerciello.sinergiajavaapp.BasePresenter;
import it.rcerciello.sinergiajavaapp.BaseView;
import it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.AppointmentEvent;

public class BaseCalendarContract {

    public interface View extends BaseView<Presenter> {

        void showInProgress(boolean showOrHide);

        void refreshCalendar();

        void removeEventFromCalendar(AppointmentEvent event);

        void showSnackbar(String message);

        void showAllAppointments(List<WeekViewEvent> appointments);


    }



    public interface Presenter  extends BasePresenter {
        void deleteAppointment(String appointmentId,final AppointmentEvent event);

        void editAppointment(AppointmentEvent event);

        void addAppointment(AppointmentEvent event);

        void getAllAppointments();
    }
}
