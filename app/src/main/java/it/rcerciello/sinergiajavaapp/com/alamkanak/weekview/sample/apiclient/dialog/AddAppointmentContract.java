package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.dialog;

import it.rcerciello.sinergiajavaapp.BasePresenter;
import it.rcerciello.sinergiajavaapp.BaseView;
import it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.BaseCalendarContract;
import it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.AppointmentEvent;

public class AddAppointmentContract {
    public interface View extends BaseView<Presenter> {

        void showInProgress(boolean showOrHide);

        void dismissView();

        void showSnackbar(String message);
    }



    public interface Presenter  extends BasePresenter {
        void addAppointment(AppointmentEvent event);

        void editAppointment(AppointmentEvent event);
    }
}
