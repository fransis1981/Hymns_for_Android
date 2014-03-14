package com.fransis1981.Android_Hymns;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Fransis on 14/03/14 11.56.
 * This class supports management of most recently used (sung) hymns according to a FIFO strategy.
 */
public class MRUManager {
   public interface MRUStateChangedListener {
      public void OnMRUStateChanged();
   }
   private MRUStateChangedListener mruStateChangedListener;

   private LinkedList<Inno> fifo;
   private int capacity;

   public MRUManager() {
      capacity = HymnsApplication.myResources.getInteger(R.integer.default_recents_capacity);
      fifo = new LinkedList<Inno>();
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
      if (fifo.contains(inno))
         fifo.remove(inno);        //Hymn already present; updating its fifo position.
      else if (fifo.size() == capacity)
         fifo.removeLast();
      fifo.addFirst(inno);
      raiseMruStateChangedEvent();
   }

   //Drops all MRU content.
   public void clearMRU() {
      fifo.clear();
      raiseMruStateChangedEvent();
   }

   //Convenience method for usage with List Adapters.
   public ArrayList<Inno> getMRUList() {
      ArrayList<Inno> ret = new ArrayList<Inno>();
      for (int i = 0; i < fifo.size(); i++)
         ret.add(fifo.get(i));
      return ret;
   }
}
