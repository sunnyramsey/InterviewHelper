package ars.ramsey.interviewhelper.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

import ars.ramsey.interviewhelper.R;


/**
 * Created by Ramsey on 2017/4/22.
 */

public class CalendarEditText extends EditText {

    private Drawable mCalenderIcon;
    private boolean isIconDown;
    private DateTimePickDialog mDialog;

    public CalendarEditText(Context context) {
        super(context);
        init();
    }

    public CalendarEditText(Context context, AttributeSet attrs) {
        super(context, attrs,android.R.attr.editTextStyle);
        init();
    }

    public CalendarEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP)
        {
            if(mCalenderIcon != null) {
                int x = (int) event.getX();
                //判断触摸点是否在水平范围内
                boolean isInnerWidth = (x > (getWidth() - getTotalPaddingRight())) &&
                        (x < (getWidth() - getPaddingRight()));
                //获取删除图标的边界，返回一个Rect对象
                Rect rect = mCalenderIcon.getBounds();
                //获取删除图标的高度
                int height = rect.height();
                int y = (int) event.getY();
                //计算图标底部到控件底部的距离
                int distance = (getHeight() - height) / 2;
                //判断触摸点是否在竖直范围内(可能会有点误差)
                //触摸点的纵坐标在distance到（distance+图标自身的高度）之内，则视为点中删除图标
                boolean isInnerHeight = (y > distance) && (y < (distance + height));
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        isIconDown = false;
                        if (isInnerHeight && isInnerWidth) {
                            isIconDown = true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (isInnerHeight && isInnerWidth  && isIconDown) {
                            mDialog.showDialog();
                        }
                        break;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void init()
    {
        mCalenderIcon = getCompoundDrawables()[2];
        if(mCalenderIcon == null)
        {
            mCalenderIcon = getResources().getDrawable(R.drawable.ic_datetime);
        }
        mCalenderIcon.setBounds(0,0,mCalenderIcon.getIntrinsicWidth(),mCalenderIcon.getIntrinsicHeight());
        mDialog = new DateTimePickDialog(getContext(),this);
    }

}
