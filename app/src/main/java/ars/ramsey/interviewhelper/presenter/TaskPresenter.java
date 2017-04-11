package ars.ramsey.interviewhelper.presenter;

import android.util.Log;

import java.util.List;

import ars.ramsey.interviewhelper.model.TasksSource;
import ars.ramsey.interviewhelper.model.bean.Task;
import ars.ramsey.interviewhelper.model.local.TasksLocalSource;
import ars.ramsey.interviewhelper.view.BaseView;
import ars.ramsey.interviewhelper.view.TaskListView;

/**
 * Created by Ramsey on 2017/4/7.
 */

public class TaskPresenter implements BasePresenter<TaskListView>{

    private TaskListView mView;
    private TasksLocalSource mTasksSource;

    public TaskPresenter(TasksLocalSource tasksLocalSource,TaskListView view)
    {
        mView = view;
        mTasksSource = tasksLocalSource;
        mView.setPresenter(this);
    }

    @Override
    public void attachView(TaskListView view) {
        mView = view;
    }

    @Override
    public void detachView(TaskListView view) {
        mView = null;
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public TaskListView getView() {
        return mView;
    }

    public void loadTasks(final int page)
    {
        mTasksSource.getTasks(page, new TasksSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                if(page == 1)
                {
                    mView.refresh(tasks);
                }else{
                    mView.loadMore(tasks);
                }
            }

            @Override
            public void onDataNotAvailable() {
                mView.loadMore(null);
            }
        });

    }

    public void saveTasks(Task task)
    {
        mTasksSource.saveTask(task);
    }

}
