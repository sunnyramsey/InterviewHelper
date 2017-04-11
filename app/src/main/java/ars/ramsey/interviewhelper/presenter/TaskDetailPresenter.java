package ars.ramsey.interviewhelper.presenter;

import android.util.Log;

import java.util.List;

import ars.ramsey.interviewhelper.model.TasksSource;
import ars.ramsey.interviewhelper.model.bean.Task;
import ars.ramsey.interviewhelper.model.local.TasksLocalSource;
import ars.ramsey.interviewhelper.view.TaskDetailView;

/**
 * Created by Ramsey on 2017/4/11.
 */

public class TaskDetailPresenter implements BasePresenter<TaskDetailView> {
    private TaskDetailView mView;
    private TasksLocalSource mLocalSource;

    public TaskDetailPresenter(TasksLocalSource source,TaskDetailView view)
    {
        this.mView = view;
        this.mLocalSource = source;
        mView.setPresenter(this);
    }

    public void getTask(int id)
    {
        mLocalSource.getTask(id, new TasksSource.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                mView.getTask(task);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }



    @Override
    public void attachView(TaskDetailView view) {
        mView = view;
    }

    @Override
    public void detachView(TaskDetailView view) {
        mView = null;
    }
}
