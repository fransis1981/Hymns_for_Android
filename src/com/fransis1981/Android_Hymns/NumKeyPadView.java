package com.fransis1981.Android_Hymns;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

/**
 * Created by Fransis on 31/01/14 12.14.
 * Compund control to implement a numerical keypad.
 */
public class NumKeyPadView extends TableLayout {
   DigitCircleButtonView numkeys[] = new DigitCircleButtonView[10];

   String composedNumber = "";

   public NumKeyPadView(Context context) {
      super(context);
      init();
   }

   public NumKeyPadView(Context context, AttributeSet attrs) {
      super(context, attrs);
      init();
   }

   //Method for managing composed number when a new digit is composed
   void newComposedDigit(String digit) {
      composedNumber += digit;

      Log.i(MyConstants.LogTag_STR, "Current composed number: " + composedNumber);
   }

   //Centralized init method called by the constructors.
   void init() {
      if (!isInEditMode()) {
         LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         li.inflate(R.layout.keypadview, this, true);
         numkeys[1] = (DigitCircleButtonView) findViewById(R.id.keypad_num1);
         numkeys[2] = (DigitCircleButtonView) findViewById(R.id.keypad_num2);
         numkeys[3] = (DigitCircleButtonView) findViewById(R.id.keypad_num3);
         numkeys[4] = (DigitCircleButtonView) findViewById(R.id.keypad_num4);
         numkeys[5] = (DigitCircleButtonView) findViewById(R.id.keypad_num5);
         numkeys[6] = (DigitCircleButtonView) findViewById(R.id.keypad_num6);

         //Number keys events
         numkeys[1].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {newComposedDigit("1");}});
         numkeys[2].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {newComposedDigit("2");}});
         numkeys[3].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {newComposedDigit("3");}});
         numkeys[4].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {newComposedDigit("4");}});
         numkeys[5].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {newComposedDigit("5");}});
         numkeys[6].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {newComposedDigit("6");}});

         //Hooking up the functionality.... ?
         //TODO: we might introduce a parameter for having this view optionally have a box or a series of button
         //TODO: with hints of available hymns for the composed number.

         ///////addView(<view to add>, new LinearLayout.LayoutParams( , ));
      }

   }

}
