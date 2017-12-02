package activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sql.DatabaseHelper;

import ca.uottawa.cohab.R;
import model.Task;
import model.User;

public class TaskView extends AppCompatActivity {
    private Task task;
    private Context context;
    private DatabaseHelper databaseHelper;
    private User user;
    private TextView taskPoints;
    private TextView taskName;
    private TextView taskDescription;
    private TextView taskDueDate;
    private TextView task_availability;
    private TextView task_creator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        context = getApplicationContext();

        initView();
        initObjects();
        loadTaskInfo();

    }
    private void initObjects() {
        databaseHelper = new DatabaseHelper(context);
        task = databaseHelper.getTask(getIntent().getStringExtra("NAME"));
    }
    private void initView() {
        //Load info from Task object instead of dummy
        taskPoints = (TextView) findViewById(R.id.taskPoints);
        taskName = (TextView) findViewById(R.id.taskName);
        taskDescription = (TextView) findViewById(R.id.taskDescription);
        taskDueDate = (TextView) findViewById(R.id.taskDueDate);
        task_availability = (TextView) findViewById(R.id.task_availability);
        task_creator = (TextView) findViewById(R.id.task_creator);
    }
    private void loadTaskInfo() {
        String pts = "Points: " + String.valueOf(task.getPoints());
        String due = "Due: " + task.getDueDate();
        String creator = "Creator: don't exist.";
        if (task.getCreator()!=null) {
            creator = "Creator: " + task.getCreator().getUsername();
        }

        taskName.setText(task.getName());
        taskDescription.setText(task.getDescription());
        taskPoints.setText(pts);
        taskDueDate.setText(due);
        user = task.getAssignedUser();
        if (user != null) {
            String avail = "Assign to " + user.getUsername();
            task_availability.setText(avail);
        }
        task_creator.setText(creator);
    }

    public void editTask(View view){
        Intent intent = new Intent(TaskView.this, TaskEdit.class);
        intent.putExtra("TASK", task.getName());
        startActivity(intent);
    }
    public void doneTask(View view){
        if (task.getAssignedUser() != null){
            databaseHelper.doneTask(task);
            finish();
        }
    }

}

