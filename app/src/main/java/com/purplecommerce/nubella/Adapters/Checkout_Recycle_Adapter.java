package com.purplecommerce.nubella.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.purplecommerce.nubella.R;

import java.util.ArrayList;

/**
 * Created by apporioinfolabs on 11-11-2016.
 */
public class Checkout_Recycle_Adapter extends RecyclerView.Adapter<Checkout_Recycle_Adapter.MyViewHolder> {


    Context ctc ;


    private final static int FADE_DURATION = 1000 ;// in milliseconds


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, name, msg;
        //public CircularImageView pic ;
        LinearLayout recycle_layout ;

        public MyViewHolder(View view) {
            super(view);
//            date = (TextView) view.findViewById(R.id.date);
//            name = (TextView) view.findViewById(R.id.msg_name);
//            msg = (TextView) view.findViewById(R.id.chat_msg);
//          //  pic = (CircularImageView) view .findViewById(R.id.pic_chat);
   //       recycle_layout = (LinearLayout) view .findViewById(R.id.noti_recycle_item);




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
    public void onBindViewHolder(MyViewHolder holder, final int position) {

       // holder.date.setText(ndate.get(position));
      //  holder.name.setText(msgs.get(position));
      //  holder.msg.setText(msgs.get(position));


//        Glide
//                .with(ctc)
//                .load(AppConstants.IMAGE_URL + msgpic.get(position))
//                .fitCenter()
//                .placeholder(R.drawable.progress_animation)
//                .crossFade()
//                .error(R.drawable.no_profile)
//                .into( holder.pic);


       // setFadeAnimation(holder.itemView);

//        holder.recycle_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                String view_Home_url = com.apporioinfolabs.editprofilemodule.AppConstants.BASE_URL+"add_my_connection.php?user_id="+ MainActivity.user_id+"&connect_id=" + idd.get(position) ;
////                ExecuteApi.Parse(ctc , "AddConnection" , view_Home_url  , Api_listener );
//
//            }
//        });

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



}
