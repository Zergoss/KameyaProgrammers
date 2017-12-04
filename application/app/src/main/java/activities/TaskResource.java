package activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapters.ResourceRecyclerAdapter;
import ca.uottawa.cohab.R;
import listener.RecyclerViewClickListener;
import listener.RecyclerViewTouchListener;
import model.Resource;
import model.Task;
import sql.DatabaseHelper;

public class TaskResource extends AppCompatActivity {

    //private View myView;
    private Button btn_new;
    private Task currentTask;
    private RecyclerView recyclerViewList;
    private List<Resource> listResource;
    private ResourceRecyclerAdapter resourceRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_resource);
        context = getApplicationContext();

        initObjects();
        initViews();
        initListeners();
        loadTaskInfo();
    }

    public void initViews() {
        btn_new = (Button) findViewById(R.id.btn_new);
    }
    public void initListeners() {
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent taskView = new Intent (context, TaskView.class);
                Bundle extras = new Bundle();
                extras.putInt("TASK", currentTask.getId());
                extras.putBoolean("ISTASK", true);
                taskView.putExtras(extras);
                startActivity(taskView);
            }
        });

        recyclerViewList.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerViewList, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(context, "Long click to unassign resource", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                //databaseHelper.deleteResource(listReward.get(position));
                Toast.makeText(context, "Resource as been unassign", Toast.LENGTH_SHORT).show();
                loadTaskInfo();
            }
        }));
    }
    public void initObjects() {
        listResource = new ArrayList<>();
        databaseHelper = new DatabaseHelper(context);
        resourceRecyclerAdapter = new ResourceRecyclerAdapter(listResource, 1);
        recyclerViewList = (RecyclerView) findViewById(R.id.recyclerViewList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerViewList.setLayoutManager(mLayoutManager);
        recyclerViewList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewList.setHasFixedSize(true);
        recyclerViewList.setAdapter(resourceRecyclerAdapter);

        currentTask = databaseHelper.getTask(getIntent().getIntExtra("TASK", -1));
    }

    private void loadTaskInfo() {
        currentTask = databaseHelper.getTask(currentTask.getId());
        getDataFromSQLite();
    }

    private void getDataFromSQLite() {
        listResource.clear();
        listResource.addAll(currentTask.getListResource());
        resourceRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadTaskInfo();
    }

}

