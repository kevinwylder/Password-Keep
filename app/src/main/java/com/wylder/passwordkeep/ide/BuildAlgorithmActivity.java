package com.wylder.passwordkeep.ide;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.algorithm.Algorithm;
import com.wylder.passwordkeep.algorithm.EvaluationError;
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

        testSite.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    Algorithm algorithm = algorithmView.getAlgorithm();
                    String output = algorithm.generatePassword("password", s.toString());
                    evalOutput.setText(output);
                } catch (EvaluationError evaluationError) {
                    evalOutput.setText(evaluationError.getMessage());
                } catch (SyntaxError error) {
                    evalOutput.setText(error.getMessage());
                }
            }
        });


        algorithmView.setTreeChangedListener(new AlgorithmView.OnTreeChanged() {
            @Override
            public void onTreeChanged() {
                try{
                    Algorithm algorithm = algorithmView.getAlgorithm();
                    syntaxOutput.setText("Hex code: " + algorithm.getHex());
                    submit.setEnabled(true);
                } catch (SyntaxError error) {
                    syntaxOutput.setText(error.getMessage());
                    submit.setEnabled(false);
                }
            }
        });

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
