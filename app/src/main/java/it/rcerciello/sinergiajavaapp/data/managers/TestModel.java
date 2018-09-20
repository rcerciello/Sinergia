package it.rcerciello.sinergiajavaapp.data.managers;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.http.Headers;

public class TestModel {

//    @SerializedName("headers")
//    private String headers;
    @SerializedName("body")
    private String body;
    @SerializedName("statusCode")
    private String statusCode;



    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }


    @Override
    public String toString() {
        return "TestModel{" +
                "body='" + body + '\'' +
                ", statusCode='" + statusCode + '\'' +
                '}';
    }
}

