package adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import ca.uottawa.cohab.R;
import model.User;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.UserViewHolder> {

    private static final int USER_LIST_CASE = 1;
    private static final int USER_SWITCH_CASE = 2;

    private List <User> listUsers;
    private int userType;

    public UserRecyclerAdapter(List<User> listUsers, int userType) {
        this.listUsers = listUsers;
        this.userType = userType;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        UserViewHolder userViewHolder = null;

        // inflating recycler item view
        switch (userType) {
            case USER_LIST_CASE:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_user_list_recycler, parent, false);

                userViewHolder =  new UserViewHolder(itemView,userType);
                break;

            case USER_SWITCH_CASE:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_user_switch_recycler, parent, false);

                userViewHolder = new UserViewHolder(itemView, userType);
                break;
        }

        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        switch (userType) {
            case USER_LIST_CASE:
                holder.textViewUsername.setText(listUsers.get(position).getUsername());
                holder.ImageViewImage1.setImageResource(R.drawable.image1);
                holder.textViewPoints.setText(String.valueOf(listUsers.get(position).getPoints()));
                holder.textViewNumberTask.setText(String.valueOf(listUsers.get(position).getNumberTask()));
                break;

            case USER_SWITCH_CASE:
                holder.textViewUsername.setText(listUsers.get(position).getUsername());
                holder.ImageViewImage1.setImageResource(R.drawable.image1);
                break;
        }
    }

    @Override
    public int getItemCount() {
        Log.v(UserRecyclerAdapter.class.getSimpleName(),""+listUsers.size());
        return listUsers.size();
    }

    protected class UserViewHolder extends RecyclerView.ViewHolder {

        protected AppCompatTextView textViewUsername;
        protected ImageView ImageViewImage1;
        protected AppCompatTextView textViewPoints;
        protected AppCompatTextView textViewNumberTask;

        protected UserViewHolder(View view, int userType) {
            super(view);
            switch (userType) {
                case USER_LIST_CASE:
                    textViewUsername = (AppCompatTextView) view.findViewById(R.id.textViewUsername);
                    ImageViewImage1 = (ImageView) view.findViewById(R.id.imageView);
                    textViewPoints = (AppCompatTextView) view.findViewById(R.id.textViewPoints);
                    textViewNumberTask = (AppCompatTextView) view.findViewById(R.id.textViewNumberTask);
                break;

                case USER_SWITCH_CASE:
                    textViewUsername = (AppCompatTextView) view.findViewById(R.id.textViewUsername);
                    ImageViewImage1 = (ImageView) view.findViewById(R.id.imageView);
                break;
            }

        }
    }


}