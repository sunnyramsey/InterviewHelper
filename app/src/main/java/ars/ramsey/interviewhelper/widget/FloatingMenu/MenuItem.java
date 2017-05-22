package ars.ramsey.interviewhelper.widget.FloatingMenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import ars.ramsey.interviewhelper.R;

/**
 * Created by Ramsey on 2017/5/20.
 */

public class MenuItem extends ViewGroup {
    public String mTagText;
    private MenuTag mMenuTag;
    private onMenuClickListener mListener;


    public interface onMenuClickListener{
        void onMenuClick();
    }


    public MenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs
                , R.styleable.MenuItem);
        mTagText = typedArray.getString(R.styleable.MenuItem_tagText);
        typedArray.recycle();
        mMenuTag = new MenuTag(context);
        mMenuTag.setText(mTagText);
        addView(mMenuTag);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View tagView = getChildAt(0);
        View iconView = getChildAt(1);

        int tl = dp2px(8);
        int tt = (getMeasuredHeight() - tagView.getMeasuredHeight()) / 2;
        int tr = tl + tagView.getMeasuredWidth();
        int tb = tt + tagView.getMeasuredHeight();

        int il = tr + dp2px(8);
        int it = (getMeasuredHeight() - iconView.getMeasuredHeight()) / 2;
        int ir = il + iconView.getMeasuredWidth();
        int ib = it + iconView.getMeasuredHeight();

        tagView.layout(tl,tt,tr,tb);
        iconView.layout(il,it,ir,ib);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0;
        int height = 0;

        int childCount = getChildCount();
        for(int i=0;i<childCount;i++)
        {
            View view = getChildAt(i);
            measureChild(view,widthMeasureSpec,heightMeasureSpec);
            width += view.getMeasuredWidth();
            height = Math.max(height,view.getMeasuredHeight());
        }

        width += dp2px(8 + 8 + 8);
        height += dp2px(8 + 8);


        setMeasuredDimension(width,height);

    }

    private int dp2px(int value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , value, getResources().getDisplayMetrics());
    }

    public void setOnMenuClickListener(onMenuClickListener listener)
    {
        this.mListener = listener;
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMenuClick();
            }
        });
    }

    public void setBackgroundColor(int color){
        mMenuTag.setBackgroundColor(color);
    }

    public void setTextColor(int color){
        mMenuTag.setTextColor(color);
    }

    public void setTextSize(float size){
        mMenuTag.setTextSize(size);
    }

}
