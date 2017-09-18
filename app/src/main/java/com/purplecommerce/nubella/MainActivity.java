package com.purplecommerce.nubella;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import com.purplecommerce.nubella.Fragments.CategoriesFragment;
import com.purplecommerce.nubella.Fragments.HomeFragment;
import com.purplecommerce.nubella.Fragments.MyAccount;
import com.purplecommerce.nubella.Fragments.MyOrdersFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        Init();




    }

    @Override
    public void cartClick() {
        super.cartClick();

        startActivity(new Intent(MainActivity.this , CartActivity.class));

    }

    private void Init() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        HomeFragment home = new HomeFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.view_stub, home , "Home");
        fragmentTransaction.commit();





       // AddBootomBar();

//                    HomeFragment home = new HomeFragment();
//                    fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_container, home , "Home");
//                    // fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();


    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);

    }

    //    public void AddBootomBar(){
//
//        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
//        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelected(@IdRes int tabId) {
//                if (tabId == R.id.tab_home) {

//                }else if (tabId == R.id.tab_listing){
//
//                    CategoriesFragment listingFragment = new CategoriesFragment();
//                    fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_container, listingFragment , "ListingFragment");
//                  //  fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//
//                }else if (tabId == R.id.tab_orders){
//
//                    MyOrdersFragment myOrdersFragment = new MyOrdersFragment();
//                    fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_container, myOrdersFragment , "MyOrders");
//                    //  fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//
//                }else if (tabId == R.id.tab_account){
//                    MyAccount myAccount = new MyAccount();
//                    fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_container, myAccount , "MyAccount");
//                    //   fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//                }
//
//
//            }
//        });
//
//
////        BottomBarTab cart = bottomBar.getTabWithId(R.id.tab_cart);
////        cart.setBadgeCount(5);
//
//
//        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
//            @Override
//            public void onTabReSelected(@IdRes int tabId) {
//                if (tabId == R.id.tab_listing) {
//                    // The tab with id R.id.tab_favorites was reselected,
//                    // change your content accordingly.
//                }
//            }
//        });
//
//
//    }


}




