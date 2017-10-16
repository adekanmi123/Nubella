package com.purplecommerce.nubella;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.purplecommerce.nubella.Adapters.Checkout_Items_Adapter;
import com.purplecommerce.nubella.ApiManager.ApiManager;
import com.purplecommerce.nubella.POJO_models.CheckOutItemsResponse;
import com.purplecommerce.nubella.POJO_models.UserAddressesResponse;

public class CheckoutActivity extends BaseActivity {


    CardView DeliveryAddress  ;
    RecyclerView recyclerView ;
    LinearLayout checkout_items_layout , Payment_Options , ll_price_details;
    TextView Summary_Change , Payment_Change , Address_Change , Default_Address , TotalCheckoutItems ;
    ApiManager apiManager ;
    static final String GETADDRESSESTAG = "GetUserAddresses";
    static final String GETORDERREVIEWTAG = "GetOrderReviews";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checkout);


        init();



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

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
        Default_Address = (TextView)findViewById(R.id.txt_default_address);

        TotalCheckoutItems = (TextView)findViewById(R.id.checkout_items_count_total);
        ll_price_details = (LinearLayout)findViewById(R.id.ll_price_parent);
        setTitle("Checkout");

        apiManager = new ApiManager(CheckoutActivity.this ,apifetcher);
        apiManager.execution_method_get(GETADDRESSESTAG , "/mobileapi/address/getAddressList");


    }

       ApiManager.APIFETCHER apifetcher = new ApiManager.APIFETCHER() {
           @Override
           public void onAPIRunningState(int a) {

           }

           @Override
           public void onFetchProgress(int progress) {

           }

           @Override
           public void onFetchComplete(String script, String APINAME, Gson gson) {
               if (APINAME.equals(GETADDRESSESTAG)){

                   UserAddressesResponse addressesResponse = new UserAddressesResponse();
                   addressesResponse = gson.fromJson(""+script , UserAddressesResponse.class);
                   if (addressesResponse.getCode() == 0){

                       if (addressesResponse.getModel().isEmpty()){
                           Default_Address.setText("Please Add Your Address !!");
                       }else {


                           apiManager.execution_method_get(GETORDERREVIEWTAG , "/mobileapi/checkout/getOrderReview");

                           boolean defaultOrNot = false ;

                           for (int i = 0 ; i < addressesResponse.getModel().size() ; i++){

                               if (addressesResponse.getModel().get(i).isIs_default_billing() == true && addressesResponse.getModel().get(i).isIs_default_shipping()==true){
                                   defaultOrNot = true ;
                                   Default_Address.setText(addressesResponse.getModel().get(i).getCompany() + ", " + addressesResponse.getModel().get(i).getStreet().get(0)+", "+
                                           addressesResponse.getModel().get(i).getCity() + ", "+addressesResponse.getModel().get(i).getRegion()+", "+
                                           addressesResponse.getModel().get(i).getPostcode());
                               }

                           }

                           if (defaultOrNot == false){
                               Default_Address.setText(addressesResponse.getModel().get(0).getCompany() + ", " + addressesResponse.getModel().get(0).getStreet().get(0)+", "+
                                       addressesResponse.getModel().get(0).getCity() + ", "+addressesResponse.getModel().get(0).getRegion()+", "+
                                       addressesResponse.getModel().get(0).getPostcode());
                           }

                       }

                   }


                 }else if (APINAME.equals(GETORDERREVIEWTAG)){

                   CheckOutItemsResponse checkOutItemsResponse = new CheckOutItemsResponse();
                   checkOutItemsResponse = gson.fromJson(script , CheckOutItemsResponse.class);
                   if (checkOutItemsResponse.getCode() == 0){

//                       TotalCheckoutItems.setText(checkOutItemsResponse.getModel().getItems().size()+" Item (Total "+
//                       checkOutItemsResponse.getModel().getSymbol()+checkOutItemsResponse.getModel().getGrand_total()+")");
                       TotalCheckoutItems.setText(checkOutItemsResponse.getModel().getItems().size()+" Item ");
                       Checkout_Items_Adapter items_adapter = new Checkout_Items_Adapter(CheckoutActivity.this ,
                               checkOutItemsResponse.getModel().getItems(),checkOutItemsResponse.getModel().getSymbol());
                       recyclerView.setAdapter(items_adapter);
                   }

                   for (int i = 0 ; i < checkOutItemsResponse.getModel().getAmount_details().size() ; i++){

                     ll_price_details.addView(AddPricesDetails(checkOutItemsResponse.getModel().getAmount_details().get(i)));

                   }




               }






           }

           @Override
           public void onFetchFailed(ANError error) {

           }

           @Override
           public void WhichApi(String APINAME) {

           }
       };


    public View AddPricesDetails(CheckOutItemsResponse.ModelBean.AmountDetailsBean customOptionBean) {

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.layout_price_details, null);


        TextView Name = (TextView) myView.findViewById(R.id.price_txt_items_count);
        TextView Value = (TextView) myView.findViewById(R.id.products_total);

        Name.setText(customOptionBean.getName()+" : ");
        Value.setText(customOptionBean.getValue());


        return myView ;

    }




}
