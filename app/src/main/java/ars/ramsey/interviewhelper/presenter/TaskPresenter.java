package ars.ramsey.interviewhelper.presenter;

import ars.ramsey.interviewhelper.model.bean.Task;
import ars.ramsey.interviewhelper.view.BaseView;
import ars.ramsey.interviewhelper.view.TaskListView;

/**
 * Created by Ramsey on 2017/4/7.
 */

public class TaskPresenter implements BasePresenter<TaskListView>{

    private TaskListView mView;


    public TaskPresenter()
    {

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

    public void loadTasks()
    {

    }

}
