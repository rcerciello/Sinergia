package it.rcerciello.weekLibrary.weekview;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static it.rcerciello.weekLibrary.weekview.WeekViewUtil.isSameDay;

/**
 * Created by Raquib-ul-Alam Kanak on 7/21/2014.
 * Website: http://april-shower.com
 */
public class WeekViewEventResponse {
    @SerializedName("result")
    List<WeekViewEvent> eventList;

    public List<WeekViewEvent> getEventList() {
        return eventList;
    }

    public void setEventList(List<WeekViewEvent> eventList) {
        this.eventList = eventList;
    }
}
