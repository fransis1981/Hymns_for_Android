package com.fransis1981.Android_Hymns;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Fransis on 05/03/14 11.56.
 */
public class MainScreenPagerAdapter extends FragmentPagerAdapter {
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

   public MainScreenPagerAdapter(FragmentManager fm) {
      super(fm);
      _fragment_keypad = new Fragment_Keypad();
      //_fragment_hymnslist = new Fragment_HymnsList();
      //_fragment_recent = new Fragment_RecentsList();
      //_fragment_starred = new Fragment_StarredList();

      //Listening to events for triggering fragment updates.
      HymnsApplication.setOnCurrentInnarioChangedListener(new HymnsApplication.OnCurrentInnarioChangedListener() {
         @Override
         public void onCurrentInnarioChanged() {
            if (_fragment_keypad != null) _fragment_keypad.resetOnCurrentInnario();
            if (_fragment_hymnslist != null) _fragment_hymnslist.resetOnCurrentInnario();
         }
      });

      final MRUManager _recentsManager = HymnsApplication.getRecentsManager();
      _recentsManager.setMruStateChangedListener(new MRUManager.MRUStateChangedListener() {
         @Override
         public void OnMRUStateChanged() {
            if (_fragment_recent != null) _fragment_recent.updateContent();
            //Log.i(MyConstants.LogTag_STR, "Forcing update of recents fragment; MRU queue is: " + _recentsManager.getMRUList().size());
         }
      });

      StarManager _starManager = HymnsApplication.getStarManager();
      _starManager.setStarredItemsChangedListener(new StarManager.StarredItemsChangedListener() {
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
      });

   }


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
}
