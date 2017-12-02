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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import activities.UserView;
import adapters.UserRecyclerAdapter;
import ca.uottawa.cohab.R;
import listener.RecyclerViewClickListener;
import listener.RecyclerViewTouchListener;
import model.User;
import sql.DatabaseHelper;


public class UserList extends Fragment {

    private View myView;
    private Context context;
    private RecyclerView recyclerViewUsers;
    private List<User> listUsers;
    private UserRecyclerAdapter userRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private User user;
    private String username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myView = inflater.inflate(R.layout.content_user_list, container, false);

        initViews();
        initObjects();
        initListeners();

        return myView;
    }


    private void initViews() {
        recyclerViewUsers = (RecyclerView) myView.findViewById(R.id.recyclerViewUserList);
        context = (Context) myView.getContext();
    }
    private void initListeners() {
        recyclerViewUsers.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity().getApplicationContext(), recyclerViewUsers, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(context, R.string.longClick, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Intent userView = new Intent (context, UserView.class);
                Bundle extras = new Bundle();
                extras.putString("USERNAME", username);
                extras.putBoolean("PROFILE", false);
                userView.putExtras(extras);
                startActivity(userView);
            }
        }));
    }

    private void initObjects() {
        listUsers = new ArrayList<>();
        userRecyclerAdapter = new UserRecyclerAdapter(listUsers, 1);
        databaseHelper = new DatabaseHelper(context);

        Bundle bundle = getArguments();
        username = bundle.getString("USERNAME");
        user = databaseHelper.getUser(username);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(myView.getContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(userRecyclerAdapter);


        getDataFromSQLite();
    }

    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                listUsers.addAll(databaseHelper.getAllUser(user));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                userRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}