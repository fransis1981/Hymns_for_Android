package com.fransis1981.Android_Hymns;
                     //SOLO PER VERIFICARE I COMMIT DI GIST
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by francesco.vitullo on 28/01/14.
 * This class implements a circle style button View for a single digit.
 * (similar to numeric keypad on iOS7 unlock screen).
 */
public class DigitCircleButtonView extends Button {
    private Paint buttonPaint;
    private Paint textPaint;

    // Color used to paint this button
    private int lineColor;

    //Shared init method for all constructors.
    private void init(int color) {
        if (!isInEditMode()) {
            buttonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            buttonPaint.setColor(HymnsApplication.myResources.getColor(R.color.circleButton_outerLine));
            buttonPaint.setStyle(Paint.Style.STROKE);
            buttonPaint.setStrokeWidth(2);      //TODO: externalize as dimension the stroke width

            textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setColor(HymnsApplication.myResources.getColor(android.R.color.darker_gray));
            textPaint.setStrokeWidth(2);
            textPaint.setStyle(Paint.Style.STROKE);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(18);          //TODO: externalize as dimension the text size

            lineColor = color;
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
            buttonPaint = new Paint(); //buttonPaint.setColor(HymnsApplication.myResources.getColor(android.R.color.white));
            textPaint = new Paint(); //textPaint.setColor(HymnsApplication.myResources.getColor(android.R.color.white));
        }
        String ttt = isInEditMode()?"0":"2";

        int center = Math.min(getMeasuredHeight(),getMeasuredWidth())/2;

        canvas.drawARGB(255, 0, 0, 0);
        canvas.drawCircle(center, center, center - 3, buttonPaint);
        if (ttt.length() > 0) canvas.drawText(ttt, center, center+9, textPaint);    //TODO: externalize half the text
        //super.onDraw(canvas);
    }
}
