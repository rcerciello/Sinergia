package it.rcerciello.sinergiajavaapp.scene.clients.list;

import javax.annotation.Nonnull;

import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;

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

}
