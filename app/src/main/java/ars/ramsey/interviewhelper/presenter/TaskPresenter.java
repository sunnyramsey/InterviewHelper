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

    public TaskPresenter(TasksLocalSource tasksLocalSource)
    {
        mTasksSource = tasksLocalSource;
    }

    @Override
    public void attachView(TaskListView view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void detachView(TaskListView view) {
        mView.setPresenter(null);
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
                if(page == 1)
                {
                    mView.refresh(null);
                }else
                {
                    mView.loadMore(null);
                }

            }
        });

    }

    public void saveTasks(Task task)
    {
        mTasksSource.saveTask(task);
    }
    public void deleteTask(Task task) { mTasksSource.deleteTask(task.getId());}

}
