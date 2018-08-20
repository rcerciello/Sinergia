package it.rcerciello.sinergiajavaapp.scene.employee.clients.list;

import java.util.ArrayList;

import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.EmployeeModel;
import it.rcerciello.sinergiajavaapp.scene.BasePresenter;
import it.rcerciello.sinergiajavaapp.scene.BaseView;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class EmployeeFragmentContract {
    interface View extends BaseView<Presenter> {

        void showSnackbarError(String message);

        void closeView();

        void showOrHideProgressBar(boolean showOrHide);

        void updateAdapterDataSource(ArrayList<EmployeeModel> clients);
    }


    interface Presenter extends BasePresenter {
        void refreshClientList();
    }
}
