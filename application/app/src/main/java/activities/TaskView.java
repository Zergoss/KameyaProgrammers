package activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ca.uottawa.cohab.R;

public class TaskView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        loadTaskInfo();
    }

    private void loadTaskInfo() {
        //Load info from Task object instead of dummy
    }

    public void editTask(View view){
        Intent intent = new Intent(TaskView.this, TaskEdit.class);
        startActivity(intent);
    }


}
