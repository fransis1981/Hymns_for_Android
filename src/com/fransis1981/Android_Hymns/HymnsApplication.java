package com.fransis1981.Android_Hymns;

import android.app.Application;
import android.content.res.Resources;

/**
 * Created by francesco.vitullo on 27/01/14.
 */
public class HymnsApplication extends Application {
    private static HymnsApplication singleton;

    //Providing at application level a one-time instantiation of the Resources table (for efficiency).
    public static Resources myResources;

    public static HymnsApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        myResources = getResources();
    }

}
