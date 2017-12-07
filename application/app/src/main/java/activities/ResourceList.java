package activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
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

public class ResourceList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //private View myView;
    private Button btn_new;
    private Task currentTask;
    private RecyclerView recyclerViewList;
    private List<Resource> list;
    private ResourceRecyclerAdapter resourceRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private Context context;
    private Boolean isTask;
    private Spinner spinnerGroup;
    private int selectedGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_list);
        context = getApplicationContext();

        initObjects();
        initViews();
        loadTaskInfo();
        initListeners();
    }

    public void initViews() {
        btn_new = (Button) findViewById(R.id.btn_new);
        spinnerGroup = (Spinner) findViewById(R.id.spinner_group);
        selectedGroup = 0;
    }
    public void initListeners() {
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateResource.class);
                startActivity(intent);
            }
        });

        recyclerViewList.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerViewList, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(isTask) {
                    Toast.makeText(context, "Long click to assign resource", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Long click to delete resource", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                if(isTask) {
                    databaseHelper.addTaskResource(currentTask, list.get(position));
                    Toast.makeText(context, "Resource assign", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHelper.deleteResource(list.get(position));
                    Toast.makeText(context, "Resource deleted", Toast.LENGTH_SHORT).show();
                    loadTaskInfo();
                }
            }
        }));

        spinnerGroup.setOnItemSelectedListener(this);
    }
    public void initObjects() {
        list = new ArrayList<>();
        databaseHelper = new DatabaseHelper(context);
        resourceRecyclerAdapter = new ResourceRecyclerAdapter(list);
        recyclerViewList = (RecyclerView) findViewById(R.id.recyclerViewList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerViewList.setLayoutManager(mLayoutManager);
        recyclerViewList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewList.setHasFixedSize(true);
        recyclerViewList.setAdapter(resourceRecyclerAdapter);

        Bundle extras = getIntent().getExtras();
        currentTask = databaseHelper.getTask(extras.getInt("TASK", -1));
        isTask = extras.getBoolean("ISTASK");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        selectedGroup = pos;
        loadTaskInfo();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Not used
    }

    private void loadTaskInfo() {
        currentTask = databaseHelper.getTask(currentTask.getId());
        getDataFromSQLite();
    }

    private void getDataFromSQLite() {
        list.clear();
        if(selectedGroup==0) {
            list.addAll(databaseHelper.getAllResource());
        } else if(selectedGroup==1) {
            list.addAll(databaseHelper.getResourceOf(0));
        } else if(selectedGroup==2) {
            list.addAll(databaseHelper.getResourceOf(1));
        } else if(selectedGroup==3) {
            list.addAll(databaseHelper.getResourceOf(2));
        }

        resourceRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        selectedGroup = 0;
        loadTaskInfo();
    }

}

