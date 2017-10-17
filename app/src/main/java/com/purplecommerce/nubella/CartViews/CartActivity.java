package com.purplecommerce.nubella.CartViews;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.mindorks.placeholderview.PlaceHolderView;
import com.purplecommerce.nubella.Adapters.CartItems_Recycle_Adapter;
import com.purplecommerce.nubella.ApiManager.ApiManager;
import com.purplecommerce.nubella.BaseActivity;
import com.purplecommerce.nubella.CheckoutActivity;
import com.purplecommerce.nubella.MainActivity;
import com.purplecommerce.nubella.POJO_models.CartResponse;
import com.purplecommerce.nubella.POJO_models.CheckResponse;
import com.purplecommerce.nubella.POJO_models.CouponApplyResponse;
import com.purplecommerce.nubella.POJO_models.CouponCodeRemoveResponse;
import com.purplecommerce.nubella.POJO_models.CouponDetailsResponse;
import com.purplecommerce.nubella.R;
import com.purplecommerce.nubella.Utils.AppUtils;

import java.util.ArrayList;

public class CartActivity extends BaseActivity {

   RecyclerView recyclerView ;
   TextView total_amount , checkout , pricetxt_withitems , product_total_price , delivery_price , payable_amount
           , No_Items , total_items ;
   ApiManager apiManager ;
   static final String GETCART = "GetCartItems" ;
   static final String APPLYCOUPON = "apply_coupon" ;
   static final String REMOVECOUPON = "remove_coupon" ;
   static final String UPDATECART = "UpdateItemQuantity" ;
   static final String REMOVECARTITEM = "RemoveItemCart" ;
   static final String GETCOUPONDETAILS = "CouponDetails";
   LinearLayout ll_price_details , ll_crosssell , ll_coupon_applied ;
   PlaceHolderView crossSellHolderView ;
   FrameLayout contentFrameLayout ;
   EditText edit_coupon ;
   TextView coupon_result , coupon_button , coupon_discount_price , coupon_discount_txt , subTotalWithoutDiscount;
   ArrayList<Double> Subtotal = new ArrayList<>();
   TextView Item_sub_total ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        init();

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(CartActivity.this , CheckoutActivity.class));
            }
        });


        apiManager.execution_method_get(GETCART , "/mobileapi/cart/getCartInfo");


        coupon_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (coupon_button.getText().toString().equals("APPLY")){
                   if (edit_coupon.getText().toString().isEmpty()){
                       Snackbar.make(coupon_button , "Enter Coupon Code !!" , Snackbar.LENGTH_SHORT).show();
                   }else {
                       String url = "/mobileapi/Cart/useCoupon?coupon_code="+edit_coupon.getText().toString();
                       apiManager.execution_method_get(APPLYCOUPON , url);

                   }

               }else {

                   apiManager.execution_method_get(REMOVECOUPON , "/mobileapi/Cart/useCoupon?remove=true");

               }



            }
        });





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

            if (APINAME.equals(GETCART)){


//                   CheckResponse checkResponse = new CheckResponse();
//                   checkResponse = gson.fromJson(script ,CheckResponse.class);
//                   if (checkResponse.getCode() ==5){
//                       contentFrameLayout.setVisibility(View.GONE);
//                       Toast.makeText(CartActivity.this, "No User Login !!", Toast.LENGTH_SHORT).show();
//                   }else {
                       CartResponse cartResponse = new CartResponse();
                       cartResponse = gson.fromJson(script , CartResponse.class);
                       if (cartResponse.getModel().getCart_items().isEmpty()){
                           No_Items.setVisibility(View.VISIBLE);
                           recyclerView.setVisibility(View.GONE);
                           contentFrameLayout.setVisibility(View.GONE);


                       }else {
                           No_Items.setVisibility(View.GONE);
                           recyclerView.setVisibility(View.VISIBLE);
                           contentFrameLayout.setVisibility(View.VISIBLE);
                           subTotalWithoutDiscount.setVisibility(View.GONE);

                           for (int i = 0 ; i < cartResponse.getModel().getCart_items().size() ; i++){
                               Subtotal.add(cartResponse.getModel().getCart_items().get(i).getItem_price() * cartResponse.getModel().getCart_items().get(i).getQty());
                           }

                           total_amount.setText(""+TotalPrice(Subtotal));
                           total_items.setText("Total ("+cartResponse.getModel().getCart_items_count()+" Item) : ");

                           RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                           recyclerView.setLayoutManager(mLayoutManager);
                           recyclerView.setItemAnimator(new DefaultItemAnimator());
                           CartItems_Recycle_Adapter checkoutRecycleAdapter = new CartItems_Recycle_Adapter(CartActivity.this , cartResponse.getModel().getSymbol() , cartResponse.getModel().getCart_items() , plusMinus);
                           recyclerView.setAdapter(checkoutRecycleAdapter);

                           if (cartResponse.getModel().getCrosssellprodtcs().isEmpty()){
                               ll_crosssell.setVisibility(View.GONE);
                           }else {
                               ll_crosssell.setVisibility(View.VISIBLE);
                               for (int i = 0 ; i < cartResponse.getModel().getCrosssellprodtcs().size() ; i++){
                                   crossSellHolderView.addView(new CrossSellProducts(CartActivity.this , crossSellHolderView , cartResponse.getModel().getCrosssellprodtcs().get(i)));
                               }
                           }

                       apiManager.execution_method_get(GETCOUPONDETAILS , "/mobileapi/Cart/getCouponDetail");


                       }

                 //  }



            }else if (APINAME.equals(UPDATECART)){

                CartResponse cartResponse = new CartResponse();
                cartResponse = gson.fromJson(script , CartResponse.class);

                if (cartResponse.getCode()==0){
                    Subtotal.clear();


                    for (int i = 0 ; i < cartResponse.getModel().getCart_items().size() ; i++){
                        Subtotal.add(cartResponse.getModel().getCart_items().get(i).getItem_price() * cartResponse.getModel().getCart_items().get(i).getQty());
                    }

                    total_amount.setText(""+TotalPrice(Subtotal));
                    total_items.setText("Total ("+cartResponse.getModel().getCart_items_count()+" Item) : ");

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    CartItems_Recycle_Adapter checkoutRecycleAdapter = new CartItems_Recycle_Adapter(CartActivity.this , cartResponse.getModel().getSymbol() , cartResponse.getModel().getCart_items() , plusMinus);
                    recyclerView.setAdapter(checkoutRecycleAdapter);

                    apiManager.execution_method_get(GETCOUPONDETAILS , "/mobileapi/Cart/getCouponDetail");

                }else {

                    Toast.makeText(CartActivity.this, ""+cartResponse.getMsg(), Toast.LENGTH_SHORT).show();

                }


            }else if (APINAME.equals(REMOVECARTITEM)){

                CheckResponse checkResponse = new CheckResponse();
                checkResponse = gson.fromJson(script , CheckResponse.class);
                if (checkResponse.getCode()==0){

                 apiManager.execution_method_get(GETCART , "/mobileapi/cart/getCartInfo");
                }else if (checkResponse.getCode()==5){
                    Toast.makeText(CartActivity.this, "No user login !!", Toast.LENGTH_SHORT).show();
                }

            }

            else if (APINAME.equals(APPLYCOUPON)){
                edit_coupon.setText("");
                CouponApplyResponse couponApplyResponse = new CouponApplyResponse();
                couponApplyResponse = gson.fromJson(script , CouponApplyResponse.class);
                if (couponApplyResponse.getCode() == 0){
                    ll_coupon_applied.setVisibility(View.VISIBLE);
                    coupon_discount_price.setText(""+couponApplyResponse.getModel().getDiscount());
                    total_amount.setText(""+couponApplyResponse.getModel().getGrand_total());
                    coupon_discount_txt.setText(""+couponApplyResponse.getModel().getCoupon_code()+" Discount : ");
                    coupon_button.setText("REMOVE");
                    AppUtils.ShowSuccessDialog(CartActivity.this , "Success" , "Coupon "+couponApplyResponse.getModel().getCoupon_code()+
                    " Applied Successfully .");
                    coupon_result.setText("Coupon "+couponApplyResponse.getModel().getCoupon_code()+
                            " Applied Successfully .");
                    edit_coupon.setVisibility(View.GONE);
                    coupon_result.setVisibility(View.VISIBLE);
                    subTotalWithoutDiscount.setVisibility(View.VISIBLE);
                    subTotalWithoutDiscount.setText(couponApplyResponse.getModel().getSymbol()
                            +""+couponApplyResponse.getModel().getSubtotal());


                }else {

                    Toast.makeText(CartActivity.this, ""+couponApplyResponse.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }else if (APINAME.equals(REMOVECOUPON)){

                CouponCodeRemoveResponse removeResponse = new CouponCodeRemoveResponse();
                removeResponse = gson.fromJson(script , CouponCodeRemoveResponse.class);
                if (removeResponse.getCode()==0){
                    ll_coupon_applied.setVisibility(View.GONE);
                    AppUtils.ShowSuccessDialog(CartActivity.this , "Success" , "Coupon Removed Successfully .");
                    coupon_result.setVisibility(View.GONE);
                    edit_coupon.setText("");
                    edit_coupon.setVisibility(View.VISIBLE);
                    coupon_button.setText("APPLY");
                    total_amount.setText(""+TotalPrice(Subtotal));
                    subTotalWithoutDiscount.setVisibility(View.GONE);

                }else {
                    Toast.makeText(CartActivity.this, ""+removeResponse.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }else if (APINAME.equals(GETCOUPONDETAILS)){

                CouponDetailsResponse couponDetailsResponse = new CouponDetailsResponse();
                couponDetailsResponse = gson.fromJson(script , CouponDetailsResponse.class);
                if (couponDetailsResponse.getCode() == 0){

                    if (couponDetailsResponse.getModel().getCoupon_code()==null){
                        ll_coupon_applied.setVisibility(View.GONE);
                        coupon_result.setVisibility(View.GONE);
                        edit_coupon.setText("");
                        edit_coupon.setVisibility(View.VISIBLE);
                        coupon_button.setText("APPLY");
                        subTotalWithoutDiscount.setVisibility(View.GONE);
                        subTotalWithoutDiscount.setText(couponDetailsResponse.getModel().getSymbol()
                                +""+couponDetailsResponse.getModel().getSubtotal());
                        total_amount.setText(couponDetailsResponse.getModel().getSymbol()+""+couponDetailsResponse.getModel().getGrand_total());

                    }else {

                        ll_coupon_applied.setVisibility(View.VISIBLE);
                        coupon_discount_price.setText(couponDetailsResponse.getModel().getSymbol()+""+couponDetailsResponse.getModel().getDiscount());
                        total_amount.setText(couponDetailsResponse.getModel().getSymbol()+""+couponDetailsResponse.getModel().getGrand_total());
                        coupon_discount_txt.setText(""+couponDetailsResponse.getModel().getCoupon_code()+" Discount : ");
                        coupon_result.setText("Coupon "+couponDetailsResponse.getModel().getCoupon_code()+
                                " Applied Successfully .");
                        edit_coupon.setVisibility(View.GONE);
                        coupon_result.setVisibility(View.VISIBLE);
                        subTotalWithoutDiscount.setVisibility(View.VISIBLE);
                        subTotalWithoutDiscount.setText(couponDetailsResponse.getModel().getSymbol()
                                +""+couponDetailsResponse.getModel().getSubtotal());
                        coupon_button.setText("REMOVE");
                    }

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


    CartItems_Recycle_Adapter.CartPlusMinus plusMinus = new CartItems_Recycle_Adapter.CartPlusMinus() {
        @Override
        public void Plus(int value_no_of_unit, int position, String item_id, TextView product_count, TextView subtotal) {

          //  product_count.setText(""+(value_no_of_unit+1));
            Item_sub_total = subtotal ;

            String url = "/mobileapi/cart/updatecart?cart_item_id="+item_id+"&qty="+String.valueOf(value_no_of_unit+1) ;

            apiManager.execution_method_get(UPDATECART , url);


        }

        @Override
        public void Minus(int value_no_of_unit, int position, String item_id, TextView product_count, TextView subtotal) {

            if (value_no_of_unit > 1) {
              //  product_count.setText("" + (value_no_of_unit - 1));

                String url = "/mobileapi/cart/updatecart?cart_item_id="+item_id+"&qty="+String.valueOf(value_no_of_unit-1) ;

                apiManager.execution_method_get(UPDATECART , url);

               }
            else{

              //  Toast.makeText(CartActivity.this, "Remove", Toast.LENGTH_SHORT).show();

            }


        }

        @Override
        public void RemoveProductFromCart(String cart_item_id) {

         String url = "/mobileapi/cart/removeCart?cart_item_id="+cart_item_id ;
         apiManager.execution_method_get(REMOVECARTITEM , url);


        }


    };


    public double TotalPrice(ArrayList<Double> subtotal){
        double sum = 0 ;

        for(int i = 0; i < subtotal.size(); i++){
            sum += subtotal.get(i);
        }

        return sum ;
    }





    private void init() {
        contentFrameLayout = (FrameLayout)findViewById(R.id.content_layout);
        apiManager = new ApiManager(CartActivity.this , apifetcher);
        total_items = (TextView)findViewById(R.id.txt_total_items);
        recyclerView = (RecyclerView)findViewById(R.id.reycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        total_amount = (TextView)findViewById(R.id.txt_total_amount);
        checkout = (TextView)findViewById(R.id.txt_checkout);
        ll_price_details = (LinearLayout)findViewById(R.id.ll_price_details);
        pricetxt_withitems = (TextView)findViewById(R.id.price_txt_items_count);
        product_total_price = (TextView)findViewById(R.id.products_total);


        No_Items = (TextView)findViewById(R.id.no_items_cart);
        ll_crosssell = (LinearLayout)findViewById(R.id.ll_crosssell);
        edit_coupon = (EditText)findViewById(R.id.ed_coupon_code);
        coupon_result = (TextView)findViewById(R.id.coupon_applied_not);
        coupon_button = (TextView)findViewById(R.id.txt_coupon_apply);
        coupon_discount_price = (TextView)findViewById(R.id.txt_discount_price);
        coupon_discount_txt = (TextView)findViewById(R.id.txt_discount_coupon);
        ll_coupon_applied = (LinearLayout)findViewById(R.id.coupon_applied_layout);
        subTotalWithoutDiscount = (TextView)findViewById(R.id.txt_subtotalallitems_price);

        setTitle("Cart");


        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_black, null);
                toolbar.setNavigationIcon(d);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        crossSellHolderView = (PlaceHolderView)findViewById(R.id.crosssell_place_holder);
        crossSellHolderView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(CartActivity.this,
                        LinearLayoutManager.HORIZONTAL, false));



    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        cartCount.SetCount(MainActivity.ItemCountOnCart);
        return super.onPrepareOptionsMenu(menu);

    }




}
