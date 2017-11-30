package fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import activities.UserEdit;
import adapters.TaskRecyclerAdapter;
import ca.uottawa.cohab.R;
import listener.RecyclerViewClickListener;
import listener.RecyclerViewTouchListener;
import model.Task;
import model.User;
import sql.DatabaseHelper;

public class ProfileView extends Fragment {

    private View myView;
    private Button btn;
    private User user;
    private RecyclerView recyclerViewList;
    private List<Task> listTask;
    private TaskRecyclerAdapter taskRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.content_profile_view,container, false);

        initObjects();
        initViews();
        initListeners();

        return myView;
    }

    public void initViews() {
        TextView usernameTextView = (TextView) myView.findViewById(R.id.usernameTextView);
        TextView pointsTextView = (TextView) myView.findViewById(R.id.pointsTextView);
        TextView numberTaskTextView = (TextView) myView.findViewById(R.id.numberTaskTextView);

        usernameTextView.setText(user.getUsername());
        pointsTextView.setText(user.getPassword());
        //pointsTextView.setText(String.valueOf(user.getPoints()));
        numberTaskTextView.setText(String.valueOf(user.getNumberTask()));

    }
    //private TextInputEditText textInputEditTextUsername;
    public void initListeners() {
        btn = (Button) myView.findViewById(R.id.btn_editProfile);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myView.getContext(), UserEdit.class);
                intent.putExtra("USERNAME", user.getUsername());
                startActivity(intent);
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
    public void initObjects() {
        listTask = new ArrayList<>();
        databaseHelper = new DatabaseHelper(getActivity());
        taskRecyclerAdapter = new TaskRecyclerAdapter(listTask, 1);
        recyclerViewList = (RecyclerView) myView.findViewById(R.id.listViewTasks);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(myView.getContext());
        recyclerViewList.setLayoutManager(mLayoutManager);
        recyclerViewList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewList.setHasFixedSize(true);
        recyclerViewList.setAdapter(taskRecyclerAdapter);

        Bundle bundle = getArguments();
        if(bundle!=null) {
            String username = bundle.getString("USERNAME");
            user = databaseHelper.getUser(username);
        }

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

