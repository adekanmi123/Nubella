package com.purplecommerce.nubella;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.purplecommerce.nubella.Adapters.Checkout_Recycle_Adapter;

public class CartActivity extends AppCompatActivity {

   RecyclerView recyclerView ;
   TextView total_amount , checkout , pricetxt_withitems , product_total_price , delivery_price , payable_amount ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor();
        setContentView(R.layout.activity_cart);


        init();

        total_amount.setText(getResources().getString(R.string.Rs_symbol)+"5050");
        pricetxt_withitems.setText("Price (5 Items)");
        product_total_price.setText(getResources().getString(R.string.Rs_symbol)+"5000");
        delivery_price.setText(getResources().getString(R.string.Rs_symbol)+"50");
        payable_amount.setText(getResources().getString(R.string.Rs_symbol)+"5050");


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(CartActivity.this , CheckoutActivity.class));
            }
        });


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new Checkout_Recycle_Adapter(CartActivity.this));





    }

    private void init() {

        recyclerView = (RecyclerView)findViewById(R.id.reycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        total_amount = (TextView)findViewById(R.id.txt_total_amount);
        checkout = (TextView)findViewById(R.id.txt_checkout);

        pricetxt_withitems = (TextView)findViewById(R.id.price_txt_items_count);
        product_total_price = (TextView)findViewById(R.id.products_total);
        delivery_price = (TextView)findViewById(R.id.delivery_total);
        payable_amount = (TextView)findViewById(R.id.payable_total);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = CartActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(CartActivity.this.getResources().getColor(R.color.colorPrimary));
        } else {
            Window window = CartActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }




}
