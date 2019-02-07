package it.rcerciello.weekLibrary.weekview;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static it.rcerciello.weekLibrary.weekview.WeekViewUtil.isSameDay;

public class WeekViewEvent {
    @SerializedName("id_appointment")
    private String id_appointment;

    @SerializedName("mStartTime")
    private Calendar mStartTime;

    @SerializedName("mEndTime")
    private Calendar mEndTime;

    private String appointmentName;

    private String mLocation;

    @SerializedName("id_customer")
    private String id_customer;

    @SerializedName("customer_name")
    private String customer_name;

    @SerializedName("customer_surname")
    private String customer_surname;

    @SerializedName("id_staff")
    private List<String> id_staff;
    private int mColor;

    @SerializedName("id_service")
    private List<String> id_service;

    private boolean mAllDay;

    public WeekViewEvent() {

    }

    /**
     * Initializes the event for week view.
     *
     * @param id_appointment  The id of the event.
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
    public WeekViewEvent(String id_appointment, String appointmentName, List<String> id_staff, List<String> id_service, String customer_name, String customer_surname, int startYear, int startMonth, int startDay, int startHour, int startMinute, int endYear, int endMonth, int endDay, int endHour, int endMinute) {
        this.id_appointment = id_appointment;
        this.id_staff = id_staff;
        this.id_service = id_service;
        this.customer_name = customer_name;
        this.customer_surname = customer_surname;

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
     * @param idAppuntments        The id of the event.
     * @param appointmentName      Name of the event.
     * @param location  The location of the event.
     * @param startTime The time when the event starts.
     * @param endTime   The time when the event ends.
     * @param allDay    Is the event an all day event.
     */
    public WeekViewEvent(String idAppuntments, String appointmentName, List<String> id_staff, List<String> id_service, String customer_name, String customer_surname, String location, Calendar startTime, Calendar endTime, boolean allDay, String cliente) {
        this.id_appointment = idAppuntments;
        this.id_staff = id_staff;
        this.appointmentName = appointmentName;
        this.id_service = id_service;
        this.mLocation = location;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mAllDay = allDay;
        this.id_customer = cliente;
        this.customer_name = customer_name;
        this.customer_surname = customer_surname;
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
    public WeekViewEvent(String idAppointment, String appointmentName, List<String> id_staff, List<String> id_service, String customer_name, String customer_surname, String location, Calendar startTime, Calendar endTime, String cliente) {
        this(idAppointment, appointmentName, id_staff, id_service,customer_name, customer_surname, location, startTime, endTime, false, cliente);
    }

    /**
     * Initializes the event for week view.
     *
     * @param idAppointment        The id of the event.
     * @param appointmentName      Name of the event.
     * @param startTime The time when the event starts.
     * @param endTime   The time when the event ends.
     */
    public WeekViewEvent(String idAppointment, String appointmentName, List<String> id_staff,  List<String>  id_service, String customer_name, String customer_surname, Calendar startTime, Calendar endTime, String cliente) {
        this(idAppointment, appointmentName, id_staff, id_service,customer_name, customer_surname,  null, startTime, endTime, cliente);
    }


    public String getCustomer_surname() {
        return customer_surname;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public Calendar getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Calendar startTime) {
        this.mStartTime = startTime;
    }

     Calendar getEndTime() {
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

     String getLocation() {
        return mLocation;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

     boolean isAllDay() {
        return mAllDay;
    }

    public String getAppointmentId() {
        return id_appointment;
    }

    public void setAppointmentId(String id) {
        this.id_appointment = id;
    }

    public String getIdCliente() {
        return id_customer;
    }

    public void setIdCliente(String idCliente) {
        this.id_customer = idCliente;
    }

    public  List<String>  getId_service() {
        return id_service;
    }

    public void setId_service( List<String>  id_service) {
        this.id_service = id_service;
    }

    public String getAppointmentName() {
        return appointmentName;
    }

    public void setAppointmentName(String appointmentName) {
        this.appointmentName = appointmentName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeekViewEvent that = (WeekViewEvent) o;
        return id_appointment.equalsIgnoreCase(that.id_appointment);
    }

    public List<String> getId_staff() {
        return id_staff;
    }

    public void setId_staff(List<String> id_staff) {
        this.id_staff = id_staff;
    }

    List<WeekViewEvent> splitWeekViewEvents() {
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        Calendar endTime = (Calendar) this.getEndTime().clone();
        endTime.add(Calendar.MILLISECOND, -1);
        if (!isSameDay(this.getStartTime(), endTime)) {
            endTime = (Calendar) this.getStartTime().clone();
            endTime.set(Calendar.HOUR_OF_DAY, 23);
            endTime.set(Calendar.MINUTE, 59);
            WeekViewEvent event1 = new WeekViewEvent(this.getAppointmentId(), this.getName(),this.id_staff,  this.id_service, this.customer_name, this. customer_surname,  this.getLocation(), this.getStartTime(), endTime, this.isAllDay(), this.getIdCliente());
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
                WeekViewEvent eventMore = new WeekViewEvent(this.getAppointmentId(), this.getName(),this.id_staff,  this.id_service, this.customer_name, this.customer_surname, null, overDay, endOfOverDay, this.isAllDay(), this.getIdCliente());
                eventMore.setColor(this.getColor());
                events.add(eventMore);

                // Add next day.
                otherDay.add(Calendar.DATE, 1);
            }

            // Add last day.
            Calendar startTime = (Calendar) this.getEndTime().clone();
            startTime.set(Calendar.HOUR_OF_DAY, 0);
            startTime.set(Calendar.MINUTE, 0);
            WeekViewEvent event2 = new WeekViewEvent(this.getAppointmentId(), this.getName(), this.id_staff, this.id_service, this.customer_name, this.customer_surname, this.getLocation(), startTime, this.getEndTime(), this.isAllDay(), this.getIdCliente());
            event2.setColor(this.getColor());
            events.add(event2);
        } else {
            events.add(this);
        }

        return events;
    }

    @Override
    public String toString() {
        return "WeekViewEvent{" +
                "id_appointment='" + id_appointment + '\'' +
                ", mStartTime=" + mStartTime +
                ", mEndTime=" + mEndTime +
                ", appointmentName='" + appointmentName + '\'' +
                ", mLocation='" + mLocation + '\'' +
                ", id_customer='" + id_customer + '\'' +
                ", customer_name='" + customer_name + '\'' +
                ", customer_surname='" + customer_surname + '\'' +
                ", id_staff=" + id_staff +
                ", mColor=" + mColor +
                ", id_service=" + id_service +
                ", mAllDay=" + mAllDay +
                '}';
    }
}
