package it.rcerciello.sinergiajavaapp.data.modelli;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

import io.realm.RealmObject;

/**
 * Created by rcerciello on 28/04/2018.
 */

public class ClientModelAdd extends RealmObject{


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



    public ClientModelAdd() {
    }

    public ClientModelAdd(@NonNull String clientIdentifier, String name, String surname, String address, String landlinePhone, String mobile_phone, String email) {
        this.name = name;
        this.clientIdentifier = clientIdentifier;
        this.surname = surname;
        this.address = address;
        this.landlinePhone = landlinePhone;
        this.mobile_phone = mobile_phone;
        this.email = email;
    }


    @Override
    public String toString() {
        return "ClientModelAdd{" +
                " clientIdentifier='" + clientIdentifier + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", landlinePhone='" + landlinePhone + '\'' +
                ", mobile_phone='" + mobile_phone + '\'' +
                ", email='" + email + '\'' +
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


    @NonNull
    public String getClientIdentifier() {
        return clientIdentifier;
    }

    public void setClientIdentifier(@NonNull String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }
}