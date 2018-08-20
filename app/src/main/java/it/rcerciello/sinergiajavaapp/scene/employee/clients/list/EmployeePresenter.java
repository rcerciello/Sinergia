package it.rcerciello.sinergiajavaapp.scene.employee.clients.list;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import it.rcerciello.sinergiajavaapp.data.managers.ClientNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.managers.EmployeeNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.EmployeeModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class EmployeePresenter implements EmployeeFragmentContract.Presenter {

    private EmployeeFragmentContract.View mView;

    public EmployeePresenter(@Nonnull EmployeeFragmentContract.View mView) {
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
        mView.updateAdapterDataSource(new ArrayList<EmployeeModel>());
    }
}
