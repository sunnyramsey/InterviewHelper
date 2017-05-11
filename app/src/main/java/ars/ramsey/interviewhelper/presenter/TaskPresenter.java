package ars.ramsey.interviewhelper.presenter;

import android.util.Log;

import org.reactivestreams.Subscriber;

import java.util.List;

import ars.ramsey.interviewhelper.model.TasksSource;
import ars.ramsey.interviewhelper.model.bean.Task;
import ars.ramsey.interviewhelper.model.local.TasksLocalSource;
import ars.ramsey.interviewhelper.view.BaseView;
import ars.ramsey.interviewhelper.view.TaskListView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ramsey on 2017/4/7.
 */

public class TaskPresenter implements BasePresenter<TaskListView>{

    private TaskListView mView;
    private TasksSource mTasksSource;

    public TaskPresenter(TasksSource tasksLocalSource)
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
        mTasksSource.getTasks(page).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Task>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Task> tasks) {
                if(tasks.size() == 0)
                {
                    if(page == 1)
                    {
                        mView.refresh(null);
                    }else
                    {
                        mView.loadMore(null);
                    }
                }else{
                    if(page == 1)
                    {
                        mView.refresh(tasks);
                    }else{
                        mView.loadMore(tasks);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


    }

    public void saveTasks(Task task)
    {
        mTasksSource.saveTask(task).subscribe();
    }
    public void deleteTask(Task task) { mTasksSource.deleteTask(task.getId()).subscribe();}

}
