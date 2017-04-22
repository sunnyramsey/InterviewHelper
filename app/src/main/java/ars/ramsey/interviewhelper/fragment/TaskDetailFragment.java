package ars.ramsey.interviewhelper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;

import ars.ramsey.interviewhelper.R;
import ars.ramsey.interviewhelper.activity.TaskDetailActivity;
import ars.ramsey.interviewhelper.activity.TaskEditActivity;
import ars.ramsey.interviewhelper.adapter.TaskDetailAdapter;
import ars.ramsey.interviewhelper.model.bean.Task;
import ars.ramsey.interviewhelper.presenter.TaskDetailPresenter;
import ars.ramsey.interviewhelper.view.TaskDetailView;

/**
 * Created by Ramsey on 2017/4/10.
 */

public class TaskDetailFragment extends Fragment implements TaskDetailView<TaskDetailPresenter>{

    private TaskDetailPresenter mPresenter;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private RecyclerView recyclerView;
    private TaskDetailAdapter mAdapter;
    private Task mTask;
    private FloatingActionButton floatingActionButton;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task_detail_layout,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("RAMSEY",this.getClass().toString()+":onViewCreated");


        mTask = getArguments().getParcelable("TASK");

        //toolbar init
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((TaskDetailActivity)getActivity()).setSupportActionBar(toolbar);
        ((TaskDetailActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout)view.findViewById(R.id.collapsingtoolbar);
        collapsingToolbarLayout.setTitle(mTask.getCompanyName());

        //recyclerview init
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        mAdapter = new TaskDetailAdapter(mTask,false);
        recyclerView.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);


        //floating action init
        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.icon_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TaskEditActivity.class);
                intent.putExtra("TASK",mTask);
                intent.putExtra("CREATE",false);
                startActivityForResult(intent,0);
            }
        });

        mPresenter.getTask(mTask.getId());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i("RAMSEY",this.getClass().toString()+":onActivityCreated");
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onResume() {
        Log.i("RAMSEY",this.getClass().toString()+":onResume");
        super.onResume();
    }

    @Override
    public void setPresenter(TaskDetailPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getTask(Task task) {
        mTask = task;
        mAdapter.transferData(task);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 0:
                Log.i("RAMSEY","CANCLE");
                break;
            case 1:
                Task task = data.getParcelableExtra("TASK");
                mTask = task;
                getTask(task);
                break;
        }
    }
}
