package it.rcerciello.sinergiajavaapp.data.managers;

/**
 * Created by rcerciello on 02/05/2018.
 */

public class CalendarNetworkLayer {
    private static final CalendarNetworkLayer ourInstance = new CalendarNetworkLayer();

    public static CalendarNetworkLayer getInstance() {
        return ourInstance;
    }

    private CalendarNetworkLayer() {
    }
}
