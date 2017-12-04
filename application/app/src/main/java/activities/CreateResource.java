package activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ca.uottawa.cohab.R;
import helpers.Input;
import model.Recompenses;
import model.User;
import sql.DatabaseHelper;


public class CreateResource extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = CreateResource.this;
    private DatabaseHelper databaseReference; 
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutDescription;
    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextDescription;
    private Input inputValidation;

    private Button ButtonSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_resource);

        initViews();
        initObjects();
        initListeners();

    }

    private void initViews() {

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutDescription = (TextInputLayout) findViewById(R.id.textInputLayoutDescription);

        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextDescription = (TextInputEditText) findViewById(R.id.textInputEditTextDescription);

        ButtonSave = (Button) findViewById(R.id.button);
    }

    private void initObjects() {
        databaseReference = new DatabaseHelper(activity);
        inputValidation = new Input(activity);
    }

    private void initListeners() {
        ButtonSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Recompenses resource = new Recompenses();
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name_empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextDescription, textInputLayoutDescription,
                getString(R.string.error_message_description_empty))) {
            return;
        }
        resource.setName(textInputEditTextName.getText().toString().trim());
        resource.setDescription(textInputEditTextDescription.getText().toString().trim());

        //databaseReference.addResource(resource);

        textInputEditTextName.setText(null);
        textInputEditTextDescription.setText(null);
        Toast.makeText(getApplicationContext(), "A ete ajoutee", Toast.LENGTH_SHORT).show();

    }

}