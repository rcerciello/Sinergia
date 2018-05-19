/*
 * Copyright © 2018 Automobili Lamborghini S.p.A. a sole shareholder company part of Audi Group. All rights reserved. VAT no. IT 00591801204
 */

package it.rcerciello.sinergiajavaapp.SaveButton;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import it.rcerciello.sinergiajavaapp.R;


/**
 * Created by rcerciello on 19/10/2017.
 */

public class CustomSaveButton extends LinearLayout implements NextViewButtonInterface
{

    private View myView;
    private Button myButton;
    private ProgressBar myCircularProgressBar;
    private ButtonStates state;

    CustomRectangleNextButtonInterface mCallback;
    CustomSaveButtonInterface mTouchCallback;

    public interface CustomSaveButtonInterface
    {
        void saveButtonClick();
    }

    public CustomSaveButton(Context context) {
        super(context);
        init(context);
    }

    public CustomSaveButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(final Context context) {

        myView = inflate(context, R.layout.custom_save_button, this);
        myButton = myView.findViewById(R.id.customSaveButton);
        myCircularProgressBar = myView.findViewById(R.id.whiteSpinner);
        setButtonState(ButtonStates.DISABLED);


        //Listener on this view
        myButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                mTouchCallback.saveButtonClick();
            }
        });
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        try {

            mTouchCallback = (CustomSaveButtonInterface) this.getContext();
        } catch (ClassCastException e) {
            throw new ClassCastException(this.getContext().toString()
                    + " must implement CustomSaveButtonInterface");
        }
    }


    @Override
    public void setButtonState(ButtonStates state) {
        this.state = state;

        if (state == ButtonStates.DISABLED) {
            myButton.setText(getResources().getString(R.string.save));
            myButton.setBackgroundColor(Color.GRAY);
            myButton.setEnabled(false);
            myButton.setClickable(false);
            if (mCallback != null) {
                mCallback.enableTouchOnUI();
            }

        } else if (getButtonState() == ButtonStates.ENABLED) {
            myButton.setBackgroundColor(Color.BLACK);
            myButton.setEnabled(true);
            myButton.setClickable(true);
            if (mCallback != null) {
                mCallback.enableTouchOnUI();
            }


        } else if (getButtonState() == ButtonStates.IN_PROGRESS) {
            if (myCircularProgressBar.getVisibility() == VISIBLE) {
                if (mCallback != null) {
                    mCallback.disableTouchOnUI();
                }
            }
        } else if (state == ButtonStates.BACKEND_ERROR) {
            myButton.setBackgroundColor(Color.BLACK);
            myButton.setText(getResources().getString(R.string.save));
            myButton.setEnabled(true);
            myButton.setClickable(true);
            myCircularProgressBar.setVisibility(GONE);

            if (mCallback != null) {
                mCallback.enableTouchOnUI();
            }
            setButtonState(ButtonStates.ENABLED);
        }
        myButton.invalidate();

    }

    @Override
    public ButtonStates getButtonState() {
        return state;
    }

    @Override
    public void showProgressBar() {
        myCircularProgressBar.setVisibility(VISIBLE);
        // startProgressBar();
    }

    @Override
    public void hideProgressBar() {
        myCircularProgressBar.setVisibility(GONE);
        //  stopProgressBar();
    }


    @Override
    public ImageButton getImageButtonReference() {
        return null;
    }

    @Override
    public void changeState()
    {
        if (getButtonState() == ButtonStates.ENABLED)
        {
            myButton.setText("");
            myButton.setEnabled(false);
            myButton.setClickable(false);
            showProgressBar();
            setButtonState(ButtonStates.IN_PROGRESS);
            myButton.invalidate();
            if (mCallback != null) {
                mCallback.disableTouchOnUI();
            }
        } else
        if (getButtonState() == ButtonStates.IN_PROGRESS) {
            myButton.setEnabled(true);
            myButton.setClickable(true);
            hideProgressBar();
            setButtonState(ButtonStates.DISABLED);
            myButton.invalidate();
            if (mCallback != null) {
                mCallback.enableTouchOnUI();
            }
        }
    }

    @Override
    public Button getButtonReference() {
        return myButton;
    }


    public void setSaveButtonVisibility(int visibility)
    {
        myView.setVisibility(visibility);
        switch (visibility)
        {
            case 0x00000000: //visible
                setButtonState(ButtonStates.ENABLED);
                break;
        }

    }


    @Override
    public void setText(String text) {}
}
