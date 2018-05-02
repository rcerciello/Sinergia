package it.rcerciello.sinergiajavaapp.scene.profile;

import javax.annotation.Nonnull;

import it.rcerciello.sinergiajavaapp.scene.Employee.EmployeeContract;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View mView;

    public ProfilePresenter(@Nonnull ProfileContract.View mView) {
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
