package it.rcerciello.sinergiajavaapp.scene.clients.AddClients;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.SaveButton.ButtonStates;
import it.rcerciello.sinergiajavaapp.SaveButton.CustomSaveButton;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.widgets.CustomEditText;
import it.rcerciello.sinergiajavaapp.widgets.CustomSharedEditTextView;
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

    @BindView(R.id.nextAppointment)
    CustomSharedEditTextView nextAppointment;


    @BindView(R.id.email)
    CustomSharedEditTextView email;


    @BindView(R.id.btnSave)
    CustomSaveButton saveButton;

    private AddClientsContract.Presenter mPresenter;


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

        mPresenter = new AddClientsPresenter(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nome.getEditTextReference().setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        cognome.getEditTextReference().setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        indirizzo.getEditTextReference().setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        landline.getEditTextReference().setInputType(InputType.TYPE_CLASS_PHONE);
        mobilePhone.getEditTextReference().setInputType(InputType.TYPE_CLASS_PHONE);
        nextAppointment.getEditTextReference().setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        email.getEditTextReference().setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        areThereFiledRequired.put(FieldRequired.NOME, false);
        areThereFiledRequired.put(FieldRequired.COGNOME, false);
        areThereFiledRequired.put(FieldRequired.LANDLINE, false);

        setTextChangedListener(nome, FieldRequired.NOME, true );
        setTextChangedListener(cognome, FieldRequired.COGNOME, true );
        setTextChangedListener(landline, FieldRequired.LANDLINE, true );
        setTextChangedListener(email, FieldRequired.EMAIL, true );


        saveButton.getButtonReference().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton.changeState();
                ClientModel  client = new ClientModel(nome.getText().toString(), cognome.getText().toString(), indirizzo.getText().toString(), landline.getText().toLowerCase(), mobilePhone.getText().toString(), nextAppointment.getText().toString(), email.toString());
                mPresenter.addClient(client);
            }
        });
    }

    private void setTextChangedListener(final CustomEditText editText,final FieldRequired type, final boolean mandatory) {
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
                                }else
                                {
                                    areThereFiledRequired.remove(FieldRequired.NOME);
                                    areThereFiledRequired.put(FieldRequired.NOME, false);
                                }
                                break;
                            case COGNOME:
                                if (!s.toString().isEmpty()) {
                                    areThereFiledRequired.remove(FieldRequired.COGNOME);
                                    areThereFiledRequired.put(FieldRequired.COGNOME, true);
                                }else
                                {
                                    areThereFiledRequired.remove(FieldRequired.COGNOME);
                                    areThereFiledRequired.put(FieldRequired.COGNOME, false);
                                }
                                break;
                            case LANDLINE:
                                if (!s.toString().isEmpty()) {
                                    areThereFiledRequired.remove(FieldRequired.LANDLINE);
                                    areThereFiledRequired.put(FieldRequired.LANDLINE, true);
                                }else
                                {
                                    areThereFiledRequired.remove(FieldRequired.LANDLINE);
                                    areThereFiledRequired.put(FieldRequired.LANDLINE, false);
                                }
                                break;

                            case EMAIL:
                                if (!s.toString().isEmpty()) {
                                    areThereFiledRequired.remove(FieldRequired.EMAIL);
                                    areThereFiledRequired.put(FieldRequired.EMAIL, true);
                                }else
                                {
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

    }

    @Override
    public void closeView() {
        onBackPressed();
    }

    @Override
    public void saveButtonClick() {
        boolean thereIsError = false;
        if (nome.getText().toString().isEmpty()) {
            nome.showError(getResources().getString(R.string.field_required));
            thereIsError = true;
        } else {
            nome.showError(null);
        }

        if (cognome.getText().toString().isEmpty()) {
            cognome.showError(getResources().getString(R.string.field_required));
            thereIsError = true;
        } else {
            cognome.showError(null);
        }

        if (landline.getText().toString().isEmpty()) {
            landline.showError(getResources().getString(R.string.field_required));
            thereIsError = true;
        } else {
            landline.showError(null);
        }

        if (!thereIsError) {
            ClientModel model = new ClientModel(nome.getText().toString(), cognome.getText().toString(), indirizzo.getText().toString(), landline.getText().toString(), mobilePhone.getText().toString(), nextAppointment.getText().toString(), email.getText().toString());
            mPresenter.addClient(model);
        }

        Toast.makeText(this, "Save", Toast.LENGTH_LONG).show();
    }
}
