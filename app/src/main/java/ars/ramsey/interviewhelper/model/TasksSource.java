package ars.ramsey.interviewhelper.model;

import java.util.Date;
import java.util.List;

import ars.ramsey.interviewhelper.model.bean.Task;
import ars.ramsey.interviewhelper.model.bean.TodoTask;
import io.reactivex.Observable;

/**
 * Created by Ramsey on 2017/4/7.
 */

public interface TasksSource {

    interface LoadTasksCallback {

        void onTasksLoaded(List<Task> tasks);

        void onDataNotAvailable();
    }

    interface LoadTodoTasksCallback {

        void onTasksLoaded(List<TodoTask> tasks);

        void onDataNotAvailable();
    }

    interface GetTaskCallback {

        void onTaskLoaded(Task task);

        void onDataNotAvailable();
    }

    Observable getTasks(int page);

    Observable getTask(int taskId);

    Observable saveTask(Task task);

    Observable updateTask(Task task);

    Observable getTasksByMonth(Date date);

    void completeTask(Task task);

    void completeTask(String taskId);

    void activateTask(Task task);

    void activateTask(String taskId);

    void clearCompletedTasks();

    void refreshTasks();

    void deleteAllTasks();

    Observable deleteTask(int taskId);
}
