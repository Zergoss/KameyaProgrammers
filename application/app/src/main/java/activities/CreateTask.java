package activities;

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

public class CreateTask extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextInputLayout textInputLayoutTaskName, textInputLayoutTaskDescription,
                            textInputLayoutTaskPoint, textInputLayoutTaskDate;
    private TextInputEditText textInputEditTextTaskName, textInputEditTextTaskDescription,
                            textInputEditTextTaskPoint, textInputEditTextTaskDate;

    private DatabaseHelper databaseHelper;
    private Task newTask;
    private Input inputValidation;
    private Button createButton;
    private User connectedUser;
    private List<User> allUser;
    private ScrollView myScrollView;
    private Spinner spinnerUser, spinnerStatus, spinnerGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        initObjects();
        initViews();
        initListeners();
        addItemsOnSpinnerUser();
    }

    private void initListeners() {
        createButton = (Button) findViewById(R.id.startCreateTask);
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
        textInputLayoutTaskPoint = (TextInputLayout) findViewById(R.id.textInputLayoutTaskPoint);
        textInputLayoutTaskDate = (TextInputLayout) findViewById(R.id.textInputLayoutTaskDate);

        textInputEditTextTaskName = (TextInputEditText) findViewById(R.id.textInputEditTaskName);
        textInputEditTextTaskDescription = (TextInputEditText) findViewById(R.id.textInputEditTaskDescription);
        textInputEditTextTaskPoint = (TextInputEditText) findViewById(R.id.textInputEditTaskPoint);
        textInputEditTextTaskDate = (TextInputEditText) findViewById(R.id.textInputEditTaskDate);

        spinnerUser = (Spinner) findViewById(R.id.spinner_user);
        spinnerStatus = (Spinner) findViewById(R.id.spinner_status);
        spinnerGroup = (Spinner) findViewById(R.id.spinner_group);

        myScrollView = (ScrollView) findViewById(R.id.myScrollView);
    }
    private void initObjects() {
        databaseHelper = new DatabaseHelper(this);
        inputValidation = new Input(this);
        connectedUser = databaseHelper.getUser(getIntent().getIntExtra("CONNECTEDUSER", -1));
        newTask = new Task ();
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
                newTask.setAssignedUser(new User());
            } else {
                newTask.setAssignedUser(allUser.get(pos-1));
            }
        } else if(parent.getId() == R.id.spinner_status) {
            newTask.setStatus(pos);
        } else {
            newTask.setGroup(pos);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Not used
    }


    private void postDataToSQLite() {
       if (!inputValidation.isInputEditTextFilled(textInputEditTextTaskName, textInputLayoutTaskName, getString(R.string.error_message_username_empty))) {
           return;
       }
       if (!textInputEditTextTaskDescription.getText().toString().trim().isEmpty()) {
           newTask.setDescription(textInputEditTextTaskDescription.getText().toString().trim());
       }
       if (!inputValidation.isInputEditTextFilled(textInputEditTextTaskDate, textInputLayoutTaskDate, getString(R.string.error_message_date_empty))) {
           return;
       }
       if (!inputValidation.isInputEditTextFilled(textInputEditTextTaskPoint, textInputLayoutTaskPoint, getString(R.string.error_message_points_empty))) {
           return;
       }

       //NOT Working : to check if the user to assign to has a task starting the same day
      /* if(inputValidation.isInputEditTextFilled(textInputEditTextTaskAssignedUser,textInputLayoutTaskAssignedUser,"") &&
               inputValidation.isInputEditTextFilled(textInputEditTextTaskDate,textInputLayoutTaskDate,"")){
            User userAsssignmenttocheck = databaseHelper.getUser(textInputEditTextTaskAssignedUser.getText().toString().trim());
            ArrayList<Task> hesTasks = databaseHelper.getTaskOf(userAsssignmenttocheck.getId());
            for(int i = 0; i < hesTasks.size(); i++){
                Task iTask = hesTasks[i];
                Date iTaskDate = iTask.getDueDate();
                Date dateToCompare = (Date)((Integer) textInputEditTextTaskDate.getText().toString().trim());
                if(dateToCompare.equals(iTaskDate)){
                    textInputLayoutTaskAssignedUser.setError("Cannot User busy at that time");
                    return;
                }
            }
       }*/
       if (!databaseHelper.checkTask(textInputEditTextTaskName.getText().toString().trim())) {

           newTask.setName(textInputEditTextTaskName.getText().toString().trim());
           newTask.setPoints(Integer.parseInt(textInputEditTextTaskPoint.getText().toString().trim()));
           newTask.setDueDate(textInputEditTextTaskDate.getText().toString().trim());
           newTask.setCreator(connectedUser);

           databaseHelper.addTask(newTask);

           Snackbar.make(myScrollView, "Task Created !", Snackbar.LENGTH_LONG).show();
           emptyInputEditText();

       } else {
           // Snack Bar to show error message that record already exists
           Snackbar.make(myScrollView, "Task name already exists!", Snackbar.LENGTH_LONG).show();
       }
   }

    private void emptyInputEditText() {
        newTask = new Task ();
        textInputEditTextTaskDate.setText(null);
        textInputEditTextTaskDescription.setText(null);
        textInputEditTextTaskName.setText(null);
        textInputEditTextTaskPoint.setText(null);
        spinnerUser.setSelection(0);
        spinnerGroup.setSelection(0);
        spinnerStatus.setSelection(0);
    }
}
