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
import android.widget.TextView;

import ca.uottawa.cohab.R;
import helpers.Input;
import model.User;
import sql.DatabaseHelper;

public class UserEdit extends AppCompatActivity {

    private final AppCompatActivity activity = this;
    private DatabaseHelper db;
    private Button btn_change;

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
        btn_change = (Button) findViewById(R.id.btn_changePic);

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
        user = db.getUser(getIntent().getStringExtra("USERNAME"));
    }
    private void loadUserInfo() {
        //Load info from database
        profile_username_layout.setHint("Username: " + user.getUsername());
    }
    //TODO Verifier la syntaxe
    btn_change.setOnClickListener(new View.OnClickListener(){
        @Override
                public void onClick(View v){
            startActivity(new Intent(MainActivity.this, activity_change_picture.class));
        }
    }


    public void picChange(View view){
        //Intent intent = new Intent(myView.getContext(), CAMERA.class);
        //startActivity(intent);
    }
    public void deleteUser(View view){
        db.deleteUser(user);
        Intent intent = new Intent(view.getContext(), Login.class);
        startActivity(intent);
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
            Intent intent = new Intent(UserEdit.this, MainActivity.class);
            intent.putExtra("USERNAME", profile_username_edit.getText().toString().trim());
            emptyInputEditText();
            startActivity(intent);

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
