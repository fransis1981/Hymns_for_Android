package com.fransis1981.Android_Hymns;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
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
      final Inno hymnToDisplay =  HymnsApplication.getCurrentInnario().getInno(hymnNumber);

      //Treating hymn number
      TextView txt_number = (TextView) findViewById(R.id.singleHymn_number);
      txt_number.setText(String.valueOf(hymnNumber) + ".");

      //Treating hymn title
      TextView title = (TextView) findViewById(R.id.hymn_title);
      title.setTypeface(HymnsApplication.fontTitolo1);
      title.setText(hymnToDisplay.getTitolo());

      //Treating star check box
      final CheckBox chk_starred = (CheckBox) findViewById(R.id.singleHymn_starcheck);
      chk_starred.setChecked(hymnToDisplay.isStarred());
      chk_starred.setOnClickListener(new CheckBox.OnClickListener() {
         @Override
         public void onClick(View v) {
            hymnToDisplay.setStarred(chk_starred.isChecked());
         }
      });

      setListAdapter(new StrofeAdapter(this, hymnToDisplay.getListStrofe()));

      Log.i(MyConstants.LogTag_STR, "Creata istanza di singlehymn Activity!!!!");
   }
}