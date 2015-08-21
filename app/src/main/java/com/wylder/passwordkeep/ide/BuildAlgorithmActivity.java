package com.wylder.passwordkeep.ide;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.algorithm.Algorithm;
import com.wylder.passwordkeep.algorithm.SyntaxError;

/**
 * Created by kevin on 8/17/15.
 *
 * A fragment to show a UI to create an algorithm
 */
public class BuildAlgorithmActivity extends Activity {

    AlgorithmView algorithmView;
    EditText name;
    EditText testSite;
    TextView evalOutput;
    TextView syntaxOutput;
    Button submit;

    @Override
    public void onCreate(Bundle sis) {
        super.onCreate(sis);
        setContentView(R.layout.build_algorithm);

        algorithmView = (AlgorithmView) findViewById(R.id.algorithmView);
        name = (EditText) findViewById(R.id.editText2);
        testSite = (EditText) findViewById(R.id.editText3);
        evalOutput = (TextView) findViewById(R.id.textView7);
        syntaxOutput = (TextView) findViewById(R.id.textView9);
        submit = (Button) findViewById(R.id.button2);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String hexCode = algorithmView.getAlgorithm().getHex();
                    // TODO: add this string to StorageDatabase
                    finish();
                } catch (SyntaxError error) {
                    syntaxOutput.setText(error.getMessage());
                }
            }
        });

    }

}
