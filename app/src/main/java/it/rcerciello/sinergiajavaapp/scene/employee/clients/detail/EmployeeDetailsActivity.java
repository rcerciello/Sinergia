package it.rcerciello.sinergiajavaapp.scene.employee.clients.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.irozon.library.HideKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.rcerciello.sinergiajavaapp.GlobalUtils;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.SaveButton.ButtonStates;
import it.rcerciello.sinergiajavaapp.SaveButton.CustomSaveButton;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.network.ApiClient;
import it.rcerciello.sinergiajavaapp.widgets.CustomEditText;
import it.rcerciello.sinergiajavaapp.widgets.CustomSharedEditTextView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EmployeeDetailsActivity extends AppCompatActivity implements EmployeeDetailsContract.View, CustomSaveButton.CustomSaveButtonInterface {


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

    @BindView(R.id.email)
    CustomSharedEditTextView email;

    @BindView(R.id.btnSave)
    CustomSaveButton saveButton;

    @BindView(R.id.ivProfile)
    ImageView ivProfile;
    private EmployeeDetailsContract.Presenter mPesenter;
    private ClientModel clientModel;

    private enum FieldType {
        TEXT,
        EMAIL,
        PHONE_NUMBER
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        HideKey.initialize(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            clientModel = ApiClient.getGson().fromJson(extras.getString("ClientModel"), ClientModel.class);
        }

        mPesenter = new EmployeeDetailsPresenter(this);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        setKeyboardType();
        initLayout();

        setTextChangedListener(nome, FieldType.TEXT, true);
        setTextChangedListener(cognome, FieldType.TEXT, true);
        setTextChangedListener(indirizzo, FieldType.TEXT, false);
        setTextChangedListener(landline, FieldType.TEXT, true);
        setTextChangedListener(mobilePhone, FieldType.TEXT, false);
        setTextChangedListener(email, FieldType.TEXT, true);
    }

    private void setTextChangedListener(final CustomEditText editText, final FieldType type, final boolean mandatory) {
        if (editText != null) {
            final EditText wrappedEditText = editText.getEditTextReference();
            if (wrappedEditText != null) {
                wrappedEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        showSaveButton();
                        enableSaveButton();
                        switch (type) {
                            case TEXT:
                                if (mandatory) {
                                    validateText(editText);
                                }
                                break;
                            case EMAIL:
                                validateEmail(editText);
                                break;
                            default:
                                break;
                        }
                    }


                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        }
    }

    /**
     * Validate the mandatory text field, providing a custom error message if not valid.
     *
     * @param editText the field to be validated
     */
    private void validateText(CustomEditText editText) {
        boolean result;
        String value = editText.getEditTextReference().getText().toString();
        result = !value.trim().isEmpty();
        if (!result) {
            editText.showError(getResources().getString(R.string.empty_fields));
        } else {
            editText.showError(null);
        }

        if (!result) {
            disableSaveButton();
        }
    }

    /**
     * Validate the email field to not be empty and also be a valid email.
     *
     * @param editText the email field
     */
    private void validateEmail(CustomEditText editText) {
        boolean result;
        String value = editText.getEditTextReference().getText().toString();
        if (value.trim().isEmpty()) {
            result = false;
            editText.showError(getResources().getString(R.string.empty_fields));
        } else if (!GlobalUtils.isEmailValidated(value)) {
            result = false;
            editText.showError(getResources().getString(R.string.invalid_email));
        } else {
            result = true;
            editText.showError(null);
        }
        if (!result) {
            disableSaveButton();
        }
    }


    private void initLayout() {
        if (clientModel != null) {
            if (clientModel.getName() != null && !clientModel.getName().isEmpty()) {
                nome.setText(clientModel.getName());
            }

            if (clientModel.getSurname() != null && !clientModel.getSurname().isEmpty()) {
                cognome.setText(clientModel.getSurname());
            }

            if (clientModel.getAddress() != null && !clientModel.getAddress().isEmpty()) {
                indirizzo.setText(clientModel.getAddress());
            }

            if (clientModel.getLandlinePhone() != null && !clientModel.getLandlinePhone().isEmpty()) {
                landline.setText(clientModel.getLandlinePhone());
            }

            if (clientModel.getMobile_phone() != null && !clientModel.getMobile_phone().isEmpty()) {
                mobilePhone.setText(clientModel.getMobile_phone());
            }

            if (clientModel.getEmail() != null && !clientModel.getEmail().isEmpty()) {
                email.setText(clientModel.getEmail());
            }
        }
    }

    private void setKeyboardType() {
        landline.getEditTextReference().setInputType(
                InputType.TYPE_CLASS_PHONE);

        mobilePhone.getEditTextReference().setInputType(
                InputType.TYPE_CLASS_PHONE);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void setPresenter(EmployeeDetailsContract.Presenter presenter) {
        mPesenter = presenter;
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
    public void updateImage(String url) {
        if (url != null && !url.isEmpty()) {
            Glide.with(this)
                    .load(url)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(ivProfile);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void saveButtonClick() {
        saveButton.changeState();
        mPesenter.editClient(new ClientModel());
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

}
