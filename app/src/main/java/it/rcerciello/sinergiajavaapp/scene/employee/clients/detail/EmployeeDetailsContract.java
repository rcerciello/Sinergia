package it.rcerciello.sinergiajavaapp.scene.employee.clients.detail;

import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.scene.BasePresenter;
import it.rcerciello.sinergiajavaapp.scene.BaseView;
 class EmployeeDetailsContract
{
    interface View extends BaseView<Presenter> {

        void showSnackbarError(String message);

        void closeView();

        void updateImage(String url);
    }


    interface Presenter extends BasePresenter {
        void editClient(ClientModel model);

    }
}
