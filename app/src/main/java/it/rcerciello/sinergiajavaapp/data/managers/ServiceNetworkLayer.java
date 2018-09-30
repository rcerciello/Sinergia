package it.rcerciello.sinergiajavaapp.data.managers;

import java.io.IOException;
import java.util.ArrayList;

import it.rcerciello.sinergiajavaapp.data.modelli.ClientListResponseModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceEditModel;
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

public class ServiceNetworkLayer {
    private static final ServiceNetworkLayer ourInstance = new ServiceNetworkLayer();

    public static ServiceNetworkLayer getInstance() {
        return ourInstance;
    }

    private ServiceNetworkLayer() {
    }

    public static void getServices( APICallback<ServiceModelResponse> mCallback)
    {
        Call<ServiceModelResponse> call = ApiClient.getApiClient().getServices();
        call.enqueue(new Callback<ServiceModelResponse>() {
            @Override
            public void onResponse(Call<ServiceModelResponse> call, Response<ServiceModelResponse> response) {
                Timber.e("GET SERVICES LIST"+response.toString());
                if (response.isSuccessful()) {
                    Timber.e("GET SERVICES LIST"+response.body());
                    mCallback.onSuccess(response.body());
                } else {
                    mCallback.onFailure(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ServiceModelResponse> call, Throwable t) {
                Timber.e(t.getMessage());
                mCallback.onFailure(null);
            }
        });
    }

    public void addService(ServiceModel model, APICallback<Boolean> mCallback)
    {
        Call<Void> call = ApiClient.getApiClient().addService(model);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Timber.e(response.toString());
                if (response.isSuccessful()) {
                    mCallback.onSuccess(true);
                } else {
                    try {
                        mCallback.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Timber.e(t.getMessage());
                mCallback.onFailure(null);
            }
        });
    }

    public void editService(ServiceEditModel serviceModel, APICallback<Boolean> mCallback) {
        Call<Void> call = ApiClient.getApiClient().putService(serviceModel);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Timber.e(response.toString());
                if (response.isSuccessful()) {
                    mCallback.onSuccess(true);
                } else {
                    try {
                        mCallback.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
