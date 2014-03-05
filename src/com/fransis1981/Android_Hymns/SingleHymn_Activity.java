package com.fransis1981.Android_Hymns;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by Fransis on 27/02/14 15.08.
 */
public class SingleHymn_Activity extends ListActivity {
   public static final String NUMERO_INNO_BUNDLEARG = "NumeroInno";

   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.single_hymn_view);

      Bundle extras = getIntent().getExtras();
      int hymnNumber = extras.getInt(NUMERO_INNO_BUNDLEARG);
      Inno hymnToDisplay =  HymnsApplication.innari.get(0).getInno(hymnNumber);

      //TODO: implementare la logica dell'innario attivo.
      //TODO: implementare la gestione di un numero inno inesistente.

      TextView title = (TextView) findViewById(R.id.hymn_title);
      title.setTypeface(HymnsApplication.fontTitolo1);
      title.setText(hymnToDisplay.getTitolo());

      //TODO: aggiungere nel titolo dell'activity la casella con il numero e la stella per il preferito.

      setListAdapter(new StrofeAdapter(this, hymnToDisplay.getListStrofe()));
   }
}