package it.rcerciello.sinergiajavaapp.scene.profile;

import it.rcerciello.sinergiajavaapp.scene.BasePresenter;
import it.rcerciello.sinergiajavaapp.scene.BaseView;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class ProfileContract {
    interface View extends BaseView<Presenter> {

        void showSnackbarError(String message);

    }


    interface Presenter extends BasePresenter {
    }
}
