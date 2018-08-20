package it.rcerciello.sinergiajavaapp.scene.employee.clients.list;

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
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.EmployeeModel;
import it.rcerciello.sinergiajavaapp.scene.employee.clients.AddClients.AddEmployeeActivity;
import it.rcerciello.sinergiajavaapp.scene.employee.clients.list.adapter.EmployeeAdapter;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnEmployeeInteractionListener}
 * interface.
 */
public class EmployeeFragment extends Fragment implements EmployeeFragmentContract.View {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 10;
    private OnEmployeeInteractionListener mListener;

    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private EmployeeFragmentContract.Presenter mPresenter;
    private ArrayList<EmployeeModel> employeeModel = new ArrayList<>();
    private EmployeeAdapter adapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EmployeeFragment newInstance(int columnCount) {
        EmployeeFragment fragment = new EmployeeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    public EmployeeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new EmployeePresenter(this);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        createFakeModel();
    }

    public void createFakeModel() {
        employeeModel.add(new EmployeeModel("Raffaella", "Cerciello", "Via Selva, 19 Mariglianella", "081 885 43 16", "333 26 55 698","a@a", "Director"));
        employeeModel.add(new EmployeeModel("Giovanna", "Cerciello", "Via Selva, 19 Mariglianella", "081 885 43 16", "333 26 55 698", "","Director"));
        employeeModel.add(new EmployeeModel("Maria", "Cerciello", "Via Selva, 19 Mariglianella", "081 885 43 16", "333 26 55 698","", "Director"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employeeitem_list, container, false);

        ButterKnife.bind(this, view);

        if (mColumnCount <= 1) {
            list.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            list.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        }
        adapter = new EmployeeAdapter(employeeModel, mListener);
        list.setAdapter(adapter);
        return view;
    }

    @OnClick(R.id.fab)
    public void fabAction() {
        Intent i = new Intent(getContext(), AddEmployeeActivity.class);
        Bundle animation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_in_bottom, R.anim.no_changes).toBundle();
        startActivity(i, animation);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEmployeeInteractionListener) {
            mListener = (OnEmployeeInteractionListener) context;
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
    public void setPresenter(EmployeeFragmentContract.Presenter presenter) {

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
    public void updateAdapterDataSource(ArrayList<EmployeeModel> clients) {
        if (clients != null) {
            adapter.updateDataSoure(clients);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.refreshClientList();
    }

    public interface OnEmployeeInteractionListener {
        void onEmployeeInteraction(EmployeeModel item);
    }
}
