package ars.ramsey.interviewhelper.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ars.ramsey.interviewhelper.R;

/**
 * Created by Ramsey on 2017/4/22.
 */

public class DateTimePickDialog implements DatePicker.OnDateChangedListener,TimePicker.OnTimeChangedListener{
    private EditText mEditText;
    private Context mContext;
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    public DateTimePickDialog(Context context,EditText editText)
    {
        this.mContext = context;
        this.mEditText = editText;
    }

    public void showDialog()
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_datetime,null);
        mDatePicker = (DatePicker)view.findViewById(R.id.datePicker);
        mTimePicker = (TimePicker)view.findViewById(R.id.timePicker);
        init();
        mDatePicker.init(mYear,mMonth,mDay,this);
        mTimePicker.setCurrentHour(mHour);
        mTimePicker.setCurrentMinute(mMinute);
        mTimePicker.setOnTimeChangedListener(this);
        mTimePicker.setIs24HourView(true);
        resizePikcer(mDatePicker);
        resizePikcer(mTimePicker);
        new AlertDialog.Builder(mContext).setTitle("请选择日期").setView(view).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                GregorianCalendar gregorianCalendar = new GregorianCalendar(mYear,mMonth,mDay,mHour,mMinute);
                String result = format.format(gregorianCalendar.getTime());
                mEditText.setText(result);
                dialogInterface.dismiss();
            }
        }).create().show();
    }

    private void init(){
        Calendar calendar = Calendar.getInstance();
        if(!mEditText.getText().toString().equals(""))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date date = sdf.parse(mEditText.getText().toString());
                calendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        mYear = i;
        mMonth = i1;
        mDay = i2;
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int i, int i1) {
        mHour = i;
        mMinute = i1;
    }


    private void resizePikcer(FrameLayout tp){
        List<NumberPicker> npList = findNumberPicker(tp);
        for(NumberPicker np:npList){
            resizeNumberPicker(np);
        }
    }

    /**
     * 得到viewGroup里面的numberpicker组件
     * @param viewGroup
     * @return
     */
    private List<NumberPicker> findNumberPicker(ViewGroup viewGroup){
        List<NumberPicker> npList = new ArrayList<NumberPicker>();
        View child = null;
        if(viewGroup != null){
            for(int i = 0; i < viewGroup.getChildCount(); i++){
                child = viewGroup.getChildAt(i);
                if(child instanceof NumberPicker){
                    npList.add((NumberPicker)child);
                }
                else if(child instanceof LinearLayout){
                    List<NumberPicker> result = findNumberPicker((ViewGroup)child);
                    if(result.size()>0){
                        return result;
                    }
                }
            }
        }
        return npList;
    }
    /*
     * 调整numberpicker大小
     */
    private void resizeNumberPicker(NumberPicker np){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        np.setLayoutParams(params);
    }
}
