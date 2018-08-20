package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.dialog;

/**
 * Created by rcerciello on 22/06/2018.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import it.rcerciello.sinergiajavaapp.R;


/**
 * Created by rcerciello on 21/05/2018.
 */

public class CustomDialog extends AlertDialog implements View.OnClickListener {

    private DragAndDropDialogInterface callback;

    ImageView line;
    RelativeLayout root;
    LinearLayout myView;
    Button buttonLine;
    Button btnOK;

    private int viewHeight, deltaY, startY;
    AlertDialog dialog;
    private boolean isDialogShow = false;



    public CustomDialog(@NonNull Context context, DragAndDropDialogInterface callback) {
        super(context);
        this.callback = callback;
    }

    public void show() {
        isDialogShow = true;
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.diaglog_drag_and_drop_in_panorama, null);
        btnOK = (Button) dialogView.findViewById(R.id.btnOK);
        buttonLine = (Button) dialogView.findViewById(R.id.buttonLine);
        line = (ImageView) dialogView.findViewById(R.id.line);
        root = (RelativeLayout) dialogView.findViewById(R.id.root);
        myView =  (LinearLayout) dialogView.findViewById(R.id.myView);

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


        setListener();
        buildDataSourceAdapter();
    }


    private void buildDataSourceAdapter() {
    }


    private void setListener() {

        ViewTreeObserver viewTreeObserver = root.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                root.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                viewHeight = root.getMeasuredHeight();
            }
        });

        buttonLine.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        performCalculations(event);
                        break;
                    case MotionEvent.ACTION_UP:
                        checkWhatShouldHappen();
                }
                return true;

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnOK:
                callback.onConfirmDialog();
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


    private void performCalculations(MotionEvent event) {

        // calculating how many pixels has the finger been dragged on the screen
        deltaY = (int) (event.getY() - startY);

        if (deltaY >= 0) {
            root.setTranslationY((int) (deltaY * ((float) 4 / 5)));
        }
    }


    private void checkWhatShouldHappen() {
        if (Math.abs(deltaY) < viewHeight / 3) {
            root.animate().translationY(0).setDuration(300).withLayer();
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
                isDialogShow = false;
            }
        }
    }

    public boolean isShow()
    {
        return isDialogShow;
    }

}


