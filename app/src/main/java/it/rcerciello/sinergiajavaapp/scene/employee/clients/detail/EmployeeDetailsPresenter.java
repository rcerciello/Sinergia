package it.rcerciello.sinergiajavaapp.scene.employee.clients.detail;

import javax.annotation.Nonnull;

import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;

/**
 * Created by rcerciello on 02/05/2018.
 */

class EmployeeDetailsPresenter implements EmployeeDetailsContract.Presenter {

    private EmployeeDetailsContract.View mView;

    /**
     * @param mView: view
     */
    EmployeeDetailsPresenter(@Nonnull EmployeeDetailsContract.View mView) {
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
        mView.closeView();
    }
}
