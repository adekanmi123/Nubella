package com.purplecommerce.nubella;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AddressesActivity extends AppCompatActivity {


    LinearLayout ll_edit_addresses, ll_parent_new , add_new;
    ArrayList<Boolean> arrayList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);


      init();


        for (int i = 0 ; i < 2 ; i++){
            ll_edit_addresses.addView(AddViewOnLinear(ll_edit_addresses , i ));
            arrayList.add(false);
        }



        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressesActivity.this , Edit_New_AddressActivity.class));
            }
        });


    }

    private void init() {

        ll_edit_addresses = (LinearLayout)findViewById(R.id.ll_edit_addresses);
        ll_parent_new = (LinearLayout)findViewById(R.id.ll_add_address);

        add_new = (LinearLayout)findViewById(R.id.ll_add_new);

    }


    public View AddViewOnLinear(LinearLayout ll_Parent, final int position){

        LayoutInflater inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.added_addresses_layout, null);


        TextView Edit_Address = (TextView)myView.findViewById(R.id.txt_edit_address);

        Edit_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               startActivity(new Intent(AddressesActivity.this , Edit_New_AddressActivity.class));

            }
        });



        return myView ;

    }







}
