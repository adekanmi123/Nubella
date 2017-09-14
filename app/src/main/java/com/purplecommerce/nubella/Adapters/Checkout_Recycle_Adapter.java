package com.purplecommerce.nubella.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.purplecommerce.nubella.R;

import java.util.ArrayList;

/**
 * Created by apporioinfolabs on 11-11-2016.
 */
public class Checkout_Recycle_Adapter extends RecyclerView.Adapter<Checkout_Recycle_Adapter.MyViewHolder> {


    Context ctc ;


    private final static int FADE_DURATION = 1000 ;// in milliseconds


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView  name, seller ,actual_price , total_price , cut_off , delivery_details , product_count , delete ;
        public ImageView pic , plus , minus ;
        LinearLayout recycle_layout ;

        public MyViewHolder(View view) {
            super(view);
            seller = (TextView) view.findViewById(R.id.prod_seller);
            name = (TextView) view.findViewById(R.id.prod_name);
            actual_price = (TextView) view.findViewById(R.id.actual_price);
            total_price = (TextView) view.findViewById(R.id.total_price);
            cut_off = (TextView)view.findViewById(R.id.cut_off_precent);
            pic = (ImageView) view .findViewById(R.id.product_img);
            delivery_details = (TextView)view.findViewById(R.id.delivery_details);
            plus = (ImageView)view.findViewById(R.id.plus);
            minus = (ImageView)view.findViewById(R.id.minus);
            product_count = (TextView)view.findViewById(R.id.items_count);
            delete = (TextView)view.findViewById(R.id.remove_product);

        }



    }

    public Checkout_Recycle_Adapter(Context ctc) {
        this.ctc = ctc ;
//        this.msgs = ms;
//        this.nid = id;
//        this.ndate = date;
//        this.ntype = type;
//        this.idd = notification_user_id ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checkout_items_layout, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {



         holder.actual_price.setText(ctc.getString(R.string.Rs_symbol)+"1000");
         holder.name.setText("Timex TW002E101 Analog Watch - For Men");
         holder.seller.setText("Seller : Best Offers");
         holder.total_price.setText(ctc.getString(R.string.Rs_symbol)+"1500");
         holder.cut_off.setText("50 % OFF");
         holder.delivery_details.setText("Delivery : Free (with in 4-7 days)");
         holder.product_count.setText("1");




        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(ctc, R.anim.click_image_anim));
                YoYo.with(Techniques.Pulse)
                        .duration(1000)
                        .playOn(holder.product_count);



                int value_no_of_unit = Integer.parseInt(holder.product_count.getText().toString());

                holder.product_count.setText(""+(value_no_of_unit+1));


            }
        });


        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(AnimationUtils.loadAnimation(ctc, R.anim.click_image_anim));
                YoYo.with(Techniques.Pulse)
                        .duration(1000)
                        .playOn(holder.product_count);

                int value_no_of_unit = Integer.parseInt(holder.product_count.getText().toString());

                if (value_no_of_unit > 1) {
                    holder.product_count.setText("" + (value_no_of_unit - 1));

                    //  dbm.changeExsistingRowintable(productid_arr.get(position), "" + (Integer.parseInt(product_no_of_unit.get(position)) - 1));
                  //  product_no_of_unit.set(position ,""+(value_no_of_unit-1));


//                    dbm.addtocart(product_cat_arr.get(position),productid_arr.get(position), productname_arr.get(position), Specialinstructions,
//                            product_price_arr.get(position),
//                            product_no_of_unit.get(position));
                }
                else{
//                    dbm.removeItemfromDB(productid_arr.get(position));
//                    productid_arr.remove(position);
//                    productname_arr.remove(position);
//                    product_no_of_unit.remove(position);
//                    product_price_arr.remove(position);


                    notifyDataSetChanged();

                }

            }
        });


//        Glide
//                .with(ctc)
//                .load(AppConstants.IMAGE_URL + msgpic.get(position))
//                .fitCenter()
//                .placeholder(R.drawable.progress_animation)
//                .crossFade()
//                .error(R.drawable.no_profile)
//                .into( holder.pic);


       // setFadeAnimation(holder.itemView);





    }
    @Override
    public int getItemCount() {
        return 5;
    }


    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }


//    public Checkout_Recycle_Adapter setupPositiveButton(Checkout_Recycle_Adapter.MyViewHolder holder ,ImageView.OnClickListener listener) {
//
//        this.mPositiveClickListener = listener;
//        return this;
//    }


//    public Checkout_Recycle_Adapter setupPositiveButton(ImageView.OnClickListener listener) {
//
//        this.mPositiveClickListener = listener;
//        return this;
//    }


}
