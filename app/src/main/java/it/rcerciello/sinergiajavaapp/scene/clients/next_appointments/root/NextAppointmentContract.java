package it.rcerciello.sinergiajavaapp.scene.clients.next_appointments.root;

import java.util.List;

import it.rcerciello.sinergiajavaapp.scene.BasePresenter;
import it.rcerciello.sinergiajavaapp.scene.BaseView;
import it.rcerciello.weekLibrary.weekview.WeekViewEvent;

public class NextAppointmentContract {
    interface View extends BaseView<Presenter> {

        void showSnackbarError(String message);

        void closeView();

        void showOrHideProgressBar(boolean showOrHide);

        void updateAdapterDataSource(List<WeekViewEvent> clients);
    }


    interface Presenter extends BasePresenter {
        void getAllAppointments(String clientId);
    }
}
