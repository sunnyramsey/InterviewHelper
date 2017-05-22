package ars.ramsey.interviewhelper.widget.FloatingMenu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import ars.ramsey.interviewhelper.R;


/**
 * Created by Ramsey on 2017/5/20.
 */

public class MainMenu extends ViewGroup {

    private View mBackgroundView;
    private FloatingActionButton mSwitchButton;

    private int mBackgroundColor;
    private Drawable mSwithIcon;
    private ColorStateList mSwitchColor;
    private int mAnimationDuration;

    private onMenuItemClickListener mListener;
    private boolean mIsMenuOpen;

    public MainMenu(Context context) {
        this(context,null);
    }

    public MainMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        mIsMenuOpen = false;
        getAttributes(context,attrs);
        addBaseView(context);

    }

    public MainMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MainMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for(int i=0; i<count; i++){
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int)ev.getX();
        int y = (int)ev.getY();
        if(mIsMenuOpen){
            switch (ev.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if(judgeIfTouchBackground(x, y)){
                        intercept = true;
                    }
                    intercept = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    intercept = false;
                    break;
                case MotionEvent.ACTION_UP:
                    intercept = false;
                    break;
            }
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mIsMenuOpen){
            closeMenu();
            changeBackground();
            return true;
        }
        return super.onTouchEvent(event);
    }

    private boolean judgeIfTouchBackground(int x, int y){
        Rect a = new Rect();
        Rect b = new Rect();
        a.set(0, 0, getWidth(), getHeight() - getChildAt(getChildCount() - 1).getTop());
        b.set(0, getChildAt(getChildCount() - 1).getTop(), getChildAt(getChildCount() - 1).getLeft(), getHeight());
        if(a.contains(x, y) || b.contains(x, y)){
            return true;
        }
        return false;
    }

    private void getAttributes(Context context,AttributeSet attrs)
    {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MainMenu);
        mBackgroundColor = typedArray.getColor(R.styleable.MainMenu_backgroundColor, Color.TRANSPARENT);
        mSwithIcon = typedArray.getDrawable(R.styleable.MainMenu_switchFabIcon);
        mSwitchColor = typedArray.getColorStateList(R.styleable.MainMenu_switchFabColor);
        mAnimationDuration = typedArray.getInt(R.styleable.MainMenu_animationDuration,150);
        typedArray.recycle();

    }

    private void addBaseView(Context context)
    {
        mBackgroundView = new View(context);
        mBackgroundView.setBackgroundColor(mBackgroundColor);
        mBackgroundView.setAlpha(0);
        addView(mBackgroundView);

        mSwitchButton = new FloatingActionButton(context);
        mSwitchButton.setBackgroundTintList(mSwitchColor);
        mSwitchButton.setImageDrawable(mSwithIcon);
        addView(mSwitchButton);

    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        if(b)
        {
            layoutBackgroundView();
            layoutSwitchButton();
            layoutMenuItem();
        }
    }

    private void layoutSwitchButton()
    {
        int width = mSwitchButton.getMeasuredWidth();
        int height = mSwitchButton.getMeasuredHeight();

        int l = getMeasuredWidth() - dp2px(20) - width;
        int t = getMeasuredHeight() - dp2px(20) - height;
        int r = l + width;
        int b = t + height;
        mSwitchButton.layout(l,t,r,b);
        mSwitchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mIsMenuOpen)
                {
                    closeMenu();
                }else{
                    openMenu();
                }
                changeBackground();
            }
        });
    }

    private void layoutBackgroundView()
    {
        mBackgroundView.layout(0,0,getMeasuredWidth(),getMeasuredHeight());
    }

    private void layoutMenuItem()
    {
        int count = getChildCount();
        int switchHeight = mSwitchButton.getMeasuredHeight();
        for(int i=2;i<count;i++)
        {

            MenuItem child = (MenuItem) getChildAt(i);
            child.setVisibility(View.INVISIBLE);

            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();

            int l = getMeasuredWidth() - width - dp2px(20);
            int t = getMeasuredHeight() - switchHeight - (count - i)*height - dp2px(20);
            child.layout(l,t,l + width,t + height);
            bindMenuEvents(child,i - 2);

        }
    }

    private int dp2px(int value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , value, getResources().getDisplayMetrics());
    }

    private void bindMenuEvents(final MenuItem child, final int pos){

        child.setOnMenuClickListener(new MenuItem.onMenuClickListener() {
            @Override
            public void onMenuClick() {
                closeMenu();
                changeBackground();
                if(mListener != null)
                    mListener.onMenuItemClick(child,pos);
            }
        });

    }

    public interface onMenuItemClickListener{
        void onMenuItemClick(MenuItem view,int pos);
    }

    private void changeBackground(){
        ObjectAnimator animator = !mIsMenuOpen ? ObjectAnimator.ofFloat(mBackgroundView, "alpha", 0.9f, 0f) :
                ObjectAnimator.ofFloat(mBackgroundView, "alpha", 0f, 0.9f);
        animator.setDuration(150);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    private void openMenu(){
        mIsMenuOpen = true;
        for(int i = 2; i<getChildCount(); i++){
            View view = getChildAt(i);
            view.setVisibility(VISIBLE);
            view.setAlpha(0);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(scaleX, scaleY, alpha);
            set.setDuration(mAnimationDuration);
            set.start();
        }
    }


    private void closeMenu(){
        mIsMenuOpen = false;
        for(int i=2; i<getChildCount(); i++){
            final View view = getChildAt(i);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            alpha.setDuration(mAnimationDuration);
            alpha.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            alpha.start();
        }
    }

    public void setOnMenuItemClickListener(onMenuItemClickListener listener)
    {
        this.mListener = listener;
    }

}
