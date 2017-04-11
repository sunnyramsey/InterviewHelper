package ars.ramsey.interviewhelper.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ars.ramsey.interviewhelper.R;
import ars.ramsey.interviewhelper.activity.TaskDetailActivity;
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
    private TaskDetailAdapter adapter;
    private Task task;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task_detail_layout,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        task = getArguments().getParcelable("TASK");

        //toolbar init
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((TaskDetailActivity)getActivity()).setSupportActionBar(toolbar);
        ((TaskDetailActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout)view.findViewById(R.id.collapsingtoolbar);
        collapsingToolbarLayout.setTitle(task.getCompanyName());

        //recyclerview init
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        adapter = new TaskDetailAdapter(task);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getTask(task.getId());
    }

    @Override
    public void setPresenter(TaskDetailPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getTask(Task task) {
        adapter.transferData(task);
        adapter.notifyDataSetChanged();
    }
}
