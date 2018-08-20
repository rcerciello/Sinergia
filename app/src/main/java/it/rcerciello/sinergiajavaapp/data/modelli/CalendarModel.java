package it.rcerciello.sinergiajavaapp.data.modelli;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rcerciello on 28/04/2018.
 */

public class CalendarModel {

    @SerializedName("id")
    String id = "";

    @SerializedName("employee_id")
    String employee_id = "";

    @SerializedName("client_id")
    String client_id = "";

    @SerializedName("service_id")
    String service_id = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    @Override
    public String toString() {
        return "CalendarModel{" +
                "id='" + id + '\'' +
                ", employee_id='" + employee_id + '\'' +
                ", client_id='" + client_id + '\'' +
                ", service_id='" + service_id + '\'' +
                '}';
    }
}
