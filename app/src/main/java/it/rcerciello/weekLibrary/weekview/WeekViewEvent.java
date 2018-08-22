package it.rcerciello.weekLibrary.weekview;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static it.rcerciello.weekLibrary.weekview.WeekViewUtil.isSameDay;

/**
 * Created by Raquib-ul-Alam Kanak on 7/21/2014.
 * Website: http://april-shower.com
 */
public class WeekViewEvent {
    private long idAppuntamento;
    private Calendar mStartTime;
    private Calendar mEndTime;
    private String appointmentName;
    private String mLocation;
    private String idCliente;
    private String idCollaborator;
    private int mColor;
    private boolean mAllDay;

    public WeekViewEvent() {

    }

    /**
     * Initializes the event for week view.
     *
     * @param idAppuntamento  The id of the event.
     * @param appointmentName Name of the event.
     * @param startYear       Year when the event starts.
     * @param startMonth      Month when the event starts.
     * @param startDay        Day when the event starts.
     * @param startHour       Hour (in 24-hour format) when the event starts.
     * @param startMinute     Minute when the event starts.
     * @param endYear         Year when the event ends.
     * @param endMonth        Month when the event ends.
     * @param endDay          Day when the event ends.
     * @param endHour         Hour (in 24-hour format) when the event ends.
     * @param endMinute       Minute when the event ends.
     */
    public WeekViewEvent(long idAppuntamento, String appointmentName, String idCollaborator, int startYear, int startMonth, int startDay, int startHour, int startMinute, int endYear, int endMonth, int endDay, int endHour, int endMinute) {
        this.idAppuntamento = idAppuntamento;
        this.idCollaborator = idCollaborator;

        this.mStartTime = Calendar.getInstance();
        this.mStartTime.set(Calendar.YEAR, startYear);
        this.mStartTime.set(Calendar.MONTH, startMonth - 1);
        this.mStartTime.set(Calendar.DAY_OF_MONTH, startDay);
        this.mStartTime.set(Calendar.HOUR_OF_DAY, startHour);
        this.mStartTime.set(Calendar.MINUTE, startMinute);

        this.mEndTime = Calendar.getInstance();
        this.mEndTime.set(Calendar.YEAR, endYear);
        this.mEndTime.set(Calendar.MONTH, endMonth - 1);
        this.mEndTime.set(Calendar.DAY_OF_MONTH, endDay);
        this.mEndTime.set(Calendar.HOUR_OF_DAY, endHour);
        this.mEndTime.set(Calendar.MINUTE, endMinute);

        this.appointmentName = appointmentName;
    }

    /**
     * Initializes the event for week view.
     *
     * @param idAppuntamento        The id of the event.
     * @param appointmentName      Name of the event.
     * @param location  The location of the event.
     * @param startTime The time when the event starts.
     * @param endTime   The time when the event ends.
     * @param allDay    Is the event an all day event.
     */
    public WeekViewEvent(long idAppuntamento, String appointmentName,String idCollaborator, String location, Calendar startTime, Calendar endTime, boolean allDay, String cliente) {
        this.idAppuntamento = idAppuntamento;
        this.idCollaborator = idCollaborator;
        this.appointmentName = appointmentName;
        this.mLocation = location;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mAllDay = allDay;
        this.idCliente = cliente;
    }

    /**
     * Initializes the event for week view.
     *
     * @param idAppointment        The id of the event.
     * @param appointmentName      Name of the event.
     * @param location  The location of the event.
     * @param startTime The time when the event starts.
     * @param endTime   The time when the event ends.
     */
    public WeekViewEvent(long idAppointment, String appointmentName, String idCollaborator, String location, Calendar startTime, Calendar endTime, String cliente) {
        this(idAppointment, appointmentName, idCollaborator, location, startTime, endTime, false, cliente);
    }

    /**
     * Initializes the event for week view.
     *
     * @param idAppointment        The id of the event.
     * @param appointmentName      Name of the event.
     * @param startTime The time when the event starts.
     * @param endTime   The time when the event ends.
     */
    public WeekViewEvent(long idAppointment, String appointmentName, String idCollaborator,Calendar startTime, Calendar endTime, String cliente) {
        this(idAppointment, appointmentName,idCollaborator, null, startTime, endTime, cliente);
    }


    public Calendar getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Calendar startTime) {
        this.mStartTime = startTime;
    }

    public Calendar getEndTime() {
        return mEndTime;
    }

    public void setEndTime(Calendar endTime) {
        this.mEndTime = endTime;
    }

    public String getName() {
        return appointmentName;
    }

    public void setName(String name) {
        this.appointmentName = name;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        this.mLocation = location;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

    public boolean isAllDay() {
        return mAllDay;
    }

    public void setAllDay(boolean allDay) {
        this.mAllDay = allDay;
    }

    public long getId() {
        return idAppuntamento;
    }

    public void setId(long id) {
        this.idAppuntamento = id;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeekViewEvent that = (WeekViewEvent) o;
        return idAppuntamento == that.idAppuntamento;
    }

    public String getIdCollaborator() {
        return idCollaborator;
    }

    public void setIdCollaborator(String idCollaborator) {
        this.idCollaborator = idCollaborator;
    }

    @Override
    public int hashCode() {
        return (int) (idAppuntamento ^ (idAppuntamento >>> 32));
    }

    public List<WeekViewEvent> splitWeekViewEvents() {
        //This function splits the WeekViewEvent in WeekViewEvents by day
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        // The first millisecond of the next day is still the same day. (no need to split events for this).
        Calendar endTime = (Calendar) this.getEndTime().clone();
        endTime.add(Calendar.MILLISECOND, -1);
        if (!isSameDay(this.getStartTime(), endTime)) {
            endTime = (Calendar) this.getStartTime().clone();
            endTime.set(Calendar.HOUR_OF_DAY, 23);
            endTime.set(Calendar.MINUTE, 59);
            WeekViewEvent event1 = new WeekViewEvent(this.getId(), this.getName(),this.idCollaborator,  this.getLocation(), this.getStartTime(), endTime, this.isAllDay(), this.getIdCliente());
            event1.setColor(this.getColor());
            events.add(event1);

            // Add other days.
            Calendar otherDay = (Calendar) this.getStartTime().clone();
            otherDay.add(Calendar.DATE, 1);
            while (!isSameDay(otherDay, this.getEndTime())) {
                Calendar overDay = (Calendar) otherDay.clone();
                overDay.set(Calendar.HOUR_OF_DAY, 0);
                overDay.set(Calendar.MINUTE, 0);
                Calendar endOfOverDay = (Calendar) overDay.clone();
                endOfOverDay.set(Calendar.HOUR_OF_DAY, 23);
                endOfOverDay.set(Calendar.MINUTE, 59);
                WeekViewEvent eventMore = new WeekViewEvent(this.getId(), this.getName(),this.idCollaborator, null, overDay, endOfOverDay, this.isAllDay(), this.getIdCliente());
                eventMore.setColor(this.getColor());
                events.add(eventMore);

                // Add next day.
                otherDay.add(Calendar.DATE, 1);
            }

            // Add last day.
            Calendar startTime = (Calendar) this.getEndTime().clone();
            startTime.set(Calendar.HOUR_OF_DAY, 0);
            startTime.set(Calendar.MINUTE, 0);
            WeekViewEvent event2 = new WeekViewEvent(this.getId(), this.getName(), this.idCollaborator,this.getLocation(), startTime, this.getEndTime(), this.isAllDay(), this.getIdCliente());
            event2.setColor(this.getColor());
            events.add(event2);
        } else {
            events.add(this);
        }

        return events;
    }
}
