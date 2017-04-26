package ars.ramsey.interviewhelper.presenter;


import android.util.Log;

import java.util.Date;
import java.util.List;

import ars.ramsey.interviewhelper.model.TasksSource;
import ars.ramsey.interviewhelper.model.bean.TodoTask;
import ars.ramsey.interviewhelper.model.local.TasksLocalSource;
import ars.ramsey.interviewhelper.view.TaskCalendarView;

/**
 * Created by Ramsey on 2017/4/26.
 */

public class TaskDatePresenter implements BasePresenter<TaskCalendarView> {
    private TaskCalendarView mView;
    private TasksLocalSource mLocalSource;

    public TaskDatePresenter(TasksLocalSource localSource)
    {
        mLocalSource = localSource;
    }

    public void getTaskByMonth(Date date)
    {
        Log.i("RAMSEY",date.toString());
        mLocalSource.getTasksByMonth(date, new TasksSource.LoadTodoTasksCallback() {
            @Override
            public void onTasksLoaded(List<TodoTask> tasks) {
                mView.getTasks(tasks);
            }

            @Override
            public void onDataNotAvailable() {
                mView.getTasks(null);
            }
        });
    }


    @Override
    public void attachView(TaskCalendarView view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void detachView(TaskCalendarView view) {
        mView.setPresenter(null);
        mView = null;
    }
}
