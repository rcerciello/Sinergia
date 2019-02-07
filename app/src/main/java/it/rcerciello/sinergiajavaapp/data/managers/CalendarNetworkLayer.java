package it.rcerciello.sinergiajavaapp.data.managers;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.AppointmentEvent;
import it.rcerciello.sinergiajavaapp.data.modelli.AppintmentDeleteModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;
import it.rcerciello.sinergiajavaapp.data.network.ApiClient;
import it.rcerciello.sinergiajavaapp.utils.GeneralConstants;
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
        Timber.e("detelet id => "+appointmentId);
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
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                //200.300
                if (response.isSuccessful()) {
                    mCallback.onSuccess(true);
                } else {
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
                        mCallback.onFailure(true);
                    }
            }

            @Override
            public void onFailure(@NonNull Call<WeekViewEventResponse> call, @NonNull Throwable t) {
                Timber.e(t.getMessage());
                mCallback.onFailure(true);
            }
        });

//        /* ** FAKE  ** */
        // Populate the week view with some events.
//        List<WeekViewEvent> allAppointments = new ArrayList<WeekViewEvent>();
//
//        int newMonth = 10;
//        int newYear = 2018;
//
//        Calendar startTime = Calendar.getInstance();
//        startTime.set(Calendar.HOUR_OF_DAY, 3);
//        startTime.set(Calendar.MINUTE, 0);
//        startTime.set(Calendar.MONTH, newMonth - 1);
//        startTime.set(Calendar.YEAR, newYear);
//        Calendar endTime = (Calendar) startTime.clone();
//        endTime.add(Calendar.HOUR, 1);
//        endTime.set(Calendar.MONTH, newMonth - 1);
//        WeekViewEvent event = new WeekViewEvent("1", GeneralConstants.ID_ANNA, "Cerciello Raffaella", GeneralConstants.ID_LELLA, "Ceretta", startTime, endTime, "ciao");
//        allAppointments.add(event);
//
//
//        startTime = Calendar.getInstance();
//        startTime.set(Calendar.HOUR_OF_DAY, 3);
//        startTime.set(Calendar.MINUTE, 30);
//        startTime.set(Calendar.MONTH, newMonth - 1);
//        startTime.set(Calendar.YEAR, newYear);
//        endTime = (Calendar) startTime.clone();
//        endTime.set(Calendar.HOUR_OF_DAY, 4);
//        endTime.set(Calendar.MINUTE, 30);
//        endTime.set(Calendar.MONTH, newMonth - 1);
//        event = new WeekViewEvent("1", GeneralConstants.ID_ANNA,GeneralConstants.ID_ANNA, GeneralConstants.ID_MARIA, "Sopracciglia", startTime, endTime, "ciao");
//        allAppointments.add(event);
//
//        startTime = Calendar.getInstance();
//        startTime.set(Calendar.HOUR_OF_DAY, 4);
//        startTime.set(Calendar.MINUTE, 20);
//        startTime.set(Calendar.MONTH, newMonth - 1);
//        startTime.set(Calendar.YEAR, newYear);
//        endTime = (Calendar) startTime.clone();
//        endTime.set(Calendar.HOUR_OF_DAY, 5);
//        endTime.set(Calendar.MINUTE, 0);
//        event =  new WeekViewEvent("1", GeneralConstants.ID_ANNA, GeneralConstants.ID_ANNA, "Sopracciglia", startTime, endTime, "ciao");
//        allAppointments.add(event);
////
////        startTime = Calendar.getInstance();
//        startTime.set(Calendar.HOUR_OF_DAY, 5);
//        startTime.set(Calendar.MINUTE, 30);
//        startTime.set(Calendar.MONTH, newMonth - 1);
//        startTime.set(Calendar.YEAR, newYear);
//        endTime = (Calendar) startTime.clone();
//        endTime.add(Calendar.HOUR_OF_DAY, 2);
//        endTime.set(Calendar.MONTH, newMonth - 1);
//        event = new WeekViewEvent(2, getEventTitle(startTime), startTime, endTime, "ciao");
//        allAppointments.add(event);
//
//        startTime = Calendar.getInstance();
//        startTime.set(Calendar.HOUR_OF_DAY, 5);
//        startTime.set(Calendar.MINUTE, 0);
//        startTime.set(Calendar.MONTH, newMonth - 1);
//        startTime.set(Calendar.YEAR, newYear);
//        startTime.add(Calendar.DATE, 1);
//        endTime = (Calendar) startTime.clone();
//        endTime.add(Calendar.HOUR_OF_DAY, 3);
//        endTime.set(Calendar.MONTH, newMonth - 1);
//        event = new WeekViewEvent(3, getEventTitle(startTime), startTime, endTime, "ciao");
//        allAppointments.add(event);
//
//        startTime = Calendar.getInstance();
//        startTime.set(Calendar.DAY_OF_MONTH, 15);
//        startTime.set(Calendar.HOUR_OF_DAY, 3);
//        startTime.set(Calendar.MINUTE, 0);
//        startTime.set(Calendar.MONTH, newMonth - 1);
//        startTime.set(Calendar.YEAR, newYear);
//        endTime = (Calendar) startTime.clone();
//        endTime.add(Calendar.HOUR_OF_DAY, 3);
//        event = new WeekViewEvent(4, getEventTitle(startTime), startTime, endTime, "ciao");
//        allAppointments.add(event);
//
//        startTime = Calendar.getInstance();
//        startTime.set(Calendar.DAY_OF_MONTH, 1);
//        startTime.set(Calendar.HOUR_OF_DAY, 3);
//        startTime.set(Calendar.MINUTE, 0);
//        startTime.set(Calendar.MONTH, newMonth - 1);
//        startTime.set(Calendar.YEAR, newYear);
//        endTime = (Calendar) startTime.clone();
//        endTime.add(Calendar.HOUR_OF_DAY, 3);
//        event = new WeekViewEvent(5, getEventTitle(startTime), startTime, endTime, "ciao");
//        allAppointments.add(event);
//
//        startTime = Calendar.getInstance();
//        startTime.set(Calendar.DAY_OF_MONTH, startTime.getActualMaximum(Calendar.DAY_OF_MONTH));
//        startTime.set(Calendar.HOUR_OF_DAY, 15);
//        startTime.set(Calendar.MINUTE, 0);
//        startTime.set(Calendar.MONTH, newMonth - 1);
//        startTime.set(Calendar.YEAR, newYear);
//        endTime = (Calendar) startTime.clone();
//        endTime.add(Calendar.HOUR_OF_DAY, 3);
//        event = new WeekViewEvent(5, getEventTitle(startTime), startTime, endTime, "ciao");
//        allAppointments.add(event);
//
//        //AllDay event
//        startTime = Calendar.getInstance();
//        startTime.set(Calendar.HOUR_OF_DAY, 0);
//        startTime.set(Calendar.MINUTE, 0);
//        startTime.set(Calendar.MONTH, newMonth - 1);
//        startTime.set(Calendar.YEAR, newYear);
//        endTime = (Calendar) startTime.clone();
//        endTime.add(Calendar.HOUR_OF_DAY, 23);
//        event = new WeekViewEvent(7, getEventTitle(startTime), null, startTime, endTime, "ciao");
//        allAppointments.add(event);
//
//        startTime = Calendar.getInstance();
//        startTime.set(Calendar.DAY_OF_MONTH, 8);
//        startTime.set(Calendar.HOUR_OF_DAY, 2);
//        startTime.set(Calendar.MINUTE, 0);
//        startTime.set(Calendar.MONTH, newMonth - 1);
//        startTime.set(Calendar.YEAR, newYear);
//        endTime = (Calendar) startTime.clone();
//        endTime.set(Calendar.DAY_OF_MONTH, 10);
//        endTime.set(Calendar.HOUR_OF_DAY, 23);
//        event = new WeekViewEvent(8, getEventTitle(startTime), null, startTime, endTime, "ciao");
//        allAppointments.add(event);
//
//        // All day event until 00:00 next day
//        startTime = Calendar.getInstance();
//        startTime.set(Calendar.DAY_OF_MONTH, 10);
//        startTime.set(Calendar.HOUR_OF_DAY, 0);
//        startTime.set(Calendar.MINUTE, 0);
//        startTime.set(Calendar.SECOND, 0);
//        startTime.set(Calendar.MILLISECOND, 0);
//        startTime.set(Calendar.MONTH, newMonth - 1);
//        startTime.set(Calendar.YEAR, newYear);
//        endTime = (Calendar) startTime.clone();
//        endTime.set(Calendar.DAY_OF_MONTH, 11);
//        event = new WeekViewEvent(8, getEventTitle(startTime), null, startTime, endTime, "ciao");
//        allAppointments.add(event);
        /* *********** */
//        mCallback.onSuccess(allAppointments);
    }

    protected String getEventTitle(Calendar time) {
        Timber.e(String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH)));
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
    }


}
