package it.rcerciello.sinergiajavaapp.scene.clients.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.scene.clients.list.ClientItemFragment;

import java.util.ArrayList;
import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder>   implements Filterable {

    private List<ClientModel> mValues;
    private List<ClientModel> filteredData;
    private final ClientItemFragment.OnClientInteractionListener mListener;

    public ClientAdapter(ArrayList<ClientModel> items, ClientItemFragment.OnClientInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_clientitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNome.setText(mValues.get(position).getName());
        holder.mCognome.setText(mValues.get(position).getSurname());

        holder.root.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.onClientInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.root)
        LinearLayout root;

        @BindView(R.id.nome)
        TextView mNome;

        @BindView(R.id.cognome)
        TextView mCognome;

        public ClientModel mItem;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mNome=" + mNome +
                    ", mCognome=" + mCognome +
                    ", mItem=" + mItem +
                    '}';
        }
    }

    public void updateDataSoure(List<ClientModel> clients) {
        if (mValues == null) {
            mValues = new ArrayList<>();
        }
        mValues.clear();
        mValues.addAll(clients);
        notifyDataSetChanged();

    }



    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mValues = (ArrayList<ClientModel>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the originalData with new filtered values
            }


            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<ClientModel> FilteredArrList = new ArrayList<>();

                if (filteredData == null) {
                    filteredData = new ArrayList<>(mValues); // saves the original originalData in mOriginalValues
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

                        assert surname != null;
                        assert name != null;
                        if (name.toLowerCase().startsWith(constraint.toString()) ||surname.toLowerCase().startsWith(constraint.toString()) ) {

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

    public void setOriginalData(List<ClientModel> allIds) {
        if (allIds != null && !allIds.isEmpty()) {
            this.mValues = allIds;
            this.filteredData = allIds;
            notifyDataSetChanged();
        }
    }

}
