package com.purplecommerce.nubella;


import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import com.purplecommerce.nubella.Adapters.SearchAdapter;
import com.purplecommerce.nubella.Fragments.HomeFragment;
import com.purplecommerce.nubella.Fragments.MyAccount;
import com.purplecommerce.nubella.Fragments.MyCartFragment;
import com.purplecommerce.nubella.Fragments.MyOrdersFragment;
import com.purplecommerce.nubella.Fragments.ProductListingFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    BottomBar bottomBar ;
    FrameLayout Fragment_Container  ;
    LinearLayout toolbar_container ;
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction ;
    MaterialSearchView searchView ;



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

//        SearchAdapter searchAdapter = new SearchAdapter(MainActivity.this);
//        searchView.setAdapter(searchAdapter);


    }

    private void Init() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        AddBootomBar();
        Fragment_Container = (FrameLayout)findViewById(R.id.fragment_container);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false); //or false
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setEllipsize(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  toolbar_container = (LinearLayout)findViewById(R.id.llContainer);

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

                    ProductListingFragment listingFragment = new ProductListingFragment();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, listingFragment , "ListingFragment");
                  //  fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }else if (tabId == R.id.tab_cart){

                    MyCartFragment cartFragment = new MyCartFragment();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, cartFragment , "MyCart");
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


        BottomBarTab cart = bottomBar.getTabWithId(R.id.tab_cart);
        cart.setBadgeCount(5);


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

        return true;
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





}
