package com.wylder.passwordkeep.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.algorithm.Algorithm;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.ide.ViewAlgorithmActivity;

/**
 * Created by kevin on 8/22/15.
 */
public class AlgorithmHolder extends RecyclerView.ViewHolder {

    ImageView expandButton;
    RadioButton checked;
    TextView title;
    TextView description;

    HolderActions listener;
    Algorithm represent;
    String name;

    public AlgorithmHolder(View itemView, HolderActions callback) {
        super(itemView);
        listener = callback;
        checked = (RadioButton) itemView.findViewById(R.id.radioButton);
        title = (TextView) itemView.findViewById(R.id.name);
        description = (TextView) itemView.findViewById(R.id.description);
        expandButton = (ImageView) itemView.findViewById(R.id.imageView);

        View.OnClickListener select = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSelect(getPosition(), checked);
                setChecked(true);
            }
        };

        View.OnClickListener detail = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onViewSelf(represent);
                try {
                    Intent intent = new Intent(view.getContext(), ViewAlgorithmActivity.class);
                    intent.putExtra(ViewAlgorithmActivity.HEX_CODE, represent.getHex());
                    view.getContext().startActivity(intent);
                } catch (SyntaxError error) {
                    Toast.makeText(view.getContext(), "Cannot view algorithm", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }
        };

        itemView.setOnClickListener(select);
        checked.setOnClickListener(select);
        title.setOnClickListener(detail);
        expandButton.setOnClickListener(detail);

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDeleteDialog(view.getContext());
                return false;
            }
        });
    }

    private void showDeleteDialog(Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Delete Algorithm");
        builder.setMessage("Are you sure you want to delete " + name + "?");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onDeleteSelf(represent);
            }
        });
        builder.create().show();
    }

    public void setAlgorithm(Algorithm algorithm) {
        represent = algorithm;
        description.setText(represent.getDescription());
    }

    public void setTitle(String title){
        this.name = title;
        this.title.setText(name);
    }

    public void setChecked(boolean checked){
        this.checked.setChecked(checked);
    }

    interface HolderActions {
        void onSelect(int position, RadioButton checked);
        void onDeleteSelf(Algorithm self);
        void onViewSelf(Algorithm self);
    }

}
