package com.purplecommerce.nubella;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purplecommerce.nubella.Adapters.Checkout_Recycle_Adapter;

public class CheckoutActivity extends AppCompatActivity {


    CardView DeliveryAddress  ;
    RecyclerView recyclerView ;
    LinearLayout checkout_items_layout , Payment_Options ;
    TextView Summary_Change , Payment_Change , Address_Change ,
    pricetxt_withitems , product_total_price , delivery_price , payable_amount ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor();
        setContentView(R.layout.activity_checkout);


        init();

        pricetxt_withitems.setText("Price (5 Items)");
        product_total_price.setText(getResources().getString(R.string.Rs_symbol)+"5000");
        delivery_price.setText(getResources().getString(R.string.Rs_symbol)+"50");
        payable_amount.setText(getResources().getString(R.string.Rs_symbol)+"5050");

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new Checkout_Recycle_Adapter(CheckoutActivity.this));

        Summary_Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkout_items_layout.getVisibility()==View.VISIBLE){
                    checkout_items_layout.setVisibility(View.GONE);
                    Summary_Change.setText("SHOW");
                }else {
                    checkout_items_layout.setVisibility(View.VISIBLE);
                    Summary_Change.setText(" HIDE ");

                }
            }
        });

        Payment_Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Payment_Options.getVisibility()==View.VISIBLE){
                    Payment_Options.setVisibility(View.GONE);
                }else {
                    Payment_Options.setVisibility(View.VISIBLE);
                }
            }
        });

        Address_Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckoutActivity.this , AddressesActivity.class));
            }
        });


    }

    private void init() {

        DeliveryAddress = (CardView)findViewById(R.id.card_view2);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_checkout);
        recyclerView.setNestedScrollingEnabled(false);
        checkout_items_layout = (LinearLayout)findViewById(R.id.ll_checkout_items);
        Summary_Change = (TextView)findViewById(R.id.summary_change);
        Payment_Change = (TextView)findViewById(R.id.txt_pay_change);
        Payment_Options = (LinearLayout)findViewById(R.id.ll_pay_options);
        Address_Change = (TextView)findViewById(R.id.address_change);

        pricetxt_withitems = (TextView)findViewById(R.id.price_txt_items_count);
        product_total_price = (TextView)findViewById(R.id.products_total);
        delivery_price = (TextView)findViewById(R.id.delivery_total);
        payable_amount = (TextView)findViewById(R.id.payable_total);



    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = CheckoutActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(CheckoutActivity.this.getResources().getColor(R.color.colorPrimary));
        } else {
            Window window = CheckoutActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


}
