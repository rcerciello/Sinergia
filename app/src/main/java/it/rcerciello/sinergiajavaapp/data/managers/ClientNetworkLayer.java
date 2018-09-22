package it.rcerciello.sinergiajavaapp.data.managers;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.rcerciello.sinergiajavaapp.Sinergia;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientListResponseModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModelAdd;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;
import it.rcerciello.sinergiajavaapp.data.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class ClientNetworkLayer {
    private static final ClientNetworkLayer ourInstance = new ClientNetworkLayer();

    public static ClientNetworkLayer getInstance() {
        return ourInstance;
    }

    private ClientNetworkLayer() {
    }

    public void addClient(ClientModel model, APICallback<Boolean> mCallback) {
        Call<Void> call = ApiClient.getApiClient().addClient(model);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Timber.e(response.toString());
                if (response.isSuccessful()) {
                    mCallback.onSuccess(true);
                } else {
                    mCallback.onFailure(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Timber.e(t.getMessage());
                mCallback.onFailure(null);
            }
        });
    }

    public void getClients(APICallback<ClientListResponseModel> mCallback) {
        Call<ClientListResponseModel> call = ApiClient.getApiClient().getClientModel();
        call.enqueue(new Callback<ClientListResponseModel>() {
            @Override
            public void onResponse(Call<ClientListResponseModel> call, Response<ClientListResponseModel> response) {
                Timber.e(response.toString());
                if (response.isSuccessful()) {
                    mCallback.onSuccess(response.body());
                } else {
                    mCallback.onFailure(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ClientListResponseModel> call, Throwable t) {
                Timber.e(t.getMessage());
                mCallback.onFailure(null);
            }
        });

    }

    public void editCustomer(ClientModelAdd clientModel, APICallback<Boolean> mCallback) {
        Call<Void> call = ApiClient.getApiClient().putCustomer(clientModel);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Timber.e(response.toString());
                if (response.isSuccessful()) {
                    mCallback.onSuccess(true);
                } else {
                    mCallback.onFailure(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Timber.e(t.getMessage());
                mCallback.onFailure(null);
            }
        });
    }
}
