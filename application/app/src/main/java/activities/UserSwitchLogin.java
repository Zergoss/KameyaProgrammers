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
import android.widget.TextView;

import ca.uottawa.cohab.R;
import helpers.Input;
import model.User;
import sql.DatabaseHelper;

public class UserSwitchLogin extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = activities.UserSwitchLogin.this;
    private DatabaseHelper databaseReference;
    private NestedScrollView nestedScrollView;

    private TextView textUsername;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private Input inputValidation;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_login);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }


    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textUsername = (TextView) findViewById(R.id.username);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

    }

    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
    }

    private void initObjects() {
        databaseReference = new DatabaseHelper(activity);
        inputValidation = new Input(activity);
        user = databaseReference.getUser(getIntent().getStringExtra("USERNAME"));
        textUsername.setText(user.getUsername());
    }


    @Override
    public void onClick(View v) {
        verifyFromSQLite();
    }


    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword,
                getString(R.string.error_message_password_empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextPassword(textInputEditTextPassword, textInputLayoutPassword,
                getString(R.string.error_message_password_invalid))) {
            return;
        }
        String aPass = textInputEditTextPassword.getText().toString().trim();
        String password = databaseReference.checkPassword(user.getUsername());

        if (aPass.equals(password)) {
            Intent accountsIntent = new Intent(activity, MainActivity.class);
            accountsIntent.putExtra("USERNAME", user.getUsername());
            emptyInputEditText();
            startActivity(accountsIntent);
            finish();
        }
        Snackbar.make(nestedScrollView, ("The password is " + password), Snackbar.LENGTH_LONG).show();

    }


    private void emptyInputEditText() {
        textInputEditTextPassword.setText(null);
    }
}