package com.fransis1981.Android_Hymns;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by Fransis on 27/02/14 15.08.
 */
public class SingleHymn_Activity extends ListActivity {

   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.single_hymn_view);

      TextView title = (TextView) findViewById(R.id.hymn_title);
      //ListView strofe_list = (ListView) findViewById(R.id.strofe_listview);
      title.setTypeface(HymnsApplication.fontTitolo1);

      setListAdapter(new StrofeAdapter(this, HymnsApplication.innari.get(0).getInno(121).getListStrofe()));
   }
}