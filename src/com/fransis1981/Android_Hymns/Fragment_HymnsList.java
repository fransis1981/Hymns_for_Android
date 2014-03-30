package com.fransis1981.Android_Hymns;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
      _list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Inno clicked_inno = (Inno) parent.getAdapter().getItem(position);
            SingleHymn_Activity.startIntentWithHymn(getActivity(), clicked_inno);
         }
      });

      resetOnCurrentInnario();
      return rootView;
   }

   public void resetOnCurrentInnario() {
      try {
      _list.setAdapter(new Inni1RowAdapter(getActivity(), HymnsApplication.getCurrentInnario().hymnsToArrayList()));
      updateContent();
      } catch (Exception e) {
         Log.w(MyConstants.LogTag_STR, "Catched EXCEPTION in Fragment Hymns List while in resetOnCurrentInnario().");
      }
   }

   public void updateContent() {
      //((ArrayAdapter<Inno>) _list.getAdapter()).notifyDataSetChanged();
      _list.invalidate();
   }
}