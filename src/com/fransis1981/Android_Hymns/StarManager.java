package com.fransis1981.Android_Hymns;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Fransis on 10/03/14 15.12.
 * Class to keep track of starred hymns.
 */
public class StarManager {
   public interface StarredItemsChangedListener {
      public void OnStarredItemsChanged();
   }
   private StarredItemsChangedListener starredItemsChangedListener;

   private ArrayList<Inno> mStarredList;

   public StarManager() {
      mStarredList = new ArrayList<Inno>();
   }

   public void setStarredItemsChangedListener(StarredItemsChangedListener listener) {
      starredItemsChangedListener = listener;
   }
   private void raiseStarredItemsChangedEvent() {
      if (starredItemsChangedListener != null)
         starredItemsChangedListener.OnStarredItemsChanged();
   }

/*
   //Adding just a number, it is assuming that the number belong to current Innario.
   public void addStarred(int number) throws InnoNotFoundException {
      Innario iii = HymnsApplication.getCurrentInnario();
      if (!iii.hasHymn(number)) throw new InnoNotFoundException(number, iii);
      mStarredList.add(iii.getInno(number).setStarred(true));
   }

   public void addStarred(int number, Innario innario) throws InnoNotFoundException {
      if (!innario.hasHymn(number)) throw new InnoNotFoundException(number, innario);
      mStarredList.add(innario.getInno(number).setStarred(true));
   }
*/

   //Already passing in the pointer to the starred hymn.
   public void addStarred(Inno inno) {
      mStarredList.add(inno);
      Collections.sort(mStarredList, new Inno.InnoComparator());
      raiseStarredItemsChangedEvent();
      //Log.i(MyConstants.LogTag_STR, "Added new starred hymn ... " + inno.getNumero());
   }

   public void removeStarred(Inno inno) {
      mStarredList.remove(inno);
      raiseStarredItemsChangedEvent();
      //Log.i(MyConstants.LogTag_STR, "!!Removed previously starred hymn ... " + inno.getNumero());
   }

   public ArrayList<Inno> getStarredList() { return mStarredList; }
}
