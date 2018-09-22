package it.rcerciello.sinergiajavaapp.scene.services.list;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import io.realm.Realm;
import io.realm.RealmResults;
import it.rcerciello.sinergiajavaapp.data.managers.ServiceModelResponse;
import it.rcerciello.sinergiajavaapp.data.managers.ServiceNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;
import timber.log.Timber;

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

        ServiceNetworkLayer.getInstance().getServices(new APICallback<ServiceModelResponse>() {
            @Override
            public void onSuccess(ServiceModelResponse response) {

                Timber.d("Service List ok ");
                mView.showOrHideProgressBar(false);
                if (response!=null && response.getServices()!=null && response.getServices().size()>0) {

                    Timber.d("Delete Service List");
                    deleteOldServicesFromDB(response);

                }
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


    private void deleteOldServicesFromDB(ServiceModelResponse response) {


        try(Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            RealmResults<ServiceModelResponse> oldServices = realm.where(ServiceModelResponse.class).findAll();
            oldServices.deleteAllFromRealm();
            realm.commitTransaction();
            addNewServices(response);
            Timber.d("Delete Service List Success");
        }catch (Exception e)
        {
            mView.showOrHideProgressBar(false);
            Timber.e(e.getLocalizedMessage());

            Timber.d("Delete Service fail => "+e.getLocalizedMessage());
        }
    }

    private void addNewServices(ServiceModelResponse response) {

        try(Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.insertOrUpdate(response);
            realm.commitTransaction();
            mView.updateAdapterDataSource(response.getServices());

        }catch (Exception e)
        {
            mView.showOrHideProgressBar(false);
            Timber.e(e.getLocalizedMessage());
        }
    }


}
