package ars.ramsey.interviewhelper.widget.Calendar;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ars.ramsey.interviewhelper.model.bean.TodoTask;

/**
 * Created by Ramsey on 2017/4/26.
 */

public class TodoTaskDecorator implements DayViewDecorator {
    private TodoTask mTodoTask;

    public TodoTaskDecorator(TodoTask todoTask)
    {
        mTodoTask = todoTask;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if(day.getDay() == mTodoTask.getDay())
            return true;
        else
            return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new TodoSpan(mTodoTask.getCompanyName()));
    }
}
