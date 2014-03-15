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
public class Fragment_RecentsList extends Fragment {
   private ListView _recentslist;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.mainscreen_fragment_recentslist, container, false);
      MRUManager _recentsManager = HymnsApplication.getRecentsManager();
      _recentslist = (ListView) rootView.findViewById(R.id.list_recent_hymns);
      _recentslist.setAdapter(new
            Inni2RowsAdapter(getActivity(), R.layout.mainscreen_fragment_recentslist, _recentsManager.getMRUList()));
      return rootView;
   }

   public void updateContent() {
      _recentslist.invalidate();
   }
}