package com.fransis1981.Android_Hymns;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.ImageView;
import org.jsoup.helper.DataUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by francesco.vitullo on 27/01/14.
 */
public class HymnsApplication extends Application {
    private static HymnsApplication singleton;

   //Providing at application level a one-time instantiation of the Resources table (for efficiency).
   public static Resources myResources;
   public static AssetManager assets;
   public static Typeface fontTitolo1;
   public static Typeface fontLabelStrofa;
   public static Typeface fontContenutoStrofa;

   public static ArrayList<Innario> innari;
   public static HashMap<Inno.Categoria, Innario> categoricalInnari;    //One separate Innario for each category.
   private static Innario currentInnario;

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
       singleton = this;
       assets = getAssets();
       myResources = getResources();
       fontTitolo1 = Typeface.createFromAsset(assets , "Caudex_Italic.ttf");
       //fontTitolo1 = Typeface.createFromAsset(assets , "Century_modern_italic2.ttf");
       //fontTitolo1 = Typeface.createFromAsset(assets , "partridg_TITOLO1.ttf");
       fontLabelStrofa = Typeface.createFromAsset(assets, "WetinCaroWant.ttf");
       //fontContenutoStrofa = Typeface.createFromAsset(assets, "Century_modern_italic2.ttf");
       fontContenutoStrofa = Typeface.createFromAsset(assets, "Caudex_Italic.ttf");

       //Si prepara l'intent per il single hymn (to avoid null pointer exceptinos at first invocation)
       SingleHymn_Activity.setupIntent();

       //Si prepara la struttura per gli innari di categoria
       categoricalInnari = new HashMap<Inno.Categoria, Innario>();
       for (Inno.Categoria cat: Inno.Categoria.values())
          categoricalInnari.put(cat, new Innario());

       //Qui si caricano gli innari veri e propri (da SD oppure file XML)
       innari = new ArrayList<Innario>();
       caricaInnari(false);

       //Si imposta l'innario corrente al primo innario disponibile
       setCurrentInnario(innari.get(0));

       //Si crea il gestore dai cantici recenti
       recentsManager = new MRUManager();

       //Si crea il gestore dei preferiti (starred)
       starManager = new StarManager();
    }

   @Override
   public void onTerminate() {
      super.onTerminate();
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
    * Questo metodo restituisce l'oggetto Innario opportuno conoscendone l'ID.
    */
   public static Innario getInnarioByID(String _id) {
      for (Innario i: innari) {
         if (i.getId().equals(_id)) return i;
      }
      return null;
   }

   /*
    * Questo metodo popola l'array globale degli innari;
    * se è disponibile un file serializzato si carica da li, altrimenti si fa il parsing del file XML.
    * Se _forceXML è TRUE allora si obbliga l'algoritmo ad acquisire i dati da XML.
    */
   private static void caricaInnari(boolean _forceXML) {
      //TODO: Gestire l'eventuale serializzazione
      Document doc;
      Element root;

      try {
         doc = DataUtil.load(assets.open(MyConstants.INNI_XML_FILE), "UTF-8", "");
         root = doc.body().getElementsByTag(MyConstants.XML_ROOT_STR).first();
         for (Element innario: root.children()) {
            Innario ii = new Innario(innario);
            innari.add(ii);
         }

      } catch (Exception e) {
         Log.e(MyConstants.LogTag_STR, "[HymsnApplication] CATCHED SOMETHING I AM NOT GOING TO MANAGE NOW...." + e.getMessage());
         e.printStackTrace();
      }
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
   public static void addCategoricalInno(Inno _inno) {
      categoricalInnari.get(_inno.getCategoria()).addInno(_inno);
   }


   public static StarManager getStarManager() { return starManager; }
   public static MRUManager getRecentsManager() { return recentsManager; }
}
