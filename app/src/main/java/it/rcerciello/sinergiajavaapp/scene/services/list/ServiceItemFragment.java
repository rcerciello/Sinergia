package it.rcerciello.sinergiajavaapp.scene.services.list;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.scene.services.AddService.AddServiceActivity;
import it.rcerciello.sinergiajavaapp.scene.services.list.adapter.ServiceAdapter;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnServiceInteractionListener}
 * interface.
 */
public class ServiceItemFragment extends Fragment implements ServiceItemFragmentContract.View {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 10;
    private OnServiceInteractionListener mListener;

    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private ServiceItemFragmentContract.Presenter mPresenter;
    private ArrayList<ServiceModel> serviceModel = new ArrayList<>();
    private ServiceAdapter adapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ServiceItemFragment newInstance(int columnCount) {
        ServiceItemFragment fragment = new ServiceItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    public ServiceItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new ServiceItemPresenter(this);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        createFakeModel();
    }

    public void createFakeModel() {
        serviceModel.add(new ServiceModel("Ceretta Inguine", 60, 10));
        serviceModel.add(new ServiceModel("Ceretta Completa", 90, 15));
        serviceModel.add(new ServiceModel("Baffetto", 5, 2));
        serviceModel.add(new ServiceModel("Sopracciglia con cera", 5, 5));
        serviceModel.add(new ServiceModel("Sopracciglia con pinzetta", 5, 5));
        serviceModel.add(new ServiceModel("Massaggio Rilassante", 45, 50));
        serviceModel.add(new ServiceModel("Massaggio Tonificante", 45, 50));
        serviceModel.add(new ServiceModel("Massaggio con il fuoco", 45, 50));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_serviceitem_list, container, false);

        ButterKnife.bind(this, view);

        if (mColumnCount <= 1) {
            list.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            list.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        }
        adapter = new ServiceAdapter(serviceModel, mListener);
        list.setAdapter(adapter);
        return view;
    }

    @OnClick(R.id.fab)
    public void fabAction() {
        Intent i = new Intent(getContext(), AddServiceActivity.class);
        Bundle animation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_in_bottom, R.anim.no_changes).toBundle();
        startActivity(i, animation);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnServiceInteractionListener) {
            mListener = (OnServiceInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setPresenter(ServiceItemFragmentContract.Presenter presenter) {

    }

    @Override
    public void logout() {

    }

    @Override
    public void showSnackbarError(String message) {

    }

    @Override
    public void closeView() {

    }

    @Override
    public void showOrHideProgressBar(boolean showOrHide) {
        if (showOrHide) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateAdapterDataSource(ArrayList<ServiceModel> clients) {
        if (clients != null) {
            adapter.updateDataSoure(clients);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.refreshServiceList();
    }

    public interface OnServiceInteractionListener {
        void onServiceInteraction(ServiceModel item);
    }
}
