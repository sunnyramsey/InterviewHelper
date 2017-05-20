package ars.ramsey.interviewhelper.widget.FloatingMenu;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Ramsey on 2017/5/20.
 */

public class MenuTag extends CardView {

    private TextView mMenuText;


    public MenuTag(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public MenuTag(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMenuText = new TextView(context);
        mMenuText.setSingleLine(true);
    }

    public MenuTag(Context context) {
        super(context);
    }

    protected void setTextSize(float size){
        mMenuText.setTextSize(size);
    }
    protected void setTextColor(int color){
        mMenuText.setTextColor(color);
    }
    protected void setText(String text)
    {
        mMenuText.setText(text);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,Gravity.CENTER);
        int l = dp2px(8);
        int t = dp2px(8);
        int r = dp2px(8);
        int b = dp2px(8);
        layoutParams.setMargins(l, t, r, b);
        addView(mMenuText,layoutParams);

    }

    private int dp2px(int value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , value, getResources().getDisplayMetrics());
    }
}
