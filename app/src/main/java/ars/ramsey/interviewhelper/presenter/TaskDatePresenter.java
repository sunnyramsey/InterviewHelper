package ars.ramsey.interviewhelper.presenter;


import android.util.Log;

import com.prolificinteractive.materialcalendarview.DayViewDecorator;

import java.util.Date;
import java.util.List;

import ars.ramsey.interviewhelper.model.TasksSource;
import ars.ramsey.interviewhelper.model.bean.TodoTask;
import ars.ramsey.interviewhelper.model.local.TasksLocalSource;
import ars.ramsey.interviewhelper.view.TaskCalendarView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
        mLocalSource.getTasksByMonth(date).observeOn(Schedulers.newThread()).map(new Function<List<TodoTask>, List<DayViewDecorator>>() {
            @Override
            public List<DayViewDecorator> apply(@NonNull List<TodoTask> todoTasks) throws Exception {
                return mView.transferDecorators(todoTasks);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<DayViewDecorator>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<DayViewDecorator> decorators) {
                mView.refreshDecorators(decorators);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
//        mLocalSource.getTasksByMonth(date, new TasksSource.LoadTodoTasksCallback() {
//            @Override
//            public void onTasksLoaded(List<TodoTask> tasks) {
//                mView.getTasks(tasks);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//                mView.getTasks(null);
//            }
//        });
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
