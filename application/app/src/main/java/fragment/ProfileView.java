package fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import activities.UserEdit;
import ca.uottawa.cohab.R;

public class ProfileView extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.content_profile_view,container, false);
        Button btn = (Button) myView.findViewById(R.id.btn_editProfile);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserEdit.class);
                startActivity(intent);
            }
        });
        return myView;
    }

    //String usernameFromIntent = getActivity().getIntent().getStringExtra("USERNAME");
    //textViewName.setText(usernameFromIntent);

}
