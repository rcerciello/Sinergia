/*
 * Copyright © 2018 Automobili Lamborghini S.p.A. a sole shareholder company part of Audi Group. All rights reserved. VAT no. IT 00591801204
 */

package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.dialog.client;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.rcerciello.sinergiajavaapp.GlobalUtils;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import timber.log.Timber;


/**
 * TODO: Write Class descriptor
 *
 * @author Markus Mattsson
 */
public class ClientSelectListAdapter extends RecyclerView.Adapter<ClientSelectListAdapter.ServiceItemViewHolder> implements Filterable {

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                originalData = (ArrayList<ClientModel>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the originalData with new filtered values
            }


            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<ClientModel> FilteredArrList = new ArrayList<>();

                if (filteredData == null) {
                    filteredData = new ArrayList<>(originalData); // saves the original originalData in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = filteredData.size();
                    results.values = filteredData;
                } else {
                    constraint = constraint.toString().toLowerCase();

                    for (int i = 0; i < filteredData.size(); i++) {

                        String name = filteredData.get(i).getName();
                        String surname = filteredData.get(i).getSurname();
                        String clientIdentifier = filteredData.get(i).getClientIdentifier();
                        assert surname != null;
                        assert name != null;
                        Timber.d("Client Identifier => " + clientIdentifier);
                        Timber.d("constraint => " + constraint);

                        if (name.toLowerCase().startsWith(constraint.toString()) || surname.toLowerCase().startsWith(constraint.toString())
                                || clientIdentifier.toLowerCase().contains(constraint.toString().toLowerCase())) {

                            // en, String it, String de, String fr, String isoCode, String e
                            ClientModel item = new ClientModel();
                            item.setAddress(filteredData.get(i).getAddress());
                            item.setClientIdentifier(filteredData.get(i).getClientIdentifier());
                            item.setName(filteredData.get(i).getName());
                            item.setEmail(filteredData.get(i).getEmail());
                            item.setMobile_phone(filteredData.get(i).getLandlinePhone());
                            item.setLandlinePhone(filteredData.get(i).getLandlinePhone());
                            item.setPrimaryKeyModel(filteredData.get(i).getPrimaryKeyModel());
                            item.setSurname(filteredData.get(i).getSurname());

                            FilteredArrList.add(item); //(mOriginalValues.get(i).name.toString(), mOriginalValues.get(i).area_code.toString()));//,mOriginalValues.get(i).country_prefix.toString()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }


    public interface OnItemClickListener {
        void onItemClicked(ClientModel model);
    }


    private List<ClientModel> originalData;
    private List<ClientModel> filteredData;
    private OnItemClickListener itemClickListener;


    public ClientSelectListAdapter() {
        this.originalData = new ArrayList<>();
        this.filteredData = new ArrayList<>();
    }


    @Override
    public ServiceItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_client_selected_item,
                parent, false);
        final ServiceItemViewHolder holder = new ServiceItemViewHolder(itemView);
        itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClicked(originalData.get(holder.getAdapterPosition()));
            }
        });

        return holder;
    }


    public void setOriginalData(List<ClientModel> allIds) {
        if (allIds != null && !allIds.isEmpty()) {
            this.originalData = allIds;
            this.filteredData = allIds;
            notifyDataSetChanged();
        }
    }


    void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onBindViewHolder(ServiceItemViewHolder holder, int position) {
        holder.bind(originalData.get(position));
    }


    @Override
    public int getItemCount() {
        return originalData.size();
    }


    class ServiceItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.clientId)
        TextView clientId;
        @BindView(R.id.clientName)
        TextView clientName;
        @BindView(R.id.clientSurname)
        TextView clientSurname;

        ServiceItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        void bind(ClientModel model) {
            if (model != null) {
                if (GlobalUtils.isNotNullAndNotEmpty(model.getPrimaryKeyModel().getPrimaryKey())) {
                    clientId.setVisibility(View.VISIBLE);
                    clientId.setText(model.getClientIdentifier().toUpperCase());
                } else {
                    clientId.setVisibility(View.GONE);
                }


                if (GlobalUtils.isNotNullAndNotEmpty(model.getName())) {
                    clientName.setText(model.getName().toUpperCase());
                }

                if (GlobalUtils.isNotNullAndNotEmpty(model.getSurname())) {
                    clientSurname.setText(model.getSurname().toUpperCase());
                }
            }

        }
    }
}
