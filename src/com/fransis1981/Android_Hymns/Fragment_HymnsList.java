package com.fransis1981.Android_Hymns;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Fransis on 05/03/14 11.38.
 */
public class Fragment_HymnsList extends Fragment {
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rooView = inflater.inflate(R.layout.mainscreen_fragment_hymnslist, container, false);
      return rooView;
   }
}