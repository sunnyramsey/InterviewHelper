/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ars.ramsey.interviewhelper.model.local;

import android.provider.BaseColumns;

/**
 * The contract used for the db to save the tasks locally.
 */
public final class TasksPersistenceContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private TasksPersistenceContract() {}

    /* Inner class that defines the table contents */
    public static abstract class TaskEntry  {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_COMPANY_NAME = "title";
        public static final String COLUMN_NAME_JOB_NAME = "description";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_CREATE_DATE = "create_date";
        public static final String COLUMN_NAME_NEXT_DATE = "next_date";
        public static final String COLUMN_NAME_FINISHED_DATE = "finished_date";
        public static final String COLUMN_NAME_OFFER = "offer";

        public static final String[][] COLUMNS = {
                {COLUMN_NAME_ID, BaseDatabaseType.TYPE_PRIMARY_KEY},
                {COLUMN_NAME_COMPANY_NAME, BaseDatabaseType.TYPE_TEXT},
                {COLUMN_NAME_JOB_NAME, BaseDatabaseType.TYPE_TEXT},
                {COLUMN_NAME_STATUS, BaseDatabaseType.TYPE_TEXT},
                {COLUMN_NAME_CREATE_DATE, BaseDatabaseType.TYPE_TEXT},
                {COLUMN_NAME_NEXT_DATE, BaseDatabaseType.TYPE_INTEGER},
                {COLUMN_NAME_FINISHED_DATE,BaseDatabaseType.TYPE_TEXT},
                {COLUMN_NAME_OFFER,BaseDatabaseType.TYPE_BOOLEAN}
        };
    }


}
