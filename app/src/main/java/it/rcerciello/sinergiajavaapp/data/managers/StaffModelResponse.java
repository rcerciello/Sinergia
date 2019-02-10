package it.rcerciello.sinergiajavaapp.data.managers;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class StaffModelResponse extends RealmObject{

    @SerializedName("result")
    RealmList<StaffModel> services;

    public RealmList<StaffModel> getStaff() {
        return services;
    }
}
