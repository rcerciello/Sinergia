package it.rcerciello.sinergiajavaapp.data.modelli;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class ClientListResponseModel extends RealmObject {

    @SerializedName("result")
    RealmList<ClientModel> result ;

    public RealmList<ClientModel> getClients() {
        return result;
    }

    public void setClients(RealmList<ClientModel> clients) {
        this.result = clients;
    }
}
