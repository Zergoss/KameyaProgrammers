package activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import ca.uottawa.cohab.R;
import helpers.Input;
import model.Task;
import model.User;
import sql.DatabaseHelper;

public class TaskEdit extends AppCompatActivity {

    private TextInputLayout textInputLayoutTaskName, textInputLayoutTaskDescription,
            textInputLayoutTaskDate, textInputLayoutTaskAssignedUser;
    private TextInputEditText textInputEditTextTaskName, textInputEditTextTaskDescription,
            textInputEditTextTaskDate, textInputEditTextTaskAssignedUser;

    private DatabaseHelper databaseHelper;
    private Task aTask;
    private Button createButton;
    private ScrollView myScrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        initObjects();
        initViews();
        initListeners();
        loadUserInfo();
    }

    private void loadUserInfo() {
        textInputLayoutTaskName.setHint(aTask.getName());
        textInputLayoutTaskDescription.setHint(aTask.getDescription());
        textInputLayoutTaskDate.setHint("Due: " + aTask.getDueDate());
        textInputLayoutTaskAssignedUser.setHint("Task unassigned");
        if(aTask.getAssignedUser()!=null) {
            textInputLayoutTaskAssignedUser.setHint("Assign to " + aTask.getAssignedUser().getUsername());
        }
    }
    private void initListeners() {
        createButton = (Button) findViewById(R.id.startEditTask);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDataToSQLite();
            }
        });
    }
    private void initViews(){

        textInputLayoutTaskName = (TextInputLayout) findViewById(R.id.textInputLayoutTaskName);
        textInputLayoutTaskDescription = (TextInputLayout) findViewById(R.id.textInputLayoutTaskDescription);
        textInputLayoutTaskDate = (TextInputLayout) findViewById(R.id.textInputLayoutTaskDate);
        textInputLayoutTaskAssignedUser = (TextInputLayout) findViewById(R.id.textInputLayoutAssignedUser);

        textInputEditTextTaskName = (TextInputEditText) findViewById(R.id.textInputEditTaskName);
        textInputEditTextTaskDescription = (TextInputEditText) findViewById(R.id.textInputEditTaskDescription);
        textInputEditTextTaskAssignedUser = (TextInputEditText) findViewById(R.id.textInputEditTaskAssignedUser);
        textInputEditTextTaskDate = (TextInputEditText) findViewById(R.id.textInputEditTaskDate);

        myScrollView = (ScrollView) findViewById(R.id.myScrollView);
    }
    private void initObjects() {
        databaseHelper = new DatabaseHelper(this);
        aTask = databaseHelper.getTask(getIntent().getIntExtra("TASK", -1));
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
        if (!textInputEditTextTaskAssignedUser.getText().toString().trim().isEmpty()) {
            if (!databaseHelper.checkUser(textInputEditTextTaskAssignedUser.getText().toString().trim())) {
                Snackbar.make(myScrollView, "User you are trying to assign doesn't exist!", Snackbar.LENGTH_LONG).show();
                return;
            }
            aTask.setAssignedUser(databaseHelper.getUser(textInputEditTextTaskAssignedUser.getText().toString().trim()));
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
        textInputEditTextTaskAssignedUser.setText(null);
        textInputEditTextTaskDescription.setText(null);
        textInputEditTextTaskName.setText(null);
    }
}
