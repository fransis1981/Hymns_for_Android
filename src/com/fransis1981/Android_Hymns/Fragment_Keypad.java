package com.fransis1981.Android_Hymns;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Fransis on 05/03/14 11.05.
 */
public class Fragment_Keypad extends Fragment implements UpdateContentItf {
   //private Intent singleHymn_intent;
   private TextView txtComposedNumber;
   private NumKeyPadView keypad;
   private DialerList mCurrentDialerList;

   private int mLastValidComposedNumber;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
   }


   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.mainscreen_fragment_keypad, container, false);

      //Getting and keeping the text view showing the composed number
      txtComposedNumber = (TextView) rootView.findViewById(R.id.txt_composed_number);
      txtComposedNumber.setText("");

      //Getting a reference to the keypad and setting the listenr for number confirmed
      keypad = (NumKeyPadView) rootView.findViewById(R.id.main_keypad);
      keypad.setOnKeyPressedListener(new NumKeyPadView.OnKeyPressedListener() {
         @Override
         public void onKeyPressed(int number) {
            CharSequence curr = txtComposedNumber.getText();
            switch (number) {
               case 0:
               case 1: case 2: case 3:
               case 4: case 5: case 6:
               case 7: case 8: case 9:
                  txtComposedNumber.setText(curr + String.valueOf(number));
                  keypad.setObscureList((mCurrentDialerList = mCurrentDialerList.getSubDialerList(number)).getObscureList());
                  keypad.cancelOkButtonTimeout();
                  if (isComposedNumberValid()) keypad.startOkButtonTimeout();
                  break;

               case NumKeyPadView.KEYPAD_CANCEL:
                  keypad.cancelOkButtonTimeout();
                  if (curr.length() == 0) break;
                  txtComposedNumber.setText(curr.subSequence(0, curr.length() - 1));
                  keypad.setObscureList((mCurrentDialerList = mCurrentDialerList.getParentDialerList()).getObscureList());
                  if (isComposedNumberValid()) keypad.startOkButtonTimeout();
                  break;

               case NumKeyPadView.KEYPAD_OK:
                  ((MyActivity) getActivity()).callback_HymnSelected(HymnsApplication.getCurrentInnario().getInno(mLastValidComposedNumber));
                  resetComposedNumber();
                  break;
            }
         }
      });

      resetComposedNumber();

      return rootView;
   }

   private boolean isComposedNumberValid() {
      CharSequence curr = txtComposedNumber.getText();
      if (curr.length() == 0) return false;
      int ttt = Integer.parseInt(curr.toString());
      if (HymnsApplication.getCurrentInnario().hasHymn(ttt)) {
         mLastValidComposedNumber = ttt;
         return true;
      }
      else return false;
   }

   public void resetComposedNumber() {
      try {
         txtComposedNumber.setText("");
         mCurrentDialerList = HymnsApplication.getCurrentInnario().getDialerList();
         keypad.setObscureList(mCurrentDialerList.getObscureList());
      } catch (NullPointerException npe) {
         //Log.w(MyConstants.LogTag_STR, "Catched a NULL Pointer in Fragment Keypad while in resetComposedNumber().");
      }
   }

   public void resetOnCurrentInnario() {
      resetComposedNumber();
   }

   @Override
   public void updateContent() {
      resetComposedNumber();
   }

   void abortKeypadTimeout() {
      try {
         keypad.cancelOkButtonTimeout();
      } catch (Exception e) {}
   }
}
