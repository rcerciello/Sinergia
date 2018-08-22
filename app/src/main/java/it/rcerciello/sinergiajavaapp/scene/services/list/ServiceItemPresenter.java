package it.rcerciello.sinergiajavaapp.scene.services.list;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import it.rcerciello.sinergiajavaapp.data.managers.ServiceNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class ServiceItemPresenter implements ServiceItemFragmentContract.Presenter {

    private ServiceItemFragmentContract.View mView;

    public ServiceItemPresenter(@Nonnull ServiceItemFragmentContract.View mView) {
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
    public void refreshServiceList() {
        mView.showOrHideProgressBar(true);
        ServiceNetworkLayer.getInstance().getServices(new APICallback<ArrayList<ServiceModel>>() {
            @Override
            public void onSuccess(ArrayList<ServiceModel> services) {
                mView.showOrHideProgressBar(false);
                mView.updateAdapterDataSource(services);
            }

            @Override
            public void onFailure(String error) {
                mView.showOrHideProgressBar(false);
            }

            @Override
            public void onSessionExpired() {
                mView.showOrHideProgressBar(false);
            }

            @Override
            public void onFailure(boolean isFailure) {
                
            }
        });
    }
}
