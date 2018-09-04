/*
 * Copyright © 2018 Automobili Lamborghini S.p.A. a sole shareholder company part of Audi Group. All rights reserved. VAT no. IT 00591801204
 */

package it.rcerciello.sinergiajavaapp.com.alamkanak.weekview.sample.apiclient.dialog.service;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class ServiceSelectListAdapter extends RecyclerView.Adapter<ServiceSelectListAdapter.ServiceItemViewHolder> {


    public interface OnItemClickListener {
        void onItemClicked(ServiceModel model);
    }


    private List<ServiceModel> data;
    private OnItemClickListener itemClickListener;


    public ServiceSelectListAdapter() {
        this.data = new ArrayList<>();
    }


    @Override
    public ServiceItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_service,
                parent, false);
        final ServiceItemViewHolder holder = new ServiceItemViewHolder(itemView);
        itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClicked(data.get(holder.getAdapterPosition()));
            }
        });

        return holder;
    }


    public void setData(List<ServiceModel> allIds) {
        if (allIds != null && !allIds.isEmpty()) {
            this.data = allIds;
            notifyDataSetChanged();
        }
    }


    void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onBindViewHolder(ServiceItemViewHolder holder, int position) {
        holder.bind(data.get(position));
    }


    @Override
    public int getItemCount() {
        return data.size();
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
}
