package it.rcerciello.sinergiajavaapp.scene.clients.AddClients;

import javax.annotation.Nonnull;

import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class AddClientsPresenter implements AddClientsContract.Presenter {

    private AddClientsContract.View mView;

    public AddClientsPresenter(@Nonnull AddClientsContract.View mView) {
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

    }
}
