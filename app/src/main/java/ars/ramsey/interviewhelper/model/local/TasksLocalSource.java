package ars.ramsey.interviewhelper.model.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ars.ramsey.interviewhelper.model.TasksSource;
import ars.ramsey.interviewhelper.model.bean.Task;
import ars.ramsey.interviewhelper.model.bean.TodoTask;
import ars.ramsey.interviewhelper.model.local.TasksPersistenceContract.TaskEntry;
import ars.ramsey.interviewhelper.model.local.TodoTasksPersistenceContract.TodoTasksEntry;

/**
 * Created by Ramsey on 2017/4/7.
 */

public class TasksLocalSource implements TasksSource {
    private DbHelper dbHelper;
    private static TasksLocalSource instance;

    private static final int PAGE_SIZE = 10;

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
    public void getTasks(int page, LoadTasksCallback callback) {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                TaskEntry.COLUMN_NAME_ID,
                TaskEntry.COLUMN_NAME_COMPANY_NAME,
                TaskEntry.COLUMN_NAME_JOB_NAME,
                TaskEntry.COLUMN_NAME_STATUS,
                TaskEntry.COLUMN_NAME_NEXT_DATE,
                TaskEntry.COLUMN_NAME_ADDRESS
        };

        int offset = (page - 1) * PAGE_SIZE;
        String limit = String.valueOf(offset)+"," + PAGE_SIZE;

        Cursor c = db.query(TaskEntry.TABLE_NAME,projection,"completed = ?",new String[]{"0"},null,null,"id asc",limit);


        if(c != null && c.getCount() > 0)
        {
            while(c.moveToNext())
            {
                int id = c.getInt(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_ID));
                String companyName = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_COMPANY_NAME));
                String jobName = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_JOB_NAME));
                String status = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_STATUS));
                String nextDate = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_NEXT_DATE));
                String address = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_ADDRESS));
                Task task = new Task(id,companyName,jobName,status,nextDate,address);
                tasks.add(task);

            }
        }

        if(c != null)
            c.close();
        db.close();
        if(tasks.isEmpty())
            callback.onDataNotAvailable();
        else
            callback.onTasksLoaded(tasks);

    }

    @Override
    public void getTask(int taskId, GetTaskCallback callback) {
        Task task = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query(TaskEntry.TABLE_NAME,null,"id=?",new String[]{String.valueOf(taskId)},null,null,null);

        if(c != null && c.getCount() == 1)
        {
            while(c.moveToNext())
            {
                int id = c.getInt(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_ID));
                String companyName = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_COMPANY_NAME));
                String jobName = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_JOB_NAME));
                String status = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_STATUS));
                String createDate = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_CREATE_DATE));
                String nextDate = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_NEXT_DATE));
                String finishDate = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_FINISHED_DATE));
                String address = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_ADDRESS));
                int offer = c.getInt(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_OFFER));
                task = new Task(id,companyName,jobName,status,createDate,nextDate,finishDate,offer == 1?true:false,address);
            }
        }

        if(c != null)
            c.close();
        db.close();
        if(task == null)
            callback.onDataNotAvailable();
        else
            callback.onTaskLoaded(task);

    }

    @Override
    public void saveTask(Task task) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TaskEntry.COLUMN_NAME_COMPANY_NAME,task.getCompanyName());
        contentValues.put(TaskEntry.COLUMN_NAME_JOB_NAME,task.getJobName());
        contentValues.put(TaskEntry.COLUMN_NAME_STATUS,task.getStatus());
        contentValues.put(TaskEntry.COLUMN_NAME_CREATE_DATE,task.getCreateDate());
        contentValues.put(TaskEntry.COLUMN_NAME_NEXT_DATE,task.getNextDate());
        contentValues.put(TaskEntry.COLUMN_NAME_FINISHED_DATE,task.getFinishedDate());
        contentValues.put(TaskEntry.COLUMN_NAME_COMPLETED,task.isCompleted());
        contentValues.put(TaskEntry.COLUMN_NAME_OFFER,task.isOffer());
        contentValues.put(TaskEntry.COLUMN_NAME_ADDRESS,task.getAddress());

        long id = db.insert(TaskEntry.TABLE_NAME,null,contentValues);
        if(!task.getNextDate().equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date date = sdf.parse(task.getNextDate());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                ContentValues todoValues = new ContentValues();
                todoValues.put(TodoTasksEntry.COLUMN_NAME_ID, id);
                todoValues.put(TodoTasksEntry.COLUMN_NAME_COMPANY_NAME, task.getCompanyName());
                todoValues.put(TodoTasksEntry.COLUMN_NAME_YEAR, calendar.get(Calendar.YEAR));
                todoValues.put(TodoTasksEntry.COLUMN_NAME_MONTH, calendar.get(Calendar.MONTH)+1);
                todoValues.put(TodoTasksEntry.COLUMN_NAME_DAY, calendar.get(Calendar.DAY_OF_MONTH));
                db.insert(TodoTasksEntry.TABLE_NAME, null, todoValues);
            } catch (ParseException e) {
                Log.e("RAMSEY", "SAVE TO DO TASK ERROR!");
                e.printStackTrace();
            }
        }
        db.close();
    }

    @Override
    public void updateTask(Task task) {
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put(TaskEntry.COLUMN_NAME_COMPANY_NAME,task.getCompanyName());
            contentValues.put(TaskEntry.COLUMN_NAME_JOB_NAME,task.getJobName());
            contentValues.put(TaskEntry.COLUMN_NAME_STATUS,task.getStatus());
            contentValues.put(TaskEntry.COLUMN_NAME_CREATE_DATE,task.getCreateDate());
            contentValues.put(TaskEntry.COLUMN_NAME_NEXT_DATE,task.getNextDate());
            contentValues.put(TaskEntry.COLUMN_NAME_FINISHED_DATE,task.getFinishedDate());
            contentValues.put(TaskEntry.COLUMN_NAME_COMPLETED,task.isCompleted());
            contentValues.put(TaskEntry.COLUMN_NAME_OFFER,task.isOffer());
            contentValues.put(TaskEntry.COLUMN_NAME_ADDRESS,task.getAddress());

            db.update(TaskEntry.TABLE_NAME,contentValues,"id=?",new String[]{String.valueOf(task.getId())});

            if(!task.getNextDate().equals("")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                    Date date = sdf.parse(task.getNextDate());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    ContentValues todoValues = new ContentValues();
                    todoValues.put(TodoTasksEntry.COLUMN_NAME_COMPANY_NAME, task.getCompanyName());
                    todoValues.put(TodoTasksEntry.COLUMN_NAME_YEAR, calendar.get(Calendar.YEAR));
                    todoValues.put(TodoTasksEntry.COLUMN_NAME_MONTH, calendar.get(Calendar.MONTH)+1);
                    todoValues.put(TodoTasksEntry.COLUMN_NAME_DAY, calendar.get(Calendar.DAY_OF_MONTH));
                    Cursor c = db.query(TodoTasksEntry.TABLE_NAME,null,"id=?",new String[]{String.valueOf(task.getId())},null,null,null);
                    if(c == null || c.getCount() == 0)
                    {
                        todoValues.put(TodoTasksEntry.COLUMN_NAME_ID,task.getId());
                        db.insert(TodoTasksEntry.TABLE_NAME,null,todoValues);
                        Log.i("RAMSEY","new to do task");
                    }else{
                        db.update(TodoTasksEntry.TABLE_NAME, todoValues,"id=?",new String[]{String.valueOf(task.getId())});
                        Log.i("RAMSEY","update to do task");
                    }
                } catch (ParseException e) {
                    Log.e("RAMSEY", "SAVE TO DO TASK ERROR!");
                    e.printStackTrace();
                }
            }
            db.close();
        }catch (Exception e)
        {
            Log.i("RAMSEY","exception");
        }


    }



    @Override
    public void getTasksByMonth(Date date, LoadTodoTasksCallback callback) {
        List<TodoTask> tasks = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                TodoTasksEntry.COLUMN_NAME_ID,
                TodoTasksEntry.COLUMN_NAME_COMPANY_NAME,
                TodoTasksEntry.COLUMN_NAME_YEAR,
                TodoTasksEntry.COLUMN_NAME_MONTH,
                TodoTasksEntry.COLUMN_NAME_DAY,
        };

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int queryYear = calendar.get(Calendar.YEAR);
        int queryMonth = calendar.get(Calendar.MONTH)+1;
        Log.i("RAMSEY","query:"+queryYear+":"+queryMonth);

        Cursor c = db.query(TodoTasksEntry.TABLE_NAME,projection,"year = ? and month = ?",new String[]{String.valueOf(queryYear),String.valueOf(queryMonth)},null,null,"id asc");


        if(c != null && c.getCount() > 0)
        {
            while(c.moveToNext())
            {
                int id = c.getInt(c.getColumnIndexOrThrow(TodoTasksEntry.COLUMN_NAME_ID));
                String companyName = c.getString(c.getColumnIndexOrThrow(TodoTasksEntry.COLUMN_NAME_COMPANY_NAME));
                int year = c.getInt(c.getColumnIndexOrThrow(TodoTasksEntry.COLUMN_NAME_YEAR));
                int month = c.getInt(c.getColumnIndexOrThrow(TodoTasksEntry.COLUMN_NAME_MONTH));
                int day = c.getInt(c.getColumnIndexOrThrow(TodoTasksEntry.COLUMN_NAME_DAY));
                TodoTask task = new TodoTask(id,companyName,year,month,day);
                tasks.add(task);
            }
        }

        if(c != null)
            c.close();
        db.close();
        if(tasks.isEmpty())
            callback.onDataNotAvailable();
        else
            callback.onTasksLoaded(tasks);
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
    public void deleteTask(int taskId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.delete(TaskEntry.TABLE_NAME,"id=?",new String[]{String.valueOf(taskId)});
        }catch (Exception e)
        {
            Log.e("RAMSEY","delete failed");
        }

    }
}
