package ars.ramsey.interviewhelper.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.EditText;
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
    private boolean isEdit = false;
    private EditText curEdit = null;
    private int curPosition;

    public TaskDetailAdapter(Task task,boolean isEdit)
    {
        this.mTask = task;
        transferData(task);
        this.isEdit = isEdit;
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

    public Task getNewTask()
    {
        Task task = mTask;
        if(curEdit != null)
        {
            String text = curEdit.getText().toString();
            mData.set(curPosition,text);
        }
        task.setJobName(mData.get(0));
        task.setStatus(mData.get(1));
        task.setCreateDate(mData.get(2));
        task.setNextDate(mData.get(3));
        task.setAddress(mData.get(4));
        task.setFinishedDate(mData.get(5));
        task.setOffer(mData.get(6).equals("已获得"));
        return task;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_detail_item,parent,false);
        if(!isEdit)
        {
            ViewStub viewStub = (ViewStub) view.findViewById(R.id.task_noedit_stub);
            viewStub.inflate();
        }else{
            ViewStub viewStub = (ViewStub) view.findViewById(R.id.task_edit_stub);
            viewStub.inflate();
        }
        TaskDetailViewHolder viewHolder = new TaskDetailViewHolder(view,isEdit);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final TaskDetailViewHolder viewHolder = (TaskDetailViewHolder)holder;
        viewHolder.tvTitle.setText(ITEM_TITLE[position]);
        String data = mData.get(position);
        if(!isEdit) {
            if(data == null || data.equals(""))
                viewHolder.tvContent.setText("未知");
            else
                viewHolder.tvContent.setText(data);
        }else {
            viewHolder.tvEditContent.setText(data);
            viewHolder.tvEditContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus)
                    {
                        String text = viewHolder.tvEditContent.getText().toString();
                        mData.set(position,text);
                        viewHolder.tvEditContent.setText(text);
                    }else{
                        curEdit = (EditText) v;
                        curPosition = position;
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private static class TaskDetailViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvContent;
        private EditText tvEditContent;

        public TaskDetailViewHolder(View itemView,boolean isEdit) {
            super(itemView);
            if(isEdit)
            {
                tvEditContent = (EditText)itemView.findViewById(R.id.txtEditContent);
            }else{
                tvContent = (TextView)itemView.findViewById(R.id.txtContent);
            }
            tvTitle = (TextView)itemView.findViewById(R.id.txtTitle);
        }
    }
}
