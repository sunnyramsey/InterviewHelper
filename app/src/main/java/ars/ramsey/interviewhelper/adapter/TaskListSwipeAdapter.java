package ars.ramsey.interviewhelper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import ars.ramsey.interviewhelper.R;
import ars.ramsey.interviewhelper.activity.TaskDetailActivity;
import ars.ramsey.interviewhelper.model.bean.Task;
import ars.ramsey.interviewhelper.presenter.TaskPresenter;

/**
 * Created by Ramsey on 2017/4/13.
 */

public class TaskListSwipeAdapter extends RecyclerSwipeAdapter<TaskListSwipeAdapter.SimpleViewHolder> {

    private ArrayList<Task> mDataList;
    private Context mContext;
    private TaskPresenter mPresenter;

    public TaskListSwipeAdapter(Context context, ArrayList<Task> data, TaskPresenter presenter)
    {
        mContext = context;
        mDataList = data;
        mPresenter = presenter;
        setHasStableIds(true);
    }


    @Override
    public TaskListSwipeAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.task_list_swipe_item,parent,false);
        SimpleViewHolder viewHolder = new SimpleViewHolder(view);
        //If target version is higher than 23,inflate cant using parent,it doesnt have the layoutparams,need to set it otherwise it cant display correct.
        //viewHolder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TaskListSwipeAdapter.SimpleViewHolder viewHolder, final int position) {
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TaskDetailActivity.class);
                intent.putExtra("TASK", mDataList.get(position));
                mContext.startActivity(intent);
            }
        });
        Task task = mDataList.get(position);
        viewHolder.txtCompany.setText(task.getCompanyName());
        viewHolder.txtJob.setText(task.getJobName());
        viewHolder.txtStatus.setText(task.getStatus());
        if(task.getAddress() == null || task.getAddress().equals("")) {
            viewHolder.txtAddress.setVisibility(View.GONE);
        }
        else {
            viewHolder.txtAddress.setVisibility(View.VISIBLE);
            viewHolder.txtAddress.setText(task.getAddress());
        }
        if(task.getNextDate() == null || task.getNextDate().equals(""))
            viewHolder.txtNextDate.setVisibility(View.GONE);
        else {
            viewHolder.txtNextDate.setVisibility(View.VISIBLE);
            viewHolder.txtNextDate.setText(task.getNextDate());
        }

        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.closeAllItems();
                Task task = mDataList.get(position);
                mPresenter.deleteTask(task);
                mDataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mDataList.size()-position);
            }

        });
        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public long getItemId(int position) {
        return mDataList.get(position).getId();
    }


    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView txtCompany;
        TextView txtJob;
        TextView txtStatus;
        TextView txtNextDate;
        TextView txtAddress;
        SimpleDraweeView icon;
        Button buttonDelete;
        CardView cardView;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            txtCompany = (TextView)itemView.findViewById(R.id.tv_company_name);
            txtJob = (TextView)itemView.findViewById(R.id.tv_job_name);
            txtStatus = (TextView)itemView.findViewById(R.id.tv_status);
            txtNextDate = (TextView)itemView.findViewById(R.id.tv_next_date);
            txtAddress = (TextView)itemView.findViewById(R.id.tv_address);
            icon = (SimpleDraweeView)itemView.findViewById(R.id.company_icon);
            buttonDelete = (Button) itemView.findViewById(R.id.delete);
            cardView = (CardView)itemView.findViewById(R.id.cardView);

        }
    }
}
