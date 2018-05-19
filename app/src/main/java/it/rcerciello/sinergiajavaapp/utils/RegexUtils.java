/*
 * Copyright © 2018 Automobili Lamborghini S.p.A. a sole shareholder company part of Audi Group. All rights reserved. VAT no. IT 00591801204
 */

package it.rcerciello.sinergiajavaapp.utils;

/**
 * In this class there are all the regex used to validate field on the app
 * Created by rcerciello on 11/09/2017.
 */

public class RegexUtils {
    /**
     * Validate an email
     * @return
     */
    public static String getEmail() {
        return "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    }


    /**
     * Validate a telephone number
     * @return
     */
    public static String getPhoneNumber() {
        return "\\+(9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]\\d|2[98654321]\\d|9[8543210]|8[6421]|6[6543210]|5[87654321]|4[987654310]|3[9643210]|2[70]|7|1)\\d{1,14}$";
    }


    /**
     * Validate an url
     * @return
     */
    public static String getUrl() {
        return "^(http|https|ftp)\\://[a-zA- Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(:[a-zA-Z0-9]*)?/?([a-zA-Z0-9\\-\\._\\?\\,\\'/\\\\\\+&%\\$#\\=~])*$";
    }


    /**
     * Validate a password. The password must be contain at lease 8 characters, at lease a character in uppercase, in lower case an a digi
     * @return
     */
    public static String getPassword() {
        return "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$";
    }

    /**
     * Validate a string with all numbers characters
     * @return
     */
    public static String getNumber() {
        return "[0-9]+";
    }


    /**
     * Validate an intenational telephone number
     * @return
     */
    public static String getInternationalPhoneNumberValidation() {
        return "^\\+(?:[0-9] ?){6,14}[0-9]$";
    }
}
