package com.fransis1981.Android_Hymns;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;

/**
 * Created by Fransis on 31/01/14 12.14.
 * Compund control to implement a numerical keypad.
 */
public class NumKeyPadView extends TableLayout {
   public interface OnNumberConfirmedListener {
      public void onNumberConfirmed(int number);      //Evento generato in corrispondenza di un numero confermato.
   }
   OnNumberConfirmedListener mOnNumberConfirmedListener;

   DigitCircleButtonView numkeys[] = new DigitCircleButtonView[10];
   ImageButton okButton, cancelButton;
   Animation okSprite, cancelSprite;

   String composedNumber = "";

   public NumKeyPadView(Context context) {
      super(context);
      init(context);
   }

   public NumKeyPadView(Context context, AttributeSet attrs) {
      super(context, attrs);
      init(context);
   }

   //Method for managing composed number when a new digit is composed
   void newComposedDigit(String digit) {
      if (composedNumber.length() == 3) composedNumber = "";
      composedNumber += digit;

      if (composedNumber.length() == 3) {
         okButton.startAnimation(okSprite);
      }
   }

   //Centralized init method called by the constructors.
   void init(final Context context) {
      if (!isInEditMode()) {
         LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         li.inflate(R.layout.keypadview, this, true);
         numkeys[1] = (DigitCircleButtonView) findViewById(R.id.keypad_num1);
         numkeys[2] = (DigitCircleButtonView) findViewById(R.id.keypad_num2);
         numkeys[3] = (DigitCircleButtonView) findViewById(R.id.keypad_num3);
         numkeys[4] = (DigitCircleButtonView) findViewById(R.id.keypad_num4);
         numkeys[5] = (DigitCircleButtonView) findViewById(R.id.keypad_num5);
         numkeys[6] = (DigitCircleButtonView) findViewById(R.id.keypad_num6);
         numkeys[7] = (DigitCircleButtonView) findViewById(R.id.keypad_num7);
         numkeys[8] = (DigitCircleButtonView) findViewById(R.id.keypad_num8);
         numkeys[9] = (DigitCircleButtonView) findViewById(R.id.keypad_num9);
         numkeys[0] = (DigitCircleButtonView) findViewById(R.id.keypad_num0);
         okButton = (ImageButton) findViewById(R.id.keypad_ok);
         cancelButton = (ImageButton) findViewById(R.id.keypad_cancel);
         okSprite = AnimationUtils.loadAnimation(context, R.anim.keypad_ok_sprite);
         cancelSprite = AnimationUtils.loadAnimation(context, R.anim.keypad_cancel_sprite);

         okSprite.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
               if (mOnNumberConfirmedListener != null)
                  mOnNumberConfirmedListener.onNumberConfirmed(Integer.parseInt(composedNumber));
            }
         });

         //spinner = (ImageView) findViewById(R.id.imageView);
         //spinner.setImageLevel(currentLevel);

         //Number keys events
         numkeys[1].setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
               newComposedDigit("1");
            }
         });
         numkeys[2].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {newComposedDigit("2");}});
         numkeys[3].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {newComposedDigit("3");}});
         numkeys[4].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {newComposedDigit("4");}});
         numkeys[5].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {newComposedDigit("5");}});
         numkeys[6].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {newComposedDigit("6");}});
         numkeys[7].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {newComposedDigit("7");}});
         numkeys[8].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {newComposedDigit("8");}});
         numkeys[9].setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
               newComposedDigit("9");
            }
         });
         numkeys[0].setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
               newComposedDigit("0");
            }
         });

         cancelButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
               cancelButton.startAnimation(cancelSprite);
            }
         });

         okButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
               okButton.clearAnimation();
            }
         });

         //Hooking up the functionality.... ?
         //TODO: we might introduce a parameter for having this view optionally have a box or a series of button
         //TODO: with hints of available hymns for the composed number.

         ///////addView(<view to add>, new LinearLayout.LayoutParams( , ));
      }

   }

   public void setOnNumberConfirmedListener(OnNumberConfirmedListener listener) {
      mOnNumberConfirmedListener = listener;
   }

}
