/*
 * Copyright © 2018 Automobili Lamborghini S.p.A. a sole shareholder company part of Audi Group. All rights reserved. VAT no. IT 00591801204
 */

package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.dialog.service;

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
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;


/**
 * TODO: Write Class descriptor
 *
 * @author Markus Mattsson
 */
public class ServiceSelectListAdapter extends RecyclerView.Adapter<ServiceSelectListAdapter.ServiceItemViewHolder> implements Filterable {


    public interface OnItemClickListener {
        void onItemClicked(ServiceModel model);
    }


    private List<ServiceModel> originalData;
    private List<ServiceModel> filteredData;
    private OnItemClickListener itemClickListener;


    public ServiceSelectListAdapter() {
        this.originalData = new ArrayList<>();
        this.filteredData = new ArrayList<>();
    }


    @Override
    public ServiceItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_service,
                parent, false);
        final ServiceItemViewHolder holder = new ServiceItemViewHolder(itemView);
        itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClicked(originalData.get(holder.getAdapterPosition()));
            }
        });

        return holder;
    }


    public void setOriginalData(List<ServiceModel> allIds) {
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

        @BindView(R.id.serviceId)
        TextView serviceId;
        @BindView(R.id.serviceName)
        TextView serviceName;

        ServiceItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        void bind(ServiceModel model) {
            if (model != null) {
                if (GlobalUtils.isNotNullAndNotEmpty(model.getServiceIdentifier())) {
                    serviceId.setText(model.getServiceIdentifier());
                }


                if (GlobalUtils.isNotNullAndNotEmpty(model.getName())) {
                    serviceName.setText(model.getName());
                }
            }

        }
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                originalData = (ArrayList<ServiceModel>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }


            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<ServiceModel> FilteredArrList = new ArrayList<>();

                if (filteredData == null) {
                    filteredData = new ArrayList<>(originalData); // saves the original data in mOriginalValues
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
                        String serviceIdentifier = filteredData.get(i).getServiceIdentifier();

                        if (name.toLowerCase().startsWith(constraint.toString()) || serviceIdentifier.toLowerCase().contains(constraint.toString().toLowerCase())) {

                            // en, String it, String de, String fr, String isoCode, String e
                            ServiceModel item = new ServiceModel();
                            item.setDuration(filteredData.get(i).getDuration());
                            item.setId(filteredData.get(i).getId());
                            item.setName(filteredData.get(i).getName());
                            item.setPrice(filteredData.get(i).getPrice());
                            item.setServiceIdentifier(filteredData.get(i).getServiceIdentifier());
                            item.setServicePrimaryKeyModel(filteredData.get(i).getServicePrimaryKeyModel());

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


}
