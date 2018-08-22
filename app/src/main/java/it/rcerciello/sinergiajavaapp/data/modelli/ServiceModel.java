package it.rcerciello.sinergiajavaapp.data.modelli;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by rcerciello on 28/04/2018.
 */

public class ServiceModel extends RealmObject {
    @SerializedName("id")
    String id = "";

    @SerializedName("identifier")
    String serviceIdentifier = "";

    @SerializedName("name")
    String name ="";

    @SerializedName("duration")
    int duration;

    @SerializedName("price")
    float price ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getServiceIdentifier() {
        return serviceIdentifier;
    }

    public void setServiceIdentifier(String serviceIdentifier) {
        this.serviceIdentifier = serviceIdentifier;
    }

    public ServiceModel(String id, String serviceIdentifier, String name, int duration, float price) {
        this.id = id;
        this.serviceIdentifier = serviceIdentifier;
        this.name = name;
        this.duration = duration;
        this.price = price;
    }

    public ServiceModel()
    {

    }
}
