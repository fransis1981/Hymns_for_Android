package com.fransis1981.Android_Hymns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class MyActivity extends Activity {
   //Using symbolic constants for menu items, as by convention.
   static final private int MENU_PREFERENCES = Menu.FIRST;

   private NumKeyPadView keypad;
   private Intent singleHymn_intent;

    /** Called when the activity is first created.  */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

           setContentView(R.layout.main);
           singleHymn_intent = new  Intent(this, SingleHymn_Activity.class);

            TextView tw = (TextView) findViewById(R.id.mytxt);
           ImageView spinner1 = (ImageView) findViewById(R.id.imgvw_spinner);
           HymnsApplication.setAvailableSpinner(spinner1);
           HymnsApplication.setSpinnerLevel(2500);

           //Getting a reference to the keypad and setting the listenr for number confirmed
           keypad = (NumKeyPadView) findViewById(R.id.main_keypad);
           keypad.setOnNumberConfirmedListener(new NumKeyPadView.OnNumberConfirmedListener() {
              @Override
              public void onNumberConfirmed(int number) {
                 Bundle newextra = new Bundle();
                 newextra.putInt(MyConstants.NUMERO_INNO_BUNDLE, number);
                 singleHymn_intent.replaceExtras(newextra);
                 startActivity(singleHymn_intent);
              }
           });

        } catch (Exception e) {
            Log.e(MyConstants.LogTag_STR, "CATCHED SOMETHING I AM NOT GOING TO MANAGE NOW...." + e.getMessage());
            e.printStackTrace();
        }

    }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      super.onCreateOptionsMenu(menu);
      MenuItem mnu_pref = menu.add(0, MENU_PREFERENCES, Menu.NONE, R.string.mnu_options_str);
      mnu_pref.setIcon(android.R.drawable.ic_menu_preferences);
      return true;
   }
}
