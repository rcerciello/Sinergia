package it.rcerciello.sinergiajavaapp.scene.clients.detail;

import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.scene.BasePresenter;
import it.rcerciello.sinergiajavaapp.scene.BaseView;

public class ClientDetailsContract
{
    interface View extends BaseView<Presenter> {

        void showSnackbarError(String message);

        void closeView();
    }


    interface Presenter extends BasePresenter {
        void editClient(ClientModel model);
    }
}
