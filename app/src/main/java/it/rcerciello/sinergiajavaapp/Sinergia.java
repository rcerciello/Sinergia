/*
 * Copyright © 2018 Automobili Lamborghini S.p.A. a sole shareholder company part of Audi Group. All rights reserved. VAT no. IT 00591801204
 */

package it.rcerciello.sinergiajavaapp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.multidex.MultiDexApplication;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by rcerciello on 01/10/2017.
 */
public class Sinergia extends MultiDexApplication {
    public static Sinergia INSTANCE ;


    public Sinergia() {
        super();
        if (INSTANCE == null) {
            INSTANCE = this;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //initialize Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

           initCalligraphy();
        initRealm();


    }

    /**
     * Inizialize calligrapgy
     */
    private void initCalligraphy() {
        //Inizialize calligraphy
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("OpenSans/OpenSansRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }


    /**
     * Inizialize realm
     */
    private void initRealm() {
//        Realm.init(getApplicationContext());
//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .deleteRealmIfMigrationNeeded()
//                .build();
//        Realm.setDefaultConfiguration(config);
//        LamboRepository.getInstance();
    }






}

