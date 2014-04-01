package com.fransis1981.Android_Hymns;

import java.util.ArrayList;

/**
 * Created by Fransis on 14/03/14 11.56.
 * This class supports management of most recently used (sung) hymns according to a FIFO strategy.
 */
public class MRUManager {
   public interface MRUStateChangedListener {
      public void OnMRUStateChanged();
   }
   private MRUStateChangedListener mruStateChangedListener;

   ArrayList<Inno> fifo_arrlist;
   private int capacity;

   public MRUManager() {
      capacity = HymnsApplication.myResources.getInteger(R.integer.default_recents_capacity);
      fifo_arrlist = new ArrayList<Inno>();
   }

   public void setMruStateChangedListener(MRUStateChangedListener listener) {
      mruStateChangedListener = listener;
   }
   private void raiseMruStateChangedEvent() {
      if (mruStateChangedListener != null)
         mruStateChangedListener.OnMRUStateChanged();
   }

   /*
    * First find if the same hymn is already in list (pop it and push it back on top).
    * If hymn is not already in the list, than just push it checking capacity.
    */
   public void pushHymn(Inno inno) {
      if (fifo_arrlist.contains(inno))
         fifo_arrlist.remove(inno);        //Hymn already present; updating its fifo position.
      else if (fifo_arrlist.size() == capacity)
         fifo_arrlist.remove(fifo_arrlist.size() - 1);
      fifo_arrlist.add(0, inno);
      raiseMruStateChangedEvent();
      //Log.i(MyConstants.LogTag_STR, "Pushed hymn " + inno.getNumero() + " into recents list.");
   }

   //Drops all MRU content.
   public void clearMRU() {
      fifo_arrlist.clear();
      raiseMruStateChangedEvent();
   }

   //Convenience method for usage with List Adapters.
   public ArrayList<Inno> getMRUList() {
      return fifo_arrlist;
   }
}
