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
        return fragment;
    }

    public ClientItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new ClientItemPresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clientitem_list, container, false);

        ButterKnife.bind(this, view);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
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
