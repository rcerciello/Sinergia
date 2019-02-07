package it.rcerciello.sinergiajavaapp.scene.clients.list;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.scene.clients.add_clients.AddClientsActivity;
import it.rcerciello.sinergiajavaapp.scene.clients.list.adapter.ClientAdapter;
import timber.log.Timber;

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

    @BindView(R.id.etFilter)
    EditText etFilter;


    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private ClientItemFragmentContract.Presenter mPresenter;
    private ArrayList<ClientModel> clientModel = new ArrayList<>();
    private ClientAdapter adapter;
    private List<ClientModel> clients;

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

        etFilter.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter

                Timber.d("Filtraggio clienti");
                Timber.d("s => "+s.toString());
                if (s.toString().trim().isEmpty()) {
                    resetAdapter();
                } else {
                    filterClientiOnAdapter(s.toString().trim());
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
        adapter.setOriginalData(clients);
    }

    public void filterClientiOnAdapter(String txt) {
        adapter.getFilter().filter(txt);
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
    public void updateAdapterDataSource(List<ClientModel> clients) {
        if (clients != null) {
            this.clients = clients;
            etFilter.setClickable(true);
            adapter.updateDataSoure(clients);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        etFilter.setClickable(false);
        mPresenter.refreshClientList();

    }

    public interface OnClientInteractionListener {
        void onClientInteraction(ClientModel item);
    }
}
