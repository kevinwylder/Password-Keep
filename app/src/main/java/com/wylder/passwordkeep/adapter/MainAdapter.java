package com.wylder.passwordkeep.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.algorithm.Algorithm;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.storage.BasePassword;
import com.wylder.passwordkeep.storage.DatabaseContract;
import com.wylder.passwordkeep.storage.DatabaseOperator;

import java.util.ArrayList;

/**
 * Created by kevin on 8/21/15.
 *
 * Adapter to show history cards on the main layout view
 */
public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    ArrayList<String> websites = new ArrayList<>();
    ArrayList<String> passwords = new ArrayList<>();

    DatabaseOperator operator;
    Context ctx;

    public MainAdapter(DatabaseOperator operator, Context ctx) {
        this.operator = operator;
        this.ctx = ctx;
        readDatabase();
    }

    public void readDatabase() {
        if(! operator.databaseReady()) {
            Log.e("KevinRuntime", "Database hasn't loaded by main page access");
            Toast.makeText(ctx, "Couldn't read database", Toast.LENGTH_SHORT).show();
            return;
        }
        websites.clear();
        passwords.clear();
        Cursor cursor = operator.getDatabase().query(
                DatabaseContract.History.TABLE_NAME,
                new String[]{
                        DatabaseContract.History.COLUMN_SITE
                },
                null,
                null,
                null,
                null,
                DatabaseContract.History.COLUMN_SITE + " COLLATE NOCASE ASC;"
        );
        while (cursor.moveToNext()) {
            websites.add(cursor.getString(0));
        }
        cursor.close();
        try {
            Algorithm algorithm = operator.getSelectedAlgorithm();
            for (String website : websites) {
                passwords.add(
                        algorithm.generatePassword(
                                BasePassword.getPassword(),
                                website
                        )
                );
            }
        } catch (EvaluationError | SyntaxError error) {
            Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.history_card,
                viewGroup,
                false
        );
        return new MainViewHolder(view, operator, this);
    }

    /**
     * Called when a view needs to be recycled. Change the viewholder depending on it's position in
     * the recyclerview
     * @param card the view to be edited
     * @param i the position of the view
     */
    @Override
    public void onBindViewHolder(MainViewHolder card, int i) {
        if(isEmpty()) {
            card.setAsEmpty();
        }
        if(i == 0) {
            card.setAsBlank();
            return;
        } else {
            i--;
        }
        card.setPassword(passwords.get(i));
        String name = websites.get(i);
        card.setName(name);
        if (i > 0) {
            char above = websites.get(i - 1).toUpperCase().charAt(0);
            char below = name.toUpperCase().charAt(0);
            if(above != below) {
                card.setDivider(Character.toString(below));
            } else {
                card.setDivider("");
            }
        } else {
            card.setDivider(name.toUpperCase().substring(0, 1));
        }
    }

    @Override
    public int getItemCount() {
        return websites.size() + 1;
    }

    public int getClosestIndex (String typed) {
        int i = 0;
        while (i < websites.size() && websites.get(i).toLowerCase().compareTo(typed) < 0) {
            i++;
        }
        return i;
    }

    public void addWebsite (String name) {
        operator.addWebsite(name);
        readDatabase();
    }

    public boolean isEmpty() {
        return websites.isEmpty();
    }
}
