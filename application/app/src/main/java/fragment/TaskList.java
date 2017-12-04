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
    private Spinner spinner;
    private Context context;
    private RecyclerView recyclerViewList;
    private List<Task> listTask;
    private TaskRecyclerAdapter taskRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private Button btn_newTask;
    private Button btn_deleteTask;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.content_task_list,container, false);

        initViews();
        addListenerOnSpinnerItemSelection();
        initObjects();
        initListeners();

        return myView;
    }

    private void addListenerOnSpinnerItemSelection() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.dropDownTask, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String itemvalue = adapterView.getItemAtPosition(i).toString();

                //Action to happend when selected

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void initViews() {
        spinner = (Spinner) myView.findViewById(R.id.spinner);
        recyclerViewList = (RecyclerView) myView.findViewById(R.id.recyclerViewTaskList);
        context = (Context) myView.getContext();
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

        btn_deleteTask = (Button) myView.findViewById(R.id.btn_deleteTask);
        btn_deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Task> taskList = databaseHelper.getAllTask();
                for (Task e : taskList){
                    databaseHelper.deleteTask(e);
                }
                getDataFromSQLite();
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
        listTask.addAll(databaseHelper.getAllTask());
        taskRecyclerAdapter.notifyDataSetChanged();
    }


}