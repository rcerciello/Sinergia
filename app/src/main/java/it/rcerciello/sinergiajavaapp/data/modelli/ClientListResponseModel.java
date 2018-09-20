package it.rcerciello.sinergiajavaapp.data.modelli;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ClientListResponseModel {

    @SerializedName("result")
    ArrayList<ClientModel> clients = new ArrayList<>();

    public ArrayList<ClientModel> getClients() {
        return clients;
    }

    public void setClients(ArrayList<ClientModel> clients) {
        this.clients = clients;
    }
}
