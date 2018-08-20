/*
 * Copyright © 2018 Automobili Lamborghini S.p.A. a sole shareholder company part of Audi Group. All rights reserved. VAT no. IT 00591801204
 */

package it.rcerciello.sinergiajavaapp.utils;

/**
 * Created by rcerciello on 15/10/2017.
 */

public enum SnackbarType {
    GENERIC("Generic"),
    ERROR("Error");

    private final String tagName;


    SnackbarType(String s) {
        tagName = s;
    }


    public String toString() {
        return this.tagName;
    }
}
