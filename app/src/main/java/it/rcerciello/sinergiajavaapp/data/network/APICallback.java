
package it.rcerciello.sinergiajavaapp.data.network;


public interface APICallback<T> {
    void onSuccess(T object);

    void onFailure(String error);

    void onSessionExpired();

    void onFailure(boolean isFailure);
}





