package com.fransis1981.Android_Hymns;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Fransis on 05/03/14 11.05.
 */
public class Fragment_Keypad extends Fragment {
   private Intent singleHymn_intent;


   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.mainscreen_fragment_keypad, container, false);
      singleHymn_intent = new  Intent("com.fransis1981.action.SINGLEHYMNSHOW");

      //Getting a reference to the keypad and setting the listenr for number confirmed
      NumKeyPadView keypad = (NumKeyPadView) rootView.findViewById(R.id.main_keypad);
      keypad.setOnNumberConfirmedListener(new NumKeyPadView.OnNumberConfirmedListener() {
         @Override
         public void onNumberConfirmed(int number) {
            Bundle newextra = new Bundle();
            newextra.putInt(SingleHymn_Activity.NUMERO_INNO_BUNDLEARG, number);
            singleHymn_intent.replaceExtras(newextra);
            startActivity(singleHymn_intent);
         }
      });

      return rootView;
   }
}
