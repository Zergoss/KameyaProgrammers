package activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ca.uottawa.cohab.R;
import helpers.Input;
import model.Task;
import model.User;
import sql.DatabaseHelper;

public class TaskEdit extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private TextInputLayout textInputLayoutTaskName, textInputLayoutTaskDescription,
            textInputLayoutTaskDate;
    private TextInputEditText textInputEditTextTaskName, textInputEditTextTaskDescription,
            textInputEditTextTaskDate;

    private DatabaseHelper databaseHelper;
    private Task aTask;
    private Button createButton;
    private ScrollView myScrollView;
    private Spinner spinnerUser, spinnerStatus, spinnerGroup;
    private List<User> allUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        initObjects();
        initViews();
        initListeners();
        loadUserInfo();
        addItemsOnSpinnerUser();
    }

    private void loadUserInfo() {
        textInputLayoutTaskName.setHint(aTask.getName());
        textInputLayoutTaskDescription.setHint(aTask.getDescription());
        textInputLayoutTaskDate.setHint("Due: " + aTask.getDueDate());
    }
    private void initListeners() {
        createButton = (Button) findViewById(R.id.startEditTask);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDataToSQLite();
            }
        });
        spinnerUser.setOnItemSelectedListener(this);
        spinnerGroup.setOnItemSelectedListener(this);
        spinnerStatus.setOnItemSelectedListener(this);
    }
    private void initViews(){

        textInputLayoutTaskName = (TextInputLayout) findViewById(R.id.textInputLayoutTaskName);
        textInputLayoutTaskDescription = (TextInputLayout) findViewById(R.id.textInputLayoutTaskDescription);
        textInputLayoutTaskDate = (TextInputLayout) findViewById(R.id.textInputLayoutTaskDate);

        textInputEditTextTaskName = (TextInputEditText) findViewById(R.id.textInputEditTaskName);
        textInputEditTextTaskDescription = (TextInputEditText) findViewById(R.id.textInputEditTaskDescription);
        textInputEditTextTaskDate = (TextInputEditText) findViewById(R.id.textInputEditTaskDate);

        myScrollView = (ScrollView) findViewById(R.id.myScrollView);

        spinnerUser = (Spinner) findViewById(R.id.spinner_user);
        spinnerStatus = (Spinner) findViewById(R.id.spinner_status);
        spinnerGroup = (Spinner) findViewById(R.id.spinner_group);
    }
    private void initObjects() {
        databaseHelper = new DatabaseHelper(this);
        aTask = databaseHelper.getTask(getIntent().getIntExtra("TASK", -1));
    }
    private void addItemsOnSpinnerUser(){
        allUser = databaseHelper.getAllUser();
        List<String> list = new ArrayList<String>();
        list.add("No user");
        for (int i = 0; i < allUser.size(); i++) {
            list.add(allUser.get(i).getUsername());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        if(parent.getId() == R.id.spinner_user)
        {
            if(pos==0) {
                aTask.setAssignedUser(new User());
            } else {
                aTask.setAssignedUser(allUser.get(pos-1));
            }
        } else if(parent.getId() == R.id.spinner_status) {
            aTask.setStatus(pos);
        } else {
            aTask.setGroup(pos);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Not used
    }

    private void postDataToSQLite() {

        if (!textInputEditTextTaskName.getText().toString().trim().isEmpty()) {
            aTask.setName(textInputEditTextTaskName.getText().toString().trim());
        }
        if (!textInputEditTextTaskDescription.getText().toString().trim().isEmpty()) {
            aTask.setDescription(textInputEditTextTaskDescription.getText().toString().trim());
        }
        if (!textInputEditTextTaskDate.getText().toString().trim().isEmpty()) {
            aTask.setDueDate(textInputEditTextTaskDate.getText().toString().trim());
        }
        if (!databaseHelper.checkTask(textInputEditTextTaskName.getText().toString().trim())) {

            databaseHelper.updateTask(aTask);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(myScrollView, "Task Saved!", Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
            loadUserInfo();
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(myScrollView, "Task name already exists!", Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText() {
        textInputEditTextTaskDate.setText(null);
        textInputEditTextTaskDescription.setText(null);
        textInputEditTextTaskName.setText(null);
    }
}
