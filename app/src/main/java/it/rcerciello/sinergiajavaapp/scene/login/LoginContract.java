package it.rcerciello.sinergiajavaapp.scene.login;

import it.rcerciello.sinergiajavaapp.data.modelli.LoginModel;
import it.rcerciello.sinergiajavaapp.scene.BasePresenter;
import it.rcerciello.sinergiajavaapp.scene.BaseView;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class LoginContract {
    interface View extends BaseView<Presenter> {

        void showSnackbarError(String message);

        void goToMainActivity();
    }


    interface Presenter extends BasePresenter {
        void doLogin(LoginModel model);
    }
}
