package it.rcerciello.sinergiajavaapp.data.managers;

import android.support.annotation.NonNull;

import java.io.IOException;

import it.rcerciello.sinergiajavaapp.data.modelli.ClientListResponseModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModelAdd;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;
import it.rcerciello.sinergiajavaapp.data.network.ApiClient;
import it.rcerciello.sinergiajavaapp.scene.clients.next_appointments.root.models.NextAppointmentGetModel;
import it.rcerciello.sinergiajavaapp.scene.clients.next_appointments.root.models.NextAppointmentsModel;
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

    public void addClient(ClientModelAdd model, APICallback<Boolean> mCallback) {
        Call<Void> call = ApiClient.getApiClient().addClient(model);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
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
                    try {
                        mCallback.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ClientListResponseModel> call, Throwable t) {
                Timber.e(t.getMessage());
                mCallback.onFailure(null);
            }
        });

    }

    public void editCustomer(ClientModel clientModel, APICallback<Boolean> mCallback) {
        Call<Void> call = ApiClient.getApiClient().putCustomer(clientModel);
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


    public void getNextAppointments(NextAppointmentGetModel customerId, APICallback<NextAppointmentsModel> mCallback) {
        Call<NextAppointmentsModel> call = ApiClient.getApiClient().postGetNextAppointment(customerId);
        call.enqueue(new Callback<NextAppointmentsModel>() {
            @Override
            public void onResponse(Call<NextAppointmentsModel> call, Response<NextAppointmentsModel> response) {
                Timber.e(response.toString());
                if (response.isSuccessful()) {
                    mCallback.onSuccess(response.body());
                } else {
                    try {
                        assert response.errorBody() != null;
                        mCallback.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<NextAppointmentsModel> call, Throwable t) {
                Timber.e(t.getMessage());
                mCallback.onFailure(null);
            }
        });
    }


    public void deleteCustomer(NextAppointmentGetModel customerId, APICallback<Boolean> mCallback) {
        Call<Void> call = ApiClient.getApiClient().deleteCustomer(customerId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Timber.e(response.toString());
                if (response.isSuccessful()) {
                    mCallback.onSuccess(true);
                } else {
                    try {
                        assert response.errorBody() != null;
                        mCallback.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, Throwable t) {
                Timber.e(t.getMessage());
                mCallback.onFailure(null);
            }
        });
    }
}
