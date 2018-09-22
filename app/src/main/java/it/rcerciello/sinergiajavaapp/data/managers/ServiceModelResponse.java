package it.rcerciello.sinergiajavaapp.data.managers;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;

public class ServiceModelResponse extends RealmObject{

    @SerializedName("result")
    RealmList<ServiceModel> services;

    public RealmList<ServiceModel> getServices() {
        return services;
    }
}
