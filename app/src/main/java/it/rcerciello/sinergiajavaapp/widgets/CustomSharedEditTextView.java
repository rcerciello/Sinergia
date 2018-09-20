package it.rcerciello.sinergiajavaapp.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import it.rcerciello.sinergiajavaapp.R;
import timber.log.Timber;


/**
 * Created by rcerciello on 19/10/2017.
 */

public class CustomSharedEditTextView extends LinearLayout implements CustomEditText {
    private View myView;
    private TextView titleTextView;
    private EditText customEditText;
    private TextInputLayout customTextInputLayout;
    private String editTextTitle;


    public CustomSharedEditTextView(Context context) {
        super(context);
        init(context);
    }

    public CustomSharedEditTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomSharedEditTextView, 0, 0);
        try {
            // get the text and colors specified using the names in attrs.xm
            editTextTitle = a.getString(R.styleable.CustomSharedEditTextView_sharedEditTextTitle);
        } finally {
            a.recycle();
        }

        init(context);
    }


    public CustomSharedEditTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomSharedEditTextView, 0, 0);
        try {
            // get the text and colors specified using the names in attrs.xm
            editTextTitle = a.getString(R.styleable.CustomSharedEditTextView_sharedEditTextTitle);
        } finally {
            a.recycle();
        }

        init(context);
    }

    private void init(final Context context) {
        myView = inflate(context, R.layout.custom_edit_text, this);
        myView.isInEditMode();
        titleTextView = myView.findViewById(R.id.customTextView);
        customTextInputLayout = myView.findViewById(R.id.customTextInputLayout);
        customEditText = myView.findViewById(R.id.customEditText);
        titleTextView.setText(editTextTitle);
    }


    /**
     * @return the string that user insert into edit text
     */
    public String getTextFromEditText() {
        return customEditText.getText().toString();
    }


    /**
     * @param text to show
     */
    public void setText(String text) {
        customEditText.setText(text);
    }


    /**
     * @return the reference of edit text
     */
    @Override
    public EditText getEditTextReference() {
        return customEditText;
    }


    /**
     * @return the reference of text  input layout
     */

    public TextInputLayout getTextInputLayoutPasswordReference() {
        return customTextInputLayout;
    }


    /**
     * this methos assign a tag to custom edit text
     *
     * @param tag
     */

    public void setCustomTag(int tag) {
        customEditText.setTag(tag);
    }


    /**
     * this methos return a tag assigned to custom edit text
     *
     * @return
     */

    public int getCustomTag() {
        return (int) customEditText.getTag();
    }


    /**
     * this method show error below edit text
     *
     * @param error : error to show. It can be null. If it is null the error is disabled while if it is not null the error message is show
     */
    @Override
    public void showError(String error) {
        if (error != null) {
            customTextInputLayout.setErrorEnabled(true);
            Log.e("", "Error enabled = true");
            customTextInputLayout.setError(error);
        } else {

            Log.e("", "Error enabled = false");
            customTextInputLayout.setErrorEnabled(false);
        }
    }


    /**
     * this method disable click on edit text. It can be used when it is neceaary show title fragment
     */
    public void disableClick() {
        customEditText.setEnabled(false);
    }


    public void setKeyboardType(String type) {
        switch (type) {
            case "decimal":
                customEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case "numeric":
                customEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            default:
                break;
        }
    }


    /**
     * return the text inside the edit text
     *
     * @return
     */

    public String getText() {
        return customEditText.getText().toString();
    }
}
