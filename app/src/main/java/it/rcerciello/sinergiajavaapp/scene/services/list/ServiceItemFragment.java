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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.scene.services.add_service.AddServiceActivity;
import it.rcerciello.sinergiajavaapp.scene.services.list.adapter.ServiceAdapter;
import timber.log.Timber;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnServiceInteractionListener}
 * interface.
 */
public class ServiceItemFragment extends Fragment implements ServiceItemFragmentContract.View {

    private OnServiceInteractionListener mListener;

    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.etFilter)
    EditText etFilter;

    private ServiceItemFragmentContract.Presenter mPresenter;
    private ArrayList<ServiceModel> serviceModel = new ArrayList<>();
    private ServiceAdapter adapter;
    private List<ServiceModel> services;


    @SuppressWarnings("unused")
    public static ServiceItemFragment newInstance(int columnCount) {
        ServiceItemFragment fragment = new ServiceItemFragment();
        return fragment;
    }

    public ServiceItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new ServiceItemPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_serviceitem_list, container, false);

        ButterKnife.bind(this, view);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ServiceAdapter(serviceModel, mListener);
        list.setAdapter(adapter);


        etFilter.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter

                if (s.toString().trim().isEmpty()) {
                    resetAdapter();
                } else {
                    filterCountryOnAdapter(s.toString().trim());
                }
                Timber.d("Search with name ");
            }


            @Override
            public void afterTextChanged(Editable editable) {
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        return view;
    }


    public void resetAdapter() {
        adapter.setOriginalData(services);
    }

    public void filterCountryOnAdapter(String txt) {
        adapter.getFilter().filter(txt);
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
    public void updateAdapterDataSource(List<ServiceModel> services) {
        if (services != null) {
            etFilter.setClickable(true);
            this.services = services;
            adapter.updateDataSoure(services);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        etFilter.setClickable(false);
        mPresenter.refreshServiceList();
    }

    public interface OnServiceInteractionListener {
        void onServiceInteraction(ServiceModel item);
    }
}
