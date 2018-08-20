package it.rcerciello.sinergiajavaapp.data.modelli;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rcerciello on 28/04/2018.
 */

public class LoginModel {


    @SerializedName("email")
    String name = "";

    @SerializedName("password")
    String surname = "";

    public LoginModel(String name, String surname) {
        this.name = name;
        this.surname = surname;
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

    @Override
    public String toString() {
        return "LoginModel{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}