package activities;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapters.TaskRecyclerAdapter;
import ca.uottawa.cohab.R;
import listener.RecyclerViewClickListener;
import listener.RecyclerViewTouchListener;
import model.Task;
import model.User;
import sql.DatabaseHelper;

public class UserView extends AppCompatActivity {

    //private View myView;
    private Button btn;
    private User user;
    private RecyclerView recyclerViewList;
    private List<Task> listTask;
    private TaskRecyclerAdapter taskRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        initObjects();
        initViews();
        initListeners();
    }

    public void initViews() {
        TextView usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        TextView pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        TextView numberTaskTextView = (TextView) findViewById(R.id.numberTaskTextView);

        usernameTextView.setText(user.getUsername());
        pointsTextView.setText(user.getPassword());
        //pointsTextView.setText(String.valueOf(user.getPoints()));
        numberTaskTextView.setText(String.valueOf(user.getNumberTask()));

        context = getApplicationContext();

    }
    //private TextInputEditText textInputEditTextUsername;
    public void initListeners() {
        btn = (Button) findViewById(R.id.btn_editProfile);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserEdit.class);
                intent.putExtra("USERNAME", user.getUsername());
                startActivity(intent);
            }
        });

        recyclerViewList.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerViewList, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(context, listTask.get(position).getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(context, listTask.get(position).getDescription(), Toast.LENGTH_SHORT).show();

            }
        }));
    }
    public void initObjects() {
        listTask = new ArrayList<>();
        databaseHelper = new DatabaseHelper(context);
        taskRecyclerAdapter = new TaskRecyclerAdapter(listTask, 1);
        recyclerViewList = (RecyclerView) findViewById(R.id.recyclerViewUserList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerViewList.setLayoutManager(mLayoutManager);
        recyclerViewList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewList.setHasFixedSize(true);
        recyclerViewList.setAdapter(taskRecyclerAdapter);

        String username = getIntent().getStringExtra("USERNAME");
        user = databaseHelper.getUser(username);

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

