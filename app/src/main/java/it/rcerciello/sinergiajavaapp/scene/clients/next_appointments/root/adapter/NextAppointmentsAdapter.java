package it.rcerciello.sinergiajavaapp.scene.clients.next_appointments.root.adapter;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.weekLibrary.weekview.WeekViewEvent;

public class NextAppointmentsAdapter extends RecyclerView.Adapter<NextAppointmentsAdapter.ViewHolder> {
    private List<WeekViewEvent> mValues = new ArrayList<>();
    private final OnNextAppointmentInteractionListener mListener;

    public NextAppointmentsAdapter(OnNextAppointmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public NextAppointmentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.next_appointment_list_item, parent, false);
        return new NextAppointmentsAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final NextAppointmentsAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime((mValues.get(position).getStartTime().getTime()));
        holder.tvDate.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR)) + " / "+calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        holder.root.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.onAppointmentAction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvDate)
        TextView tvDate;

        @BindView(R.id.root)
        ConstraintLayout root;

        WeekViewEvent mItem;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }

    public void updateDataSoure(List<WeekViewEvent> clients) {
        if (mValues == null) {
            mValues = new ArrayList<>();
        }
        mValues.clear();
        mValues.addAll(clients);
        notifyDataSetChanged();
    }


    public interface OnNextAppointmentInteractionListener {
        void onAppointmentAction(WeekViewEvent item);
    }
}
