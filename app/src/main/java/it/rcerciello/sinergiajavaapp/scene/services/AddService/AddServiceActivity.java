package it.rcerciello.sinergiajavaapp.scene.services.AddService;

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
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.widgets.CustomEditText;
import it.rcerciello.sinergiajavaapp.widgets.CustomSharedEditTextView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddServiceActivity extends AppCompatActivity implements AddServiceContract.View, CustomSaveButton.CustomSaveButtonInterface {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nome)
    CustomSharedEditTextView nome;

    @BindView(R.id.durata)
    CustomSharedEditTextView durata;

    @BindView(R.id.prezzo)
    CustomSharedEditTextView prezzo;


    @BindView(R.id.btnSave)
    CustomSaveButton saveButton;

    private AddServiceContract.Presenter mPresenter;


    private enum FieldRequired {
        NOME,
        DURATA,
        LANDLINE
    }

    private Map<FieldRequired, Boolean> areThereFiledRequired = new HashMap<>();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        ButterKnife.bind(this);

        mPresenter = new AddServicePresenter(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nome.getEditTextReference().setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        durata.getEditTextReference().setInputType(InputType.TYPE_CLASS_NUMBER);
        prezzo.getEditTextReference().setInputType(InputType.TYPE_CLASS_NUMBER);

        setTextChangedListener(nome, FieldRequired.NOME, true );
        setTextChangedListener(durata, FieldRequired.NOME, true );
        setTextChangedListener(prezzo, FieldRequired.NOME, true );


        saveButton.getButtonReference().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton.changeState();
                ServiceModel service = new ServiceModel("1", "Ad", nome.getText().toString(), Integer.valueOf( durata.getText().toString()), Float.valueOf(prezzo.getText().toString()));
                mPresenter.addService(service);
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
    public void setPresenter(AddServiceContract.Presenter presenter) {

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

        if (durata.getText().toString().isEmpty()) {
            durata.showError(getResources().getString(R.string.field_required));
            thereIsError = true;
        } else {
            durata.showError(null);
        }

        if (prezzo.getText().toString().isEmpty()) {
            prezzo.showError(getResources().getString(R.string.field_required));
            thereIsError = true;
        } else {
            prezzo.showError(null);
        }

        if (!thereIsError) {
            ServiceModel service = new ServiceModel("1", "Ad", nome.getText().toString(), Integer.valueOf( durata.getText().toString()), Float.valueOf(prezzo.getText().toString()));
            mPresenter.addService(service);
        }

        Toast.makeText(this, "Save", Toast.LENGTH_LONG).show();
    }
}
