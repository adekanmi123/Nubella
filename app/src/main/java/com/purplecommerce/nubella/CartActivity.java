package com.purplecommerce.nubella;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.purplecommerce.nubella.Adapters.Checkout_Recycle_Adapter;

public class CartActivity extends AppCompatActivity {

   RecyclerView recyclerView ;
   TextView total_amount , checkout ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        init();

        total_amount.setText(getResources().getString(R.string.Rs_symbol)+"5000");

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
        total_amount = (TextView)findViewById(R.id.txt_total_amount);
        checkout = (TextView)findViewById(R.id.txt_checkout);

    }


}
