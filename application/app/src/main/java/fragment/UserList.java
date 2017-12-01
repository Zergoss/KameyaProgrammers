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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapters.UserRecyclerAdapter;
import ca.uottawa.cohab.R;
import listener.RecyclerViewClickListener;
import listener.RecyclerViewTouchListener;
import model.User;
import sql.DatabaseHelper;


public class UserList extends Fragment {

    private View myView;
    private AppCompatActivity activity;
    private RecyclerView recyclerViewUsers;
    private List<User> listUsers;
    private UserRecyclerAdapter userRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myView = inflater.inflate(R.layout.content_user_list, container, false);

        initViews();
        initListeners();
        initObjects();

        return myView;
    }


    private void initViews() {
        recyclerViewUsers = (RecyclerView) myView.findViewById(R.id.recyclerViewUserList);
    }
    private void initListeners() {
        recyclerViewUsers.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity().getApplicationContext(), recyclerViewUsers, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity().getApplicationContext(), listUsers.get(position).getUsername(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity().getApplicationContext(), listUsers.get(position).getPassword(), Toast.LENGTH_SHORT).show();

            }
        }));
    }

    private void initObjects() {
        listUsers = new ArrayList<>();
        activity = (AppCompatActivity)getActivity();
        userRecyclerAdapter = new UserRecyclerAdapter(listUsers, 1);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(myView.getContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(userRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

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
                userRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}