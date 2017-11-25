package ca.uottawa.cohab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TaskEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
    }

    public void saveEditTask(View view){
        Intent intent = new Intent(TaskEdit.this, TaskView.class);
        startActivity(intent);
    }

    public void cancelEditTask(View view){
        Intent intent = new Intent(TaskEdit.this, TaskView.class);
        startActivity(intent);
    }
}
