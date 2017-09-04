package com.purplecommerce.nubella;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddressesActivity extends AppCompatActivity {


    LinearLayout ll_parent_edit , ll_parent_new , add_new;
    TextView Edit_Address ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);


      init();

        Edit_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_parent_edit.addView(AddViewOnLinear(ll_parent_edit));
            }
        });

        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_parent_new.addView(AddViewOnLinear(ll_parent_new));
            }
        });


    }

    private void init() {

        ll_parent_edit = (LinearLayout)findViewById(R.id.ll_edit_address);
        ll_parent_new = (LinearLayout)findViewById(R.id.ll_add_address);
        Edit_Address = (TextView)findViewById(R.id.txt_edit_address);
        add_new = (LinearLayout)findViewById(R.id.ll_add_new);

    }


    public View AddViewOnLinear(LinearLayout ll_Parent){

        LayoutInflater inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.add_address_layout, null);



        return myView ;

    }





}
