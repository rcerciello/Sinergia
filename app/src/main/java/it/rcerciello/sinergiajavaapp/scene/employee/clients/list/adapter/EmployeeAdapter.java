package it.rcerciello.sinergiajavaapp.scene.employee.clients.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.EmployeeModel;
import it.rcerciello.sinergiajavaapp.scene.employee.clients.list.EmployeeFragment;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private  ArrayList<EmployeeModel> mValues;
    private final EmployeeFragment.OnEmployeeInteractionListener mListener;

    public EmployeeAdapter(ArrayList<EmployeeModel> items, EmployeeFragment.OnEmployeeInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_employeeitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNome.setText(mValues.get(position).getName());
        holder.mCognome.setText(mValues.get(position).getSurname());

        holder.root.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.onEmployeeInteraction(holder.mItem);
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

        public EmployeeModel mItem;

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

    public void updateDataSoure(ArrayList<EmployeeModel> clients)
    {
        if(mValues==null)
        {
            mValues = new ArrayList<>();
        }

        mValues.addAll(clients);
        notifyDataSetChanged();

    }
}
