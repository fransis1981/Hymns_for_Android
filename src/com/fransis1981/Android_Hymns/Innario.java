package com.fransis1981.Android_Hymns;

import android.database.Cursor;
import android.util.SparseArray;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

/**
 * Created by Fransis on 23/02/14 20.07.
 */
public class Innario {
   private String titolo;
   private String id;
   private int numeroInni;
   private SparseArray<Inno> inni;
   private DialerList mDialerList;
   private Cursor hymnsCursor;

   private void init(int _numeroinni, String _titolo, String _id) {
      mDialerList = new DialerList();
      numeroInni = _numeroinni;
      titolo = _titolo;
      id = _id;
      inni = new SparseArray<Inno>(_numeroinni);

      //Populating dialer list only (not hymns objects)
      //Log.i(MyConstants.LogTag_STR, "Stato del DB nwl costruttore dell'Innario:" + HymnBooksHelper.me().db.isOpen());
      hymnsCursor = HymnBooksHelper.me().db.query(MyConstants.TABLE_INNI,
                           null,
                           MyConstants.FIELD_INNI_ID_INNARIO + "=?", new String[] {String.valueOf(id)},
                           null, null, MyConstants.FIELD_INNI_NUMERO);
      while (hymnsCursor.moveToNext()) {
         Inno iii = new Inno(hymnsCursor, this);
         int num = iii.getNumero();
         inni.append(num, iii);
         mDialerList.addAvailableNumber(num);
         //TODO: Inizializzare gli inni senza la strofe, in modo da avere le informazioni di starred disponibili.
      }

      hymnsCursor.moveToFirst();
   }

   //This constructor initializes the Innarioo with the XML tag elemnt extracted from the XML hymns definition
   public Innario(Element _tagInnario) throws Exception {
      if (!(_tagInnario.tagName().equals(MyConstants.TAG_INNARIO_STR))) {
         throw new Exception("Costruttore Innario invocato su un tag di tipo non valido. [" + _tagInnario.tagName() + "]");
      }

      init(Integer.parseInt(_tagInnario.attr(MyConstants.INNARIO_NUM_INNI_ATTR)),
            _tagInnario.attr(MyConstants.INNARIO_TITOLO_ATTR),
            _tagInnario.attr(MyConstants.ID_INNARIO_STR));

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

   //This constructor is used if you want to extract fields from the cursor from an external class.
   public Innario(int numeroinni, String titolo, String id) {
      init(numeroinni, titolo, id);
   }

   //This constructor prepares an empty innario (populate using addInno() method).
   public Innario(String titolo) {
      init(0, titolo, titolo);
   }

   /*
    * This chainable method allows to add an already existing inno object to a given innario.
    * That inno (being already created) has the parent pointer correctly populated with its actual container.
    * This method is useful for building category innario preserving proper inno/innario relationship.
    */
   public Innario addInno(Inno _inno) {
      inni.append(_inno.getNumero(), _inno);
      mDialerList.addAvailableNumber(_inno.getNumero());
      numeroInni++;
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
      //TODO: qui implementare l'allocazione on demand.
      return inni.get(number);
   }

   public DialerList getDialerList() {
      return mDialerList;
   }

   public Cursor getHymnsCursor() {
      return hymnsCursor;
   }

   //TODO: this method might not work as by future implementation (hymn is only allocated on demand)
   public boolean hasHymn(int number) {
      return inni.indexOfKey(number) >= 0;
      //TODO: posso sfruttare il dialer list per reperire questa informazione oppure mi accollo una piccola query.
   }

   //toString returns the title
   @Override
   public String toString() {
      return getTitolo();
   }

   //TODO: valutare se questa funzione può essere ancora utile e soprattutto se può ancora funzionare.
   public ArrayList<Inno> hymnsToArrayList() {
      int ss = inni.size();
      ArrayList<Inno> ret = new ArrayList<Inno>(ss);
      for (int i = 0; i < ss; i++)
         ret.add(inni.valueAt(i));
      return ret;
   }

   public void dispose() {
      hymnsCursor.close();
   }
}
