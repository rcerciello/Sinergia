package it.rcerciello.sinergiajavaapp.scene.clients.list;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import it.rcerciello.sinergiajavaapp.scene.clients.add_clients.AddClientsActivity;
import it.rcerciello.sinergiajavaapp.scene.clients.list.adapter.ClientAdapter;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnClientInteractionListener}
 * interface.
 */
public class ClientItemFragment extends Fragment implements ClientItemFragmentContract.View {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 10;
    private OnClientInteractionListener mListener;

    @BindView(R.id.list)
    RecyclerView list;


    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private ClientItemFragmentContract.Presenter mPresenter;
    private ArrayList<ClientModel> clientModel = new ArrayList<>();
    private ClientAdapter adapter;

    @SuppressWarnings("unused")
    public static ClientItemFragment newInstance(int columnCount) {
        ClientItemFragment fragment = new ClientItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    public ClientItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new ClientItemPresenter(this);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        createFakeModel();
    }

    public void createFakeModel() {
        clientModel.add(new ClientModel("RC","Raffaella", "Cerciello", "Via Selva, 19 Mariglianella", "081 885 43 16", "333 26 55 698",  "a@a.com"));
        clientModel.add(new ClientModel("AC","Angela", "Cerciello", "Via Selva, 19 Mariglianella", "081 885 43 16", "333 34 56 67", "a@a.com"));
        clientModel.add(new ClientModel("MP","Michela", "Panico", "Via Selva, 19 Mariglianella", "081 885 43 16", "323 32 32 32","a@a.com"));
        clientModel.add(new ClientModel("DC","Domenico", "Cerciello", "Via Selva, 19 Mariglianella", "081 885 43 16", "323 12 32 32","a@a.com"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clientitem_list, container, false);

        ButterKnife.bind(this, view);

        if (mColumnCount <= 1) {
            list.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            list.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        }
        adapter = new ClientAdapter(clientModel, mListener);
        list.setAdapter(adapter);
        return view;
    }


    @OnClick(R.id.fab)
    public void fabAction() {
        Intent i = new Intent(getContext(), AddClientsActivity.class);
        Bundle animation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_in_bottom, R.anim.no_changes).toBundle();
        startActivity(i, animation);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnClientInteractionListener) {
            mListener = (OnClientInteractionListener) context;
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
    public void setPresenter(ClientItemFragmentContract.Presenter presenter) {

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
    public void updateAdapterDataSource(ArrayList<ClientModel> clients) {
        if (clients != null) {
            adapter.updateDataSoure(clients);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.refreshClientList();

    }

    public interface OnClientInteractionListener {
        void onClientInteraction(ClientModel item);
    }
}
