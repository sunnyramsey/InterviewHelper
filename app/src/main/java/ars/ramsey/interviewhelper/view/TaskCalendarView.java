package ars.ramsey.interviewhelper.view;

import com.prolificinteractive.materialcalendarview.DayViewDecorator;

import java.util.List;

import ars.ramsey.interviewhelper.model.bean.Task;
import ars.ramsey.interviewhelper.model.bean.TodoTask;

/**
 * Created by Ramsey on 2017/4/26.
 */

public interface TaskCalendarView<T> extends BaseView<T> {
    public void refreshDecorators(List<DayViewDecorator> tasks);
    public List<DayViewDecorator> transferDecorators(List<TodoTask> tasks);
}
