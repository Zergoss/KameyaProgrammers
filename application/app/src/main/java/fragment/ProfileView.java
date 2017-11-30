package fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import activities.UserEdit;
import ca.uottawa.cohab.R;
import model.User;
import sql.DatabaseHelper;

public class ProfileView extends Fragment {

    private View myView;
    private Button btn;
    private DatabaseHelper databaseHelper;
    private User user;

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
    }
    public void initObjects() {
        databaseHelper = new DatabaseHelper(getActivity());

        Bundle bundle = this.getArguments();
        if(bundle!=null) {
            String username = bundle.getString("USERNAME");
            user = databaseHelper.getUser(username);
        }

    }


    //String usernameFromIntent = getActivity().getIntent().getStringExtra("USERNAME");
    //textViewName.setText(usernameFromIntent);

    //TextView usernameTextMain = (TextView) header.findViewById(R.id.usernameTextMain);
    // usernameTextMain.setText(getIntent().getStringExtra("USERNAME"));
}

