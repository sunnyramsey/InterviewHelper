package ars.ramsey.interviewhelper.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import ars.ramsey.interviewhelper.R;
import ars.ramsey.interviewhelper.model.bean.Task;

/**
 * Created by Ramsey on 2017/4/11.
 */

public class TaskDetailAdapter extends RecyclerView.Adapter {

    private static final String[] ITEM_TITLE = {
            "岗  位",
            "状  态",
            "创建日期",
            "下个日期",
            "地址",
            "结束日期",
            "OFFER",
    };
    private Task mTask;
    private ArrayList<String> mData = new ArrayList<>();

    public TaskDetailAdapter(Task task)
    {
        this.mTask = task;
        transferData(task);
    }

    public void transferData(Task task)
    {
        this.mTask = task;
        mData.clear();
        mData.add(task.getJobName());
        mData.add(task.getStatus());
        mData.add(task.getCreateDate());
        mData.add(task.getNextDate());
        mData.add(task.getAddress());
        mData.add(task.getFinishedDate());
        mData.add(task.isOffer()?"已获得":"");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_detail_item,parent,false);
        TaskDetailViewHolder viewHolder = new TaskDetailViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TaskDetailViewHolder viewHolder = (TaskDetailViewHolder)holder;
        viewHolder.tvTitle.setText(ITEM_TITLE[position]);
        String data = mData.get(position);
        if(data == null || data.equals(""))
            viewHolder.tvContent.setText("未知");
        else
            viewHolder.tvContent.setText(data);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private static class TaskDetailViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvContent;

        public TaskDetailViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView)itemView.findViewById(R.id.txtTitle);
            tvContent = (TextView)itemView.findViewById(R.id.txtContent);
        }
    }
}
