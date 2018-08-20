package it.rcerciello.sinergiajavaapp.scene.employee.clients.detail;

import javax.annotation.Nonnull;

import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class EmployeeDetailsPresenter implements EmployeeDetailsContract.Presenter {

    private EmployeeDetailsContract.View mView;

    public EmployeeDetailsPresenter(@Nonnull EmployeeDetailsContract.View mView) {
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

    @Override
    public void uploadPhoto() {

        //TODO Make api call
        mView.updateImage("http://img2.tgcom24.mediaset.it/binary/articolo/istockphoto/41.$plit/C_2_articolo_3076498_upiImagepp.jpg");

    }
}
