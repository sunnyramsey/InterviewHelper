package ars.ramsey.interviewhelper.widget.Calendar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import ars.ramsey.interviewhelper.R;

/**
 * Created by Ramsey on 2017/4/26.
 */

public class TodoSpan implements LineBackgroundSpan {
    private String mText;
    private int mColor;
    private int mRadius;

    public TodoSpan(String text)
    {
        mText = text;
        mColor = 0xFFFFC0CB;
        mRadius = 8;
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        int oldColor = p.getColor();
        if (mColor != 0) {
            p.setColor(mColor);
        }
        Paint textPaint = new Paint();
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(30);
        textPaint.setColor(oldColor);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        float baseY = bottom + fontHeight - fontMetrics.descent;
        c.drawText(mText,(left+right)/2,baseY,textPaint);
        c.drawCircle(right - mRadius , top - 3 * mRadius, mRadius, p);
        p.setColor(oldColor);
    }
}
