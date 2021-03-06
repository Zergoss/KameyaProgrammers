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
import model.Resource;

public class ResourceRecyclerAdapter extends RecyclerView.Adapter<ResourceRecyclerAdapter.ResourceViewHolder> {

    private List<Resource> listResource;

    public ResourceRecyclerAdapter(List<Resource> listResource) {
        this.listResource = listResource;
    }

    @Override
    public ResourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_resource_recycler, parent, false);

        ResourceViewHolder resourceViewHolder =  new ResourceViewHolder(itemView);

        return resourceViewHolder;
    }

    @Override
    public void onBindViewHolder(ResourceViewHolder holder, int position) {
        holder.textResourceName.setText(listResource.get(position).getName());
        holder.textResourceDescription.setText((listResource.get(position).getDescription()));
    }

    @Override
    public int getItemCount() {
        Log.v(ResourceRecyclerAdapter.class.getSimpleName(),""+listResource.size());
        return listResource.size();
    }


    protected class ResourceViewHolder extends RecyclerView.ViewHolder {

        protected AppCompatTextView textResourceName;
        protected AppCompatTextView textResourceDescription;

        protected ResourceViewHolder(View view) {
            super(view);

            textResourceName = (AppCompatTextView) view.findViewById(R.id.textResourceName);
            textResourceDescription = (AppCompatTextView) view.findViewById(R.id.textResourceDescription);
        }
    }


}