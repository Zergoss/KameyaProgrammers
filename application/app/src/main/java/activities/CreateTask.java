package activities;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ca.uottawa.cohab.R;
import helpers.Input;
import model.Task;
import model.User;
import sql.DatabaseHelper;

public class CreateTask extends AppCompatActivity {

    private TextInputLayout textInputLayoutTaskName, textInputLayoutTaskDescription,
                            textInputLayoutTaskPoint, textInputLayoutTaskDate,
                            textInputLayoutTaskAssignedUser;
    private TextInputEditText textInputEditTextTaskName, textInputEditTextTaskDescription,
                            textInputEditTextTaskPoint, textInputEditTextTaskDate,
                            textInputEditTextTaskAssignedUser,getTextInputEditTextTaskDate;

    private DatabaseHelper databaseHelper;
    private Task newTask;
    private Input inputValidation;
    private Button createButton;

    private User connectedUser;
    private ScrollView myScrollView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        initObjects();
        initViews();
    }

    private void initViews(){

        textInputLayoutTaskName = (TextInputLayout) findViewById(R.id.textInputLayoutTaskName);
        textInputLayoutTaskDescription = (TextInputLayout) findViewById(R.id.textInputLayoutTaskDescription);
        textInputLayoutTaskPoint = (TextInputLayout) findViewById(R.id.textInputLayoutTaskPoint);
        textInputLayoutTaskDate = (TextInputLayout) findViewById(R.id.textInputLayoutTaskDate);
        textInputLayoutTaskAssignedUser = (TextInputLayout) findViewById(R.id.textInputLayoutAssignedUser);

        textInputEditTextTaskName = (TextInputEditText) findViewById(R.id.textInputEditTaskName);
        textInputEditTextTaskDescription = (TextInputEditText) findViewById(R.id.textInputEditTaskDescription);
        textInputEditTextTaskPoint = (TextInputEditText) findViewById(R.id.textInputEditTaskPoint);
        textInputEditTextTaskAssignedUser = (TextInputEditText) findViewById(R.id.textInputEditTaskAssignedUser);
        textInputEditTextTaskDate = (TextInputEditText) findViewById(R.id.textInputEditTaskDate);

        connectedUser = databaseHelper.getUser(getIntent().getStringExtra("creator"));

        createButton = (Button) findViewById(R.id.startCreateTask);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDataToSQLite();
            }
        });
        myScrollView = (ScrollView) findViewById(R.id.myScrollView);

    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(this);
        inputValidation = new Input(this);
        newTask = new Task ();

    }

   private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextTaskName, textInputLayoutTaskName, "Insert task name")) {
            return;
        }
       /* if (!inputValidation.isInputEditTextFilled(textInputEditTextTaskDescription, textInputLayoutTaskDescription, "")) {
            textInputEditTextTaskDescription.setText("");
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextTaskDate, textInputLayoutTaskDate, "")) {

        }*/
        if (!inputValidation.isInputEditTextFilled(textInputEditTextTaskPoint, textInputLayoutTaskPoint,"")) {
            textInputEditTextTaskPoint.setText("0");
        }
       /*if (!inputValidation.isInputEditTextFilled(textInputEditTextTaskAssignedUser, textInputLayoutTaskAssignedUser,"")) {
           textInputEditTextTaskAssignedUser.setText(null);
       }*/

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
           newTask.setCreator(connectedUser);

           databaseHelper.addTask(newTask);

           // Snack Bar to show success message that record saved successfully
          Snackbar.make(myScrollView,"Task Created !", Snackbar.LENGTH_LONG).show();
          emptyInputEditText();

       } else {
           // Snack Bar to show error message that record already exists
           Snackbar.make(myScrollView,"Task name already exists!", Snackbar.LENGTH_LONG).show();
           textInputEditTextTaskName.setText(null);
       }
       }

    private void emptyInputEditText() {
        textInputEditTextTaskDate.setText(null);
        textInputEditTextTaskAssignedUser.setText(null);
        textInputEditTextTaskDescription.setText(null);
        textInputEditTextTaskName.setText(null);
        textInputEditTextTaskPoint.setText(null);
    }
}
