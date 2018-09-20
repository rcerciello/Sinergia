package it.rcerciello.sinergiajavaapp.scene.clients.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.scene.clients.list.ClientItemFragment;

import java.util.ArrayList;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder> {

    private  ArrayList<ClientModel> mValues;
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

    public void updateDataSoure(ArrayList<ClientModel> clients)
    {
        if(mValues==null)
        {
            mValues = new ArrayList<>();
        }

        mValues.addAll(clients);
        notifyDataSetChanged();

    }
}
