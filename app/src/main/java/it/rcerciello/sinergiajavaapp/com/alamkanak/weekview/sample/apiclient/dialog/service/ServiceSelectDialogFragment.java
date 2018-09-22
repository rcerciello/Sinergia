/*
 * Copyright © 2018 Automobili Lamborghini S.p.A. a sole shareholder company part of Audi Group. All rights reserved. VAT no. IT 00591801204
 */

package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.dialog.service;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.data.managers.ServiceModelResponse;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientListResponseModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import timber.log.Timber;


/**
 * DialogFragment to select a country from a list.
 *
 * @author Markus Mattsson
 */
public class ServiceSelectDialogFragment extends DialogFragment {

    @BindView(R.id.servicesListView)
    RecyclerView countryListView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    public interface ServiceSelectedListener {
        void onServiceSelected(ServiceModel serviceModel);
    }


    private ServiceSelectedListener serviceSelectedListener;
    private ServiceSelectListAdapter adapter;


    public static ServiceSelectDialogFragment newInstance() {
        return new ServiceSelectDialogFragment();
    }


    public ServiceSelectDialogFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_services, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        adapter = new ServiceSelectListAdapter();
        adapter.setItemClickListener(item -> {
            if (serviceSelectedListener != null) {
                serviceSelectedListener.onServiceSelected(item);
                dismiss();
            }
        });

        countryListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        countryListView.setAdapter(adapter);
        initializeToolbar();

        readServicesFromDB();


    }

    private void readServicesFromDB() {

        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            ServiceModelResponse services = realm.where(ServiceModelResponse.class).findFirst();
            realm.commitTransaction();
            if (services != null) {
                showServices(realm.copyFromRealm(services));
            }

        } catch (Exception e) {
            Timber.e(e.getLocalizedMessage());
        }


    }


    private void initializeToolbar() {
        toolbar.setNavigationOnClickListener(view -> dismiss());

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }


    public void showServices(ServiceModelResponse DBServices) {
        List<ServiceModel> services = DBServices.getServices();
        adapter.setData(services);
    }


    public void setServiceSelectedListener(ServiceSelectedListener serviceSelectedListener) {
        this.serviceSelectedListener = serviceSelectedListener;
    }

}
