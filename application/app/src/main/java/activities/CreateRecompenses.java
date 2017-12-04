package activities;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.uottawa.cohab.R;
import helpers.Input;
import model.Recompenses;
import model.User;
import sql.DatabaseHelper;


public class CreateRecompenses extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = CreateRecompenses.this;
    private DatabaseHelper databaseReference; 
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutDescription;
    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextDescription;
    private Input inputValidation;
    private User user;

    private Button ButtonSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recompenses);

        initViews();
        initObjects();
        initListeners();

    }

    private void initViews() {

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutUsername);
        textInputLayoutDescription = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextUsername);
        textInputEditTextDescription = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        ButtonSave = (Button) findViewById(R.id.button);
    }

    private void initObjects() {
        databaseReference = new DatabaseHelper(activity);
        inputValidation = new Input(activity);

        user = databaseReference.getUser(getIntent().getIntExtra("VIEWUSER", -1));
    }

    private void initListeners() {
        ButtonSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Recompenses reward = new Recompenses();
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name_empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextDescription, textInputLayoutDescription,
                getString(R.string.error_message_description_empty))) {
            return;
        }
        reward.setName(textInputEditTextName.getText().toString().trim());
        reward.setDescription(textInputEditTextDescription.getText().toString().trim());
        reward.setUser(user);

        databaseReference.addReward(reward);

        textInputEditTextName.setText(null);
        textInputEditTextDescription.setText(null);
        Toast.makeText(getApplicationContext(), "A ete ajoutee", Toast.LENGTH_SHORT).show();

    }

}