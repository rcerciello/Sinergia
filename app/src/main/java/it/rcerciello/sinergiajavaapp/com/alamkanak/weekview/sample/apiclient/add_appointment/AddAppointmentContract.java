package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.add_appointment;

import it.rcerciello.sinergiajavaapp.BasePresenter;
import it.rcerciello.sinergiajavaapp.BaseView;
import it.rcerciello.weekLibrary.weekview.WeekViewEvent;

public class AddAppointmentContract {
    public interface View extends BaseView<Presenter> {
        void showInProgress(boolean showOrHide);
        void dismissView();
        void showSnackbar(String message);
    }



    public interface Presenter  extends BasePresenter {
        void addAppointment(WeekViewEvent event);
        void editAppointment(WeekViewEvent event);
    }
}
