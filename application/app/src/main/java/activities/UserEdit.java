package activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ca.uottawa.cohab.R;
import helpers.Input;
import model.User;
import sql.DatabaseHelper;

public class UserEdit extends AppCompatActivity {

    private final AppCompatActivity activity = this;
    private DatabaseHelper db;

    private User user;
    private Input inputValidation;
    private NestedScrollView nestedScrollView;

    private TextInputLayout profile_username_layout;
    private TextInputLayout profile_password_layout;
    private TextInputLayout profile_passwordConfirm_layout;

    private TextInputEditText profile_username_edit;
    private TextInputEditText profile_password_edit;
    private TextInputEditText profile_passwordConfirm_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        initViews();
        initObjects();
        loadUserInfo();
    }

    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        profile_username_layout = (TextInputLayout) findViewById(R.id.profile_username_layout);
        profile_password_layout = (TextInputLayout) findViewById(R.id.profile_password_layout);
        profile_passwordConfirm_layout = (TextInputLayout) findViewById(R.id.profile_passwordConfirm_layout);

        profile_username_edit = (TextInputEditText) findViewById(R.id.profile_username_edit);
        profile_password_edit = (TextInputEditText) findViewById(R.id.profile_password_edit);
        profile_passwordConfirm_edit = (TextInputEditText) findViewById(R.id.profile_passwordConfirm_edit);

    }
    private void initObjects() {
        inputValidation = new Input(activity);
        db = new DatabaseHelper(activity);
        user = db.getUser(getIntent().getIntExtra("CONNECTEDUSER", -1));
    }
    private void loadUserInfo() {
        //Load info from database
        profile_username_layout.setHint("Username: " + user.getUsername());
    }

    public void deleteUser(View view){
        db.deleteUser(user);
        Intent intent = new Intent(view.getContext(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "User deleted", Toast.LENGTH_SHORT).show();
        finish();
    }
    public void saveUser(View view){
        updateDataToSQLite();
    }

    private void updateDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(profile_username_edit, profile_username_layout, getString(R.string.error_message_username_empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextUsername(profile_username_edit, profile_username_layout, getString(R.string.error_message_username_invalid))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(profile_password_edit, profile_password_layout, getString(R.string.error_message_password_empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextPassword(profile_password_edit, profile_password_layout,
                getString(R.string.error_message_password_invalid))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(profile_password_edit, profile_passwordConfirm_edit,
                profile_passwordConfirm_layout, getString(R.string.error_password_match))) {
            return;
        }

        if (!db.checkUser(profile_username_edit.getText().toString().trim())) {
            user.setUsername(profile_username_edit.getText().toString().trim());
            user.setPassword(profile_password_edit.getText().toString().trim());

            db.updateUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.success_user_edit), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
            loadUserInfo();
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_username_exists), Snackbar.LENGTH_LONG).show();
        }


    }


    private void emptyInputEditText() {
        profile_username_edit.setText(null);
        profile_password_edit.setText(null);
        profile_passwordConfirm_edit.setText(null);
    }

}
