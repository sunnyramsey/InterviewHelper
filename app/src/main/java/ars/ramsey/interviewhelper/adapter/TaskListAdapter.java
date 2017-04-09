package ars.ramsey.interviewhelper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ars.ramsey.interviewhelper.R;

/**
 * Created by dell on 2017/4/8.
 */

public class TaskListAdapter extends RecyclerView.Adapter {
    private ArrayList<Integer> mDataList;
    private Context mContext;

    public TaskListAdapter(Context context,ArrayList<Integer> data)
    {
        mContext = context;
        mDataList = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.task_list_item,null);
        RecyclerView.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).txtView.setText(String.valueOf(mDataList.get(position)));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtView;
        public ViewHolder(View itemView) {
            super(itemView);
            txtView = (TextView)itemView.findViewById(R.id.testView);
        }
    }
}
