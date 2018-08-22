package it.rcerciello.sinergiajavaapp.data.modelli;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by rcerciello on 28/04/2018.
 */

public class ClientModel extends RealmObject{

    @SerializedName("id")
    String id = "";

    @SerializedName("name")
    String name = "";

    @SerializedName("surname")
    String surname = "";

    @SerializedName("address")
    String address = "";

    @SerializedName("landline_phone")
    String landlinePhone = "";

    @SerializedName("mobile_phone")
    String mobile_phone = "";

    @SerializedName("email")
    String email = "";


    @SerializedName("nextAppointment")
    String nextAppointment = "";

    public ClientModel() {
    }

    public ClientModel(String name, String surname, String address, String landlinePhone, String mobile_phone, String nextAppointment, String email) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.landlinePhone = landlinePhone;
        this.mobile_phone = mobile_phone;
        this.nextAppointment = nextAppointment;
        this.email = email;
    }


    public ClientModel(String id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "ClientModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", landlinePhone='" + landlinePhone + '\'' +
                ", mobile_phone='" + mobile_phone + '\'' +
                ", email='" + email + '\'' +
                ", nextAppointment='" + nextAppointment + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getNextAppointment() {
        return nextAppointment;
    }

    public void setNextAppointment(String nextAppointment) {
        this.nextAppointment = nextAppointment;
    }

}