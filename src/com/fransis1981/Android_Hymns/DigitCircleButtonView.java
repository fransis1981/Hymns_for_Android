package com.fransis1981.Android_Hymns;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

/**
 * Created by francesco.vitullo on 28/01/14.
 * This class implements a circle style button View for a single digit.
 * (similar to numeric keypad on iOS7 unlock screen).
 */
public class DigitCircleButtonView extends Button {
    private Paint buttonPaint;
    private Paint textPaint;
    private Rect txtBounds = new Rect();         //Helper object for centering text; preallocated for efficiency.

    int radius;
    // Color used to paint this button
    private int lineColor;

    //Shared init method for all constructors.
    private void init(int color) {
        Log.v(MyConstants.LogTag_STR, "Init() for a circle button...");
        if (!isInEditMode()) {
            try {
                buttonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                buttonPaint.setColor(HymnsApplication.myResources.getColor(R.color.circleButton_outerLine));
                buttonPaint.setStyle(Paint.Style.STROKE);
                buttonPaint.setStrokeWidth(2);      //TODO: externalize as dimension the stroke width

                textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                textPaint.setColor(HymnsApplication.myResources.getColor(android.R.color.darker_gray));
                textPaint.setStrokeWidth(2);
                textPaint.setStyle(Paint.Style.STROKE);
                textPaint.setTextAlign(Paint.Align.CENTER);

                lineColor = color;
            } catch (Exception e) {
                Log.e(MyConstants.LogTag_STR, e.getMessage());
            }
        }
    }

    public DigitCircleButtonView(Context context, int color) {
        super(context);
        init(color);
    }

    public DigitCircleButtonView(Context context) {
        super(context);
        init(android.R.color.white);
    }

    public DigitCircleButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(android.R.color.white);
    }

    public DigitCircleButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(android.R.color.white);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (isInEditMode()) {
            super.onDraw(canvas);
        } else {
            radius = Math.min(getMeasuredHeight(), getMeasuredWidth()) / 2;
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawCircle(radius, radius, radius - 3, buttonPaint);
            textPaint.setTextSize(radius - 4);          //TODO: externalize as dimension the text size
            if (getText().length() > 0) {
                textPaint.getTextBounds(getText().toString(), 0, 1, txtBounds);
                Log.i(MyConstants.LogTag_STR, "centerY(): " + txtBounds.centerY());
                canvas.drawText(getText().toString(), radius, radius - txtBounds.centerY(), textPaint);
            }
        }
    }
}
