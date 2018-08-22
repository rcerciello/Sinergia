package it.rcerciello.sinergiajavaapp.data.network;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.List;

import it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.AppointmentEvent;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * All the API endpoints that we use across the app.
 * <p>
 */
public interface ApiInterfaces {
    /* =========================
     * Endpoints for the User
    ========================== */
//    @GET("userservice/users/current")
//    Call<UserPersonalInfoModelResponse> getCurrentUserInformations(@Header(GeneralConstants.AUTHORIZATION_BEARER) String authorization); //This api call don't have parameter in input
//
//    @PUT("userservice/users/current")
//    Call<AccessTokenModel> putCurrentUserInformations(@Header(GeneralConstants.AUTHORIZATION_BEARER) String authorization, @Body UserPersonalInfoModelResponse newModel); //This api call don't have parameter in input
//
//    @DELETE("userservice/users/current")
//    Call<Void> deleteCurrentUserInformations(@Header(GeneralConstants.AUTHORIZATION_BEARER) String authorization);


    @POST("")
    Call<Boolean> editAppointment(@Body AppointmentEvent event);

    @DELETE("userservice/users/current")
    Call<Boolean> deleteAppointment();


    @POST("")
    Call<Boolean> addAppointment(@Body AppointmentEvent event);

    @GET("user/bla")
    Call<List<WeekViewEvent>> getAllAppointments();

}

