package it.rcerciello.sinergiajavaapp.data.managers;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class ProfileNetworkLayer {
    private static final ProfileNetworkLayer ourInstance = new ProfileNetworkLayer();

    public static ProfileNetworkLayer getInstance() {
        return ourInstance;
    }

    private ProfileNetworkLayer() {
    }
}
