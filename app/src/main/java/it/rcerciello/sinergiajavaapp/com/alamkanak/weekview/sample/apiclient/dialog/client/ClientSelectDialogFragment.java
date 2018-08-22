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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;


/**
 * DialogFragment to select a country from a list.
 *
 * @author Markus Mattsson
 */
public class ClientSelectDialogFragment extends DialogFragment  {

    private static final String ARGUMENT_TITLE = "ARGUMENT_TITLE";

    private static final String DEFAULT_TITLE = "";

    @BindView(R.id.servicesListView)
    RecyclerView list;
    @BindView(R.id.toolbar)
    Toolbar toolbar;



    public interface ClientSelectedListener {
        void onClientSelected(ClientModel Id);
    }



    private ClientSelectedListener clientSelectedListener;
    private ClientSelectListAdapter adapter;


    public static ClientSelectDialogFragment newInstance() {
        ClientSelectDialogFragment instance = new ClientSelectDialogFragment();
        return instance;
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
        adapter.setItemClickListener(new ClientSelectListAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(ClientModel item) {
                if (clientSelectedListener != null) {
                    clientSelectedListener.onClientSelected(item);
                    dismiss();
                }
            }
        });

        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);

        initializeToolbar();
        //TODO select all serices;
        showServices();

    }


    private void initializeToolbar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }


    public void showServices() {
        List<ClientModel> servie = new ArrayList<>();

        servie.add(new ClientModel("AC", "Angela", "Cerciello"));
        servie.add(new ClientModel("RC", "Raffaella", "Cerciello"));
        servie.add(new ClientModel("DC", "Domenico", "Cerciello"));
        servie.add(new ClientModel("PM", "Panico", "Michela"));
        servie.add(new ClientModel("AR", "Antonio", "Russo"));
        
        adapter.setData(servie);
    }


    public void setClientSelectedListener(ClientSelectedListener clientSelectedListener) {
        this.clientSelectedListener = clientSelectedListener;
    }

}
