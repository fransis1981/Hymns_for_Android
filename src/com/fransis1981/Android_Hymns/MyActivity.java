package com.fransis1981.Android_Hymns;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import org.jsoup.helper.DataUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class MyActivity extends Activity {
    Document doc;
    AssetManager assets;

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
        } catch (Exception e) {
            Log.e(MyConstants.LogTag_STR, "CATCHED SOMETHING I AM NOT GOING TO MANAGE NOW....");
            e.printStackTrace();
        }
        //File input = new File("/tmp/input.html");

    }
}
