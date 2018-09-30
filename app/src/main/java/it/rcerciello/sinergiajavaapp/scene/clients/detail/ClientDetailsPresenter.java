package it.rcerciello.sinergiajavaapp.scene.clients.detail;

import javax.annotation.Nonnull;

import it.rcerciello.sinergiajavaapp.data.managers.ClientNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModelAdd;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class ClientDetailsPresenter implements ClientDetailsContract.Presenter {

    private ClientDetailsContract.View mView;

    public ClientDetailsPresenter(@Nonnull ClientDetailsContract.View mView) {
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
    public void editClient(ClientModel model) {

        ClientNetworkLayer.getInstance().editCustomer(model, new APICallback<Boolean>() {
            @Override
            public void onSuccess(Boolean object) {
                mView.closeView();

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

    @Override
    public void uploadPhoto() {

    }
}
