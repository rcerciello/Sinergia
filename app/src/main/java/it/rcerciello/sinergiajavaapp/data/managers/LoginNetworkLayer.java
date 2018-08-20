package it.rcerciello.sinergiajavaapp.data.managers;

import java.util.ArrayList;

import it.rcerciello.sinergiajavaapp.data.modelli.EmployeeModel;
import it.rcerciello.sinergiajavaapp.data.modelli.LoginModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class LoginNetworkLayer {
    private static final LoginNetworkLayer ourInstance = new LoginNetworkLayer();

    public static LoginNetworkLayer getInstance() {
        return ourInstance;
    }

    private LoginNetworkLayer() {
    }

    public  void doLogin( LoginModel model, APICallback<Boolean> mCallback)
    {
        mCallback.onSuccess(true);
    }

    public  void doRegistration(EmployeeModel model, APICallback<Boolean> mCallback)
    {
        mCallback.onSuccess(true);
    }
}
