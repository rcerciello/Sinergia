package it.rcerciello.sinergiajavaapp.data.modelli;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

import io.realm.RealmObject;

/**
 * Created by rcerciello on 28/04/2018.
 */

public class ClientModel extends RealmObject{

    @SerializedName("id")
    String id;
    @NonNull
    @SerializedName("identifier")
    String clientIdentifier = "";

    @Nullable
    @SerializedName("name")
    String name = "";

    @Nullable
    @SerializedName("surname")
    String surname = "";

    @Nullable
    @SerializedName("address")
    String address = "";

    @Nullable
    @SerializedName("landline_phone")
    String landlinePhone ;

    @Nullable
    @SerializedName("mobile_phone")
    String mobile_phone;

    @Nullable
    @SerializedName("email")
    String email ;

    @SerializedName("_id")
    ApiPrimaryKeyModel primaryKeyModel = new ApiPrimaryKeyModel();



    public ClientModel() {
    }

    public ClientModel(@NonNull String id, @NonNull String clientIdentifier, String name, String surname, String address, String landlinePhone, String mobile_phone, String email) {
        this.id = id;
        this.name = name;
        this.clientIdentifier = clientIdentifier;
        this.surname = surname;
        this.address = address;
        this.landlinePhone = landlinePhone;
        this.mobile_phone = mobile_phone;
        this.email = email;
    }


    public ClientModel( @NonNull String clientIdentifier, String name, String surname, String address, String landlinePhone, String mobile_phone, String email) {
        this.name = name;
        this.clientIdentifier = clientIdentifier;
        this.surname = surname;
        this.address = address;
        this.landlinePhone = landlinePhone;
        this.mobile_phone = mobile_phone;
        this.email = email;
    }




    public ClientModel(String id, String name, String surname) {
        this.primaryKeyModel.setPrimaryKey(id);
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "ClientModel{" +
                "id='" + id + '\'' +
                ", clientIdentifier='" + clientIdentifier + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", landlinePhone='" + landlinePhone + '\'' +
                ", mobile_phone='" + mobile_phone + '\'' +
                ", email='" + email + '\'' +
                ", primaryKeyModel=" + primaryKeyModel +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandlinePhone() {
        return landlinePhone;
    }

    public void setLandlinePhone(String landlinePhone) {
        this.landlinePhone = landlinePhone;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public ApiPrimaryKeyModel getPrimaryKeyModel() {
        return primaryKeyModel;
    }

    public void setPrimaryKeyModel(ApiPrimaryKeyModel primaryKeyModel) {
        this.primaryKeyModel = primaryKeyModel;
    }

    @NonNull
    public String getClientIdentifier() {
        return clientIdentifier;
    }

    public void setClientIdentifier(@NonNull String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }
}