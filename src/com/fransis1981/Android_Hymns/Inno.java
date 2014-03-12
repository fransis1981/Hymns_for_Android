package com.fransis1981.Android_Hymns;

import org.jsoup.nodes.Element;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Fransis on 23/02/14 21.29.
 */
public class Inno {
   public static enum Categoria {
      NESSUNA(0),
      BAMBINI(2),
      FUNERALE(4),
      MATRIMONIO(8),
      SANTACENA(16);

      private final int val;
      private Categoria(int v) {
         val = v;
      }

      @Override
      public String toString() {
         switch (val) {
            case 16: return "SANTA CENA";
            default: return super.toString();
         }
      }

      //Method for casting from integer to Categoria
      public static Categoria parseInt(int _x) {
         switch (_x) {
            case 2: return BAMBINI;
            case 4: return FUNERALE;
            case 8: return MATRIMONIO;
            case 16: return SANTACENA;
            default: return NESSUNA;
         }
      }
      public static ArrayList<String> getCategoriesStringList() {
         ArrayList<String> ret = new ArrayList<String>();
         for (Categoria val: values())
            ret.add(val.toString());
         return ret;
      }
   }

   public static class InnoComparator implements Comparator<Inno> {
      @Override
      public int compare(Inno lhs, Inno rhs) {
         return ((Integer) lhs.numero).compareTo((Integer) rhs.numero);
      }
   }

   private Innario parentInnario;
   private int numero;
   private String titolo;
   private Integer numStrofe;              //Numero di strofe non definite come cori
   private int numCori;                    //Numero di strofe definite come cori
   private Categoria categoria;
   private ArrayList<Strofa> strofe_cori;      //Lista ordinata delle strofe e dei cori
   private boolean mStarred;

   public String getTitolo() {
      return titolo;
   }
   public int getNumero() { return numero; }
   public Innario getParentInnario() { return parentInnario; }
   public Categoria getCategoria() { return categoria; }

   public int getNumTotaleStrofe() { return numStrofe + numCori; }

   public Inno (Element _tagInno, Innario _parent) throws Exception {
      if (!(_tagInno.tagName().equals(MyConstants.TAG_INNO_STR))) {
         throw new Exception("Costruttore Inno invocato su un tag di tipo non valido. [" + _tagInno.tagName() + "]");
      }

      numStrofe = 0 ;
      numCori = 0;
      strofe_cori = new ArrayList<Strofa>();

      parentInnario = _parent;
      numero = Integer.parseInt(_tagInno.attr(MyConstants.INNO_NUMERO_ATTR));
      titolo = _tagInno.attr(MyConstants.INNO_TITOLO_ATTR);
      categoria = Categoria.parseInt(Integer.parseInt(_tagInno.attr(MyConstants.INNO_CATEGORIA_ATTR)));

      Strofa newstrofa;
      for (Element strofa: _tagInno.children()) {
         newstrofa = new Strofa(strofa, numStrofe, this);               //HERE: side-effect on parameter numStrofe
         if (newstrofa.IsChorus()) numCori++;
         else numStrofe++;
         strofe_cori.add(newstrofa);
      }
   }

   public ArrayList<Strofa> getListStrofe() {
      return strofe_cori;
   }

   public Inno setStarred(boolean starred) {
      if (mStarred != starred) {
         if (starred) {
            HymnsApplication.getStarManager().addStarred(this);
         }
         else {
            HymnsApplication.getStarManager().removeStarred(this);
         }
         mStarred = starred;
      }
      return this;                //Implementing chaining
   }

   public boolean isStarred() { return mStarred; }

}
