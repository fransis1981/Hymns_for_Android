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
   public interface OnKeyPressedListener {
      public void onKeyPressed(int number);      //Evento generato in corrispondenza di un numero confermato.
   }
   OnKeyPressedListener mOnKeyPressedListener;
   public void setOnKeyPressedListener(OnKeyPressedListener listener) {
      mOnKeyPressedListener = listener;
   }

   public static final int KEYPAD_OK = -1;
   public static final int KEYPAD_CANCEL = -2;


   DigitCircleButtonView numkeys[] = new DigitCircleButtonView[10];
   ImageButton okButton, cancelButton;   //ok button is a ghost one, actual event is reased upon timeout or forced confirmation.
   Animation okSprite, cancelSprite;

   int mLastPressed;
   boolean mAnimationStarted;

   public NumKeyPadView(Context context) {
      super(context);
      init(context);
   }

   public NumKeyPadView(Context context, AttributeSet attrs) {
      super(context, attrs);
      init(context);
   }

   //Method for managing composed number when a new digit is composed
   void raiseKeyPressedEvent(int button) {
      mLastPressed = button;
      okButton.clearAnimation();

      if (mOnKeyPressedListener != null)
         mOnKeyPressedListener.onKeyPressed(button);
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
            public void onAnimationStart(Animation animation) { mAnimationStarted = true; }
            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
               mAnimationStarted = false;
               if (mLastPressed == KEYPAD_OK) raiseKeyPressedEvent(KEYPAD_OK);
            }
         });

         //spinner = (ImageView) findViewById(R.id.imageView);
         //spinner.setImageLevel(currentLevel);

         //Number keys events
         numkeys[1].setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
               raiseKeyPressedEvent(1);
            }
         });
         numkeys[2].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {
            raiseKeyPressedEvent(2);}});
         numkeys[3].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {
            raiseKeyPressedEvent(3);}});
         numkeys[4].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {
            raiseKeyPressedEvent(4);}});
         numkeys[5].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {
            raiseKeyPressedEvent(5);}});
         numkeys[6].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {
            raiseKeyPressedEvent(6);}});
         numkeys[7].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {
            raiseKeyPressedEvent(7);}});
         numkeys[8].setOnClickListener(new Button.OnClickListener() {public void onClick(View v) {
            raiseKeyPressedEvent(8);}});
         numkeys[9].setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
               raiseKeyPressedEvent(9);
            }
         });
         numkeys[0].setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
               raiseKeyPressedEvent(0);
            }
         });

         cancelButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
               cancelButton.startAnimation(cancelSprite);
               raiseKeyPressedEvent(KEYPAD_CANCEL);
            }
         });

         okButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
               if (mAnimationStarted) okButton.clearAnimation();
            }
         });

         //Hooking up the functionality.... ?
         //TODO: we might introduce a parameter for having this view optionally have a box or a series of button
         //TODO: with hints of available hymns for the composed number.

         ///////addView(<view to add>, new LinearLayout.LayoutParams( , ));
         mLastPressed = KEYPAD_CANCEL;
         mAnimationStarted = false;
      }

   }

   //When the container checks that a proper hymn number has been reached, it may call this method to start the timeout
   public void startOkButtonTimeout() {
      mLastPressed = KEYPAD_OK;
      okButton.startAnimation(okSprite);
   }

   /*
   A list of 10 booleans (one for each digit); when TRUE, the corresponding digit is disable in the keypad.
    */
   public void setObscureList(boolean[] _list) {

   }
}
