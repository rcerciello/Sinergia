package it.rcerciello.sinergiajavaapp.scene.Employee;

import javax.annotation.Nonnull;

import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class EmployeePresenter implements EmployeeContract.Presenter {

    private EmployeeContract.View mView;

    public EmployeePresenter(@Nonnull EmployeeContract.View mView) {
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
