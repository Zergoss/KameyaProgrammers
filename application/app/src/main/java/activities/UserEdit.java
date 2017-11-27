package activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    private EditText profile_username_edit;
    private EditText profile_password_edit;
    private EditText profile_passwordConfirm_edit;

    private Button btn_changePic;
    private Button btn_delete_profile;
    private Button btn_saveEditProfile;


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

        profile_username_edit = (EditText) findViewById(R.id.profile_username_edit);
        profile_password_edit = (EditText) findViewById(R.id.profile_password_edit);
        profile_passwordConfirm_edit = (EditText) findViewById(R.id.profile_passwordConfirm_edit);

        btn_changePic = (Button) findViewById(R.id.btn_changePic);
        btn_delete_profile = (Button) findViewById(R.id.btn_delete_profile);
        btn_saveEditProfile = (Button) findViewById(R.id.btn_saveEditProfile);
    }
    private void initObjects() {
        inputValidation = new Input(activity);
        db = new DatabaseHelper(activity);
        user = new User();
    }
    private void loadUserInfo() {
        //Load info from database
        user = db.getUser(getIntent().getStringExtra("USERNAME"));
        profile_username_edit.setText(user.getUsername());
    }


    public void picChange(View view){
        //Intent intent = new Intent(myView.getContext(), CAMERA.class);
        //startActivity(intent);
    }
    public void deleteUser(View view){
        db.deleteUser(user);
        Intent intent = new Intent(view.getContext(), Login.class);
        startActivity(intent);
    }
    public void saveUser(View view){
        postDataToSQLite();
        db.deleteUser(user);
        Intent intent = new Intent(view.getContext(), Login.class);
        startActivity(intent);
    }

    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextUsername, textInputLayoutUsername, getString(R.string.error_message_username_empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextUsername(textInputEditTextUsername, textInputLayoutUsername, getString(R.string.error_message_username_invalid))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password_empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextPassword(textInputEditTextPassword, textInputLayoutPassword,
                getString(R.string.error_message_password_invalid))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }

        if (!databaseHelper.checkUser(textInputEditTextUsername.getText().toString().trim())) {

            user.setUsername(textInputEditTextUsername.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());

            databaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_username_exists), Snackbar.LENGTH_LONG).show();
        }


    }


    private void emptyInputEditText() {
        textInputEditTextUsername.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }

}
