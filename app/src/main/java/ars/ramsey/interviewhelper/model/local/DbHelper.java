package ars.ramsey.interviewhelper.model.local;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ramsey on 2017/4/7.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "interview.db";
    private static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON;");
        sqLiteDatabase.execSQL(createTable(TasksPersistenceContract.TaskEntry.TABLE_NAME,TasksPersistenceContract.TaskEntry.COLUMNS,null));
        sqLiteDatabase.execSQL(createTable(TodoTasksPersistenceContract.TodoTasksEntry.TABLE_NAME,TodoTasksPersistenceContract.TodoTasksEntry.COLUMNS,
                "foreign key (id) references task(id) on delete cascade"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private static String createTable(String tableName,String[][] columns,String constrain)
    {
        if(tableName == null || columns == null)
            throw  new IllegalArgumentException("Create table error!");
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append(tableName);
        sb.append("(");
        for(int i = 0;i<columns.length;i++)
        {
            if(i > 0)
                sb.append(",");
            sb.append(columns[i][0]).append(" ").append(columns[i][1]);
        }
        if(constrain!= null && !constrain.equals(""))
        {
            sb.append(",");
            sb.append(constrain);
        }
        sb.append(");");
        return sb.toString();
    }
}
