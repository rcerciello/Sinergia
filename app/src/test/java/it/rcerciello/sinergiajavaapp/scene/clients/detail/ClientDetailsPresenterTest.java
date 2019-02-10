package it.rcerciello.sinergiajavaapp.scene.clients.detail;

import org.junit.Assert;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import it.rcerciello.sinergiajavaapp.data.managers.ClientNetworkLayer;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;

public class ClientDetailsPresenterTest {


    @Mock
    private ClientNetworkLayer clientNetworkLayer;

    @Mock
    private ClientDetailsContract.View view;

    ClientDetailsPresenter instance;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        instance = new ClientDetailsPresenter(view);
    }



    @Test
    public void editClient() {
        ClientModel modelToTest = new ClientModel();
        clientNetworkLayer.editCustomer(modelToTest, new APICallback<Boolean>() {
            @Override
            public void onSuccess(Boolean object) {
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
//        instance.editClient(modelToTest);
//        verify(view).showSnackbarError("Ciao");
    }

    @Test
    public void deleteClient() {
        clientNetworkLayer.deleteCustomer(null, new APICallback<Boolean>() {
            @Override
            public void onSuccess(Boolean object) {

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
                Assert.fail();
            }
        });
    }
}