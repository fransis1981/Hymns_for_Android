package com.fransis1981.Android_Hymns;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import org.jsoup.helper.DataUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Fransis on 18/04/14 11.38.
 * Helper class introduced to gather in a single class methods for managing hymnbooks
 * and to possibly implement efficient persistence of them.
 * Current HymnBook XML version is stored in SharedPreferences and is checked to determine the
 * source for loading hymns (persistent storage or XML).
 */
public class HymnBooksHelper implements Serializable {
   private static final String PREF_XML_VERSION = "XMLVersion";

   private Context mContext;
   static HymnBooksHelper singleton;

   ArrayList<Innario> innari;
   HashMap<Inno.Categoria, Innario> categoricalInnari;    //One separate Innario for each category.

   HymnBooksHelper(Context context) {
      mContext = context;
      singleton = this;
   }
   public static HymnBooksHelper me() { return singleton; }

   //TODO: implement possibly threads to improve performance.

   private void initDataStructures() {
      //Si prepara la struttura per gli innari di categoria
      categoricalInnari = new HashMap<Inno.Categoria, Innario>();
      for (Inno.Categoria cat: Inno.Categoria.values())
         categoricalInnari.put(cat, new Innario());

      //Qui si caricano gli innari veri e propri (da SD oppure file XML)
      innari = new ArrayList<Innario>();
   }

   /*
    * Questo metodo popola l'array globale degli innari;
    * se è disponibile un file serializzato si carica da li, altrimenti si fa il parsing del file XML.
    * Se _forceXML è TRUE allora si obbliga l'algoritmo ad acquisire i dati da XML.
    */
   void caricaInnari(boolean _forceXML) {
      Document doc;
      Element root;
      SharedPreferences sp;

      initDataStructures();

      try {
         doc = DataUtil.load(HymnsApplication.assets.open(MyConstants.INNI_XML_FILE), "UTF-8", "");
         root = doc.body().getElementsByTag(MyConstants.XML_ROOT_STR).first();
         HymnsApplication.tl.addSplit("Opened XML from assets.");

         sp = mContext.getSharedPreferences(HymnsApplication.HelperPreferences_STR, Context.MODE_PRIVATE);
         HymnsApplication.tl.addSplit("Opened helper shared preferences.");

         long xmlversion = getXMLVersionInAssets(root);
         if (_forceXML || xmlversion > getXMLVersionInPersistentStorage(sp)) {
            //Loading hymns from XML asset
            for (Element innario: root.children()) {
               Innario ii = new Innario(innario);
               innari.add(ii);
            }
            HymnsApplication.tl.addSplit("Loaded hymns from XML assets.");

            serialize();
            HymnsApplication.tl.addSplit("Serialized hymnbooks.");

            sp.edit().putLong(PREF_XML_VERSION, xmlversion).commit();
         }
         else {
            //Loading hymns from persistent storage
            //TODO: deserialize data structures
            deserialize(this);
            HymnsApplication.tl.addSplit("Loaded all hymnbooks from persistent storage (serialization).");
         }

      } catch (Exception e) {
         Log.e(MyConstants.LogTag_STR, "CATCHED SOMETHING WHILE LOADING HYMNS...." + e.getMessage());
         e.printStackTrace();
      }
   }

   //TODO: somewhere a method for serializing this object.

   static long getXMLVersionInAssets(Element xmlroot) {
      return Long.parseLong(xmlroot.attr(MyConstants.INNARI_VERSION_ATTR));
   }

   //TODO:for the sake of optimization, let a method that fastly read the version exist.

   static long getXMLVersionInPersistentStorage(SharedPreferences _sp) {
      return _sp.getLong(PREF_XML_VERSION, 0);
   }

   public void addCategoricalInno(Inno _inno) {
      categoricalInnari.get(_inno.getCategoria()).addInno(_inno);
   }

   void serialize() {
      FileOutputStream fos;
      ObjectOutputStream oot;
      try {
         fos = mContext.openFileOutput(MyConstants.Persistence_FILENAME, Context.MODE_PRIVATE);
         oot = new ObjectOutputStream(fos);
         oot.writeObject(this);
         oot.close();
      } catch (Exception e) {
         Log.e(MyConstants.LogTag_STR, "CATCHED SOMETHING WHILE SERIALIZING...." + e.getMessage());
         e.printStackTrace();
      }
   }

   void deserialize(HymnBooksHelper destination) {
      FileInputStream fis;
      ObjectInputStream oit;
      boolean failed = false;
      SharedPreferences sp;

      try {
         fis = new FileInputStream(MyConstants.Persistence_FILENAME);
         oit = new ObjectInputStream(fis);
         destination = (HymnBooksHelper) oit.readObject();
         oit.close();
      } catch (Exception e) {
         Log.e(MyConstants.LogTag_STR, "CATCHED SOMETHING WHILE DE-SERIALIZING...." + e.getMessage());
         failed = true;
         e.printStackTrace();
      }

      if (failed) {
         //If serialization fails once, avoid further fails by deleting serialized file and clearing version in preferences
         sp = mContext.getSharedPreferences(HymnsApplication.HelperPreferences_STR, Context.MODE_PRIVATE);
         sp.edit().remove(PREF_XML_VERSION).commit();
      }
   }
}
