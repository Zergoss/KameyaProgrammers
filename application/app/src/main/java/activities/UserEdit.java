package activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ca.uottawa.cohab.R;
import model.User;
import sql.DatabaseHelper;

public class UserEdit extends AppCompatActivity {

    private DatabaseHelper db;
    private AppCompatActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        loadUserInfo();
    }

    private void loadUserInfo() {
        //Load info from Task object instead of dummy
    }


    public void picChange(View view){
        //Intent intent = new Intent(myView.getContext(), CAMERA.class);
        //startActivity(intent);
    }


    public void deleteUser(View view){
        activity = this;
        db = new DatabaseHelper(activity);
        User user = db.getUser(getIntent().getStringExtra("USERNAME"));

        db.deleteUser(user);
        Intent intent = new Intent(view.getContext(), Login.class);
        startActivity(intent);
    }

}
