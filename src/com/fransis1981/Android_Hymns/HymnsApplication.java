package com.fransis1981.Android_Hymns;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;

/**
 * Created by francesco.vitullo on 27/01/14.
 */
public class HymnsApplication extends Application {
    private static HymnsApplication singleton;

   AssetManager assets;

   //Providing at application level a one-time instantiation of the Resources table (for efficiency).
   public static Resources myResources;
   public static Typeface fontTitolo1;

    public static HymnsApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
       super.onCreate();
       singleton = this;
       assets = getAssets();
       myResources = getResources();
       fontTitolo1 = Typeface.createFromAsset(assets , "partridg_TITOLO1.ttf");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
