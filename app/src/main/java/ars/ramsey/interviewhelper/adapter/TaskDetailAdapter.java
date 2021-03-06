package ars.ramsey.interviewhelper.adapter;

import android.app.DatePickerDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import ars.ramsey.interviewhelper.R;
import ars.ramsey.interviewhelper.model.bean.Task;

/**
 * Created by Ramsey on 2017/4/11.
 */

public class TaskDetailAdapter extends RecyclerView.Adapter {

    private static final int NORMAL_ITEM = 1;
    private static final int CALENDAR_ITEM  = 2;
    private static final int DROP_ITEM = 3;
    private static final int OFFER_ITEM = 4;

    private static final String[] ITEM_DATA = {
            "公  司",
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
    private boolean isCreate = false;
    private EditText curEdit = null;
    private int curPosition;
    private String[] itemTitle;

    public TaskDetailAdapter(Task task,boolean isEdit)
    {
        if(task != null)
            this.mTask = task;
        else
            this.mTask = new Task();
        transferData(mTask);
        this.isEdit = isEdit;
        this.itemTitle = Arrays.copyOfRange(ITEM_DATA,1,ITEM_DATA.length);
    }

    public TaskDetailAdapter(boolean isCreate)
    {
        this.mTask = new Task();
        this.isCreate = isCreate;
        this.isEdit = true;
        this.mData.clear();
        this.itemTitle = Arrays.copyOfRange(ITEM_DATA,0,ITEM_DATA.length);
        for(int i=0;i<itemTitle.length;i++)
            mData.add("");
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
        mData.add(task.isOffer()?"已获得":"未获得");
    }

    public Task getNewTask()
    {
        Task task = mTask;
        if(curEdit != null)
        {
            String text = curEdit.getText().toString();
            mData.set(curPosition,text);
        }
        int i = 0;
        if(isCreate) {
            task.setCompanyName(mData.get(i++));
        }
        task.setJobName(mData.get(i++));
        task.setStatus(mData.get(i++));
        task.setCreateDate(mData.get(i++));
        task.setNextDate(mData.get(i++));
        task.setAddress(mData.get(i++));
        task.setFinishedDate(mData.get(i++));
        task.setOffer(mData.get(i++).equals("已获得"));
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
            if(viewType == NORMAL_ITEM) {
                ViewStub viewStub = (ViewStub) view.findViewById(R.id.task_edit_stub);
                viewStub.inflate();
            }else if(viewType == DROP_ITEM) {
                ViewStub viewStub = (ViewStub) view.findViewById(R.id.task_drop_stub);
                viewStub.inflate();
            }else if(viewType == OFFER_ITEM)
            {
                ViewStub viewStub = (ViewStub) view.findViewById(R.id.task_offer_stub);
                viewStub.inflate();
            }else {
                ViewStub viewStub = (ViewStub) view.findViewById(R.id.task_calendar_stub);
                viewStub.inflate();
            }
        }
        TaskDetailViewHolder viewHolder = new TaskDetailViewHolder(view,isEdit,viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final TaskDetailViewHolder viewHolder = (TaskDetailViewHolder)holder;
        viewHolder.tvTitle.setText(itemTitle[position]);
        String data = mData.get(position);
        if(!isEdit) {
            if(data == null || data.equals(""))
                viewHolder.tvContent.setText("未知");
            else
                viewHolder.tvContent.setText(data);
        }else {
            if(getItemViewType(position) != OFFER_ITEM)
            {
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
            }else{
                viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
                        String[] items = view.getContext().getResources().getStringArray(R.array.offer_items);
                        mData.set(position,items[p]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        mData.set(position,"未获得");
                    }
                });
            }

        }

    }

    @Override
    public int getItemViewType(int position) {
        if(itemTitle[position].contains("日期"))
            return CALENDAR_ITEM;
        else if(itemTitle[position].equals("状  态"))
            return DROP_ITEM;
        else if(itemTitle[position].equals("OFFER"))
            return OFFER_ITEM;
        else
            return NORMAL_ITEM;
        //return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return itemTitle.length;
    }

    private static class TaskDetailViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvContent;
        private EditText tvEditContent;
        private Spinner spinner;

        public TaskDetailViewHolder(View itemView,boolean isEdit,int viewType) {
            super(itemView);
            if(isEdit)
            {
                if(viewType != OFFER_ITEM)
                    tvEditContent = (EditText)itemView.findViewById(R.id.txtEditContent);
                else
                    spinner = (Spinner)itemView.findViewById(R.id.spinner);
            }else{
                tvContent = (TextView)itemView.findViewById(R.id.txtContent);
            }
            tvTitle = (TextView)itemView.findViewById(R.id.txtTitle);
        }
    }
}
