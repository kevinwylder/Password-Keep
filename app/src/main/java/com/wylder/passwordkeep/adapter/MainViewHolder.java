package com.wylder.passwordkeep.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.activity.MainActivity;
import com.wylder.passwordkeep.storage.DatabaseOperator;

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

    String name = null;
    MainAdapter adapter;
    DatabaseOperator operator;

    public MainViewHolder(View v, DatabaseOperator operator, MainAdapter adapter){
        super(v);
        root = (RelativeLayout) v;
        site = (TextView) root.findViewById(R.id.siteName);
        password = (TextView) root.findViewById(R.id.sitePassword);
        letter = (TextView) root.findViewById(R.id.letter);
        this.operator = operator;
        this.adapter = adapter;
        root.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDeleteDialog();
                return true;
            }
        });
    }

    public void setAsEmpty() {
        setName("No Website History");
        name = null;
    }

    public void setAsBlank() {
        root.setMinimumHeight(MainActivity.typeCardSize);
        root.setVisibility(View.INVISIBLE);
        name = null;
    }

    public void setName(String name){
        this.name = name;
        root.setVisibility(View.VISIBLE);
        root.setMinimumHeight(0);
        site.setText(name);
    }

    public void setPassword(String value) {
        password.setText(value);
    }

    public void setDivider(String character) {
        letter.setText(character);
    }

    private void showDeleteDialog() {
        if(name == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(letter.getContext());
        builder.setTitle("Delete " + name + "?");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                operator.deleteFromHistory(name);
                adapter.readDatabase();
                adapter.notifyDataSetChanged();
            }
        });
        builder.create().show();
    }

}
