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

public class DropEditText extends RightIconEditText implements RightIconEditText.RightIconClickListener{

    private ListPopupWindow mListPopupWindow;
    private String[] mData;

    public DropEditText(Context context) {
        super(context);
        init();
    }

    public DropEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DropEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        mData = new String[]{"笔试","一面","二面","三面","HR面","待定"};
        setIconClickListener(this);
    }


    @Override
    public void onRightIconClick() {

        mListPopupWindow = new ListPopupWindow(getContext());
        mListPopupWindow.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, mData));
        mListPopupWindow.setAnchorView(this);
        mListPopupWindow.setModal(true);
        mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DropEditText.this.setText(mData[i]);
                mListPopupWindow.dismiss();
            }
        });
        mListPopupWindow.show();
    }
}
