package it.rcerciello.sinergiajavaapp.scene.registrazione;

import it.rcerciello.sinergiajavaapp.data.modelli.EmployeeModel;
import it.rcerciello.sinergiajavaapp.data.modelli.LoginModel;
import it.rcerciello.sinergiajavaapp.scene.BasePresenter;
import it.rcerciello.sinergiajavaapp.scene.BaseView;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class RegistrazioneContract {
    interface View extends BaseView<Presenter> {

        void showSnackbarError(String message);

        void goToMainActivity();
    }


    interface Presenter extends BasePresenter {
        void doRegistration(EmployeeModel model);
    }
}
