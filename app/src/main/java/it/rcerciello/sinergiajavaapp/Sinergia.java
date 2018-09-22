/*
 * Copyright © 2018 Automobili Lamborghini S.p.A. a sole shareholder company part of Audi Group. All rights reserved. VAT no. IT 00591801204
 */

package it.rcerciello.sinergiajavaapp;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by rcerciello on 01/10/2017.
 */
public class Sinergia extends Application {
    public static Sinergia INSTANCE;


    public Sinergia() {
        super();
        if (INSTANCE == null) {
            INSTANCE = this;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
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
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("SinergiaDB.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(config);
    }
}

