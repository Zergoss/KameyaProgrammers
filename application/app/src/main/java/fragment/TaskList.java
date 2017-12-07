package fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import activities.CreateTask;
import activities.CreateTask;
import adapters.TaskRecyclerAdapter;
import ca.uottawa.cohab.R;
import activities.TaskView;
import listener.RecyclerViewClickListener;
import listener.RecyclerViewTouchListener;
import model.Task;
import model.User;
import sql.DatabaseHelper;


public class TaskList extends Fragment {

    private View myView;
    private Context context;
    private RecyclerView recyclerViewList;
    private List<Task> listTask;
    private TaskRecyclerAdapter taskRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private Button btn_newTask;
    private User user;
    private Spinner spinnerStatus;
    private int group;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.content_task_list,container, false);

        initViews();
        initObjects();
        initListeners();

        return myView;
    }

    private void initViews() {
        recyclerViewList = (RecyclerView) myView.findViewById(R.id.recyclerViewTaskList);
        context = (Context) myView.getContext();
        spinnerStatus = (Spinner) myView.findViewById(R.id.spinner_task);
        group = 0;
    }
    private void initListeners() {
        btn_newTask = (Button) myView.findViewById(R.id.btn_newTask);
        btn_newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintentCreateTask = new Intent(context, CreateTask.class);
                myintentCreateTask.putExtra("CONNECTEDUSER", user.getId());
                startActivity(myintentCreateTask);
            }
        });


        recyclerViewList.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity().getApplicationContext(), recyclerViewList, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.longClick, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Intent taskView = new Intent (context, TaskView.class);
                Bundle extras = new Bundle();
                extras.putInt("TASK", listTask.get(position).getId());
                extras.putInt("CONNECTEDUSER", user.getId());
                taskView.putExtras(extras);
                startActivity(taskView);
            }
        }));

        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                group = position;
                getDataFromSQLite();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // not used
            }
        });
    }
    private void initObjects() {
        listTask = new ArrayList<>();
        taskRecyclerAdapter = new TaskRecyclerAdapter(listTask, 1);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(myView.getContext());
        recyclerViewList.setLayoutManager(mLayoutManager);
        recyclerViewList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewList.setHasFixedSize(true);
        recyclerViewList.setAdapter(taskRecyclerAdapter);
        databaseHelper = new DatabaseHelper(context);
        Bundle bundle = getArguments();
        user = databaseHelper.getUser(bundle.getInt("CONNECTEDUSER"));

        getDataFromSQLite();
    }


    private void getDataFromSQLite() {
        listTask.clear();
        if(group==0) {
            listTask.addAll(databaseHelper.getAllTask());
        } else if(group==1) {
            listTask.addAll(databaseHelper.getTaskOf(0));
        } else if(group==2) {
            listTask.addAll(databaseHelper.getTaskOf(1));
        } else if(group==3) {
            listTask.addAll(databaseHelper.getTaskOf(2));
        }
        taskRecyclerAdapter.notifyDataSetChanged();
    }


}