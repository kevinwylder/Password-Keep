package com.wylder.passwordkeep.ide;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wylder.passwordkeep.R;

/**
 * Created by kevin on 8/17/15.
 *
 * A fragment to show a UI to create an algorithm
 */
public class BuildAlgorithmFragment extends Fragment {

    EditText name;
    EditText testSite;
    TextView evalOutput;
    TextView syntaxOutput;
    Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sis) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.build_algorithm, container, false);
        name = (EditText) root.findViewById(R.id.editText2);
        testSite = (EditText) root.findViewById(R.id.editText3);
        evalOutput = (TextView) root.findViewById(R.id.textView7);
        syntaxOutput = (TextView) root.findViewById(R.id.textView9);
        return root;
    }

}
