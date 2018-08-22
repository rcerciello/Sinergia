package it.rcerciello.sinergiajavaapp.scene.registrazione;

import android.support.annotation.NonNull;

import it.rcerciello.sinergiajavaapp.data.managers.LoginNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.modelli.EmployeeModel;
import it.rcerciello.sinergiajavaapp.data.modelli.LoginModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class RegistrationPresenter implements RegistrazioneContract.Presenter {
    private RegistrazioneContract.View mView;

    public RegistrationPresenter(@NonNull RegistrazioneContract.View view) {
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
    public void doRegistration(final EmployeeModel model) {
        LoginNetworkLayer.getInstance().doRegistration(model, new APICallback<Boolean>() {
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

            @Override
            public void onFailure(boolean isFailure) {

            }
        });
    }
}
