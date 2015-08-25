package com.wylder.passwordkeep.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.algorithm.Algorithm;
import com.wylder.passwordkeep.algorithm.AlgorithmFactory;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.storage.DatabaseContract;
import com.wylder.passwordkeep.storage.DatabaseOperator;

import java.util.ArrayList;

/**
 * Created by kevin on 8/22/15.
 *
 * adapter for the database to show algorithms and their descriptions
 */
public class AlgorithmAdapter extends RecyclerView.Adapter<AlgorithmHolder> implements AlgorithmHolder.HolderActions {

    DatabaseOperator operator;
    ArrayList<Algorithm> algorithms = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();

    private int position = -1;
    private RadioButton selected = null;

    public AlgorithmAdapter(DatabaseOperator operator){
        this.operator = operator;
        loadFromSource();
    }

    /**
     * read the databaseoperator and fill the lists
     */
    public void loadFromSource() {
        algorithms.clear();
        names.clear();
        Cursor cursor = operator.getDatabase().query(
                DatabaseContract.Algorithms.TABLE_NAME,                 // table name
                new String[]{                                           // columns
                        DatabaseContract.Algorithms.COLUMN_HEX,
                        DatabaseContract.Algorithms.COLUMN_NAME,
                        DatabaseContract.Algorithms.COLUMN_SELECTED
                },
                null,                                                   // what to match
                null,                                                   // group by
                null,                                                   // having
                null,
                // order by
                DatabaseContract.Algorithms.COLUMN_SELECTED + " DESC , "
                        + DatabaseContract.Algorithms.COLUMN_CREATED + " DESC, "
                        + DatabaseContract.Algorithms.COLUMN_NAME + " ASC" ,
                null    // limit
        );
        while(cursor.moveToNext()){
            String name = cursor.getString(1);
            String code = cursor.getString(0);
            boolean checked = (cursor.getInt(2) == 1);
            addAlgorithm(name, code, checked);
        }
    }

    /**
     * add another algorithm to the end of this recycleriew. If the algorithm doesn't compile it is
     * not added. be sure to call notifyDatasetChanged();
     */
    public void addAlgorithm(String title, String code, boolean checked) {
        AlgorithmFactory factory = new AlgorithmFactory();
        try{
            Algorithm algorithm = factory.buildAlgorithm(code);
            algorithms.add(algorithm);
            if (checked && position == -1) {
                position = names.size();
            }
            names.add(title);
        } catch (SyntaxError error) {
            Log.e("KevinRuntime", "index " + position + " SyntaxError: " + error.getMessage());
        }
    }

    @Override
    public AlgorithmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.algorithm_card,
                parent,
                false
        );
        return new AlgorithmHolder(view, this);
    }

    @Override
    public void onBindViewHolder(AlgorithmHolder viewHolder, int position) {
        viewHolder.setChecked(position == this.position);
        viewHolder.setTitle(names.get(position));
        viewHolder.setAlgorithm(algorithms.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    /**
     * gets algorithm of the selected card, or null if none is selected
     */
    public Algorithm getSelected(){
        if(position == -1){
            return null;
        } else {
            return algorithms.get(position);
        }
    }

    /**
     * called when the radiobutton is clicked
     * @param position
     */
    @Override
    public void onSelect(int position, RadioButton selected) {
        this.position = position;
        Log.e("KevinRuntime", "Selecting");
        if (this.selected != null) {
            this.selected.setChecked(false);
        }
        this.selected = selected;
    }

    /**
     * called when there was a long click, then the user pressed delete on the little dialog popup thing
     * @param self
     */
    @Override
    public void onDeleteSelf(Algorithm self) {
        Log.e("KevinRuntime", "Deleting");
    }

    /**
     * when the card is clicked
     * @param self
     */
    @Override
    public void onViewSelf(Algorithm self) {

        Log.e("KevinRuntime", "Viewing");
    }
}
