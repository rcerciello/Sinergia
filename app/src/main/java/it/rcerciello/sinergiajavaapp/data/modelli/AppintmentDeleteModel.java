package it.rcerciello.sinergiajavaapp.data.modelli;

import com.google.gson.annotations.SerializedName;

public class AppintmentDeleteModel {
    @SerializedName("id_appointment")
    String id;

    public AppintmentDeleteModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
