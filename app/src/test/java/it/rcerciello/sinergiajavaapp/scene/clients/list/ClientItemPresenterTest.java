package it.rcerciello.sinergiajavaapp.scene.clients.list;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import it.rcerciello.sinergiajavaapp.data.managers.ClientNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientListResponseModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;
import it.rcerciello.sinergiajavaapp.scene.clients.detail.ClientDetailsContract;
import it.rcerciello.sinergiajavaapp.scene.clients.detail.ClientDetailsPresenter;

import static org.junit.Assert.*;

public class ClientItemPresenterTest {

    @Mock
    private ClientNetworkLayer clientNetworkLayer;

    @Mock
    private ClientItemFragmentContract.View view;

    ClientItemPresenter instance;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        instance = new ClientItemPresenter(view);
    }

    @Test
    public void refreshClientList() {
        clientNetworkLayer.getClients(new APICallback<ClientListResponseModel>() {
            @Override
            public void onSuccess(ClientListResponseModel object) {
                if (object!=null &&  object.getClients()!=null && object.getClients().size()>0)
                {
                }else
                {
                    Assert.fail();
                }
            }

            @Override
            public void onFailure(String error) {
                Assert.fail();
            }

            @Override
            public void onSessionExpired() {

            }

            @Override
            public void onFailure(boolean isFailure) {

            }
        });
    }
}