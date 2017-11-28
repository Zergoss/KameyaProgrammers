package activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ca.uottawa.cohab.R;

public class UserView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);
        loadUserInfo();
    }

    private void loadUserInfo() {
        //Load info from Task object instead of dummy
    }

}
