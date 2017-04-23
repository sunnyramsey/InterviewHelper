package ars.ramsey.interviewhelper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;
import com.jpardogo.android.googleprogressbar.library.NexusRotationCrossDrawable;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ars.ramsey.interviewhelper.R;
import ars.ramsey.interviewhelper.activity.TaskEditActivity;
import ars.ramsey.interviewhelper.adapter.TaskListAdapter;
import ars.ramsey.interviewhelper.adapter.TaskListSwipeAdapter;
import ars.ramsey.interviewhelper.model.bean.Task;
import ars.ramsey.interviewhelper.presenter.TaskPresenter;
import ars.ramsey.interviewhelper.view.LoadMoreRecyclerView;
import ars.ramsey.interviewhelper.view.TaskListView;

/**
 * Created by Ramsey on 2017/4/7.
 */

public class TaskListFragment extends Fragment implements TaskListView<TaskPresenter>{

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView loadMoreRecyclerView;
    private TaskListSwipeAdapter taskListAdapter;
    private FloatingActionButton actionButton;

    private ArrayList<Task> mDataList = new ArrayList<>();
    private int page = 1;

    private TaskPresenter presenter;

    //For testing
    private RecyclerView.Adapter adapter;
    private ArrayList<String> mDataSet;

    public TaskListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task_list_layout,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actionButton = (FloatingActionButton)view.findViewById(R.id.icon_fab);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
        loadMoreRecyclerView = (LoadMoreRecyclerView)view.findViewById(R.id.load_more_recycleview);

        actionButton.attachToRecyclerView(loadMoreRecyclerView);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("RAMSEY","Create New Task");
                Intent intent = new Intent(getContext(), TaskEditActivity.class);
                intent.putExtra("CREATE",true);
                startActivity(intent);
            }
        });


        //Init Recyclerview
        taskListAdapter = new TaskListSwipeAdapter(getContext(),mDataList);
        taskListAdapter.setMode(Attributes.Mode.Single);

        loadMoreRecyclerView.setAdapter(taskListAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        loadMoreRecyclerView.setLayoutManager(manager);

        ProgressBar progressBar = new ProgressBar(getContext());
        progressBar.setIndeterminateDrawable(new NexusRotationCrossDrawable.Builder(getContext()).colors(getResources().getIntArray(R.array.progress_bar_colors)).build());
        loadMoreRecyclerView.setLoadingFooterView(progressBar);

        TextView textView = new TextView(getContext());
        textView.setText("已经到底拉╮(╯_╰)╭");
        loadMoreRecyclerView.setEndFooterView(textView);

        loadMoreRecyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                presenter.loadTasks(page);
//                mDataSet.add("RAMSEY");
//                adapter.notifyDataSetChanged();
//                loadMoreRecyclerView.loadMoreComplete();
            }
        });



        //Init swipe refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                presenter.loadTasks(page);

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadTasks(page);
        taskListAdapter.notifyDataSetChanged();
    }


    @Override
    public void setPresenter(TaskPresenter presenter) {
        this.presenter = presenter;
    }

    //Make sure this function should be called in UI thread.
    @Override
    public void refresh(List<Task> data) {
        loadMoreRecyclerView.refreshComplete();
        loadMoreRecyclerView.loadMoreComplete();
        swipeRefreshLayout.setRefreshing(false);
        mDataList.clear();
        if(data != null)
            mDataList.addAll(data);
        taskListAdapter.notifyDataSetChanged();
    }

    //Make sure this function should be called in UI thread.
    @Override
    public void loadMore(final List<Task> data) {
        if(data == null || data.size() == 0)
        {
            loadMoreRecyclerView.loadMoreEnd();
        }else{
            mDataList.addAll(data);
            taskListAdapter.notifyDataSetChanged();
            loadMoreRecyclerView.loadMoreComplete();
        }
    }
}
