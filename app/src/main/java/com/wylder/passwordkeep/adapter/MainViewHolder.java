package com.wylder.passwordkeep.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.activity.MainActivity;

/**
 * Created by kevin on 8/21/15.
 *
 * Class to hold a small layout for the RecyclerView in MainActivity
 */
public class MainViewHolder extends RecyclerView.ViewHolder {

    RelativeLayout root;
    TextView site;
    TextView password;
    TextView letter;
    View divider;

    public MainViewHolder(View v){
        super(v);
        root = (RelativeLayout) v;
        site = (TextView) root.findViewById(R.id.siteName);
        password = (TextView) root.findViewById(R.id.sitePassword);
        letter = (TextView) root.findViewById(R.id.letter);
        divider = root.findViewById(R.id.divider);
    }

    public void setAsEmpty() {
        setName("No Website History");
    }

    public void setAsBlank() {
        root.setMinimumHeight(MainActivity.typeCardSize);
        root.setVisibility(View.INVISIBLE);
    }

    public void setName(String name){
        root.setVisibility(View.VISIBLE);
        root.setMinimumHeight(0);
        site.setText(name);
    }

    public void setPassword(String value) {
        password.setText(value);
    }

    public void setDivider(String character) {
        if(character != null) {
            divider.setVisibility(View.VISIBLE);
            letter.setText(character);
        } else {
            divider.setVisibility(View.GONE);
            letter.setText("");
        }
    }

}
