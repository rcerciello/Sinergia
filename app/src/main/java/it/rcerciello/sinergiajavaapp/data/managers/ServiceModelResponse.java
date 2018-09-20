package it.rcerciello.sinergiajavaapp.data.managers;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;

public class ServiceModelResponse {

    @SerializedName("response")
    ArrayList<ServiceModel> services;

    public ArrayList<ServiceModel> getServices() {
        return services;
    }
}
