package com.fransis1981.Android_Hymns;

import android.util.SparseArray;
import org.jsoup.nodes.Element;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Fransis on 23/02/14 20.07.
 */
public class Innario implements Serializable {
   private String titolo;
   private String id;
   private int numeroInni;
   private SparseArray<Inno> inni;
   private DialerList mDialerList;

   private void init() {
      mDialerList = new DialerList();
      inni = new SparseArray<Inno>(numeroInni);
   }

   //This constructor initializes the Innarioo with the XML tag elemnt extracted from the XML hymns definition
   public Innario(Element _tagInnario) throws Exception {
      init();

      if (!(_tagInnario.tagName().equals(MyConstants.TAG_INNARIO_STR))) {
         throw new Exception("Costruttore Innario invocato su un tag di tipo non valido. [" + _tagInnario.tagName() + "]");
      }

      titolo = _tagInnario.attr(MyConstants.INNARIO_TITOLO_ATTR);
      id = _tagInnario.attr(MyConstants.ID_INNARIO_STR);
      numeroInni = Integer.parseInt(_tagInnario.attr(MyConstants.INNARIO_NUM_INNI_ATTR));

      for (Element inno: _tagInnario.children()) {
         Inno iii = new Inno(inno, this);
         inni.append(iii.getNumero(), iii);
         mDialerList.addAvailableNumber(iii.getNumero());

         //Se l'inno appartiene ad una categoria, lo si sistema nell'opportuna struttura dati
         if (iii.getCategoria() != Inno.Categoria.NESSUNA) {
            HymnBooksHelper.me().addCategoricalInno(iii);
         }
      }

   }

   //This constructor prepares an empty innario (populate using addInno() method).
   public Innario() {
      init();
   }

   /*
    * This chainable method allows to add an already existing inno object to a given innario.
    * That inno (being already created) has the parent pointer correctly populated with its actual container.
    * This method is useful for building category innario preserving proper inno/innario relationship.
    */
   public Innario addInno(Inno _inno) {
      inni.append(_inno.getNumero(), _inno);
      mDialerList.addAvailableNumber(_inno.getNumero());
      return this;
   }

   public String getTitolo() {
      return titolo;
   }
   public Innario setTitolo(String _titolo) {
      titolo = _titolo;
      return this;
   }

   public String getId() { return id; }

   public int getNumeroInni() {
      return numeroInni;
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

   public ArrayList<Inno> hymnsToArrayList() {
      int ss = inni.size();
      ArrayList<Inno> ret = new ArrayList<Inno>(ss);
      for (int i = 0; i < ss; i++)
         ret.add(inni.valueAt(i));
      return ret;
   }
}
