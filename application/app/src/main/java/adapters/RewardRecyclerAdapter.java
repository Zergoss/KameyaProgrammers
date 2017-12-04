package adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ca.uottawa.cohab.R;
import model.Recompenses;

public class RewardRecyclerAdapter extends RecyclerView.Adapter<RewardRecyclerAdapter.RewardViewHolder> {

    private List<Recompenses> listReward;
    private int rewardType;

    public RewardRecyclerAdapter(List<Recompenses> listReward, int rewardType) {
        this.listReward = listReward;
        this.rewardType = rewardType;
    }

    @Override
    public RewardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        RewardViewHolder rewardViewHolder = null;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reward_recycler, parent, false);

        rewardViewHolder =  new RewardViewHolder(itemView, rewardType);

        return rewardViewHolder;
    }

    @Override
    public void onBindViewHolder(RewardViewHolder holder, int position) {
        holder.textRewardName.setText(listReward.get(position).getName());
        holder.textRewardDescription.setText((listReward.get(position).getDescription()));
    }

    @Override
    public int getItemCount() {
        Log.v(RewardRecyclerAdapter.class.getSimpleName(),""+listReward.size());
        return listReward.size();
    }


    protected class RewardViewHolder extends RecyclerView.ViewHolder {

        protected AppCompatTextView textRewardName;
        protected AppCompatTextView textRewardDescription;

        protected RewardViewHolder(View view, int userType) {
            super(view);

            textRewardName = (AppCompatTextView) view.findViewById(R.id.textRewardName);
            textRewardDescription = (AppCompatTextView) view.findViewById(R.id.textRewardDescription);
        }
    }


}