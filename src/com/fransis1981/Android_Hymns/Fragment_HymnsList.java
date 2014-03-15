package com.fransis1981.Android_Hymns;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Fransis on 05/03/14 11.38.
 */
public class Fragment_HymnsList extends Fragment {

   private ListView _list;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.mainscreen_fragment_hymnslist, container, false);
      _list = (ListView) rootView.findViewById(R.id.list_hymns);
      resetOnCurrentInnario();
      return rootView;
   }

   public void resetOnCurrentInnario() {
      _list.setAdapter(new Inni1RowAdapter(getActivity(), HymnsApplication.getCurrentInnario().hymnsToArrayList()));
      updateContent();
   }

   public void updateContent() {
      _list.invalidate();
   }
}