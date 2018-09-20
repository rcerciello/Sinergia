package it.rcerciello.sinergiajavaapp.data.managers;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class EmployeeNetworkLayer {
    /**
     *
     */
    private static final EmployeeNetworkLayer ourInstance;

    static {
        ourInstance = new EmployeeNetworkLayer();
    }

    public static EmployeeNetworkLayer getInstance() {
        return ourInstance;
    }

    private EmployeeNetworkLayer() {
    }
}
