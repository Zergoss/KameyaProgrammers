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
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapters.RewardRecyclerAdapter;
import adapters.TaskRecyclerAdapter;
import ca.uottawa.cohab.R;
import listener.RecyclerViewClickListener;
import listener.RecyclerViewTouchListener;
import model.Recompenses;
import model.Task;
import model.User;
import sql.DatabaseHelper;

public class UserView extends AppCompatActivity {

    //private View myView;
    private Button btn;
    private User user;
    private User connectedUser;
    private RecyclerView recyclerViewList;
    private List<Task> listTask;
    private List<Recompenses> listReward;
    private TaskRecyclerAdapter taskRecyclerAdapter;
    private RewardRecyclerAdapter rewardRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private Context context;
    private Boolean isProfile;
    private TextView usernameTextView;
    private TextView pointsTextView;
    private TextView numberTaskTextView;
    private TextView listTextView;
    private Switch simpleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);
        context = getApplicationContext();

        initObjects();
        initViews();
        initListeners();
        loadTaskInfo();
    }

    public void initViews() {
        usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        numberTaskTextView = (TextView) findViewById(R.id.numberTaskTextView);
        listTextView = (TextView) findViewById(R.id.taskListTextView);

        btn = (Button) findViewById(R.id.btn_editProfile);
        simpleSwitch = (Switch) findViewById(R.id.switchList);

        if(!isProfile){
            btn.setText(R.string.add_reward);
        }
    }
    public void initListeners() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isProfile) {
                    Intent intent = new Intent(getApplicationContext(), UserEdit.class);
                    intent.putExtra("CONNECTEDUSER", connectedUser.getId());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), CreateReward.class);
                    intent.putExtra("VIEWUSER", user.getId());
                    startActivity(intent);
                }
            }
        });

        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    listTextView.setText("Reward List");
                    recyclerViewList.setAdapter(rewardRecyclerAdapter);
                } else {
                    listTextView.setText("Task List");
                    recyclerViewList.setAdapter(taskRecyclerAdapter);
                }
                getDataFromSQLite();
            }
        });

        recyclerViewList.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerViewList, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(simpleSwitch.isChecked()) {
                    Toast.makeText(context, "Long click to delete reward", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, R.string.longClick, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                if(simpleSwitch.isChecked()) {
                    databaseHelper.deleteReward(listReward.get(position));
                    recyclerViewList.setAdapter(rewardRecyclerAdapter);
                    Toast.makeText(context, "Reward deleted", Toast.LENGTH_SHORT).show();
                    loadTaskInfo();
                } else {
                    Intent taskView = new Intent (context, TaskView.class);
                    Bundle extras = new Bundle();
                    extras.putInt("TASK", listTask.get(position).getId());
                    extras.putInt("CONNECTEDUSER", connectedUser.getId());
                    taskView.putExtras(extras);
                    startActivity(taskView);
                }
            }
        }));
    }
    public void initObjects() {
        listTask = new ArrayList<>();
        listReward = new ArrayList<>();
        databaseHelper = new DatabaseHelper(context);
        taskRecyclerAdapter = new TaskRecyclerAdapter(listTask, 1);
        rewardRecyclerAdapter = new RewardRecyclerAdapter(listReward, 1);
        recyclerViewList = (RecyclerView) findViewById(R.id.recyclerViewUserList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerViewList.setLayoutManager(mLayoutManager);
        recyclerViewList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewList.setHasFixedSize(true);
        recyclerViewList.setAdapter(taskRecyclerAdapter);

        Bundle extras = getIntent().getExtras();
        user = databaseHelper.getUser(extras.getInt("VIEWUSER", -1));
        isProfile = extras.getBoolean("ISPROFILE");
        connectedUser = databaseHelper.getUser(extras.getInt("CONNECTEDUSER", -1));
    }

    private void loadTaskInfo() {
        user = databaseHelper.getUser(user.getId());
        usernameTextView.setText(user.getUsername());
        String pts = "Points: " + String.valueOf(user.getPoints());
        pointsTextView.setText(pts);
        String numTask = "# Task: " + String.valueOf(user.getNumberTask());
        numberTaskTextView.setText(numTask);
        getDataFromSQLite();
    }

    private void getDataFromSQLite() {
        if(simpleSwitch.isChecked()) {
            listReward.clear();
            listReward.addAll(databaseHelper.getRewardOf(user.getId()));
            //listReward.addAll(databaseHelper.getAllReward());
        } else {
            listTask.clear();
            listTask.addAll(databaseHelper.getTaskOf(user.getId()));
        }
        taskRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getDataFromSQLite();
    }

}

