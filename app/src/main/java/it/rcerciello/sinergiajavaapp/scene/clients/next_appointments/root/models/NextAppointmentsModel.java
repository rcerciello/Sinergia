package it.rcerciello.sinergiajavaapp.scene.clients.next_appointments.root.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import it.rcerciello.weekLibrary.weekview.WeekViewEvent;

public class NextAppointmentsModel {

    @SerializedName("result")
    List<WeekViewEvent> nextAppointmentsList;

    public List<WeekViewEvent> getEventList() {
        return nextAppointmentsList;
    }


}
