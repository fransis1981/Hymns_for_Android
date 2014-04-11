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
      implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener,
                 Spinner.OnItemSelectedListener {

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
   private static final String CATEGORIASELECTION_BUNDLESTATE = "SelectedCategory";
   private static final String INNARIOSELECTION_BUNDLESTATE = "SelectedHymnBook";

   Context _context;
   MainScreenPagerAdapter mPagerAdapter;
   ViewPager mViewPager;
   TabHost mTabHost;

   Fragment_Keypad fragment_keypad;
   Fragment_HymnsList fragment_hymnslist;
   Fragment_RecentsList fragment_recent;
   Fragment_StarredList fragment_starred;

   TextView lblCategorie, lblInnari;
   Spinner mSpinnerInnari, mSpinnerCategoria;
   ArrayAdapter<String> spin_innariAdapter, spin_catAdapter;

   int currentInnariSelection = -1, currentCategoriaSelection = -1;

    /** Called when the activity is first created.  */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
           setContentView(R.layout.main);
           _context = this;

           initUI();

        } catch (Exception e) {
            Log.e(MyConstants.LogTag_STR, "CATCHED SOMETHING WHILE CREATNG MAIN ACTIVITY GUI...." + e.getMessage());
            e.printStackTrace();
        }
   }

   private void initUI() throws Exception {
      lblCategorie = (TextView) findViewById(R.id.lbl_categoria);
      lblInnari = (TextView) findViewById(R.id.lbl_innari);

      //Treating spinner innari
      mSpinnerInnari = (Spinner) findViewById(R.id.spinner_innari);
      spin_innariAdapter =
            new ArrayAdapter<String>(this, R.layout.mainspinners_item, HymnsApplication.getInnariTitles());
      spin_innariAdapter.insert(HymnsApplication.myResources.getString(R.string.generic_categoria_spinner_label), 0);
      spin_innariAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      mSpinnerInnari.setAdapter(spin_innariAdapter);
      mSpinnerInnari.setSelection(1);
      mSpinnerInnari.setOnItemSelectedListener(this);

      //Treating spinner categoria
      mSpinnerCategoria = (Spinner) findViewById(R.id.spinner_categoria);
      spin_catAdapter =
            new ArrayAdapter<String>(this, R.layout.mainspinners_item, Inno.Categoria.getCategoriesStringList());
      spin_catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      mSpinnerCategoria.setAdapter(spin_catAdapter);
      mSpinnerCategoria.setOnItemSelectedListener(this);

      //Treating tabs
      mTabHost = (TabHost) findViewById(android.R.id.tabhost);
      mTabHost.setup();
      addTabToTabHost(MyConstants.TAB_MAIN_KEYPAD, HymnsApplication.myResources.getDrawable(android.R.drawable.ic_dialog_dialer));
      addTabToTabHost(MyConstants.TAB_MAIN_HYMNSLIST, null);
      addTabToTabHost(MyConstants.TAB_MAIN_RECENT, null);
      addTabToTabHost(MyConstants.TAB_MAIN_STARRED, null);
      mTabHost.setOnTabChangedListener(this);

      //Treating ViewPager, fragments and related adapter
      if (fragment_keypad == null)
         fragment_keypad = (Fragment_Keypad) Fragment_Keypad.instantiate(this, Fragment_Keypad.class.getName());

      if (fragment_hymnslist == null)
         fragment_hymnslist = (Fragment_HymnsList) Fragment_HymnsList.instantiate(this, Fragment_HymnsList.class.getName());
      if (fragment_recent == null)
         fragment_recent = (Fragment_RecentsList) Fragment_RecentsList.instantiate(this, Fragment_RecentsList.class.getName());
      if (fragment_starred == null)
         fragment_starred = (Fragment_StarredList) Fragment_StarredList.instantiate(this, Fragment_StarredList.class.getName());
      mPagerAdapter = new MainScreenPagerAdapter(getSupportFragmentManager(), fragment_keypad, fragment_hymnslist,fragment_recent, fragment_starred);
      mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
      mViewPager.setAdapter(mPagerAdapter);
      mViewPager.setAdapter(mPagerAdapter);
      mViewPager.setOnPageChangeListener(this);
      mViewPager.setOffscreenPageLimit(4);

      mPagerAdapter.bindEventListeners();
   }

   /*
    * Call this method to make reverse color on the label innari; convenience method to save status between orientations.
    * This method eventually also remove special formatting from the label categoria.
    */
   private void highlightLabelInnari() {
      lblInnari.setTextAppearance(_context, R.style.spinners_labels_style_inverse);
      lblInnari.setBackgroundColor(HymnsApplication.myResources.getColor(android.R.color.white));
      lblCategorie.setTextAppearance(_context, R.style.spinners_labels_style_direct);
      lblCategorie.setBackgroundColor(HymnsApplication.myResources.getColor(android.R.color.transparent));
   }

   /*
    * Call this method to make reverse color on the label categoria; convenience method to save status between orientations.
    * This method eventually also remove special formatting from the label innari.
    */
   private void highlightLabelCategoria() {
      lblCategorie.setTextAppearance(_context, R.style.spinners_labels_style_inverse);
      lblCategorie.setBackgroundColor(HymnsApplication.myResources.getColor(android.R.color.white));
      lblInnari.setTextAppearance(_context, R.style.spinners_labels_style_direct);
      lblInnari.setBackgroundColor(HymnsApplication.myResources.getColor(android.R.color.transparent));
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

   @Override
   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      //TODO check at the time of breakpoint, which is the saved state of selection in the watches....
      if (parent == mSpinnerInnari) {
         //Log.i(MyConstants.LogTag_STR, "A selection happened in the spinner Innari!!!! [" + selected_str + "]");
         //Handling selection on spinner Innari; 1st useful element is at index 1.

         //Se non si seleziona nessun innario, si seleziona almeno una categoria diversa da "NESSUNA"
         if (position == 0) {
            if (mSpinnerCategoria.getSelectedItemPosition() == 0) mSpinnerCategoria.setSelection(1);
         } else {
            String selected_str = (String) parent.getItemAtPosition(position);
            mSpinnerCategoria.setSelection(0);
            HymnsApplication.setCurrentInnario(selected_str);
            highlightLabelInnari();
         }
         currentInnariSelection = position;
      }
      else if (parent == mSpinnerCategoria) {
         //Handling selection on spinner Categoria
         if (position == 0) {
            if (mSpinnerInnari.getSelectedItemPosition() == 0) mSpinnerInnari.setSelection(1);
         } else {
            Inno.Categoria cat = Inno.Categoria.parseString((String) mSpinnerCategoria.getItemAtPosition(position));
            mSpinnerInnari.setSelection(0);
            HymnsApplication.setCurrentInnario(cat);
            highlightLabelCategoria();
         }
      }
   }

   @Override
   public void onNothingSelected(AdapterView<?> parent) {

   }

   @Override
   public void onPageScrollStateChanged(int i) {
      //TODO: Da approfondire la semantica di questo listener.
   }

   @Override
   public void onPageScrolled(int i, float v, int i2) {      //TODO: Da approfondire la semantica di questo listener.

   }

   @Override
   public void onPageSelected(int i) {
      mTabHost.setCurrentTab(i);
      mPagerAdapter.setCurrentFragmentContext(i);
   }

   @Override
   public void onTabChanged(String tabId) {
      int i = mTabHost.getCurrentTab();
      mViewPager.setCurrentItem(i, true);
   }

   @Override
   protected void onRestoreInstanceState(Bundle savedInstanceState) {
      super.onRestoreInstanceState(savedInstanceState);

      if (savedInstanceState != null) {
         currentCategoriaSelection = savedInstanceState.getInt(CATEGORIASELECTION_BUNDLESTATE);
         currentInnariSelection = savedInstanceState.getInt(INNARIOSELECTION_BUNDLESTATE);
         mTabHost.setCurrentTabByTag(savedInstanceState.getString(TAB_BUNDLESTATE, MyConstants.TAB_MAIN_KEYPAD));
      }
   }

   @Override
   protected void onSaveInstanceState(Bundle outState) {
      outState.putString(TAB_BUNDLESTATE, mTabHost.getCurrentTabTag());
      outState.putInt(CATEGORIASELECTION_BUNDLESTATE, currentCategoriaSelection);
      outState.putInt(INNARIOSELECTION_BUNDLESTATE, currentInnariSelection);
      super.onSaveInstanceState(outState);
   }

   @Override
   protected void onDestroy() {
      //Saving preferences (recents and starred) before the activity gets destroyed
      HymnsApplication.getRecentsManager().saveToPreferences(this);
      HymnsApplication.getStarManager().saveToPreferences(this);
      super.onDestroy();
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
   protected void onPause() {
      currentInnariSelection = mSpinnerInnari.getSelectedItemPosition();
      currentCategoriaSelection = mSpinnerCategoria.getSelectedItemPosition();
      super.onPause();
   }

   //Using this callback to manage spinners' state.
   @Override
   protected void onResume() {
      super.onResume();
      if (currentInnariSelection == -1 && currentCategoriaSelection == -1) mSpinnerInnari.setSelection(1);
      else {
         if (currentInnariSelection > 0) mSpinnerInnari.setSelection(currentInnariSelection);
         else if (currentCategoriaSelection > 0) mSpinnerCategoria.setSelection(currentCategoriaSelection);
      }
   }
}
