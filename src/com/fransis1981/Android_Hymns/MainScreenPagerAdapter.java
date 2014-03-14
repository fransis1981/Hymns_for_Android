package com.fransis1981.Android_Hymns;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Fransis on 05/03/14 11.56.
 */
public class MainScreenPagerAdapter extends FragmentPagerAdapter {
   //Preallocating main fragments, each having its own layout and features
   Fragment_Keypad _fragment_keypad;
   Fragment_HymnsList _fragment_hymnslist;
   Fragment_RecentsList _fragment_recent;
   Fragment_StarredList _fragment_starred;

   public MainScreenPagerAdapter(FragmentManager fm) {
      super(fm);
      _fragment_keypad = new Fragment_Keypad();
      //_fragment_hymnslist = new Fragment_HymnsList();
      //_fragment_recent = new Fragment_RecentsList();
      //_fragment_starred = new Fragment_StarredList();

      HymnsApplication.setOnCurrentInnarioChangedListener(new HymnsApplication.OnCurrentInnarioChangedListener() {
         @Override
         public void onCurrentInnarioChanged() {
            if (_fragment_keypad != null) _fragment_keypad.resetOnCurrentInnario();
            if (_fragment_hymnslist != null) _fragment_hymnslist.resetOnCurrentInnario();
         }
      });
   }

   @Override
   public int getCount() {
      return 4;
         //Si prevede di avere i seguenti tabs:
         //    - keypad
         //    - lista degli inni
         //    - i più cantati recentemente
         //    - i preferiti
   }

   @Override
   public CharSequence getPageTitle(int position) {
      switch (position) {
         case 0: return MyConstants.TAB_MAIN_KEYPAD;
         case 1: return MyConstants.TAB_MAIN_HYMNSLIST;
         case 2: return MyConstants.TAB_MAIN_RECENT;
         case 3: return MyConstants.TAB_MAIN_STARRED;
         default: return "Unexpected!!!";
      }
   }

   @Override
   public Fragment getItem(int i) {
      switch (i) {
         case 0: return (_fragment_keypad == null)?_fragment_keypad = new Fragment_Keypad():_fragment_keypad;
         case 1: return (_fragment_hymnslist == null)?_fragment_hymnslist = new Fragment_HymnsList():_fragment_hymnslist;
         case 2: return (_fragment_recent == null)?_fragment_recent = new Fragment_RecentsList():_fragment_recent;
         case 3: return (_fragment_starred == null)?_fragment_starred = new Fragment_StarredList():_fragment_starred;
         default: return null;
      }
   }
}
