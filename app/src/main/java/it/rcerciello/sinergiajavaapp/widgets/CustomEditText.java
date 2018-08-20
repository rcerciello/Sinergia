package it.rcerciello.sinergiajavaapp.widgets;

import android.widget.EditText;

/**
 * Interface for a custom EditText wrapper.
 *
 * @author Markus Mattsson
 */
public interface CustomEditText {

    /**
     * Method to access the wrapped EditText reference.
     *
     * @return the reference
     */
    EditText getEditTextReference();

    /**
     * Show validation errors related to the EditText.
     *
     * @param error the error to show
     */
    void showError(String error);

}
