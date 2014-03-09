package com.fransis1981.Android_Hymns;

import android.util.SparseArray;
import org.jsoup.nodes.Element;

/**
 * Created by Fransis on 23/02/14 20.07.
 */
public class Innario {
   private String titolo;
   private int numeroInni;
   private SparseArray<Inno> inni;
   private DialerList mDialerList;

   public String getTitolo() {
      return titolo;
   }
   public Innario setTitolo(String _titolo) {
      titolo = _titolo;
      return this;
   }

   public int getNumeroInni() {
      return numeroInni;
   }

   //This constructor initializes the Innarioo with the XML tag elemnt extracted from the XML hymns definition
   public Innario(Element _tagInnario) throws Exception {
      mDialerList = new DialerList();

      if (!(_tagInnario.tagName().equals(MyConstants.TAG_INNARIO_STR))) {
         throw new Exception("Costruttore Innario invocato su un tag di tipo non valido. [" + _tagInnario.tagName() + "]");
      }

      titolo = _tagInnario.attr(MyConstants.INNARIO_TITOLO_ATTR);
      numeroInni = Integer.parseInt(_tagInnario.attr(MyConstants.INNARIO_NUM_INNI_ATTR));

      inni = new SparseArray<Inno>(numeroInni);
      for (Element inno: _tagInnario.children()) {
         Inno iii = new Inno(inno, this);
         inni.append(Integer.parseInt(inno.attr(MyConstants.INNO_NUMERO_ATTR)), iii);
         mDialerList.addAvailableNumber(iii.getNumero());
      }

   }

   public Inno getInno(int number) {
      return inni.get(number);
   }

   public DialerList getDialerList() {
      return mDialerList;
   }

   public boolean hasHymn(int number) {
      return inni.indexOfKey(number) >= 0;
   }

   //toString returns the title
   @Override
   public String toString() {
      return getTitolo();
   }
}
