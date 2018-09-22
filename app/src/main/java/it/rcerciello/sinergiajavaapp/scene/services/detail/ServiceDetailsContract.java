package it.rcerciello.sinergiajavaapp.scene.services.detail;

import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.scene.BasePresenter;
import it.rcerciello.sinergiajavaapp.scene.BaseView;

 class ServiceDetailsContract
{
    interface View extends BaseView<Presenter> {

        void showSnackbarError(String message);

        void closeView();

        void updateImage(String url);
    }


    interface Presenter extends BasePresenter {

        void uploadPhoto();

        void editService(ServiceModel serviceModel);
    }
}
