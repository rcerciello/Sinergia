package it.rcerciello.sinergiajavaapp.scene.services;

import javax.annotation.Nonnull;

import it.rcerciello.sinergiajavaapp.scene.profile.ProfileContract;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class ServicePresenter implements ServiceContract.Presenter {

    private ServiceContract.View mView;

    public ServicePresenter(@Nonnull ServiceContract.View mView) {
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
