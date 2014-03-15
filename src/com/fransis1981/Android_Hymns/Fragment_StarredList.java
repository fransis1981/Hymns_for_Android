package com.fransis1981.Android_Hymns;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Fransis on 05/03/14 11.38.
 */
public class Fragment_StarredList extends Fragment {
   private ListView _starredlist;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.mainscreen_fragment_starredlist, container, false);
      StarManager _starManager = HymnsApplication.getStarManager();
      _starredlist = (ListView) rootView.findViewById(R.id.list_starred_hymns);
      _starredlist.setAdapter(new
            Inni2RowsAdapter(getActivity(), R.layout.mainscreen_fragment_starredlist, _starManager.getStarredList()));
      return rootView;
   }

   public void updateContent() {
      _starredlist.invalidate();
   }
}