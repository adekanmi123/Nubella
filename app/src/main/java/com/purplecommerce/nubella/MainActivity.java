package com.purplecommerce.nubella;


import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import com.purplecommerce.nubella.Fragments.CategoriesFragment;
import com.purplecommerce.nubella.Fragments.HomeFragment;
import com.purplecommerce.nubella.Fragments.MyAccount;
import com.purplecommerce.nubella.Fragments.MyOrdersFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    BottomBar bottomBar ;
    FrameLayout Fragment_Container  ;
    LinearLayout toolbar_container ;
    public static FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction ;
    MaterialSearchView searchView ;
    public static TextView Toolbar_title_txt ;
    public static ImageView Toolbar_image ;


    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Init();

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic

                Log.e("**","query"+query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                Log.e("**","query"+newText);

                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });


    }

    private void Init() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
       // AddBootomBar();
        Fragment_Container = (FrameLayout)findViewById(R.id.fragment_container);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false); //or false
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setEllipsize(true);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        Toolbar_title_txt = (TextView)findViewById(R.id.title_txt);
//        Toolbar_image = (ImageView)findViewById(R.id.toolbar_img);
//        Toolbar_title_txt.setVisibility(View.GONE);
//        Toolbar_image.setVisibility(View.VISIBLE);

       // navigationView = (NavigationView) findViewById(R.id.navigation_view);


        AddBootomBar();


    }


    public void AddBootomBar(){

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_home) {
                    HomeFragment home = new HomeFragment();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, home , "Home");
                    // fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else if (tabId == R.id.tab_listing){

                    CategoriesFragment listingFragment = new CategoriesFragment();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, listingFragment , "ListingFragment");
                  //  fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }else if (tabId == R.id.tab_orders){

                    MyOrdersFragment myOrdersFragment = new MyOrdersFragment();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, myOrdersFragment , "MyOrders");
                    //  fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }else if (tabId == R.id.tab_account){
                    MyAccount myAccount = new MyAccount();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, myAccount , "MyAccount");
                    //   fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }


            }
        });


//        BottomBarTab cart = bottomBar.getTabWithId(R.id.tab_cart);
//        cart.setBadgeCount(5);


        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_listing) {
                    // The tab with id R.id.tab_favorites was reselected,
                    // change your content accordingly.
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        MenuItem item1 = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item1, R.layout.badge_layout);
        RelativeLayout notifCount = (RelativeLayout)   MenuItemCompat.getActionView(item1);

        TextView tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
        tv.setText("12");

        ImageView cart = (ImageView)notifCount.findViewById(R.id.cart_img_action);

        cart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

              // Toast.makeText(MainActivity.this, "test", Toast.LENGTH_SHORT).show();

               startActivity(new Intent(MainActivity.this , CartActivity.class));
           }
       });


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else  if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }



    }

    @Override
    protected void onResume() {
        super.onResume();

        String[] arr = getResources().getStringArray(R.array.query_suggestions);

        searchView.setSuggestions(arr);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e("**","result code"+resultCode + data);

        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {

                    Log.e("**","word"+searchWrd);

                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void uncheckAllMenuItems(NavigationView navigationView) {
        final Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.hasSubMenu()) {
                SubMenu subMenu = item.getSubMenu();
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    subMenuItem.setChecked(false);
                }
            } else {
                item.setChecked(false);
            }
        }

    }





}

//    public void AddNavigationView(){
//
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//
//            // This method will trigger on item Click of navigation menu
//            @Override
//            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                Log.e("**","menu id"+menuItem.getItemId());
//
//                uncheckAllMenuItems(navigationView);
//
//                // now set clicked menu item to checked
//                menuItem.setChecked(true);
//
//                // close drawer and load the content for that menu item
//                drawerLayout.closeDrawers();
//
//
//
//                if (menuItem.getItemId() == R.id.tab_home) {
//                    HomeFragment home = new HomeFragment();
//                    fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_container, home, "Home");
//                    // fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//                } else if (menuItem.getItemId()  == R.id.tab_listing) {
//
////                    CategoriesFragment listingFragment = new CategoriesFragment();
////                    fragmentTransaction = fragmentManager.beginTransaction();
////                    fragmentTransaction.replace(R.id.fragment_container, listingFragment , "ListingFragment");
////                  //  fragmentTransaction.addToBackStack(null);
////                    fragmentTransaction.commit();
//
//                } else if (menuItem.getItemId()  == R.id.tab_cart) {
//
//                    MyCartFragment cartFragment = new MyCartFragment();
//                    fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_container, cartFragment, "MyCart");
//                    //  fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//
//                } else if (menuItem.getItemId()  == R.id.tab_orders) {
//
//                    MyOrdersFragment myOrdersFragment = new MyOrdersFragment();
//                    fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_container, myOrdersFragment, "MyOrders");
//                    //  fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//
//                } else if (menuItem.getItemId()  == R.id.tab_account) {
//                    MyAccount myAccount = new MyAccount();
//                    fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_container, myAccount, "MyAccount");
//                    //   fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//
//                }
//
//                return true ;
//
//            }
//
//        });
//
//        // Initializing Drawer Layout and ActionBarToggle
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
//                super.onDrawerClosed(drawerView);
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
//
//                super.onDrawerOpened(drawerView);
//            }
//        };
//
//        //Setting the actionbarToggle to drawer layout
//        drawerLayout.setDrawerListener(actionBarDrawerToggle);
//
//        //calling sync state is necessay or else your hamburger icon wont show up
//        actionBarDrawerToggle.syncState();
//
//
//
//    }


