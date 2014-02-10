package com.fransis1981.Android_Hymns;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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

   int buttonX, buttonY, radius;
   boolean isPressed = false;

   // Color used to paint this button
   private int lineColor;

    //Shared init method for all constructors.
    private void init(int color) {
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

               setOnTouchListener(new OnTouchListener() {
                  @Override
                  public boolean onTouch(View v, MotionEvent event) {
                     if (event.getActionMasked() == MotionEvent.ACTION_DOWN) { isPressed = true; }
                     else if (event.getActionMasked() == MotionEvent.ACTION_UP) { isPressed = false; }
                     invalidate();
                     return false;           //IMPORTANT: Returns false so that we may continue further event processing
                  }
               });
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
           // Just for Debug:  canvas.drawRect(0,0, getMeasuredWidth(), getMeasuredHeight(), textPaint);
           buttonX = getMeasuredWidth() / 2;
           buttonY = getMeasuredHeight() / 2;
           radius = (Math.min(buttonX, buttonY)) - 3;   //TODO: externalize as dimension the text size
           setBackgroundColor(HymnsApplication.myResources.getColor(android.R.color.transparent));
           canvas.drawCircle(buttonX, buttonY, radius, buttonPaint);
           textPaint.setTextSize(radius);          //TODO: externalize as dimension the text size
           if (getText().length() > 0) {
               textPaint.getTextBounds(getText().toString(), 0, 1, txtBounds);
               canvas.drawText(getText().toString(), buttonX, buttonY - txtBounds.centerY(), textPaint);
           }
           if (isPressed) Log.i("HYMNS", "BUTTON PRESSED!!!!!!!!!");
        }
    }

   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      //Decoding width and height
      int modeX = MeasureSpec.getMode(widthMeasureSpec);
      int willX = MeasureSpec.getSize(widthMeasureSpec);
      int modeY = MeasureSpec.getMode(heightMeasureSpec);
      int willY = MeasureSpec.getSize(heightMeasureSpec);
      //Log.i(MyConstants.LogTag_STR, "PROPOSED MEASUREMENT:" + getText() + " (" + willX + ", " + willY + ")");

      //Willing to make the maximum possible square (some redundant assignments better than comparison and jump).
      int defaultX = 150, defaultY = 150;       //TODO: default values; to externalize into XML such resource
      if (modeX == MeasureSpec.UNSPECIFIED) willX = defaultX;
      if (modeY == MeasureSpec.UNSPECIFIED) willY = defaultY;
      if (willX < willY) { willY = willX; }
      else { willX = willY; }
      setMeasuredDimension(willX, willY);
      //Log.i(MyConstants.LogTag_STR, "OFFICIAL MEASUREMENT:" + getText() + " (" + willX + ", " + willY + ")");
   }
}
