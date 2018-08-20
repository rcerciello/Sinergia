package it.rcerciello.sinergiajavaapp.data.modelli;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rcerciello on 28/04/2018.
 */

public class EmployeeModel {

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


    @SerializedName("role")
    String role = "";

    public EmployeeModel() {
    }

    public EmployeeModel(String name, String surname, String address, String landlinePhone, String mobile_phone, String email, String role) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.landlinePhone = landlinePhone;
        this.mobile_phone = mobile_phone;
        this.email = email;
        this.role = role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String
    toString() {
        return "EmployeeModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", landlinePhone='" + landlinePhone + '\'' +
                ", mobile_phone='" + mobile_phone + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}