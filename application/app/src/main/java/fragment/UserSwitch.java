package fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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

import activities.UserSwitchLogin;
import activities.UserView;
import adapters.UserRecyclerAdapter;
import ca.uottawa.cohab.R;
import listener.RecyclerViewClickListener;
import listener.RecyclerViewTouchListener;
import model.User;
import sql.DatabaseHelper;


public class UserSwitch extends Fragment {

    private View myView;
    private Context context;
    private AppCompatActivity activity;
    private AppCompatTextView textViewUsername;
    private RecyclerView recyclerViewUsers;
    private List<User> listUsers;
    private UserRecyclerAdapter userRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myView = inflater.inflate(R.layout.content_user_switch, container, false);
        initViews();
        initListeners();
        initObjects();
        return myView;
    }


    private void initViews() {
        textViewUsername = (AppCompatTextView) myView.findViewById(R.id.textViewUsername);
        recyclerViewUsers = (RecyclerView) myView.findViewById(R.id.recyclerViewUserSwitch);
        context = (Context) myView.getContext();
    }
    private void initListeners() {
        recyclerViewUsers.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity().getApplicationContext(), recyclerViewUsers, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(context, "Long click to change user.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Intent userSwitch = new Intent (context, UserSwitchLogin.class);
                userSwitch.putExtra("USERNAME", listUsers.get(position).getUsername());
                startActivity(userSwitch);
            }
        }));
    }
    private void initObjects() {
        listUsers = new ArrayList<>();
        activity = (AppCompatActivity)getActivity();
        userRecyclerAdapter = new UserRecyclerAdapter(listUsers, 2);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(myView.getContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(userRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        Bundle bundle = getArguments();
        user = databaseHelper.getUser(bundle.getInt("CONNECTEDUSER"));

        getDataFromSQLite();
    }

    private void getDataFromSQLite() {
        listUsers.clear();
        listUsers.addAll(databaseHelper.getAllUser(user));
        userRecyclerAdapter.notifyDataSetChanged();
    }

}