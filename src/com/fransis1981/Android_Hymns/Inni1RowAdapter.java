package com.fransis1981.Android_Hymns;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Fransis on 02/03/14 21.30.
 */
public class Inni1RowAdapter extends ArrayAdapter<Inno> {
   private final Activity _context;
   private final ArrayList<Inno> _inni;

   public static class ViewHolder {
      TextView numero_inno;
      TextView titolo_inno;
      CheckBox chk_starred;
   }

   public Inni1RowAdapter(Activity context, ArrayList<Inno> objects) {
      super(context, R.layout.mainscreen_fragment_hymnslist, objects);
      _context = context;
      _inni = objects;
   }

   //WARNING: this final in the 1st parameter might hide some runtime error; do extensive testing
   @Override
   public View getView(final int position, View convertView, ViewGroup parent) {
      ViewHolder vh;
      View innoTemplate;

      innoTemplate = convertView;
      if (convertView != null) {
         //Per questa particolare posizione nella ListView un oggetto holder è stato già allocato
         vh = (ViewHolder) convertView.getTag();
      }
      else {

         //TODO: memorizzare nel viewHolder il numero dell'inno e aggiungere lì un handler per il click sulla stella
         //TODO: basato sul nuovo campo, popolato dinamicamente. Dovrebbe andare un po' meglio
         vh = new ViewHolder();
         LayoutInflater li = _context.getLayoutInflater();
         innoTemplate = li.inflate(R.layout.hymnslist_onerow_item, null, true);
         vh.numero_inno = (TextView) innoTemplate.findViewById(R.id.item_Hymn_number);
         vh.titolo_inno = (TextView) innoTemplate.findViewById(R.id.item_hymn_title);
         vh.titolo_inno.setTypeface(HymnsApplication.fontTitolo1);
         vh.chk_starred = (CheckBox) innoTemplate.findViewById(R.id.item_Hymn_starcheck);
         innoTemplate.setTag(vh);
      }

      //CANNOT RECYCLE THE EVENT LISTENER-> I GET A MISMATCH BETWEEN THE CLICK AND THE ACTUAL LOCATION
      vh.chk_starred.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            _inni.get(position).setStarred(((CheckBox) v).isChecked());
         }
      });

      //Populating texts and customizing font properties
      Inno ii = _inni.get(position);
      vh.numero_inno.setText(String.valueOf(ii.getNumero()) + ".");
      vh.titolo_inno.setText(ii.getTitolo());
      vh.chk_starred.setChecked(ii.isStarred());
      return innoTemplate;
   }
}
