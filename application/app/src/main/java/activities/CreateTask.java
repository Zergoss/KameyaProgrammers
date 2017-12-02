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

    private static final  String TAG = "CreateTask";

    private TextInputLayout textInputLayoutTaskName, textInputLayoutTaskDescription,
                            textInputLayoutTaskPoint, textInputLayoutTaskDate,
                            textInputLayoutTaskAssignedUser;
    private TextInputEditText textInputEditTextTaskName, textInputEditTextTaskDescription,
                            textInputEditTextTaskPoint, textInputEditTextTaskDate,
                            textInputEditTextTaskAssignedUser;
    private Button pickDateButton;

    private DatabaseHelper databaseHelper;
    private List<User> userList;
    private Task newTask;
    private Input inputValidation;

    private DatePickerDialog.OnDateSetListener myDateSetListener;
    private User connectedUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
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

        pickDateButton = (Button) findViewById(R.id.pickDateButton);
        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CreateTask.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , myDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        myDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                Log.d(TAG,"onDateSet: mm/dd/yyyy" + month + "/" + day + "/" +year);

                String date =  month + "/" + day + "/" +year;
                pickDateButton.setText(date);
            }
        };

        connectedUser = databaseHelper.getUser(getIntent().getStringExtra("creator"));


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
        if (!inputValidation.isInputEditTextFilled(textInputEditTextTaskDescription, textInputLayoutTaskDescription, "")) {
            textInputEditTextTaskDescription.setText("");
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextTaskDate, textInputLayoutTaskDate, "")) {
            textInputEditTextTaskDate.setText("0");
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextTaskPoint, textInputLayoutTaskPoint,"")) {
            textInputEditTextTaskPoint.setText("0");
        }
       if (!inputValidation.isInputEditTextFilled(textInputEditTextTaskAssignedUser, textInputLayoutTaskAssignedUser,"")) {
           textInputEditTextTaskAssignedUser.setText(null);
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
       }


       if (!databaseHelper.checkTask(textInputEditTextTaskName.getText().toString().trim())) {

            newTask.setName(textInputEditTextTaskName.getText().toString().trim());


            databaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();

        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_username_exists), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        }

    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                userList.clear();
                userList.addAll(databaseHelper.getAllUser(connectedUser));
                return null;
            }

           /* @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                taskRecyclerAdapter.notifyDataSetChanged();
            }*/
        }.execute();
    }
}
