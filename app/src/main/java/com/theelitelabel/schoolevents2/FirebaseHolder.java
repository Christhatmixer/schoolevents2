package com.theelitelabel.schoolevents2;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by cfarl_000 on 8/17/2016.
 */
public class FirebaseHolder extends RecyclerView.ViewHolder {
    View mView;
    //hgfhg

    public FirebaseHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ImageButton uparrow = (ImageButton) mView.findViewById(R.id.uparrow);
        CardView card = (CardView) mView.findViewById(R.id.person1_card);
        ImageView pic = (ImageView)mView.findViewById(R.id.layout_background);
    }

    public void setName(String name) {
        TextView field = (TextView) mView.findViewById(R.id.name);
        field.setText(name);
    }

    public void setVotes(int votes) {
        TextView field = (TextView) mView.findViewById(R.id.votes);

        field.setText(String.valueOf(votes));
    }
    public void setAdress(String address){
        TextView field = (TextView)mView.findViewById(R.id.address);
        field.setText(address);
    }
    public void setTime(String time){
        TextView field = (TextView)mView.findViewById(R.id.time);
        field.setText(time);
    }
    public void setDescription(String description){
        TextView field = (TextView)mView.findViewById(R.id.description);
        field.setText(description);
    }
    public void setCategory(String category){
        TextView field = (TextView)mView.findViewById(R.id.category);
        field.setText(category);
    }
    void setPicture(final String imageURL, final Context context) {
        final ImageView postImage = (ImageView) mView.findViewById(R.id.layout_background);
        if (BuildConfig.DEBUG) {
            Picasso.with(context).setIndicatorsEnabled(true);
        }
        Picasso.with(context)
                .load(imageURL)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.ic_menu_camera)
                .fit().centerCrop().into(postImage, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
                Picasso.with(context)
                        .load(imageURL)
                        .placeholder(R.drawable.ksuyellowwhite)
                        .fit().centerCrop().into(postImage);
            }
        });
    }
    /*public void setPicture(ImageView picture){
        ImageView field = (ImageView)mView.findViewById(R.id.layout_background);
        field.setImageResource(picture);
    }*/

}
