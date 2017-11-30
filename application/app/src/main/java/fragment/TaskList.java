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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapters.TaskRecyclerAdapter;
import ca.uottawa.cohab.R;
import activities.TaskView;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.content_task_list,container, false);

        addListenerOnSpinnerItemSelection();
        initViews();
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
                Bundle bundle = getArguments();
                String username = bundle.getString("USERNAME");
                User user = databaseHelper.getUser(username);
                databaseHelper.addTask(new Task (1, "Clean", "Room", new Date(), user));
                databaseHelper.addTask(new Task (2, "Sleep", "Bed", new Date(), user));
                databaseHelper.addTask(new Task (3, "Eat", "Dinner", new Date(), new User()));
                databaseHelper.addTask(new Task (4, "Garbage", "Outside", new Date(), new User()));
                //Task (int points, String name, String description, Date dueDate, User creator)

            }
        });
    /*private void initListeners() {
        listView = (ListView) myView.findViewById(R.id.userlist);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(myView.getContext(), UserView.class);
                //intent.putExtra("name", listView.getItemAtPosition(i).toString());
                startActivity(intent);
            }
        });*/
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