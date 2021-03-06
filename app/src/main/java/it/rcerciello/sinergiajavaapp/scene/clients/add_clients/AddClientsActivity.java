package it.rcerciello.sinergiajavaapp.scene.clients.add_clients;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.irozon.library.HideKey;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.SaveButton.ButtonStates;
import it.rcerciello.sinergiajavaapp.SaveButton.CustomSaveButton;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModelAdd;
import it.rcerciello.sinergiajavaapp.data.network.ApiClient;
import it.rcerciello.sinergiajavaapp.widgets.CustomEditText;
import it.rcerciello.sinergiajavaapp.widgets.CustomSharedEditTextView;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddClientsActivity extends AppCompatActivity implements AddClientsContract.View, CustomSaveButton.CustomSaveButtonInterface {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nome)
    CustomSharedEditTextView nome;

    @BindView(R.id.cognome)
    CustomSharedEditTextView cognome;

    @BindView(R.id.indirizzo)
    CustomSharedEditTextView indirizzo;

    @BindView(R.id.landline)
    CustomSharedEditTextView landline;

    @BindView(R.id.mobilePhone)
    CustomSharedEditTextView mobilePhone;

    @BindView(R.id.identificativo)
    CustomSharedEditTextView identificativo;


    @BindView(R.id.email)
    CustomSharedEditTextView email;


    @BindView(R.id.btnSave)
    CustomSaveButton saveButton;

    @BindView(R.id.root)
    RelativeLayout root;

    private AddClientsContract.Presenter mPresenter;
    private boolean comeFromCalendar = false;

    private enum FieldRequired {
        NOME,
        COGNOME,
        LANDLINE,
        EMAIL
    }

    private Map<FieldRequired, Boolean> areThereFiledRequired = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clients);
        ButterKnife.bind(this);
        HideKey.initialize(this);
        mPresenter = new AddClientsPresenter(this);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        comeFromCalendar = getIntent().getBooleanExtra("comeFromCalendar", false);

        nome.getEditTextReference().setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        cognome.getEditTextReference().setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        indirizzo.getEditTextReference().setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        landline.getEditTextReference().setInputType(InputType.TYPE_CLASS_PHONE);
        mobilePhone.getEditTextReference().setInputType(InputType.TYPE_CLASS_PHONE);
        identificativo.getEditTextReference().setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        email.getEditTextReference().setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        areThereFiledRequired.put(FieldRequired.NOME, false);
        areThereFiledRequired.put(FieldRequired.COGNOME, false);
        areThereFiledRequired.put(FieldRequired.LANDLINE, false);

        setTextChangedListener(nome, FieldRequired.NOME, true);
        setTextChangedListener(cognome, FieldRequired.COGNOME, true);
        setTextChangedListener(landline, FieldRequired.LANDLINE, true);
        setTextChangedListener(email, FieldRequired.EMAIL, true);


        saveButton.getButtonReference().setOnClickListener(v -> {
            saveButton.changeState();
            ClientModelAdd client = new ClientModelAdd(identificativo.getText(), nome.getText(), cognome.getText(), indirizzo.getText(), landline.getText().toLowerCase(), mobilePhone.getText(), email.getText());
            mPresenter.addClient(client);
        });
    }

    private void setTextChangedListener(final CustomEditText editText, final FieldRequired type, final boolean mandatory) {
        if (editText != null) {
            final EditText wrappedEditText = editText.getEditTextReference();
            if (wrappedEditText != null) {
                wrappedEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }


                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        switch (type) {
                            case NOME:
                                if (!s.toString().isEmpty()) {
                                    areThereFiledRequired.remove(FieldRequired.NOME);
                                    areThereFiledRequired.put(FieldRequired.NOME, true);
                                } else {
                                    areThereFiledRequired.remove(FieldRequired.NOME);
                                    areThereFiledRequired.put(FieldRequired.NOME, false);
                                }
                                break;
                            case COGNOME:
                                if (!s.toString().isEmpty()) {
                                    areThereFiledRequired.remove(FieldRequired.COGNOME);
                                    areThereFiledRequired.put(FieldRequired.COGNOME, true);
                                } else {
                                    areThereFiledRequired.remove(FieldRequired.COGNOME);
                                    areThereFiledRequired.put(FieldRequired.COGNOME, false);
                                }
                                break;
                            case LANDLINE:
                                if (!s.toString().isEmpty()) {
                                    areThereFiledRequired.remove(FieldRequired.LANDLINE);
                                    areThereFiledRequired.put(FieldRequired.LANDLINE, true);
                                } else {
                                    areThereFiledRequired.remove(FieldRequired.LANDLINE);
                                    areThereFiledRequired.put(FieldRequired.LANDLINE, false);
                                }
                                break;

                            case EMAIL:
                                if (!s.toString().isEmpty()) {
                                    areThereFiledRequired.remove(FieldRequired.EMAIL);
                                    areThereFiledRequired.put(FieldRequired.EMAIL, true);
                                } else {
                                    areThereFiledRequired.remove(FieldRequired.EMAIL);
                                    areThereFiledRequired.put(FieldRequired.EMAIL, false);
                                }
                        }

                        showSaveButton();
                        if (areAllFieldValidated()) {
                            enableSaveButton();
                        } else {
                            disableSaveButton();
                        }
                    }


                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        }
    }


    private boolean areAllFieldValidated() {
        for (Boolean item : areThereFiledRequired.values()) {
            if (!item) {
                return false;
            }
        }

        return true;
    }


    private void enableSaveButton() {
        saveButton.setButtonState(ButtonStates.ENABLED);
    }


    private void disableSaveButton() {
        saveButton.setButtonState(ButtonStates.DISABLED);
    }

    private void showSaveButton() {
        saveButton.setSaveButtonVisibility(View.VISIBLE);
    }


    @Override
    public void onBackPressed() {
        if (comeFromCalendar) {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }
        super.onBackPressed();
        overridePendingTransition(R.anim.no_changes, R.anim.slide_out_top);

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void setPresenter(AddClientsContract.Presenter presenter) {

    }

    @Override
    public void logout() {

    }

    @Override
    public void showSnackbarError(String message) {

        Snackbar snackbar = Snackbar
                .make(root, message, Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    @Override
    public void closeView(ClientModelAdd model) {
        Timber.d("come from calendar = "+comeFromCalendar);
        if (comeFromCalendar) {
            Intent returnIntent = new Intent();
            String data = ApiClient.getGson().toJson(model);
            Timber.d("modello = "+data);
            returnIntent.putExtra("name", "Cliente");
            returnIntent.putExtra("modello", data);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else {
            onBackPressed();
        }
    }


    @Override
    public void saveButtonClick() {
        boolean thereIsError = false;
        if (nome.getText().isEmpty()) {
            nome.showError(getResources().getString(R.string.field_required));
            thereIsError = true;
        } else {
            nome.showError(null);
        }

        if (cognome.getText().isEmpty()) {
            cognome.showError(getResources().getString(R.string.field_required));
            thereIsError = true;
        } else {
            cognome.showError(null);
        }

        if (landline.getText().isEmpty()) {
            landline.showError(getResources().getString(R.string.field_required));
            thereIsError = true;
        } else {
            landline.showError(null);
        }

        if (!thereIsError) {
            ClientModelAdd model = new ClientModelAdd(identificativo.getText(), nome.getText(), cognome.getText(), indirizzo.getText(), landline.getText(), mobilePhone.getText(), email.getText());
            mPresenter.addClient(model);
        }

        Toast.makeText(this, "Save", Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeStateButton() {
        saveButton.changeState();
    }
}
