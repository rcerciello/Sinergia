package it.rcerciello.sinergiajavaapp;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.rcerciello.sinergiajavaapp.utils.RegexUtils;

/**
 * Created by rcerciello on 14/05/2018.
 */

public class GlobalUtils {
    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        return image;
    }

    /**
     * Validate the email string.
     *
     * @param email
     * @return true if the parameter is an email otherwise false
     */
    public static boolean isEmailValidated(String email) {
        if (email == null) {
            return false;
        }
        return email.matches(RegexUtils.getEmail());
    }


}
