package com.fransis1981.Android_Hymns;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by Fransis on 05/03/14 11.56.
 */
public class MainScreenPagerAdapter extends FragmentPagerAdapter
      implements HymnsApplication.OnCurrentInnarioChangedListener, StarManager.StarredItemsChangedListener,
                 MRUManager.MRUStateChangedListener {

   public static enum FragmentContextEnum {
      KEYPAD,
      LIST,
      RECENTS,
      STARRED;

      public static FragmentContextEnum parseInt(int i) {
         switch (i) {
            case 0: return KEYPAD;
            case 1: return LIST;
            case 2: return RECENTS;
            case 3: return STARRED;
         }
         return KEYPAD;
      }
   }

   //Preallocating main fragments, each having its own layout and features
   Fragment_Keypad _fragment_keypad;
   Fragment_HymnsList _fragment_hymnslist;
   Fragment_RecentsList _fragment_recent;
   Fragment_StarredList _fragment_starred;

   FragmentContextEnum fragmentContext;     //Used to identify currently shown fragment.

   public MainScreenPagerAdapter(FragmentManager fm, Fragment_Keypad fk, Fragment_HymnsList fh, Fragment_RecentsList fr, Fragment_StarredList fs) {
      super(fm);
      _fragment_keypad = fk;
      _fragment_hymnslist = fh;
      _fragment_recent = fr;
      _fragment_starred = fs;

      bindEventListeners();
   }


   void bindEventListeners() {
      Log.i(MyConstants.LogTag_STR, "----------- bindEventListeners() --------------------");
      //Listening to events for triggering fragment updates.
      HymnsApplication.setOnCurrentInnarioChangedListener(this);

      HymnsApplication.getRecentsManager().setMruStateChangedListener(this);

      HymnsApplication.getStarManager().setStarredItemsChangedListener(this);
   }

   //TODO: is this method actually useful?
   public void setCurrentFragmentContext(int i) {
      fragmentContext = FragmentContextEnum.parseInt(i);
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

   @Override
   public void onCurrentInnarioChanged() {
      //_fragment_keypad = ((Fragment_Keypad) getItem(0));
      _fragment_keypad.resetOnCurrentInnario();
      ((Fragment_HymnsList) getItem(1)).resetOnCurrentInnario();
   }

   @Override
   public void OnStarredItemsChanged() {
      switch (fragmentContext) {
         //TODO: if performance is getting slow, then we may add a flag to trigger update content only when
         //TODO: a star change is actually made.
         case LIST:
            if (_fragment_recent != null) _fragment_recent.updateContent();
            if (_fragment_starred != null) _fragment_starred.updateContent();
            break;
         case RECENTS:
            if (_fragment_hymnslist != null) _fragment_hymnslist.updateContent();
            if (_fragment_starred != null) _fragment_starred.updateContent();
            break;
         case STARRED:
            if (_fragment_hymnslist != null) _fragment_hymnslist.updateContent();
            if (_fragment_recent != null) _fragment_recent.updateContent();
            break;
      }
   }

   @Override
   public void OnMRUStateChanged() {
      //TODO: implement this handler
   }
}
