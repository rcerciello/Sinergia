package it.rcerciello.sinergiajavaapp.data.network;

/**
 * Created by rcerciello on 27/09/2017.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import it.rcerciello.sinergiajavaapp.BuildConfig;
import it.rcerciello.sinergiajavaapp.NetworkConstants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    private static Gson gson;
    private static ApiInterfaces apiInterfaces;


    private static final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);


    public static Retrofit getClient() {
        if (retrofit == null) {

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(NetworkConstants.BASE_URL)
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
        if (apiInterfaces == null) {
            apiInterfaces = getClient().create(ApiInterfaces.class);
        }

        return apiInterfaces;
    }
}
