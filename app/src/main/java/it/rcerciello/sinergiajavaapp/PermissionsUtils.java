/*
 * Copyright © 2018 Automobili Lamborghini S.p.A. a sole shareholder company part of Audi Group. All rights reserved. VAT no. IT 00591801204
 */

package it.rcerciello.sinergiajavaapp;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;

/**
 * Created by rcerciello on 26/09/2017.
 */

public class PermissionsUtils {
    public static void checkPermission(Activity activity, String permission, int permissionCode) {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, permissionCode);
    }
}
