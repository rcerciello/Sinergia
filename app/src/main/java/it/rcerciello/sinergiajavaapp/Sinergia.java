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
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import it.rcerciello.sinergiajavaapp.data.managers.ClientNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.managers.ServiceNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;
import it.rcerciello.sinergiajavaapp.data.repository.SinergiaRepo;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by rcerciello on 01/10/2017.
 */
public class Sinergia extends MultiDexApplication {
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
        //initialize Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        initCalligraphy();
        initRealm();

        ClientNetworkLayer.getInstance().getClients(new APICallback<ArrayList<ClientModel>>() {
            @Override
            public void onSuccess(ArrayList<ClientModel> object) {
                if (object != null && !object.isEmpty()) {
                    SinergiaRepo.getInstance().insertClients(object);
                }
            }

            @Override
            public void onFailure(String error) {

            }

            @Override
            public void onSessionExpired() {

            }

            @Override
            public void onFailure(boolean isFailure) {

            }
        });


        ServiceNetworkLayer.getServices(new APICallback<ArrayList<ServiceModel>>() {
            @Override
            public void onSuccess(ArrayList<ServiceModel> object) {
                if (object!=null && !object.isEmpty()) {
                    SinergiaRepo.getInstance().insertServices(object);
                }
            }

            @Override
            public void onFailure(String error) {

            }

            @Override
            public void onSessionExpired() {

            }

            @Override
            public void onFailure(boolean isFailure) {

            }
        });

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

