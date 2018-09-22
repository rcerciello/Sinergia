package it.rcerciello.sinergiajavaapp.scene.services.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.scene.services.list.ServiceItemFragment;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private ArrayList<ServiceModel> mValues;
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

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onServiceInteraction(holder.mItem);
                }
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

        @BindView(R.id.nome_servizio)
        TextView nome_servizio;
        public ServiceModel mItem;

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
}
