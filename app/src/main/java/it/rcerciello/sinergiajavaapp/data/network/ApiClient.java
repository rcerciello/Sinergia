/*
 * Copyright © 2018 Automobili Lamborghini S.p.A. a sole shareholder company part of Audi Group. All rights reserved. VAT no. IT 00591801204
 */

package it.rcerciello.sinergiajavaapp.data.network;

/**
 * Created by rcerciello on 27/09/2017.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import it.rcerciello.sinergiajavaapp.BuildConfig;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.Sinergia;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    private static Gson gson;
    private static ApiInterfaces apiInterfaceForLambo;


    private static final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);


    public static Retrofit getClient() {
        if (retrofit == null) {

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Sinergia.INSTANCE.getResources().getString(R.string.base_url))
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }


    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().create();
        }

        return gson;
    }


    public synchronized static ApiInterfaces getApiClient() {
        if (apiInterfaceForLambo == null) {
            apiInterfaceForLambo = getClient().create(ApiInterfaces.class);
        }

        return apiInterfaceForLambo;
    }
}
