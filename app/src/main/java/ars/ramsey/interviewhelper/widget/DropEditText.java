package ars.ramsey.interviewhelper.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.ListPopupWindow;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import ars.ramsey.interviewhelper.R;

/**
 * Created by Ramsey on 2017/4/22.
 */

public class DropEditText extends EditText {

    private Drawable mDropIcon;
    private boolean isIconDown;
    private ListPopupWindow mListPopupWindow;

    public DropEditText(Context context) {
        super(context);
        init();
    }

    public DropEditText(Context context, AttributeSet attrs) {
        super(context, attrs,android.R.attr.editTextStyle);
        init();
    }

    public DropEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP)
        {
            if(mDropIcon != null) {
                int x = (int) event.getX();
                //判断触摸点是否在水平范围内
                boolean isInnerWidth = (x > (getWidth() - getTotalPaddingRight())) &&
                        (x < (getWidth() - getPaddingRight()));
                //获取删除图标的边界，返回一个Rect对象
                Rect rect = mDropIcon.getBounds();
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
                            showListPopulWindow();
                        }
                        break;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void showListPopulWindow(){
        final String[] list = { "item1", "item2", "item3", "item4" };

        mListPopupWindow = new ListPopupWindow(getContext());
        mListPopupWindow.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, list));
        mListPopupWindow.setAnchorView(this);
        mListPopupWindow.setModal(true);
        mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DropEditText.this.setText(list[i]);
                mListPopupWindow.dismiss();
            }
        });
        mListPopupWindow.show();

    }

    private void init()
    {
        mDropIcon = getCompoundDrawables()[2];
        if(mDropIcon == null)
        {
            mDropIcon = getResources().getDrawable(R.drawable.ic_down);
        }
        mDropIcon.setBounds(0,0,mDropIcon.getIntrinsicWidth(),mDropIcon.getIntrinsicHeight());
    }
}
