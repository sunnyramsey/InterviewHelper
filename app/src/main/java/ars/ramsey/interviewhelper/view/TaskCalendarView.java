package ars.ramsey.interviewhelper.view;

import java.util.List;

import ars.ramsey.interviewhelper.model.bean.Task;
import ars.ramsey.interviewhelper.model.bean.TodoTask;

/**
 * Created by Ramsey on 2017/4/26.
 */

public interface TaskCalendarView<T> extends BaseView<T> {
    public void getTasks(List<TodoTask> tasks);
}
