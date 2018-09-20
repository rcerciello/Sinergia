package it.rcerciello.sinergiajavaapp.data.managers;

import com.google.gson.annotations.SerializedName;

public class NameModel {
    @SerializedName("name")
    private String body;


    public NameModel(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
