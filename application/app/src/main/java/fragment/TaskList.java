package fragment;

import android.app.Fragment;
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
    private ListView listView;
    private AppCompatActivity activity;
    private RecyclerView recyclerViewList;
    private List<Task> listTask;
    private TaskRecyclerAdapter taskRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private Button btn_newTask;
    private Button btn_deleteTask;


    Task t1 = new Task (1, "Clean", "Room", new Date(), new User());
    Task t2 = new Task (2, "Sleep", "Bed", new Date(), new User());
    Task t3 = new Task (3, "Eat", "Dinner", new Date(), new User());
    Task t4 = new Task (4, "Garbage", "Outside", new Date(), new User());


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.content_task_list,container, false);

        initViews();
        addListenerOnSpinnerItemSelection();
        initListeners();
        initObjects();

        return myView;
    }

    //Spinner
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
    }
    private void initListeners() {
        btn_newTask = (Button) myView.findViewById(R.id.btn_newTask);
        btn_newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myintentCreateTask = new Intent(getContext(), CreateTask.class);
                startActivity(myintentCreateTask);
                /*databaseHelper.addTask(t1);
                databaseHelper.addTask(t2);
                databaseHelper.addTask(t3);
                databaseHelper.addTask(t4);
                getDataFromSQLite();*/
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
                Toast.makeText(getActivity().getApplicationContext(), listTask.get(position).getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity().getApplicationContext(), listTask.get(position).getDescription(), Toast.LENGTH_SHORT).show();

            }
        }));
    }
    private void initObjects() {
        listTask = new ArrayList<>();
        activity = (AppCompatActivity)getActivity();
        taskRecyclerAdapter = new TaskRecyclerAdapter(listTask, 1);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(myView.getContext());
        recyclerViewList.setLayoutManager(mLayoutManager);
        recyclerViewList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewList.setHasFixedSize(true);
        recyclerViewList.setAdapter(taskRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        getDataFromSQLite();
    }

    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listTask.clear();
                listTask.addAll(databaseHelper.getAllTask());


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                taskRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}