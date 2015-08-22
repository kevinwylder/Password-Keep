package com.wylder.passwordkeep.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wylder.passwordkeep.R;

/**
 * Created by kevin on 8/21/15.
 *
 * Adapter to show history cards on the main layout view
 */
public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    public MainAdapter() {
        // TODO: make this constructor take some data from the database
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.history_card,
                viewGroup,
                false
        );
        return new MainViewHolder(view);
    }

    /**
     * Called when a view needs to be recycled. Change the viewholder depending on it's position in
     * the recyclerview
     * @param card the view to be edited
     * @param i the position of the view
     */
    @Override
    public void onBindViewHolder(MainViewHolder card, int i) {
        card.randomize(i);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
