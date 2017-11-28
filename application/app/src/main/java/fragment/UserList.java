package fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import adapters.UserListRecyclerAdapter;
import ca.uottawa.cohab.R;
import model.User;
import sql.DatabaseHelper;


public class UserList extends Fragment {

    private View myView;
    private AppCompatActivity activity;
    private AppCompatTextView textViewUsername;
    private RecyclerView recyclerViewUsers;
    private List<User> listUsers;
    private UserListRecyclerAdapter userListRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myView = inflater.inflate(R.layout.content_user_list, container, false);
        initViews();
        //initListeners();
        initObjects();
        return myView;
    }


    private void initViews() {
        textViewUsername = (AppCompatTextView) myView.findViewById(R.id.textViewUsername);
        recyclerViewUsers = (RecyclerView) myView.findViewById(R.id.recyclerViewUsers);
    }

    /*private void initListeners() {
        listView = (ListView) myView.findViewById(R.id.userlist);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(myView.getContext(), UserView.class);
                //intent.putExtra("name", listView.getItemAtPosition(i).toString());
                startActivity(intent);
            }
        });
    }*/

    private void initObjects() {
        listUsers = new ArrayList<>();
        activity = (AppCompatActivity)getActivity();
        userListRecyclerAdapter = new UserListRecyclerAdapter(listUsers);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(myView.getContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(userListRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        //String usernameFromIntent = getActivity().getIntent().getStringExtra("USERNAME");
        //textViewUsername.setText(usernameFromIntent);

        getDataFromSQLite();
    }

    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                listUsers.addAll(databaseHelper.getAllUser());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                userListRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}