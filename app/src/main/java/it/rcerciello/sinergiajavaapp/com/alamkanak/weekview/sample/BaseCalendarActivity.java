package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample;

import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.rcerciello.sinergiajavaapp.GlobalUtils;
import it.rcerciello.sinergiajavaapp.MainActivity;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.add_appointment.AddAppointmentActivity;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;
import it.rcerciello.sinergiajavaapp.data.network.ApiClient;
import it.rcerciello.sinergiajavaapp.data.repository.SinergiaRepo;
import it.rcerciello.sinergiajavaapp.utils.GeneralConstants;
import it.rcerciello.weekLibrary.weekview.DateTimeInterpreter;
import it.rcerciello.weekLibrary.weekview.MonthLoader;
import it.rcerciello.weekLibrary.weekview.WeekView;
import it.rcerciello.weekLibrary.weekview.WeekViewEvent;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * This is a base activity which contains week view and all the codes necessary to initialize the
 * week view.
 * Created by Raquib-ul-Alam Kanak on 1/3/2014.
 * Website: http://alamkanak.github.io
 */
public class BaseCalendarActivity extends AppCompatActivity implements BaseCalendarContract.View, WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;

    @BindView(R.id.weekView)
    WeekView mWeekViewLella;

    @BindView(R.id.weekViewTwo)
    WeekView mWeekViewAnna;

    @BindView(R.id.weekViewThree)
    WeekView mWeekViewMaria;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.root)
    RelativeLayout root;

    List<WeekViewEvent> allAppointmentsLella = new ArrayList<>();
    List<WeekViewEvent> allAppointmentsMaria = new ArrayList<>();
    List<WeekViewEvent> allAppointmentsAnna = new ArrayList<>();
    private BaseCalendarContract.Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        ButterKnife.bind(this);
        mPresenter = new BaseCalendarPresenter(this);
        mWeekViewLella.setCollaboratorId(GeneralConstants.ID_LELLA);
        mWeekViewAnna.setCollaboratorId(GeneralConstants.ID_ANNA);
        mWeekViewMaria.setCollaboratorId(GeneralConstants.ID_MARIA);

        setCalendaarWidgetListener();
        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);
    }


    private void setCalendaarWidgetListener() {
        // Show a toast message about the touched event.
        setEventListener(mWeekViewLella);
        setEventListener(mWeekViewAnna);
        setEventListener(mWeekViewMaria);
    }

    private void setEventListener(WeekView weekView) {
        weekView.setOnEventClickListener(this);
        weekView.setMonthChangeListener(this);
        weekView.setEventLongPressListener(this);
        weekView.setEmptyViewLongPressListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id) {
            case R.id.action_today:
                mWeekViewLella.goToToday();
                mWeekViewMaria.goToToday();
                mWeekViewAnna.goToToday();
                return true;
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    setDailyView(item);
                }
                return true;
            case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    setThreeDayView(item);
                }
                return true;
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    setWeekView(item);
                }
                return true;
            case R.id.action_details:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setThreeDayView(MenuItem item) {
        item.setChecked(!item.isChecked());
        mWeekViewType = TYPE_THREE_DAY_VIEW;
        setThreeDayStyle(mWeekViewLella);
        setThreeDayStyle(mWeekViewAnna);
        setThreeDayStyle(mWeekViewMaria);

    }

    private void setThreeDayStyle(WeekView weekView) {
        weekView.setNumberOfVisibleDays(3);
        // Lets change some dimensions to best fit the view.
        weekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
        weekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        weekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
    }


    private void setWeekView(MenuItem item) {

        item.setChecked(!item.isChecked());
        mWeekViewType = TYPE_WEEK_VIEW;
        setWeekStyle(mWeekViewLella);
        setWeekStyle(mWeekViewAnna);
        setWeekStyle(mWeekViewMaria);
    }


    private void setWeekStyle(WeekView weekView) {
        weekView.setNumberOfVisibleDays(5);
        // Lets change some dimensions to best fit the view.
        weekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        weekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8, getResources().getDisplayMetrics()));
        weekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
    }


    private void setDailyView(MenuItem item) {
        item.setChecked(!item.isChecked());
        mWeekViewType = TYPE_DAY_VIEW;

        setDailyStyle(mWeekViewLella);
        setDailyStyle(mWeekViewAnna);
        setDailyStyle(mWeekViewMaria);
    }

    private void setDailyStyle(WeekView weekView) {
        weekView.setNumberOfVisibleDays(1);

        // Lets change some dimensions to best fit the view.
        weekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
        weekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        weekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     *
     * @param shortDate True if the date values should be short.
     */
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekViewLella.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }


    /**
     * Listener per i giorni pieni
     *
     * @param event:     event clicked.
     * @param eventRect: view containing the clicked event.
     */
    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        setAlertDialogForAnEvent(event);
    }

    /**
     * Listener per i giorni vuoti
     *
     * @param event:     event clicked.
     * @param eventRect: view containing the clicked event.
     */
    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
//        setAlertDialogForAnEvent(event);
    }

    @Override
    public void onEmptyViewLongPress(String collaboratorId, Calendar time) {
        Intent i = new Intent(this, AddAppointmentActivity.class);
        i.putExtra("collaboratorID", collaboratorId);
        i.putExtra("time", time.getTimeInMillis());
        startActivity(i);
    }


    public WeekView getWeekView() {
        return mWeekViewLella;
    }


    private void setAlertDialogForAnEvent(final WeekViewEvent event) {
//        CharSequence options[] = new CharSequence[]{"Cancella Evento", "Modifica Evento"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_label_editor, null);
        builder.setView(dialogView);

        Button btnAnnull = dialogView.findViewById(R.id.btnAnnull);


        TextView title = dialogView.findViewById(R.id.title);
        title.setText(event.getAppointmentName().toUpperCase());


        Button deleteAppointment = dialogView.findViewById(R.id.deleteAppointment);


        Button updateAppointment = dialogView.findViewById(R.id.updateAppointment);
        updateAppointment.setOnClickListener(v ->
        {
            Intent i = new Intent(getApplicationContext(), AddAppointmentActivity.class);
            String data = ApiClient.getGson().toJson(event);
            i.putExtra("isEditable", true);
            i.putExtra("AppointmentModel", data);
            startActivity(i);
        });

        AlertDialog alertDialog = builder.create();
        btnAnnull.setOnClickListener(v -> alertDialog.dismiss());
        deleteAppointment.setOnClickListener(v ->
        {
            mPresenter.deleteAppointment(String.valueOf(event.getAppointmentId()), event);
            alertDialog.dismiss();

        });
        alertDialog.show();
    }


    @Override
    public List<? extends WeekViewEvent> onMonthChange(String collaboratorId, int newYear, int newMonth) {
        switch (collaboratorId) {
            case GeneralConstants.ID_LELLA:
                return allAppointmentsLella;
            case GeneralConstants.ID_MARIA:
                return allAppointmentsMaria;
            case GeneralConstants.ID_ANNA:
                return allAppointmentsAnna;
        }
        return new ArrayList<>();
    }

    @Override
    public void showInProgress(boolean showOrHide) {
        if (showOrHide) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void refreshCalendar() {
        Timber.d("-- Refresh Calendar --");
        mPresenter.getAllAppointments();
    }

    @Override
    public void removeEventFromCalendar(WeekViewEvent event) {
        for (int i = 0; i < event.getId_staff().size(); i++) {
            switch (event.getId_staff().get(i)) {
                case GeneralConstants.ID_LELLA:
                    allAppointmentsLella.remove(event);
                    mWeekViewLella.notifyDatasetChanged();
                    break;
                case GeneralConstants.ID_MARIA:
                    allAppointmentsMaria.remove(event);
                    mWeekViewMaria.notifyDatasetChanged();
                    break;
                case GeneralConstants.ID_ANNA:
                    allAppointmentsAnna.remove(event);
                    mWeekViewAnna.notifyDatasetChanged();
                    break;
            }
        }
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(BaseCalendarContract.Presenter presenter) {

    }

    @Override
    public void logout() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getAllAppointments();
    }


    @Override
    public void showMariaAppointments(List<WeekViewEvent> appointments) {
        for (WeekViewEvent appointment : appointments) {
            appointment.setAppointmentName(appointment.getCustomer_name() + " " + appointment.getCustomer_surname());
        }
        allAppointmentsMaria = appointments;
        mWeekViewMaria.notifyDatasetChanged();

        Timber.i("Notify MARIA");

    }

    @Override
    public void showAnnaAppointments(List<WeekViewEvent> appointments) {
        for (WeekViewEvent appointment : appointments) {
            appointment.setAppointmentName(appointment.getCustomer_name() + " " + appointment.getCustomer_surname());
        }
        allAppointmentsAnna = appointments;
        //Print
        for (WeekViewEvent e : appointments) {
            Timber.i(e.toString());
        }
        //End Print
        mWeekViewAnna.notifyDatasetChanged();
        Timber.i("Notify ANNA");


    }

    @Override
    public void showLellaAppointments(List<WeekViewEvent> appointments) {
        for (WeekViewEvent appointment : appointments) {
            appointment.setAppointmentName(appointment.getCustomer_name() + " " + appointment.getCustomer_surname());
        }

        allAppointmentsLella = appointments;
        mWeekViewLella.notifyDatasetChanged();

        Timber.i("Notify LELLA");

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
