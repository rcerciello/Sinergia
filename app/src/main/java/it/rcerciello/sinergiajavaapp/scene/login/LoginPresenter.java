package it.rcerciello.sinergiajavaapp.scene.login;

import android.support.annotation.NonNull;

import it.rcerciello.sinergiajavaapp.data.managers.LoginNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.modelli.LoginModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;

    public LoginPresenter(@NonNull LoginContract.View view) {
        this.mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void clearDB() {

    }

    @Override
    public void clearPreferences() {

    }

    @Override
    public void doLogin(final LoginModel model) {
        LoginNetworkLayer.getInstance().doLogin(model, new APICallback<Boolean>() {
            @Override
            public void onSuccess(Boolean object) {
                if (object) {
                    mView.goToMainActivity();
                }
            }

            @Override
            public void onFailure(String error) {

            }

            @Override
            public void onSessionExpired() {

            }
        });
    }
}
