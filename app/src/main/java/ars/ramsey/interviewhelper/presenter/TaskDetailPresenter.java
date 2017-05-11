package ars.ramsey.interviewhelper.presenter;

import android.util.Log;

import java.util.List;

import ars.ramsey.interviewhelper.model.TasksSource;
import ars.ramsey.interviewhelper.model.bean.Task;
import ars.ramsey.interviewhelper.model.local.TasksLocalSource;
import ars.ramsey.interviewhelper.view.TaskDetailView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

/**
 * Created by Ramsey on 2017/4/11.
 */

public class TaskDetailPresenter implements BasePresenter<TaskDetailView> {
    private TaskDetailView mView;
    private TasksLocalSource mLocalSource;

    public TaskDetailPresenter(TasksLocalSource source)
    {
        this.mLocalSource = source;
    }

    public void getTask(int id)
    {
        mLocalSource.getTask(id).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Task task) {
                mView.getTask(task);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
//        mLocalSource.getTask(id, new TasksSource.GetTaskCallback() {
//            @Override
//            public void onTaskLoaded(Task task) {
//                mView.getTask(task);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//
//            }
//        });
    }



    @Override
    public void attachView(TaskDetailView view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void detachView(TaskDetailView view) {
        mView.setPresenter(null);
        mView = null;
    }
}
