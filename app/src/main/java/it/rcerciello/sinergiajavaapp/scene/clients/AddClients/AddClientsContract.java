package it.rcerciello.sinergiajavaapp.scene.clients.AddClients;

import java.util.Map;

import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.scene.BasePresenter;
import it.rcerciello.sinergiajavaapp.scene.BaseView;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class AddClientsContract {
    interface View extends BaseView<Presenter> {

        void showSnackbarError(String message);

        void closeView();
    }


    interface Presenter extends BasePresenter {
        void addClient(ClientModel model);
    }
}
