package activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import adapters.TaskRecyclerAdapter;
import sql.DatabaseHelper;

import ca.uottawa.cohab.R;
import model.Task;
import model.User;

public class TaskView extends AppCompatActivity {
    private Task task;
    private Button editBtn;
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
        task = databaseHelper.getTask(getIntent().getStringExtra("USERNAME"));
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
        taskPoints.setText(String.valueOf(task.getPoints()));
        taskName.setText(task.getName());
        taskDescription.setText(task.getDescription());
        taskDueDate.setText(task.getDueDate());
        task_availability.setText(task.getAssignedUser().getUsername());
        task_creator.setText(task.getCreator().getUsername());
    }

    public void editTask(View view){
        Intent intent = new Intent(TaskView.this, TaskEdit.class);
        startActivity(intent);
    }

}

