package ars.ramsey.interviewhelper.model.local;

import android.content.Context;

import ars.ramsey.interviewhelper.model.TasksSource;
import ars.ramsey.interviewhelper.model.bean.Task;

/**
 * Created by Ramsey on 2017/4/7.
 */

public class TasksLocalSource implements TasksSource {
    private DbHelper dbHelper;
    private static TasksLocalSource instance;

    private TasksLocalSource(Context context)
    {
        dbHelper = new DbHelper(context);
    }

    public static TasksLocalSource getInstance(Context context)
    {
        if(instance == null)
        {
            synchronized (TasksLocalSource.class)
            {
                if(instance == null)
                    instance = new TasksLocalSource(context);
            }
        }
        return instance;
    }


    @Override
    public void getTasks(LoadTasksCallback callback) {

    }

    @Override
    public void getTask(String taskId, GetTaskCallback callback) {

    }

    @Override
    public void saveTask(Task task) {

    }

    @Override
    public void completeTask(Task task) {

    }

    @Override
    public void completeTask(String taskId) {

    }

    @Override
    public void activateTask(Task task) {

    }

    @Override
    public void activateTask(String taskId) {

    }

    @Override
    public void clearCompletedTasks() {

    }

    @Override
    public void refreshTasks() {

    }

    @Override
    public void deleteAllTasks() {

    }

    @Override
    public void deleteTask(String taskId) {

    }
}
