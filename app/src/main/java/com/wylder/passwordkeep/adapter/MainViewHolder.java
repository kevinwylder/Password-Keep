package com.wylder.passwordkeep.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wylder.passwordkeep.R;

/**
 * Created by kevin on 8/21/15.
 *
 * Class to hold a small layout for the RecyclerView in MainActivity
 */
public class MainViewHolder extends RecyclerView.ViewHolder {

    CardView root;
    TextView site;
    TextView password;

    public MainViewHolder(View v){
        super(v);
        root = (CardView) v;
        site = (TextView) root.findViewById(R.id.siteName);
        password = (TextView) root.findViewById(R.id.sitePassword);
    }

    /**
     * dummy to fill this view with stuff before the adapter is done
     */
    public void randomize(int i){
        site.setText("Website number " + i);
        password.setText("password" + i);
    }

}
