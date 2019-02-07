package it.rcerciello.sinergiajavaapp.scene.services.add_service;

import javax.annotation.Nonnull;

import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.Sinergia;
import it.rcerciello.sinergiajavaapp.data.managers.ServiceNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class AddServicePresenter implements AddServiceContract.Presenter {

    private AddServiceContract.View mView;

    public AddServicePresenter(@Nonnull AddServiceContract.View mView) {
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
    public void addService(ServiceModel model) {
        ServiceNetworkLayer.getInstance().addService(model, new APICallback<Boolean>() {
            @Override
            public void onSuccess(Boolean object) {
                if (object) {
                    mView.closeView(model);
                } else {
                    mView.showSnackbarError(Sinergia.INSTANCE.getResources().getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void onFailure(String error) {
                if (error != null) {
                    mView.showSnackbarError(error);
                }
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
