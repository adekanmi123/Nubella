package com.purplecommerce.nubella;


import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;

import com.purplecommerce.nubella.CartViews.CartActivity;
import com.purplecommerce.nubella.Database.CategoryDBManager;
import com.purplecommerce.nubella.Fragments.HomeFragment;

public class MainActivity extends BaseActivity {


    CategoryDBManager categoryDBManager ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        Init();

       categoryDBManager = new CategoryDBManager(MainActivity.this);

     // categoryDBManager.ClearCategoriesTable();

//        if (categoryDBManager.CategoryTableIsEmpty()){
//
//            categoryDBManager.SaveNewCategory(1 , "" ,0 , 0 , "Root Catalog" , 0 , 0);
//            categoryDBManager.SaveNewCategory(2 , "" ,1 , 1 , "Default Category" , 1 , 1);
//            categoryDBManager.SaveNewCategory(145 , "" ,0 , 2 , "Sale" , 2 , 1);
//            categoryDBManager.SaveNewCategory(153 , "" ,1 , 3 , "Outerwear" , 145 , 1);
//            categoryDBManager.SaveNewCategory(80 , "" ,0 , 2 , "About Us" , 2 , 14);
//            categoryDBManager.SaveNewCategory(118 , "" ,0 , 2 , "price list" , 2 , 18);
//            categoryDBManager.SaveNewCategory(119 , "" ,0 , 2 , "catalog" , 2 , 19);
//            categoryDBManager.SaveNewCategory(178 , "" ,1 , 2 , "New In" , 2 , 20);
//            categoryDBManager.SaveNewCategory(177 , "" ,1 , 3 , "Current Highlights" , 178 , 1);
//            categoryDBManager.SaveNewCategory(202 , "" ,1 , 3 , "Shop by Clothing" , 178 , 2);
//            categoryDBManager.SaveNewCategory(203 , "" ,1 , 4 , "Blouses, Shirts & Tees" , 202 , 1);
//            categoryDBManager.SaveNewCategory(204 , "" ,0 , 4 , "Tees" , 202 , 2);
//            categoryDBManager.SaveNewCategory(205 , "" ,1 , 4 , "Dresses" , 202 , 3);
//            categoryDBManager.SaveNewCategory(206 , "" ,0 , 4 , "Maxi Dresses" , 202 , 4);
//            categoryDBManager.SaveNewCategory(207 , "" ,1 , 4 , "Trousers & Jeggings" , 202 , 5);
//            categoryDBManager.SaveNewCategory(208 , "" ,1 , 4 , "Capes and jackets" , 202 , 6);
//            categoryDBManager.SaveNewCategory(209 , "" ,0 , 4 , "Jackets & Blazers" , 202 , 7);
//            categoryDBManager.SaveNewCategory(211 , "" ,1 , 3 , "Shop By ocassion" , 178 , 3);
//            categoryDBManager.SaveNewCategory(212 , "" ,1 , 4 , "Workwear" , 211 , 1);
//            categoryDBManager.SaveNewCategory(213 , "" ,1 , 4 , "Evening" , 211 , 2);
//            categoryDBManager.SaveNewCategory(189 , "" ,1 , 4 , "Evening" , 187 , 2);
//            categoryDBManager.SaveNewCategory(214 , "" ,1 , 4 , "Casual" , 211 , 3);
//            categoryDBManager.SaveNewCategory(162 , "" ,1 , 2 , "Clothing" , 2 , 21);
//            categoryDBManager.SaveNewCategory(180 , "" ,1 , 3 , "Shop by Clothing" , 162 , 1);
//            categoryDBManager.SaveNewCategory(181 , "" ,1 , 4 , "Blouses, Shirts & Tees" , 180 , 1);
//            categoryDBManager.SaveNewCategory(210 , "" ,0 , 4 , "Tees" , 180 , 2);
//            categoryDBManager.SaveNewCategory(182 , "" ,1 , 4 , "Dresses" , 180 , 3);
//            categoryDBManager.SaveNewCategory(216 , "" ,1 , 5 , "Dresses" , 182 , 2);
//            categoryDBManager.SaveNewCategory(183 , "" ,0 , 4 , "Maxi Dresses" , 180 , 4);
//            categoryDBManager.SaveNewCategory(184 , "" ,1 , 4 , "Trousers & Jeggings" , 180 , 5);
//            categoryDBManager.SaveNewCategory(185 , "" ,1 , 4 , "Capes and jackets" , 180 , 6);
//            categoryDBManager.SaveNewCategory(186 , "" ,0 , 4 , "Jackets & Blazers" , 180 , 7);
//            categoryDBManager.SaveNewCategory(188 , "" ,1 , 4 , "Workwear" , 187  , 1);
//            categoryDBManager.SaveNewCategory(190 , "" ,1 , 3 , "Casual" , 187  , 4);
//            categoryDBManager.SaveNewCategory(191 , "" ,1 , 4 , "Pear" , 166  , 1);
//            categoryDBManager.SaveNewCategory(192 , "" ,1 , 4 , "Rectangle" , 166  , 2);
//            categoryDBManager.SaveNewCategory(193 , "" ,1 , 4 , "Oval" , 166  , 3);
//            categoryDBManager.SaveNewCategory(194 , "" ,1 , 4 , "Strawberry" , 166  , 4);
//            categoryDBManager.SaveNewCategory(195 , "" ,1 , 4 , "Hourglass" , 166  , 5);
//            categoryDBManager.SaveNewCategory(196 , "" ,1 , 3 , "Size Guide" , 161  , 2);
//            categoryDBManager.SaveNewCategory(197 , "" ,1 , 4 , "How to Measure" , 196  , 1);
//            categoryDBManager.SaveNewCategory(198 , "" ,1 , 3 , "Styling Tips" , 161  , 3);
//            categoryDBManager.SaveNewCategory(199 , "" ,1 , 4 , "How to make tummy look flatter" , 198  , 1);
//            categoryDBManager.SaveNewCategory(200 , "" ,1 , 4 , "How to dress wide hips" , 198  , 2);
//            categoryDBManager.SaveNewCategory(201 , "" ,1 , 4 , "How to dress large bust" , 198  , 3);
//            categoryDBManager.SaveNewCategory(161 , "" ,1 , 2 ,"Fashion Advice" ,2 , 22);
//            categoryDBManager.SaveNewCategory(163 , "" ,1 , 2 , "Sale" , 2  , 23);
//            categoryDBManager.SaveNewCategory(164 , "" ,0 , 2 , "Lookbook" , 2 , 24);
//            categoryDBManager.SaveNewCategory(165 , "" ,0 , 2 , "Offers" , 2  , 25);
//            categoryDBManager.SaveNewCategory(215 , "" ,0 , 2 , "Top Wear" , 2  , 26);
//            categoryDBManager.SaveNewCategory(135 , "" ,0 , 1 , "Corporates" , 1 , 2);
//            categoryDBManager.SaveNewCategory(146 , "" ,1 , 1 , "Salesasfewy" , 1  , 3);
//
//            categoryDBManager.SaveNewCategory(187 , "" ,1 , 3 ,"SHOP BY OCCASION" ,162 , 2);
//            categoryDBManager.SaveNewCategory(166 , "" ,1 , 3 ,"Shop by body shape" ,161 , 1);
//
//        }
//
//         Log.e("**Category","Size"+categoryDBManager.GetAllCategories().size());


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




