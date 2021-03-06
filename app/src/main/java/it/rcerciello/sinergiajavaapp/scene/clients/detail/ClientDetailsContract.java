package it.rcerciello.sinergiajavaapp.scene.clients.detail;

import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModelAdd;
import it.rcerciello.sinergiajavaapp.scene.BasePresenter;
import it.rcerciello.sinergiajavaapp.scene.BaseView;

public class ClientDetailsContract
{
    interface View extends BaseView<Presenter> {

        void showSnackbarError(String message);

        void closeView();

        void updateImage(String url);

        void showHideProgress(boolean b);
    }


    interface Presenter extends BasePresenter {
        void editClient(ClientModel model);

        void uploadPhoto();

        void deleteClient(String primaryKey);
    }
}
