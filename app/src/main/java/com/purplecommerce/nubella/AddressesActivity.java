package com.purplecommerce.nubella;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purplecommerce.nubella.AccountModule.LoginActivity;

import java.util.ArrayList;

public class AddressesActivity extends AppCompatActivity {


    LinearLayout ll_edit_addresses, ll_parent_new , add_new;
    ArrayList<Boolean> arrayList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor();
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


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = AddressesActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(AddressesActivity.this.getResources().getColor(R.color.colorPrimary));
        } else {
            Window window = AddressesActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }




}
