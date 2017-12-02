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
    Task t1 ;
    Task t2 ;
    Task t3 ;
    Task t4 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        context = getApplicationContext();
        initObjects();

        loadTaskInfo();

    }
    public void initObjects() {
        databaseHelper = new DatabaseHelper(context);

        task = databaseHelper.getTask(getIntent().getStringExtra("USERNAME"));
    }

    private void loadTaskInfo() {
        //Load info from Task object instead of dummy
        TextView taskPoints = (TextView) findViewById(R.id.taskPoints);
        TextView taskName = (TextView) findViewById(R.id.taskName);
        TextView taskDescription = (TextView) findViewById(R.id.taskDescription);
        TextView taskDueDate = (TextView) findViewById(R.id.taskDueDate);
        TextView task_availability = (TextView) findViewById(R.id.task_availability);
        TextView task_creator = (TextView) findViewById(R.id.task_creator);

        taskPoints.setText(task.getPoints());
        taskName.setText(task.getName());
        taskDescription.setText(task.getDescription());
        taskDueDate.setText(task.getDueDate().toString());
        task_availability.setText(task.getAssignedUser().toString());
        task_creator.setText(task.getCreator().toString());
    }

    public void editTask(View view){
        Intent intent = new Intent(TaskView.this, TaskEdit.class);
        startActivity(intent);
    }




        //numberTaskTextView.setText(String.valueOf(user.getNumberTask()));

}

