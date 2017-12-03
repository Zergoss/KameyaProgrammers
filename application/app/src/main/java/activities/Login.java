package activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import ca.uottawa.cohab.R;
import helpers.Input;
import model.User;
import sql.DatabaseHelper;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = Login.this;
    private DatabaseHelper databaseReference;
    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextUsername;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private Input inputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }


    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutUsername = (TextInputLayout) findViewById(R.id.textInputLayoutUsername);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextUsername = (TextInputEditText) findViewById(R.id.textInputEditTextUsername);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);

    }

    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    private void initObjects() {
        databaseReference = new DatabaseHelper(activity);
        inputValidation = new Input(activity);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), Register.class);
                emptyInputEditText();
                startActivity(intentRegister);
                break;
        }
    }


    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextUsername, textInputLayoutUsername, getString(R.string.error_message_username_empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextUsername(textInputEditTextUsername, textInputLayoutUsername,
                getString(R.string.error_message_username_invalid))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword,
                getString(R.string.error_message_password_empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextPassword(textInputEditTextPassword, textInputLayoutPassword,
                getString(R.string.error_message_password_invalid))) {
            return;
        }

        if (databaseReference.checkUser(textInputEditTextUsername.getText().toString().trim())) {
            String aPass = textInputEditTextPassword.getText().toString().trim();
            String password = databaseReference.checkPassword(textInputEditTextUsername.getText().toString().trim());

            if (aPass.equals(password)) {
                Intent accountsIntent = new Intent(activity, MainActivity.class);
                accountsIntent.putExtra("ID", databaseReference.getUser(textInputEditTextUsername.getText().toString().trim()).getId());
                emptyInputEditText();
                startActivity(accountsIntent);
            } else {
                Snackbar.make(nestedScrollView, ("The password is " + password), Snackbar.LENGTH_LONG).show();
            }
            return;
        }
        Snackbar.make(nestedScrollView, getString(R.string.error_valid_username_exist), Snackbar.LENGTH_LONG).show();

    }


    private void emptyInputEditText() {
        textInputEditTextUsername.setText(null);
        textInputEditTextPassword.setText(null);
    }
}