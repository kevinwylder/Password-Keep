package com.wylder.passwordkeep.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.adapter.AlgorithmAdapter;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.ide.BuildAlgorithmActivity;
import com.wylder.passwordkeep.storage.DatabaseOperator;


/**
 * Created by kevin on 8/21/15.
 *
 * Fragment to show a list of algorithms saved in the database
 */
public class AlgorithmFragment extends Fragment implements View.OnClickListener {

    DatabaseOperator operator;
    AlgorithmAdapter adapter;

    public static AlgorithmFragment newInstance(DatabaseOperator operator){
        AlgorithmFragment fragment = new AlgorithmFragment();
        fragment.operator = operator;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sis) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.algorithm_fragment, container, false);

        // setup recyclerview
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        adapter = new AlgorithmAdapter(operator);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // add onclick listeners to the action buttons
        root.findViewById(R.id.button7).setOnClickListener(this);
        root.findViewById(R.id.button8).setOnClickListener(this);

        // disable cancel button if there is no selected algorithm
        if (adapter.getSelected() == null) {
            root.findViewById(R.id.button8).setEnabled(false);
        }

        // add button will start the IDE for a result
        root.findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BuildAlgorithmActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button7) {
            // remove the previous selected algorithm in the database
            try {
                String code = adapter.getSelected().getHex();
                operator.setSelected(code);
            } catch (SyntaxError error) {
                Log.e("KevinRuntime", "error generating hex code when setting selected: " + error.getMessage());
                return; // do not finish the activity
            }
        }
        getActivity().finish();
    }

}
