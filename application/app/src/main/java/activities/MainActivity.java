package activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ca.uottawa.cohab.R;
import fragment.TaskList;
import fragment.UserList;
import fragment.UserSwitch;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final AppCompatActivity activity = MainActivity.this;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        defaultDrawer();
        initNav();
    }

    private void initNav() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView usernameTextMain = (TextView) header.findViewById(R.id.usernameTextMain);
        username = getIntent().getStringExtra("USERNAME");
        usernameTextMain.setText(username);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("USERNAME", username);

        if (id == R.id.nav_task_list) {
            getSupportActionBar().setTitle("Task list");

            TaskList taskList = new TaskList();
            taskList.setArguments(bundle);

            fragmentManager.beginTransaction().replace(R.id.content_frame, taskList).commit();
        } else if (id == R.id.nav_user_list) {
            getSupportActionBar().setTitle("User list");

            UserList userList = new UserList();
            userList.setArguments(bundle);

            fragmentManager.beginTransaction().replace(R.id.content_frame, userList).commit();
        } else if (id == R.id.nav_profile) {

            Intent profileIntent = new Intent (MainActivity.this, UserView.class);
            Bundle extras = new Bundle();
            extras.putString("USERNAME", username);
            extras.putBoolean("PROFILE", true);
            profileIntent.putExtras(extras);
            startActivity(profileIntent);
        } else if (id == R.id.nav_user_switch) {
            getSupportActionBar().setTitle("Switch user");

            UserSwitch userSwitch = new UserSwitch();
            userSwitch.setArguments(bundle);

            fragmentManager.beginTransaction().replace(R.id.content_frame, userSwitch).commit();
        } else if (id == R.id.nav_log_off) {
            Intent MainIntent = new Intent (MainActivity.this, Login.class);
            startActivity(MainIntent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void defaultDrawer() {
        FragmentManager fragmentManager = getFragmentManager();
        getSupportActionBar().setTitle("Task list");

        Bundle bundle = new Bundle();
        bundle.putString("USERNAME", username);
        TaskList taskList = new TaskList();
        taskList.setArguments(bundle);

        fragmentManager.beginTransaction().replace(R.id.content_frame, taskList).commit();
    }

    public String getUsername() {
        return this.username;
    }
    public void getUsername(String username) {
        this.username = username;
    }

}
