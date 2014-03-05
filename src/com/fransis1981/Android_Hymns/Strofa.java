package com.fransis1981.Android_Hymns;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

/**
 * Created by Fransis on 24/02/14 0.42.
 */
public class Strofa {
   private Inno parentInno;
   private boolean isChorus;
   private String contenuto;
   private short indiceStrofa;      //Numero sequenziale che non distingue strofe normali dai cori
   private String label;            //Etichetta associata:
                                    // <numero progressivo> per le strofe normali
                                    // CORO per le strofe flaggate come cori


   public boolean IsChorus() { return isChorus; }
   public String getLabel() { return label; }
   public String getContenuto() { return contenuto; }
   public Inno getParent() { return parentInno; }

   /*
      First time constructor is invoked for a given hymn, lastNumericLabel is set to 0;
       if this is not a chorus, value is increased by 1 and its string representation is assigned to label.
    */
   public Strofa(Element _tagStrofa, Integer _lastNumericLabel, Inno _parent) throws Exception {
      if (!(_tagStrofa.tagName().equals(MyConstants.TAG_STROFA_STR))) {
         throw new Exception("Costruttore Strofa invocato su un tag di tipo non valido. [" + _tagStrofa.tagName() + "]");
      }

      parentInno = _parent;
      isChorus = (Integer.parseInt(_tagStrofa.attr(MyConstants.STROFA_ISCHORUS_ATTR))) != 0;
      indiceStrofa = Short.parseShort(_tagStrofa.attr(MyConstants.STROFA_NUMERO_ATTR));
      contenuto = Jsoup.parse( _tagStrofa.html().replaceAll("(?i)<br[^>]*>[\\s]*", "br2n")).text();
      contenuto = contenuto.replaceAll("br2n", "\n");
      label = isChorus?HymnsApplication.myResources.getString(R.string.coro_label):(++_lastNumericLabel).toString();
   }
}
