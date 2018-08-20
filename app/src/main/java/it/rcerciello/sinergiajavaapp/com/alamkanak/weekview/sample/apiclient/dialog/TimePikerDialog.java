package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import it.rcerciello.sinergiajavaapp.R;

public class TimePikerDialog extends AlertDialog implements View.OnClickListener {

    private TimePicker timePicker1;
    private TextView time;
    private Calendar calendar;
    private String format = "";


    AlertDialog dialog;
    private boolean isDialogShow = false;



    public TimePikerDialog(@NonNull Context context) {
        super(context);
    }

    public void show() {
        isDialogShow = true;
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.diaglog_drag_and_drop_in_panorama, null);

        Builder dialogBuilder = new Builder(dialogView.getContext(), R.style.CustomDialogTheme);


        dialogBuilder.setView(dialogView);
        dialog = dialogBuilder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogTheme;


        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }

        if (!dialog.isShowing()) {
            dialog.show();
        }
        buildDataSourceAdapter();

        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        time = (TextView) findViewById(R.id.textView1);
        calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);
    }

    public void setTime(View view) {
        int hour = timePicker1.getCurrentHour();
        int min = timePicker1.getCurrentMinute();
        showTime(hour, min);
    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

}


    private void buildDataSourceAdapter() {
    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnOK:
                this.dismiss();
                break;
        }
    }


    /**
     * This is an interface
     */
    public interface DragAndDropDialogInterface {
        void onConfirmDialog();

    }


    public boolean isShow()
    {
        return isDialogShow;
    }

}


