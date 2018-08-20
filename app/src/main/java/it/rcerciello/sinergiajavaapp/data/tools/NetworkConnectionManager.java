package it.rcerciello.sinergiajavaapp.data.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.utils.GeneralConstants;
import it.rcerciello.sinergiajavaapp.utils.SnackbarType;
import timber.log.Timber;

/**
 * This class define a listener on internet connection, when there is not internet connection on the view appear a snackbar
 * Created by rcerciello on 13/09/2017.
 */

public class NetworkConnectionManager {
    private Context ctx;
    private Disposable networkConnectiviy;
    private static NetworkConnectionManager ourInstance = null;


    public NetworkConnectionManager(Context context) {
        ctx = context;
    }


    public static NetworkConnectionManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new NetworkConnectionManager(context);
        }

        return ourInstance;
    }


    public void networkConnectionSubscribe() {
        final String disconected = GeneralConstants.DISCONNECTED;

        networkConnectiviy = ReactiveNetwork.observeNetworkConnectivity(ctx)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Connectivity>() {
                    @Override
                    public void accept(final Connectivity connectivity) {
                        if (connectivity.getState().toString().equals(disconected)) {
                            AlertDialog.Builder builder;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                builder = new AlertDialog.Builder(ctx, android.R.style.Theme_Material_Dialog_Alert);
                            } else {
                                builder = new AlertDialog.Builder(ctx);
                            }
                            builder.setTitle("Attenzione")
                                    .setMessage(ctx.getString(R.string.offline_title))
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //do nothing
                                        }
                                    })
                                    .show();
                        }
                    }
                });
    }


    public void networkConnectionUnsubscrive() {
        safelyDispose(networkConnectiviy);
    }


    private void safelyDispose(Disposable... disposables) {
        for (Disposable subscription : disposables) {
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }

}
