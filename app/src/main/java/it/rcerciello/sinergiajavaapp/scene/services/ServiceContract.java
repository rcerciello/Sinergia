package it.rcerciello.sinergiajavaapp.scene.services;

import it.rcerciello.sinergiajavaapp.scene.BasePresenter;
import it.rcerciello.sinergiajavaapp.scene.BaseView;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class ServiceContract {
    interface View extends BaseView<Presenter> {

        void showSnackbarError(String message);

    }


    interface Presenter extends BasePresenter {
    }
}
