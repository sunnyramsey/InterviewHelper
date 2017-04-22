package ars.ramsey.interviewhelper.model;

import java.util.List;

import ars.ramsey.interviewhelper.model.bean.Task;

/**
 * Created by Ramsey on 2017/4/7.
 */

public interface TasksSource {

    interface LoadTasksCallback {

        void onTasksLoaded(List<Task> tasks);

        void onDataNotAvailable();
    }

    interface GetTaskCallback {

        void onTaskLoaded(Task task);

        void onDataNotAvailable();
    }

    void getTasks(int page,LoadTasksCallback callback);

    void getTask(int taskId, GetTaskCallback callback);

    void saveTask(Task task);

    void updateTask(Task task);

    void completeTask(Task task);

    void completeTask(String taskId);

    void activateTask(Task task);

    void activateTask(String taskId);

    void clearCompletedTasks();

    void refreshTasks();

    void deleteAllTasks();

    void deleteTask(String taskId);
}
