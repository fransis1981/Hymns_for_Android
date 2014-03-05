package com.fransis1981.Android_Hymns;

/**
 * Created by francesco.vitullo on 27/01/14.
 */
public final class MyConstants {
   public static final String LogTag_STR = "HYMNS";
   public static final String INNI_XML_FILE = "Inni_XML.xml";

   //XML tags naming
   public static final String XML_ROOT_STR = "innari";

   public static final String TAG_INNARIO_STR = "innario";
   public static final String INNARIO_TITOLO_ATTR = "titolo";
   public static final String INNARIO_NUM_INNI_ATTR = "numero_inni";

   public static final String TAG_INNO_STR = "inno";
   public static final String INNO_TITOLO_ATTR = "titolo";
   public static final String INNO_NUMERO_ATTR = "numero";
   public static final String INNO_CATEGORIA_ATTR = "categoria";

   public static final String TAG_STROFA_STR = "strofa";
   public static final String STROFA_NUMERO_ATTR = "num_strofa";     //Numero cumulativo di strofe e cori
   public static final String STROFA_ISCHORUS_ATTR = "is_chorus";

   //Titoli dei tab nella pagina principale
   public static final String TAB_MAIN_KEYPAD = "Tastierino";
   public static final String TAB_MAIN_HYMNSLIST = "Elenco";
   public static final String TAB_MAIN_RECENT = "Recenti";
   public static final String TAB_MAIN_STARRED = "Preferiti";
}
