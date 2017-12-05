package activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sql.DatabaseHelper;

import ca.uottawa.cohab.R;
import model.Task;
import model.User;

public class TaskView extends AppCompatActivity {
    private Task task;
    private Context context;
    private DatabaseHelper databaseHelper;
    private User connectedUser;
    private TextView taskPoints, taskName, taskDescription, taskDueDate,
            task_availability, task_creator, task_status, task_group;
    private Button btn_doneTask, btn_editTask;


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
    }
    private void initView() {
        //Load info from Task object instead of dummy
        taskPoints = (TextView) findViewById(R.id.taskPoints);
        taskName = (TextView) findViewById(R.id.taskName);
        taskDescription = (TextView) findViewById(R.id.taskDescription);
        taskDueDate = (TextView) findViewById(R.id.taskDueDate);
        task_availability = (TextView) findViewById(R.id.task_availability);
        task_creator = (TextView) findViewById(R.id.task_creator);
        btn_doneTask = (Button) findViewById(R.id.btn_doneTask);
        btn_editTask = (Button) findViewById(R.id.btn_editTask);
        task_status = (TextView) findViewById(R.id.task_status);
        task_group = (TextView) findViewById(R.id.task_group);
    }
    private void loadTaskInfo() {
        Bundle extras = getIntent().getExtras();
        task = databaseHelper.getTask(extras.getInt("TASK", -1));
        connectedUser = databaseHelper.getUser(extras.getInt("CONNECTEDUSER", -1));
        User AssignUser = task.getAssignedUser();


        if(connectedUser.getId()==AssignUser.getId()){
            btn_doneTask.setVisibility(View.VISIBLE);
        }else {
            btn_doneTask.setVisibility(View.INVISIBLE);
        }
        if(task.getStatus() == 3){
            btn_doneTask.setVisibility(View.INVISIBLE);
            btn_editTask.setVisibility(View.INVISIBLE);
        }
        String pts = "Points: " + String.valueOf(task.getPoints());
        String due = "Due: " + task.getDueDate();
        String creator;
        if(task.getCreator().getId()!=-1) {
            creator = "Creator: " + task.getCreator().getUsername();
        } else {
            creator = "Creator: User deleted";
        }



        taskName.setText(task.getName());
        taskDescription.setText(task.getDescription());
        taskPoints.setText(pts);
        taskDueDate.setText(due);
        task_creator.setText(creator);
        task_status.setText(databaseHelper.checkNameValue(task.getStatus(), 0));
        task_group.setText(databaseHelper.checkNameValue(task.getGroup(), 1));

        String avail = "Assign to " + AssignUser.getUsername();
        task_availability.setText(avail);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadTaskInfo();
    }

    public void editTask(View view){
        Intent intent = new Intent(TaskView.this, TaskEdit.class);
        intent.putExtra("TASK", task.getId());
        startActivity(intent);
    }
    public void doneTask(View view){
        databaseHelper.doneTask(task);
        Toast.makeText(getApplicationContext(), "Task now done. Points added!", Toast.LENGTH_SHORT).show();
        finish();
    }
    public void deleteTask(View view){
        databaseHelper.deleteTask(task);
        Toast.makeText(getApplicationContext(), "Task deleted", Toast.LENGTH_SHORT).show();
        finish();
    }
    public void manageResource(View view) {
        Intent intent = new Intent(TaskView.this, TaskResource.class);
        intent.putExtra("TASK", task.getId());
        startActivity(intent);
    }

}

