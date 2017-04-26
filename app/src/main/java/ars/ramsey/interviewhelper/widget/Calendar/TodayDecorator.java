package ars.ramsey.interviewhelper.widget.Calendar;

import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

/**
 * Created by Ramsey on 2017/4/26.
 */

public class TodayDecorator implements DayViewDecorator {

    private final CalendarDay mToday;
    private final Drawable mBackgroundDrawable;

    public TodayDecorator(Drawable backgroundDrawable) {
        mToday = CalendarDay.today();
        mBackgroundDrawable = backgroundDrawable;
    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return mToday.equals(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(mBackgroundDrawable);
    }
}
