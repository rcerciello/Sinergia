package it.rcerciello.sinergiajavaapp.scene.clients.list;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import it.rcerciello.sinergiajavaapp.data.managers.ClientNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class ClientItemPresenter implements ClientItemFragmentContract.Presenter {

    private ClientItemFragmentContract.View mView;

    public ClientItemPresenter(@Nonnull ClientItemFragmentContract.View mView) {
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
    public void refreshClientList() {
        mView.showOrHideProgressBar(true);
        ClientNetworkLayer.getInstance().getClients(new APICallback<ArrayList<ClientModel>>() {
            @Override
            public void onSuccess(ArrayList<ClientModel> clients) {
                mView.showOrHideProgressBar(false);
                mView.updateAdapterDataSource(clients);
            }

            @Override
            public void onFailure(String error) {

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
