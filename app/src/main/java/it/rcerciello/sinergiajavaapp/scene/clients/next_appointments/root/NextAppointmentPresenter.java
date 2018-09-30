package it.rcerciello.sinergiajavaapp.scene.clients.next_appointments.root;

import io.reactivex.annotations.NonNull;
import it.rcerciello.sinergiajavaapp.data.managers.ClientNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;
import it.rcerciello.sinergiajavaapp.scene.clients.next_appointments.root.models.NextAppointmentGetModel;
import it.rcerciello.sinergiajavaapp.scene.clients.next_appointments.root.models.NextAppointmentsModel;

public class NextAppointmentPresenter implements NextAppointmentContract.Presenter {


    private NextAppointmentContract.View mView;

    public NextAppointmentPresenter(@NonNull NextAppointmentContract.View view) {
        this.mView = view;
    }

    @Override
    public void getAllAppointments(String clientId) {
        mView.showOrHideProgressBar(true);

        NextAppointmentGetModel model = new NextAppointmentGetModel(clientId);
        ClientNetworkLayer.getInstance().getNextAppointments(model, new APICallback<NextAppointmentsModel>() {
            @Override
            public void onSuccess(NextAppointmentsModel object) {
                mView.showOrHideProgressBar(false);
                if (object != null && object.getEventList() != null) {
                    mView.updateAdapterDataSource(object.getEventList());
                }
            }

            @Override
            public void onFailure(String error) {
                mView.showOrHideProgressBar(false);
                if (error != null) {
                    mView.showSnackbarError(error);
                }
            }

            @Override
            public void onSessionExpired() {
                mView.showOrHideProgressBar(false);
            }

            @Override
            public void onFailure(boolean isFailure) {
                mView.showOrHideProgressBar(false);
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void clearDB() {

    }

    @Override
    public void clearPreferences() {

    }
}
