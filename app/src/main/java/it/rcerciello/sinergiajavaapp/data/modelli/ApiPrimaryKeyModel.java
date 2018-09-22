package it.rcerciello.sinergiajavaapp.data.modelli;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ApiPrimaryKeyModel extends RealmObject {
    @SerializedName("$oid")
    String primaryKey;

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Override
    public String toString() {
        return "ApiPrimaryKeyModel{" +
                "primaryKey='" + primaryKey + '\'' +
                '}';
    }
}
