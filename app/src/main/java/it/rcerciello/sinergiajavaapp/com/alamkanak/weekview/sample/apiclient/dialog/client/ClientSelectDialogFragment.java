/*
 * Copyright © 2018 Automobili Lamborghini S.p.A. a sole shareholder company part of Audi Group. All rights reserved. VAT no. IT 00591801204
 */

package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.dialog.client;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientListResponseModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import timber.log.Timber;


/**
 * DialogFragment to select a country from a list.
 *
 * @author Markus Mattsson
 */
public class ClientSelectDialogFragment extends DialogFragment {

    @BindView(R.id.servicesListView)
    RecyclerView list;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etFilter)
    EditText etFilter;


    public interface ClientSelectedListener {
        void onClientSelected(ClientModel Id);
    }


    private ClientSelectedListener clientSelectedListener;
    private ClientSelectListAdapter adapter;
    private List<ClientModel> clients;

    public static ClientSelectDialogFragment newInstance() {
        return new ClientSelectDialogFragment();
    }


    public ClientSelectDialogFragment() {

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


        adapter = new ClientSelectListAdapter();
        adapter.setItemClickListener(item -> {
            if (clientSelectedListener != null) {
                clientSelectedListener.onClientSelected(item);
                dismiss();
            }
        });

        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);

        initializeToolbar();
        readServicesFromDB();


        etFilter.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter

                if (s.toString().trim().isEmpty()) {
                    resetAdapter();
                } else {
                    filterCountryOnAdapter(s.toString().trim());
                }
                Timber.d("Search with name ");
            }


            @Override
            public void afterTextChanged(Editable editable) {
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });


    }

    public void resetAdapter() {
        adapter.setOriginalData(clients);
    }

    public void filterCountryOnAdapter(String txt) {
        adapter.getFilter().filter(txt);
    }

    private void readServicesFromDB() {

        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            ClientListResponseModel clientList = realm.where(ClientListResponseModel.class).findFirst();
            realm.commitTransaction();
            if (clientList != null) {
                showClients(realm.copyFromRealm(clientList));
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


    public void showClients(ClientListResponseModel response) {
        clients = response.getClients();
        adapter.setOriginalData(clients);
    }


    public void setClientSelectedListener(ClientSelectedListener clientSelectedListener) {
        this.clientSelectedListener = clientSelectedListener;
    }

}
