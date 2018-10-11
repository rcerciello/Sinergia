package it.rcerciello.sinergiajavaapp.scene.services.detail;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.rcerciello.sinergiajavaapp.GlobalUtils;
import it.rcerciello.sinergiajavaapp.PermissionConstants;
import it.rcerciello.sinergiajavaapp.PermissionsUtils;
import it.rcerciello.sinergiajavaapp.R;
import it.rcerciello.sinergiajavaapp.SaveButton.ButtonStates;
import it.rcerciello.sinergiajavaapp.SaveButton.CustomSaveButton;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceEditModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.data.network.ApiClient;
import it.rcerciello.sinergiajavaapp.utils.GeneralConstants;
import it.rcerciello.sinergiajavaapp.widgets.CustomEditText;
import it.rcerciello.sinergiajavaapp.widgets.CustomSharedEditTextView;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ServiceDetailsActivity extends AppCompatActivity implements ServiceDetailsContract.View, CustomSaveButton.CustomSaveButtonInterface {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nome_servizio)
    CustomSharedEditTextView nome_servizio;

    @BindView(R.id.durata_servizio)
    CustomSharedEditTextView durata_servizio;

    @BindView(R.id.prezzo)
    CustomSharedEditTextView prezzo;

    @BindView(R.id.identificativo)
    CustomSharedEditTextView identificativo;

    @BindView(R.id.btnSave)
    CustomSaveButton saveButton;

    @BindView(R.id.camera)
    ImageView camera;

    @BindView(R.id.ivProfile)
    ImageView ivProfile;

    @BindView(R.id.root)
    RelativeLayout root;

    private ServiceDetailsContract.Presenter mPesenter;
    private ServiceEditModel serviceModel;
    private String imageToUpload = null;

    private enum FieldType {
        TEXT,
        EMAIL,
        PHONE_NUMBER
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ServiceModel backendModel = ApiClient.getGson().fromJson(extras.getString("ServiceModel"), ServiceModel.class);
            serviceModel = new ServiceEditModel();
            serviceModel.setDuration(backendModel.getDuration());
            serviceModel.setName(backendModel.getName());
            serviceModel.setPrice(backendModel.getPrice());
            serviceModel.setServiceIdentifier(backendModel.getServiceIdentifier());
            serviceModel.setId(backendModel.getServicePrimaryKeyModel().getPrimaryKey());
        }

        mPesenter = new ServiceDetailsPresenter(this);

        toolbar.setNavigationOnClickListener((View v) ->
                onBackPressed());

        initLayout();

        setTextChangedListener(nome_servizio, FieldType.TEXT, true);
        setTextChangedListener(durata_servizio, FieldType.TEXT, true);
        setTextChangedListener(prezzo, FieldType.TEXT, true);
        setTextChangedListener(identificativo, FieldType.TEXT, true);
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
                                    validateText(editText, type);
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
     * @return true if the text field is valid
     */
    private boolean validateText(CustomEditText editText, FieldType fieldType) {
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
        return result;
    }

    /**
     * Validate the email field to not be empty and also be a valid email.
     *
     * @param editText the email field
     * @return true if the email field is valid
     */
    private boolean validateEmail(CustomEditText editText) {
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
        return result;
    }


    private void initLayout() {
        nome_servizio.getEditTextReference().setInputType(InputType.TYPE_CLASS_TEXT);
        durata_servizio.getEditTextReference().setInputType(InputType.TYPE_CLASS_NUMBER);
        prezzo.getEditTextReference().setInputType(InputType.TYPE_CLASS_NUMBER);
        identificativo.getEditTextReference().setInputType(InputType.TYPE_CLASS_TEXT);

        if (serviceModel != null) {
            if (serviceModel.getName() != null && !serviceModel.getName().isEmpty()) {
                nome_servizio.setText(serviceModel.getName());
            }

            durata_servizio.setText(String.valueOf(serviceModel.getDuration()));
            prezzo.setText(String.valueOf(serviceModel.getPrice()));

            if (serviceModel.getServiceIdentifier() != null && !serviceModel.getServiceIdentifier().isEmpty())
                identificativo.setText(String.valueOf(serviceModel.getServiceIdentifier()));
        }
        saveButton.setButtonState(ButtonStates.ENABLED);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void setPresenter(ServiceDetailsContract.Presenter presenter) {
        mPesenter = presenter;
    }

    @Override
    public void logout() {

    }

    @Override
    public void showSnackbarError(String message) {
        saveButton.changeState();
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show();
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
        if (identificativo.getText() != null && !identificativo.getText().isEmpty()) {
            serviceModel.setServiceIdentifier(identificativo.getText());
        }


        if (durata_servizio.getText() != null && !durata_servizio.getText().isEmpty()) {
            serviceModel.setDuration(Integer.valueOf(durata_servizio.getText()));
        }

        if (prezzo.getText() != null && !prezzo.getText().isEmpty()) {
            serviceModel.setPrice(Float.valueOf(prezzo.getText()));
        }

        if (nome_servizio.getText() != null && !nome_servizio.getText().isEmpty())
        {
            serviceModel.setName(nome_servizio.getText());
        }


        mPesenter.editService(serviceModel);
    }

    @OnClick({R.id.camera})
    public void takePhoto(View v) {
        PermissionsUtils.checkPermission(this, android.Manifest.permission.CAMERA, PermissionConstants.CAMERA);

        final String[] options = {"Scatta Foto", "Scegli dalla galleria", "Annulla"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(options, (dialog, which) -> {
            Intent intent;
            String optionChoice = options[which].toString();

            switch (which) {
                case 0:
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        try {
                            File photoFile = GlobalUtils.createImageFile(getApplicationContext());
                            // Continue only if the File was successfully created
                            if (photoFile != null) {
                                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                                        "sinergia.android.fileprovider",
                                        photoFile);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                startActivityForResult(intent, GeneralConstants.INTENT_TAKE_PHOTO);
                            }
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                            ex.printStackTrace();
                        }
                    }
                    break;

                case 1:
                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, GeneralConstants.INTENT_CHOOSE_PHOTO);//one can be replaced with any action code
                    break;

                default:
                    dialog.dismiss();
                    break;
            }
        });

        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intentResponse) {
        super.onActivityResult(requestCode, resultCode, intentResponse);

        switch (requestCode) {
            case GeneralConstants.INTENT_TAKE_PHOTO: //Photo from camera
                if (resultCode == RESULT_OK) {
                    if (imageToUpload != null) {
                        Bitmap photo = BitmapFactory.decodeFile(imageToUpload);
                        mPesenter.uploadPhoto();
                        //TODO Load Photo
                    }
                }
                break;

            case GeneralConstants.INTENT_CHOOSE_PHOTO: //Photo  from gallery
                if (resultCode == RESULT_OK) {
                    Uri photoUri = intentResponse.getData();
                    if (photoUri != null) {
                        File newFilePhoto = new File(photoUri.toString());
                        try {
                            Bitmap photo = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                            String path = newFilePhoto.getAbsolutePath();
                            mPesenter.uploadPhoto();
                            //TODO Load Photo
                        } catch (IOException e) {
                            Timber.e(e, "Failed to fetch photo from gallery, photoUri: %s", photoUri);
                        }
                    }
                }
                break;

            default:
                break;
        }
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


    private void hideSaveButton() {
        saveButton.setSaveButtonVisibility(View.INVISIBLE);
    }

}
