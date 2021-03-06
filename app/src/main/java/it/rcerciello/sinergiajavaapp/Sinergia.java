/*
 * Copyright © 2018 Automobili Lamborghini S.p.A. a sole shareholder company part of Audi Group. All rights reserved. VAT no. IT 00591801204
 */

package it.rcerciello.sinergiajavaapp;

import android.app.Application;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import it.rcerciello.sinergiajavaapp.data.managers.ClientNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.managers.ServiceModelResponse;
import it.rcerciello.sinergiajavaapp.data.managers.ServiceNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.managers.StaffModelResponse;
import it.rcerciello.sinergiajavaapp.data.managers.StaffNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientListResponseModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;
import it.rcerciello.sinergiajavaapp.data.repository.SinergiaRepo;
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




        ClientNetworkLayer.getInstance().getClients(new APICallback<ClientListResponseModel>() {
            @Override
            public void onSuccess(ClientListResponseModel clients) {
                deleteOldCustomersFromDB(clients);
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


        ServiceNetworkLayer.getInstance().getServices(new APICallback<ServiceModelResponse>() {
            @Override
            public void onSuccess(ServiceModelResponse response) {

                Timber.d("Service List ok ");
                if (response != null && response.getServices() != null && response.getServices().size() > 0) {

                    Timber.d("Delete Service List");
                    deleteOldServicesFromDB(response);

                }
            }

            @Override
            public void onFailure(String error) {
                Timber.e(error);
            }

            @Override
            public void onSessionExpired() {
            }

            @Override
            public void onFailure(boolean isFailure) {

            }
        });


        StaffNetworkLayer.getInstance().getStaffResources(new APICallback<StaffModelResponse>() {
            @Override
            public void onSuccess(StaffModelResponse object) {
                if (object != null && object.getStaff() != null && object.getStaff().size() > 0) {

                    SinergiaRepo.getInstance().saveStaff(object, new APICallback<Boolean>() {
                        @Override
                        public void onSuccess(Boolean object) {

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


    private void deleteOldServicesFromDB(ServiceModelResponse response) {


        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            RealmResults<ServiceModel> oldServices = realm.where(ServiceModel.class).findAll();
            oldServices.deleteAllFromRealm();
            realm.commitTransaction();
            addNewServices(response.getServices());
            Timber.d("Delete Service List Success");
        } catch (Exception e) {
            Timber.e(e.getLocalizedMessage());

            Timber.d("Delete Service fail => " + e.getLocalizedMessage());
        }
    }

    private void addNewServices(List<ServiceModel> response) {

        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.insertOrUpdate(response);
            realm.commitTransaction();

        } catch (Exception e) {
            Timber.e(e.getLocalizedMessage());
        }
    }


    private void deleteOldCustomersFromDB(ClientListResponseModel response) {

        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            RealmResults<ClientListResponseModel> oldCustomers = realm.where(ClientListResponseModel.class).findAll();
            oldCustomers.deleteAllFromRealm();
            realm.commitTransaction();
            addCustomersServices(response);
        } catch (Exception e) {
            Timber.e(e.getLocalizedMessage());
        }
    }

    private void addCustomersServices(ClientListResponseModel response) {

        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.insertOrUpdate(response);
            realm.commitTransaction();

        } catch (Exception e) {
            Timber.e(e.getLocalizedMessage());
        }
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

