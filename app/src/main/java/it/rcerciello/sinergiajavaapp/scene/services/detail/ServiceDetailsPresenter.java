package it.rcerciello.sinergiajavaapp.scene.services.detail;

import javax.annotation.Nonnull;

import it.rcerciello.sinergiajavaapp.data.managers.ServiceNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;

/**
 * Created by rcerciello on 02/05/2018.
 */
class ServiceDetailsPresenter implements ServiceDetailsContract.Presenter {

    private ServiceDetailsContract.View mView;

    ServiceDetailsPresenter(@Nonnull ServiceDetailsContract.View mView) {
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
    public void uploadPhoto() {
        //TODO Make api call
        mView.updateImage("http://img2.tgcom24.mediaset.it/binary/articolo/istockphoto/41.$plit/C_2_articolo_3076498_upiImagepp.jpg");

    }

    @Override
    public void editService(ServiceModel serviceModel) {
        ServiceNetworkLayer.getInstance().editService(serviceModel, new APICallback<Boolean>() {
            @Override
            public void onSuccess(Boolean object) {
                if (object) {
                    mView.closeView();
                }
            }

            @Override
            public void onFailure(String error) {
                    mView.showSnackbarError(error);
            }

            @Override
            public void onSessionExpired() {

            }

            @Override
            public void onFailure(boolean isFailure) {

            }
        });

    }
}
