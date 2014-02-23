package com.fransis1981.Android_Hymns;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import org.jsoup.helper.DataUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class MyActivity extends Activity {
    Document doc;
    AssetManager assets;

   //Using symbolic constants for menu items, as by convention.
   static final private int MENU_PREFERENCES = Menu.FIRST;

    /** Called when the activity is first created.  */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assets = getAssets();
        Element innario;
        try {
            Log.i(MyConstants.LogTag_STR, "First application starting....");
            doc = DataUtil.load(assets.open("MODELLO_XML_DEFINIZIONI.xml"), "UTF-8", "");
            setContentView(R.layout.main);

            //Trying extrapolation of some text
            innario = doc.body().getElementsByTag("Innario").first();
            TextView tw = (TextView) findViewById(R.id.mytxt);
            tw.append("\n" + innario.attr("Titolo"));
            tw.append("\nNumero totale di inni:" + innario.attr("Numero_Inni"));
           TextView large = (TextView) findViewById(R.id.largetxt);
           large.setTypeface(HymnsApplication.fontTitolo1);
           large.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.keypad_ok_64, 0);
           ImageView spinner1 = (ImageView) findViewById(R.id.imgvw_spinner);
           //spinner1.setImageLevel(2500);
           HymnsApplication.setAvailableSpinner(spinner1);
           HymnsApplication.setSpinnerLevel(2500);
        } catch (Exception e) {
            Log.e(MyConstants.LogTag_STR, "CATCHED SOMETHING I AM NOT GOING TO MANAGE NOW...." + e.getMessage());
            e.printStackTrace();
        }
        //File input = new File("/tmp/input.html");

    }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      super.onCreateOptionsMenu(menu);
      MenuItem mnu_pref = menu.add(0, MENU_PREFERENCES, Menu.NONE, R.string.mnu_options_str);
      mnu_pref.setIcon(android.R.drawable.ic_menu_preferences);
      return true;
   }
}
