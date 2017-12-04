package adapters;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ca.uottawa.cohab.R;
import model.Task;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.TaskViewHolder> {

    private List<Task> listTasks;
    private int taskType;

    public TaskRecyclerAdapter(List<Task> listTasks, int taskType) {
        this.listTasks = listTasks;
        this.taskType = taskType;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        TaskViewHolder taskViewHolder = null;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task_list_recycler, parent, false);

        taskViewHolder =  new TaskViewHolder(itemView, taskType);

        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.textViewName.setText(listTasks.get(position).getName());
        holder.textViewDueDate.setText((listTasks.get(position).getDueDate()));
        holder.textViewAssign.setText((listTasks.get(position).getAssignedUser().getUsername()));
    }

    @Override
    public int getItemCount() {
        Log.v(TaskRecyclerAdapter.class.getSimpleName(),""+listTasks.size());
        return listTasks.size();
    }


    protected class TaskViewHolder extends RecyclerView.ViewHolder {

        protected AppCompatTextView textViewName;
        protected AppCompatTextView textViewDueDate;
        protected AppCompatTextView textViewAssign;

        protected TaskViewHolder(View view, int userType) {
            super(view);

            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            textViewDueDate = (AppCompatTextView) view.findViewById(R.id.textViewDueDate);
            textViewAssign = (AppCompatTextView) view.findViewById(R.id.textViewAssign);
        }
    }


}