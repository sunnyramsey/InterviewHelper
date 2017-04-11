package ars.ramsey.interviewhelper.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ars.ramsey.interviewhelper.R;
import ars.ramsey.interviewhelper.activity.TaskDetailActivity;
import ars.ramsey.interviewhelper.model.bean.Task;

/**
 * Created by dell on 2017/4/8.
 */

public class TaskListAdapter extends RecyclerView.Adapter {
    private ArrayList<Task> mDataList;
    private Context mContext;

    public TaskListAdapter(Context context,ArrayList<Task> data)
    {
        mContext = context;
        mDataList = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.task_list_item,parent,false);
        RecyclerView.ViewHolder viewHolder = new ViewHolder(view);
        //If target version is higher than 23,inflate cant using parent,it doesnt have the layoutparams,need to set it otherwise it cant display correct.
        //viewHolder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        if(task.getAddress() == null || task.getAddress().equals(""))
            viewHolder.txtAddress.setVisibility(View.GONE);
        else
            viewHolder.txtAddress.setText(task.getAddress());
        if(task.getNextDate() == null || task.getNextDate().equals(""))
            viewHolder.txtNextDate.setVisibility(View.GONE);
        else
            viewHolder.txtNextDate.setText(task.getAddress());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtCompany;
        TextView txtJob;
        TextView txtStatus;
        TextView txtNextDate;
        TextView txtAddress;
        SimpleDraweeView icon;
        public ViewHolder(View itemView) {
            super(itemView);
            txtCompany = (TextView)itemView.findViewById(R.id.tv_company_name);
            txtJob = (TextView)itemView.findViewById(R.id.tv_job_name);
            txtStatus = (TextView)itemView.findViewById(R.id.tv_status);
            txtNextDate = (TextView)itemView.findViewById(R.id.tv_next_date);
            txtAddress = (TextView)itemView.findViewById(R.id.tv_address);
            icon = (SimpleDraweeView)itemView.findViewById(R.id.company_icon);
        }
    }
}
