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

/**
 * Created by francesco.vitullo on 27/01/14.
 */
public class HymnsApplication extends Application {
    private static HymnsApplication singleton;

   //Providing at application level a one-time instantiation of the Resources table (for efficiency).
   public static Resources myResources;
   public static Typeface fontTitolo1;
   public static AssetManager assets;

   public static ArrayList<Innario> innari;

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
       fontTitolo1 = Typeface.createFromAsset(assets , "partridg_TITOLO1.ttf");

       innari = new ArrayList<Innario>();
       caricaInnari(false);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
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
}
