package com.fransis1981.Android_Hymns;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;


public class MyActivity extends FragmentActivity
      implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

   class MainTabFactory implements TabHost.TabContentFactory {
      private final Context mContext;
      private View v;            //One-for-all dummy View pointers

      public MainTabFactory(Context context) {
         mContext = context;
         v = new View(mContext);
         v.setMinimumWidth(0);
         v.setMinimumHeight(0);
      }

      public View createTabContent(String tag) {
         return v;
      }

   }

   //Using symbolic constants for menu items, as by convention.
   private static final int MENU_PREFERENCES = Menu.FIRST;

   //Constants for bundle arguments
   private static final String TAB_BUNDLESTATE = "SelectedTab";

   Context _context;
   MainScreenPagerAdapter mPagerAdapter;
   ViewPager mViewPager;
   TabHost mTabHost;

   Spinner mSpinnerInnari, mSpinnerCategoria;


    /** Called when the activity is first created.  */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
           setContentView(R.layout.main);
           _context = this;

           final TextView lblCategorie = (TextView) findViewById(R.id.lbl_categoria);
           final TextView lblInnari = (TextView) findViewById(R.id.lbl_innari);

           //Treating spinner innari
           mSpinnerInnari = (Spinner) findViewById(R.id.spinner_innari);
           final ArrayAdapter<String> spin_innariAdapter =
                 new ArrayAdapter<String>(this, R.layout.mainspinners_item, HymnsApplication.getInnariTitles());
           spin_innariAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           mSpinnerInnari.setAdapter(spin_innariAdapter);

           //Treating spinner categoria
           mSpinnerCategoria = (Spinner) findViewById(R.id.spinner_categoria);
           final ArrayAdapter<String> spin_catAdapter =
                 new ArrayAdapter<String>(this, R.layout.mainspinners_item, Inno.Categoria.getCategoriesStringList());
           spin_catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           mSpinnerCategoria.setAdapter(spin_catAdapter);

           //Managing selection events on spinners
           mSpinnerInnari.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 String selected_str = (String) parent.getItemAtPosition(position);
                 if (selected_str.length() > 0) {
                    spin_innariAdapter.remove("");
                    spin_innariAdapter.notifyDataSetChanged();
                    mSpinnerCategoria.setSelection(0);
                    HymnsApplication.setCurrentInnario(selected_str);
                    lblInnari.setTextAppearance(_context, R.style.spinners_labels_style_inverse);
                    lblInnari.setBackgroundColor(HymnsApplication.myResources.getColor(android.R.color.white));
                    lblCategorie.setTextAppearance(_context, R.style.spinners_labels_style_direct);
                    lblCategorie.setBackgroundColor(HymnsApplication.myResources.getColor(android.R.color.transparent));
                 }
              }

              @Override
              public void onNothingSelected(AdapterView<?> parent) {}
           });

           mSpinnerCategoria.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 Inno.Categoria cat = Inno.Categoria.parseString((String) mSpinnerCategoria.getItemAtPosition(position));
                 if (position != 0) {
                    HymnsApplication.setCurrentInnario(cat);
                    spin_innariAdapter.insert("", 0);
                    spin_innariAdapter.notifyDataSetChanged();
                    mSpinnerInnari.setSelection(0);
                    lblCategorie.setTextAppearance(_context, R.style.spinners_labels_style_inverse);
                    lblCategorie.setBackgroundColor(HymnsApplication.myResources.getColor(android.R.color.white));
                    lblInnari.setTextAppearance(_context, R.style.spinners_labels_style_direct);
                    lblInnari.setBackgroundColor(HymnsApplication.myResources.getColor(android.R.color.transparent));
                 }
              }

              @Override
              public void onNothingSelected(AdapterView<?> parent) {}
           });

           //Treating tabs
           mTabHost = (TabHost) findViewById(android.R.id.tabhost);
           mTabHost.setup();
           addTabToTabHost(MyConstants.TAB_MAIN_KEYPAD, HymnsApplication.myResources.getDrawable(android.R.drawable.ic_dialog_dialer));
           addTabToTabHost(MyConstants.TAB_MAIN_HYMNSLIST, null);
           addTabToTabHost(MyConstants.TAB_MAIN_RECENT, null);
           addTabToTabHost(MyConstants.TAB_MAIN_STARRED, null);
           mTabHost.setOnTabChangedListener(this);

           //Treating ViewPager and related adapter
           mPagerAdapter = new MainScreenPagerAdapter(getSupportFragmentManager());
           mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
           mViewPager.setAdapter(mPagerAdapter);
           mViewPager.setOnPageChangeListener(this);

           mSpinnerInnari.setSelection(0);

        } catch (Exception e) {
            Log.e(MyConstants.LogTag_STR, "CATCHED SOMETHING I AM NOT GOING TO MANAGE NOW...." + e.getMessage());
            e.printStackTrace();
        }

       //Restoring selected tab
       if (savedInstanceState != null) {
          mTabHost.setCurrentTabByTag(savedInstanceState.getString(TAB_BUNDLESTATE, MyConstants.TAB_MAIN_KEYPAD));
       }
    }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      //TODO: se mi conservo a livello di classe il puntatore al menu, posso cambiarne il contenuto a run-time
      //TODO: fintanto che questo metodo non viene di nuovo invocato; lo stesso vale per i singoli MenuItem.
      super.onCreateOptionsMenu(menu);
      MenuItem mnu_pref = menu.add(0, MENU_PREFERENCES, Menu.NONE, R.string.mnu_options_str);
      mnu_pref.setIcon(android.R.drawable.ic_menu_preferences);
      return true;
   }

   private void addTabToTabHost(String _tabName, Drawable _drawable) {
      TabHost.TabSpec ts = mTabHost.newTabSpec(_tabName);
      if (_drawable != null) ts.setIndicator(_tabName, _drawable);
      else ts.setIndicator(_tabName);
      //Managing exception: you must specify a way to create the tab content (even if producing here dummy views).
      ts.setContent(new MainTabFactory(this));
      mTabHost.addTab(ts);
   }

   @Override
   public void onPageScrollStateChanged(int i) {
      //TODO: Da approfondire la semantica di questo listener.
   }

   @Override
   public void onPageScrolled(int i, float v, int i2) {
      //TODO: Da approfondire la semantica di questo listener.
   }

   @Override
   public void onPageSelected(int i) {
      mTabHost.setCurrentTab(i);
   }

   @Override
   public void onTabChanged(String tabId) {
      mViewPager.setCurrentItem(mTabHost.getCurrentTab(), true);
   }

   @Override
   protected void onSaveInstanceState(Bundle outState) {
      outState.putString(TAB_BUNDLESTATE, mTabHost.getCurrentTabTag());
      super.onSaveInstanceState(outState);
   }
}
