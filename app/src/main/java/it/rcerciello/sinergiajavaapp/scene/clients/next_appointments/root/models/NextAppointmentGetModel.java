package it.rcerciello.sinergiajavaapp.scene.clients.next_appointments.root.models;

import com.google.gson.annotations.SerializedName;

public class NextAppointmentGetModel {

    @SerializedName("id")
    private String id;

    public NextAppointmentGetModel(String clientId) {
        this.id = clientId;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NextAppointmentGetModel{" +
                "id='" + id + '\'' +
                '}';
    }
}
