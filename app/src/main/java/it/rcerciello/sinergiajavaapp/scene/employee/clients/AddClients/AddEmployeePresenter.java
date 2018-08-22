package it.rcerciello.sinergiajavaapp.scene.employee.clients.AddClients;

import javax.annotation.Nonnull;

import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.Sinergia;
import it.rcerciello.sinergiajavaapp.data.managers.ClientNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class AddEmployeePresenter implements AddEmployeeContract.Presenter {

    private AddEmployeeContract.View mView;

    public AddEmployeePresenter(@Nonnull AddEmployeeContract.View mView) {
        this.mView = mView;
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

    @Override
    public void addClient(ClientModel model) {
        ClientNetworkLayer.getInstance().addClient(model, new APICallback<Boolean>() {
            @Override
            public void onSuccess(Boolean object) {
                if (object) {
                    mView.closeView();
                } else {
                    mView.showSnackbarError(Sinergia.INSTANCE.getResources().getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void onFailure(String error) {
                if (error != null) {
                    mView.showSnackbarError(error);
                }
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
