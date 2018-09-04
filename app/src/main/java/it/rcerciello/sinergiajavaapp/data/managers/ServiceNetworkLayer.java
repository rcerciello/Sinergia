package it.rcerciello.sinergiajavaapp.data.managers;

import java.util.ArrayList;

import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class ServiceNetworkLayer {
    private static final ServiceNetworkLayer ourInstance = new ServiceNetworkLayer();

    public static ServiceNetworkLayer getInstance() {
        return ourInstance;
    }

    private ServiceNetworkLayer() {
    }

    public static void getServices( APICallback<ArrayList<ServiceModel>> mCallback)
    {
        mCallback.onSuccess(new ArrayList<>());
    }

    public void addService(ServiceModel model, APICallback<Boolean> mCallback)
    {
        mCallback.onSuccess(true);
    }
}
