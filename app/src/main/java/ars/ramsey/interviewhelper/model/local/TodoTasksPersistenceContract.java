package ars.ramsey.interviewhelper.model.local;

/**
 * Created by Ramsey on 2017/4/26.
 */

public final class TodoTasksPersistenceContract {
    private TodoTasksPersistenceContract()
    {}
    /* Inner class that defines the table contents */

    public static abstract class TodoTasksEntry  {
        public static final String TABLE_NAME = "todo_task";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_MONTH = "month";
        public static final String COLUMN_NAME_DAY = "day";
        public static final String COLUMN_NAME_COMPANY_NAME = "company_name";



        public static final String[][] COLUMNS = {
                {COLUMN_NAME_ID, BaseDatabaseType.TYPE_INT},
                {COLUMN_NAME_COMPANY_NAME, BaseDatabaseType.TYPE_TEXT},
                {COLUMN_NAME_YEAR, BaseDatabaseType.TYPE_INT},
                {COLUMN_NAME_MONTH, BaseDatabaseType.TYPE_INT},
                {COLUMN_NAME_DAY, BaseDatabaseType.TYPE_INT},
        };
    }

}
