package adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ca.uottawa.cohab.R;
import model.User;

public class UserListRecyclerAdapter extends RecyclerView.Adapter<UserListRecyclerAdapter.UserViewHolder> {

    private List<User> listUsers;

    public UserListRecyclerAdapter(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_list_recycler, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewUsername.setText(listUsers.get(position).getUsername());
        holder.textViewPoints.setText(String.valueOf(listUsers.get(position).getPoints()));
        holder.textViewNumberTask.setText(String.valueOf(listUsers.get(position).getNumberTask()));
    }

    @Override
    public int getItemCount() {
        Log.v(UserListRecyclerAdapter.class.getSimpleName(),""+listUsers.size());
        return listUsers.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewUsername;
        public AppCompatTextView textViewPoints;
        public AppCompatTextView textViewNumberTask;

        public UserViewHolder(View view) {
            super(view);
            textViewUsername = (AppCompatTextView) view.findViewById(R.id.textViewUsername);
            textViewPoints = (AppCompatTextView) view.findViewById(R.id.textViewEmail);
            textViewNumberTask = (AppCompatTextView) view.findViewById(R.id.textViewPassword);
        }
    }


}