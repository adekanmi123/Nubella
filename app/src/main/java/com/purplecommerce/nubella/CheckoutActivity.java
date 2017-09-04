package com.purplecommerce.nubella;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class CheckoutActivity extends AppCompatActivity {


    CardView DeliveryAddress ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        init();


        DeliveryAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(CheckoutActivity.this , AddressesActivity.class));
            }
        });


    }

    private void init() {

        DeliveryAddress = (CardView)findViewById(R.id.card_view2);


    }


}
