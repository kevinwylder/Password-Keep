package com.wylder.passwordkeep.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.algorithm.Algorithm;

/**
 * Created by kevin on 8/22/15.
 */
public class AlgorithmHolder extends RecyclerView.ViewHolder {

    RadioButton checked;
    TextView title;
    TextView description;

    Algorithm represent;

    public AlgorithmHolder(View itemView, final HolderActions listener) {
        super(itemView);
        checked = (RadioButton) itemView.findViewById(R.id.radioButton);
        title = (TextView) itemView.findViewById(R.id.name);
        description = (TextView) itemView.findViewById(R.id.description);
        checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSelect(getPosition());
            }
        });
    }

    public void setAlgorithm(Algorithm algorithm) {
        represent = algorithm;
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setChecked(boolean checked){
        this.checked.setChecked(checked);
    }

    interface HolderActions {
        void onSelect(int position);
        void onDeleteSelf(Algorithm self);
        void onViewSelf(Algorithm self);
    }

}
