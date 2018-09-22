package it.rcerciello.sinergiajavaapp.data.network;


import java.util.List;

import it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.AppointmentEvent;
import it.rcerciello.sinergiajavaapp.data.managers.NameModel;
import it.rcerciello.sinergiajavaapp.data.managers.ServiceModelResponse;
import it.rcerciello.sinergiajavaapp.data.managers.TestModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientListResponseModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModelAdd;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.weekLibrary.weekview.WeekViewEvent;
import it.rcerciello.weekLibrary.weekview.WeekViewEventResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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


    @PUT("appointments/update")
    Call<Boolean> editAppointment(@Body WeekViewEvent event);

    @DELETE("appointments/delete")
    Call<Boolean> deleteAppointment(@Body String appointmentId);

    @POST("appointments/add")
    Call<Void> addAppointment(@Body WeekViewEvent event);

    @GET("appointments/list")
    Call<WeekViewEventResponse> getAllAppointments();

    @POST("customers/add")
    Call<Void> addClient(@Body ClientModel clientToAdd);

    @GET("customers/list")
    Call<ClientListResponseModel> getClientModel();

    @POST("services/add")
    Call<Void> addService(@Body  ServiceModel model);

    @GET("services/list")
    Call<ServiceModelResponse> getServices();

    @PUT("services/update")
    Call<Void> putService(ServiceModel serviceModel);

    @PUT("customers/update")
    Call<Void> putCustomer(@Body ClientModelAdd clientModel);
}

