package it.rcerciello.sinergiajavaapp.scene.clients.list;

import javax.annotation.Nonnull;
import io.realm.Realm;
import io.realm.RealmResults;
import it.rcerciello.sinergiajavaapp.data.managers.ClientNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientListResponseModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;
import timber.log.Timber;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class ClientItemPresenter implements ClientItemFragmentContract.Presenter {

    private ClientItemFragmentContract.View mView;

    ClientItemPresenter(@Nonnull ClientItemFragmentContract.View mView) {
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
    public void refreshClientList() {
        mView.showOrHideProgressBar(true);
         ClientNetworkLayer.getInstance().getClients(new APICallback<ClientListResponseModel>() {
            @Override
            public void onSuccess(ClientListResponseModel clients) {
                mView.showOrHideProgressBar(false);
                deleteOldCustomersFromDB(clients);
            }

            @Override
            public void onFailure(String error) {
                mView.showOrHideProgressBar(false);
                if (error!=null)
                {
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

    private void deleteOldCustomersFromDB(ClientListResponseModel response) {

        try(Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            RealmResults<ClientListResponseModel> oldCustomers = realm.where(ClientListResponseModel.class).findAll();
            oldCustomers.deleteAllFromRealm();
            realm.commitTransaction();
            addCustomersServices(response);
        }catch (Exception e)
        {
            mView.showOrHideProgressBar(false);
            Timber.e(e.getLocalizedMessage());
        }
    }

    private void addCustomersServices(ClientListResponseModel response) {

        try(Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.insertOrUpdate(response);
            realm.commitTransaction();
            mView.updateAdapterDataSource(response.getClients());

        }catch (Exception e)
        {
            mView.showOrHideProgressBar(false);
            Timber.e(e.getLocalizedMessage());
        }
    }

}
