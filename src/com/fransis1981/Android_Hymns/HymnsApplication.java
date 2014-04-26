package com.fransis1981.Android_Hymns;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by francesco.vitullo on 27/01/14.
 */
public class HymnsApplication extends Application {
   private static HymnsApplication singleton;
   static final String HelperPreferences_STR = "HelperPreferences";

   //Providing at application level a one-time instantiation of the Resources table (for efficiency).
   public static Resources myResources;
   public static AssetManager assets;
   public static Typeface fontTitolo1;
   public static Typeface fontLabelStrofa;
   public static Typeface fontContenutoStrofa;

   public static ArrayList<Innario> innari;
   public static HashMap<Inno.Categoria, Innario> categoricalInnari;    //One separate Innario for each category.
   private static Innario currentInnario;
   //////private static Cursor currentInnario;

   //Definizione evento per gestire il cambiamento di innario corrente
   public interface OnCurrentInnarioChangedListener {
      public void onCurrentInnarioChanged();
   }
   private static OnCurrentInnarioChangedListener mOnCurrentInnarioChangedListener;
   public static void setOnCurrentInnarioChangedListener(OnCurrentInnarioChangedListener listener) {
      mOnCurrentInnarioChangedListener = listener;
   }


   private static MRUManager recentsManager;
   private static StarManager starManager;

   private static int currentSpinnerLevel = 0;
   private static ImageView availableSpinner;

   //static TimingLogger tl;

   public static HymnsApplication getInstance() {
        return singleton;
    }

   public static void setAvailableSpinner(ImageView _spinner) {
      availableSpinner = _spinner;
   }

   public static void setSpinnerLevel(int _level) {
      availableSpinner.setImageLevel(currentSpinnerLevel = _level);
   }

    @Override
    public void onCreate() {
       super.onCreate();
       //tl = new TimingLogger(MyConstants.LogTag_STR, "HymnsApplication.onCreate");

       singleton = this;
       assets = getAssets();
       myResources = getResources();
       fontTitolo1 = Typeface.createFromAsset(assets , "Caudex_Italic.ttf");
       fontLabelStrofa = Typeface.createFromAsset(assets, "WetinCaroWant.ttf");
       fontContenutoStrofa = Typeface.createFromAsset(assets, "Caudex_Italic.ttf");
       //tl.addSplit("Prepared resources and fonts.");

       //Si prepara l'intent per il single hymn (to avoid null pointer exceptions at first invocation)
       SingleHymn_Activity.setupIntent();
       //tl.addSplit("Prepared intent.");

       //Time logging continued within the helper class...
       HymnBooksHelper hymnBooksHelper = new HymnBooksHelper(getApplicationContext());
       HymnBooksHelper.me().caricaInnari(true);
       innari = HymnBooksHelper.me().innari;
       categoricalInnari = HymnBooksHelper.me().categoricalInnari;

       //Si imposta l'innario corrente al primo innario disponibile
       setCurrentInnario(innari.get(0));

       //Si crea il gestore dai cantici recenti
       recentsManager = new MRUManager();
       try {
          //Restoring saved preferences (recents)
          recentsManager.readFromPreferences(getApplicationContext());
       }
       catch (Exception e) {
          Log.e(MyConstants.LogTag_STR, "CATCHED SOMETHING WHILE RESTORING RECENT HYMNS...." + e.getMessage());
       }
       //tl.addSplit("Prepared recents manager with preferences.");

       //Si crea il gestore dei preferiti (starred)
       starManager = new StarManager();
       try {
          //Restoring saved preferences (starred)
          starManager.readFromPreferences(getApplicationContext());
       }
       catch (Exception e) {
          Log.e(MyConstants.LogTag_STR, "CATCHED SOMETHING WHILE RESTORING STARRED HYMNS...." + e.getMessage());
       }
       //tl.addSplit("Prepared star manager with preferences.");
       //tl.dumpToLog();
    }

   public static void setCurrentInnario(Innario _innario) {
      if (currentInnario == _innario) return;
      currentInnario = _innario;
      if (mOnCurrentInnarioChangedListener != null)
         mOnCurrentInnarioChangedListener.onCurrentInnarioChanged();
   }
   public static void setCurrentInnario(String _titolo) {
      setCurrentInnario(getInnarioByTitle(_titolo));
   }
   public static void setCurrentInnario(Inno.Categoria _categoria) {
      if (_categoria == Inno.Categoria.NESSUNA) setCurrentInnario(innari.get(0));
      else setCurrentInnario(categoricalInnari.get(_categoria));
   }

   public static Innario getCurrentInnario() {
      return currentInnario;
   }

   /*
    * Questo metodo restituisce l'oggetto Innario opportuno conoscendone il titolo.
    */
   public static Innario getInnarioByTitle(String _title) {
      for (Innario i: innari) {
         if (i.getTitolo().equals(_title)) return i;
      }
      return null;
   }


   /*
    * This is a convenience method to get an ArrayList of titles for use with spinner's adapter.
    * REMOVED:In first position an empty string is put to allow an empty item in the spinner when no innario is selected.
    */
   public static ArrayList<String> getInnariTitles() {
      ArrayList<String> ret = new ArrayList<String>();
      //ret.add("");
      for (Innario i: innari)
         ret.add(i.toString());
      return ret;
   }

   /*
    * This method adds a hymn in the proper categorized Innario.
    */

   public static StarManager getStarManager() { return starManager; }
   public static MRUManager getRecentsManager() { return recentsManager; }

}
