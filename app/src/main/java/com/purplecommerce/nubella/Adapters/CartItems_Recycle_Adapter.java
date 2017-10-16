package com.purplecommerce.nubella.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.purplecommerce.nubella.POJO_models.CartResponse;
import com.purplecommerce.nubella.R;

import java.util.List;

/**
 * Created by apporioinfolabs on 11-11-2016.
 */
public class CartItems_Recycle_Adapter extends RecyclerView.Adapter<CartItems_Recycle_Adapter.MyViewHolder> {


    Context ctc ;
    List<CartResponse.ModelBean.CartItemsBean> cart_items ;
    String Price_Symbol ;
    CartPlusMinus plusMinus ;
    private final static int FADE_DURATION = 1000 ;// in milliseconds


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView  name, seller ,actual_price , total_price , cut_off , delivery_details , product_count ,
                delete , subtotal;
        public ImageView pic , plus , minus ;
        LinearLayout recycle_layout , custom_options_layout  ;

        public MyViewHolder(View view) {
            super(view);
            seller = (TextView) view.findViewById(R.id.prod_sku);
            name = (TextView) view.findViewById(R.id.prod_name);
            actual_price = (TextView) view.findViewById(R.id.actual_price);
            total_price = (TextView) view.findViewById(R.id.regular_price);
            cut_off = (TextView)view.findViewById(R.id.cut_off_precent);
            pic = (ImageView) view .findViewById(R.id.product_img);
            delivery_details = (TextView)view.findViewById(R.id.delivery_details);
            plus = (ImageView)view.findViewById(R.id.plus);
            minus = (ImageView)view.findViewById(R.id.minus);
            product_count = (TextView)view.findViewById(R.id.items_count);
            delete = (TextView)view.findViewById(R.id.remove_product);
            subtotal = (TextView)view.findViewById(R.id.subtotal_price);
            custom_options_layout = (LinearLayout)view.findViewById(R.id.parent_custom_options);
        }



    }

    public CartItems_Recycle_Adapter(Context ctc, String symbol, List<CartResponse.ModelBean.CartItemsBean> cart_items,
                                     CartPlusMinus plusMinus) {
        this.ctc = ctc ;
        this.cart_items = cart_items;
        this.Price_Symbol = symbol ;
        this.plusMinus = plusMinus ;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checkout_items_layout, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

         holder.actual_price.setText(Price_Symbol + cart_items.get(position).getItem_price());
         holder.subtotal.setVisibility(View.VISIBLE);
         holder.subtotal.setText("SUBTOTAL : "+Price_Symbol+String.valueOf(cart_items.get(position).getItem_price()*cart_items.get(position).getQty()));
         holder.name.setText(cart_items.get(position).getItem_title());
         holder.seller.setText(cart_items.get(position).getSku());
         holder.total_price.setText(ctc.getString(R.string.Rs_symbol)+"1500");
         holder.total_price.setVisibility(View.GONE);
         holder.cut_off.setText("50 % OFF");
         holder.cut_off.setVisibility(View.GONE);
         holder.delivery_details.setText("Delivery : Free (with in 4-7 days)");
         holder.delivery_details.setVisibility(View.GONE);
         holder.product_count.setText(""+cart_items.get(position).getQty());

        Glide.with(ctc)
                .load(cart_items.get(position).getThumbnail_pic_url())
                .placeholder(R.drawable.png_loading)
                .error(R.drawable.no_image)
                .into(holder.pic);

         for (int i = 0 ; i < cart_items.get(position).getCustom_option().size() ; i++){

             holder.custom_options_layout.addView(AddCustomOptions(cart_items.get(position).getCustom_option().get(i)));

         }


        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(ctc, R.anim.click_image_anim));
                YoYo.with(Techniques.Pulse)
                        .duration(1000)
                        .playOn(holder.product_count);

                int value_no_of_unit = Integer.parseInt(holder.product_count.getText().toString());

                plusMinus.Plus(value_no_of_unit , position ,
                        cart_items.get(position).getItem_id() , holder.product_count , holder.subtotal);


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

                plusMinus.Minus(value_no_of_unit , position ,cart_items.get(position).getItem_id(),
                        holder.product_count , holder.subtotal);



            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusMinus.RemoveProductFromCart(cart_items.get(position).getCart_item_id());
            }
        });



    }
    @Override
    public int getItemCount() {
        return cart_items.size();
    }

    public View AddCustomOptions(CartResponse.ModelBean.CartItemsBean.CustomOptionBean customOptionBean) {

        LayoutInflater inflater = (LayoutInflater) ctc.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.custom_options_layout, null);


        TextView optionName = (TextView) myView.findViewById(R.id.custom_option_name);
        TextView optionValue = (TextView) myView.findViewById(R.id.custom_option_value);

        optionName.setText(customOptionBean.getLabel()+" : ");
        optionValue.setText(customOptionBean.getValue());


        return myView ;

    }



    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }


    public interface CartPlusMinus {

        void Plus(int value_no_of_unit, int position, String item_id, TextView product_count, TextView subtotal);

        void Minus(int value_no_of_unit, int position, String item_id, TextView product_count, TextView subtotal);

        void RemoveProductFromCart(String cart_item_id);

    }


}
