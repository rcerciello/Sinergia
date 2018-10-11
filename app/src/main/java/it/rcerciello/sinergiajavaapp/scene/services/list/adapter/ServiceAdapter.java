package it.rcerciello.sinergiajavaapp.scene.services.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.scene.services.list.ServiceItemFragment;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>  implements Filterable {

    private List<ServiceModel> mValues;
    private List<ServiceModel> filteredData;
    private final ServiceItemFragment.OnServiceInteractionListener mListener;

    public ServiceAdapter(ArrayList<ServiceModel> items, ServiceItemFragment.OnServiceInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_servicetem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.nome_servizio.setText(mValues.get(position).getName());

        holder.root.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.onServiceInteraction(holder.mItem);
            }
        });
    }



    public void setOriginalData(List<ServiceModel> allIds) {
        if (allIds != null && !allIds.isEmpty()) {
            this.mValues = allIds;
            this.filteredData = allIds;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.root)
        LinearLayout root;

        @BindView(R.id.nome_servizio)
        TextView nome_servizio;

        ServiceModel mItem;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "root=" + root +
                    ", nome_servizio=" + nome_servizio +
                    ", mItem=" + mItem +
                    '}';
        }
    }

    public void updateDataSoure(List<ServiceModel> clients) {
        if (mValues == null) {
            mValues = new ArrayList<>();
        }
        mValues.clear();
        mValues.addAll(clients);
        notifyDataSetChanged();

    }


    @Override
    public Filter getFilter() {
        return  new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mValues= (ArrayList<ServiceModel>) results.values;
                notifyDataSetChanged();
            }


            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<ServiceModel> FilteredArrList = new ArrayList<>();

                if (filteredData == null) {
                    filteredData = new ArrayList<>(mValues);
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

                        if (name.toLowerCase().startsWith(constraint.toString())) {

                            ServiceModel item = new ServiceModel();
                            item.setDuration(filteredData.get(i).getDuration());
                            item.setId(filteredData.get(i).getId());
                            item.setName(filteredData.get(i).getName());
                            item.setPrice(filteredData.get(i).getPrice());
                            item.setServiceIdentifier(filteredData.get(i).getServiceIdentifier());
                            item.setServicePrimaryKeyModel(filteredData.get(i).getServicePrimaryKeyModel());

                            FilteredArrList.add(item);
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
    }


}
