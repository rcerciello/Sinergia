/*
 * Copyright © 2018 Automobili Lamborghini S.p.A. a sole shareholder company part of Audi Group. All rights reserved. VAT no. IT 00591801204
 */

package it.rcerciello.sinergiajavaapp.SaveButton;

import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by rcerciello on 21/09/2017.
 */

public interface NextViewButtonInterface {

    void setButtonState(ButtonStates state);
    ButtonStates getButtonState();

    void showProgressBar();
    void hideProgressBar();
    void changeState();
    ImageButton getImageButtonReference();
    Button getButtonReference();
    void setText(String text);

}
