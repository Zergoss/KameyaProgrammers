package activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ca.uottawa.cohab.R;

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
