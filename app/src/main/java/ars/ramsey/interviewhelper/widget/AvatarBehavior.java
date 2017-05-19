package ars.ramsey.interviewhelper.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Ramsey on 2017/5/19.
 */

public class AvatarBehavior extends CoordinatorLayout.Behavior<ImageView> {

    private Context mContext;
    private int mStartHeight;
    private int mMaxScrollDistance;
    private int mCurrentOffset;

    public AvatarBehavior(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        mContext = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ImageView child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ImageView child, View dependency) {
        maybeInitProperties(child,dependency);
        if(mCurrentOffset<mMaxScrollDistance/2)
        {
            child.setVisibility(View.GONE);
        }else{
            float percent = (float)((mCurrentOffset - mMaxScrollDistance/2)/(1.0*mMaxScrollDistance/2));
            int height = (int)(mStartHeight * percent);
            child.setVisibility(View.VISIBLE);
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            layoutParams.width = height;
            layoutParams.height = height;
            child.setLayoutParams(layoutParams);
        }
        return true;
    }

    private void maybeInitProperties(ImageView child, View dependency) {

        if (mMaxScrollDistance == 0) {
            mMaxScrollDistance = ((AppBarLayout) dependency).getTotalScrollRange();
            ((AppBarLayout) dependency).addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    mCurrentOffset = mMaxScrollDistance - Math.abs(verticalOffset);
                }
            });
        }

        if (mStartHeight == 0)
            mStartHeight = child.getHeight();


    }
}
