package it.rcerciello.sinergiajavaapp.data.managers;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;
import it.rcerciello.sinergiajavaapp.data.modelli.AppintmentDeleteModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;
import it.rcerciello.sinergiajavaapp.data.network.ApiClient;
import it.rcerciello.weekLibrary.weekview.WeekViewEvent;
import it.rcerciello.weekLibrary.weekview.WeekViewEventResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class CalendarNetworkLayer {
    private static final CalendarNetworkLayer ourInstance = new CalendarNetworkLayer();

    public static CalendarNetworkLayer getInstance() {
        return ourInstance;
    }

    private CalendarNetworkLayer() {
    }


    public void editAppointment(WeekViewEvent event, final APICallback<Boolean> mCallback) {
        Call<Void> call = ApiClient.getApiClient().editAppointment(event);
        Timber.d("Evento da modificare => "+event.toString());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                //200.300
                if (response.isSuccessful()) {
                    mCallback.onSuccess(true);
                } else {
                    try {
                        Timber.e("Edit => "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mCallback.onFailure(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Timber.e("EDIT => "+t.getMessage());
                mCallback.onFailure(true);
            }
        });
    }


    public void deleteAppointment(String appointmentId, final APICallback<Boolean> mCallback) {
        Call<Void> call = ApiClient.getApiClient().deleteAppointment(new AppintmentDeleteModel(appointmentId));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) { //200.300
                    mCallback.onSuccess(true);
                } else {
                    try {
                        Timber.e("detelet failure => "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mCallback.onFailure(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Timber.e(t.getMessage());
                mCallback.onFailure(true);
            }
        });

    }


    public void addAppointment(WeekViewEvent event, final APICallback<Boolean> mCallback) {
        Call<Void> call = ApiClient.getApiClient().addAppointment(event);
        Timber.i("Appontment => "+event.toString());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                //200.300
                if (response.isSuccessful()) {
                    Timber.i("ADD APPOINTMENT SUCCESS");
                    mCallback.onSuccess(true);
                } else {
                    Timber.i("ADD APPOINTMENT FAILURE "+response.errorBody());
                    mCallback.onFailure(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Timber.e(t.getMessage());
                Timber.i("ADD APPOINTMENT FAILURE ");
                mCallback.onFailure(true);
            }
        });
    }

    public void getAllAppointments(final APICallback<List<WeekViewEvent>> mCallback) {
        Call<WeekViewEventResponse> call = ApiClient.getApiClient().getAllAppointments();
        call.enqueue(new Callback<WeekViewEventResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeekViewEventResponse> call, @NonNull Response<WeekViewEventResponse> response) {
                if (response.isSuccessful())
                    if (response.body() != null && response.body().getEventList() != null) {
                        assert response.body() != null;
                        Timber.d("Appointments => " + response.body().getEventList());
                        mCallback.onSuccess(response.body().getEventList());
                    }
                    else {
                        Timber.d("Appointments Failure=> " + response.errorBody());
                        mCallback.onFailure(true);
                    }
            }

            @Override
            public void onFailure(@NonNull Call<WeekViewEventResponse> call, @NonNull Throwable t) {
                Timber.e(t.getMessage());
                mCallback.onFailure(true);
            }
        });
    }
}
