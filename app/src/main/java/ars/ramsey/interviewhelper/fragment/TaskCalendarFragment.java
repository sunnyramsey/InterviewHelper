package ars.ramsey.interviewhelper.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ars.ramsey.interviewhelper.R;
import ars.ramsey.interviewhelper.model.bean.TodoTask;
import ars.ramsey.interviewhelper.presenter.TaskDatePresenter;
import ars.ramsey.interviewhelper.view.TaskCalendarView;
import ars.ramsey.interviewhelper.widget.Calendar.TodayDecorator;
import ars.ramsey.interviewhelper.widget.Calendar.TodoTaskDecorator;

/**
 * Created by Ramsey on 2017/4/26.
 */

public class TaskCalendarFragment extends Fragment implements TaskCalendarView<TaskDatePresenter> {
    private TaskDatePresenter mPresenter;
    private MaterialCalendarView mCalendarWidget;
    private List<TodoTaskDecorator> mDecorators = new ArrayList<>();


    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden)
        {
            Log.i("RAMSEY","currentdate："+mCalendarWidget.getCurrentDate().getDate());
            mPresenter.getTaskByMonth(mCalendarWidget.getCurrentDate().getDate());
        }
        super.onHiddenChanged(hidden);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task_calendar_layout,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCalendarWidget = (MaterialCalendarView)view.findViewById(R.id.calendarView);

        Calendar instance = Calendar.getInstance();
        mCalendarWidget.setSelectedDate(instance.getTime());

        //Set range of date
        Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR), Calendar.JANUARY, 1);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR), Calendar.DECEMBER, 31);

        mCalendarWidget.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .commit();

        mCalendarWidget.setDateTextAppearance(R.style.CalendarTextAppearance);
        mCalendarWidget.addDecorator(new TodayDecorator(getResources().getDrawable(R.drawable.today_circle_background)));
        mCalendarWidget.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                mPresenter.getTaskByMonth(date.getDate());
            }
        });
        mCalendarWidget.addDecorator(new TodayDecorator(getResources().getDrawable(R.drawable.today_circle_background)));
        mPresenter.getTaskByMonth(instance.getTime());
    }

    @Override
    public void onResume() {
        super.onResume();
//        mCalendarWidget.addDecorator(new TodayDecorator(getResources().getDrawable(R.drawable.today_circle_background)));
//        mCalendarWidget.addDecorator(new TodoTaskDecorator(new TodoTask(1,"ACB",299,1,3)));
//        mCalendarWidget.addDecorator(new TodoTaskDecorator(new TodoTask(2,"AyB",299,1,6)));
//        mCalendarWidget.addDecorator(new TodoTaskDecorator(new TodoTask(4,"AxB",299,1,24)));
//        mCalendarWidget.addDecorator(new TodoTaskDecorator(new TodoTask(5,"ArB",299,1,15)));
//        mCalendarWidget.invalidateDecorators();

    }



    @Override
    public void setPresenter(TaskDatePresenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void getTasks(List<TodoTask> tasks) {
        mCalendarWidget.removeDecorators();
        mCalendarWidget.addDecorator(new TodayDecorator(getResources().getDrawable(R.drawable.today_circle_background)));
        if(tasks!=null) {
            for (TodoTask task : tasks) {
                mCalendarWidget.addDecorator(new TodoTaskDecorator(task));
            }
        }
        mCalendarWidget.invalidateDecorators();
    }
}
