package com.fransis1981.Android_Hymns;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Fransis on 10/03/14 15.12.
 * Class to keep track of starred hymns.
 */
public class StarManager {

   private ArrayList<Inno> mStarredList;

   public StarManager() {
      mStarredList = new ArrayList<Inno>();
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
      Log.i(MyConstants.LogTag_STR, "Added new starred hymn ... " + inno.getNumero());
   }

   public void removeStarred(Inno inno) {
      mStarredList.remove(inno);
      Log.i(MyConstants.LogTag_STR, "!!Removed previously starred hymn ... " + inno.getNumero());
   }

   public ArrayList<Inno> getStarredList() { return mStarredList; }
}
