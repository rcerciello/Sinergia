package it.rcerciello.sinergiajavaapp.data.managers;

import java.util.ArrayList;

import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class ClientNetworkLayer {
    private static final ClientNetworkLayer ourInstance = new ClientNetworkLayer();

    public static ClientNetworkLayer getInstance() {
        return ourInstance;
    }

    private ClientNetworkLayer() {
    }

    public void addClient(ClientModel model, APICallback<Boolean> mCallback)
    {
        mCallback.onSuccess(true);
    }

    public void getClients( APICallback<ArrayList<ClientModel>> mCallback)
    {
        mCallback.onSuccess(new ArrayList<ClientModel>());
    }
}
