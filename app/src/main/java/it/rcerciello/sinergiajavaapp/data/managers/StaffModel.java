package it.rcerciello.sinergiajavaapp.data.managers;

import com.google.gson.annotations.SerializedName;
import io.realm.RealmObject;
import it.rcerciello.sinergiajavaapp.data.modelli.ApiPrimaryKeyModel;

public class StaffModel extends RealmObject {

    @SerializedName("surname")
    String surname;

    @SerializedName("name")
    String name;

    @SerializedName("_id")
    ApiPrimaryKeyModel primaryKeyModel;

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public ApiPrimaryKeyModel getPrimaryKeyModel() {
        return primaryKeyModel;
    }
}
