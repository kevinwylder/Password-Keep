package com.wylder.passwordkeep.ide;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.algorithm.Algorithm;
import com.wylder.passwordkeep.algorithm.AlgorithmFactory;
import com.wylder.passwordkeep.algorithm.SyntaxError;

/**
 * Created by kevin on 8/21/15.
 *
 * Activity meant to explain an arbitrary Algorithm object. This will accept parameters via intent
 * string extra
 */
public class ViewAlgorithmActivity extends Activity {

    public static final String HEX_CODE = "hex";

    private TextView codeText;
    private TextView explainText;
    private TextView binaryText;

    @Override
    protected void onCreate(Bundle sis){
        super.onCreate(sis);

        // first construct the Algorithm and quit if anything is wrong
        String hex = getIntent().getStringExtra(HEX_CODE);
        if(hex == null) {
            Log.e("KevinRuntime", "no hex given to ViewAlgorithmActivity");
            finish();
            return;
        }
        AlgorithmFactory factory = new AlgorithmFactory();
        Algorithm algorithm = null;
        try {
            algorithm = factory.buildAlgorithm(hex);
        } catch (SyntaxError error) {
            Log.e("KevinRuntime", "syntax error in code given to ViewAlgorithmActivity: " + error.getMessage());
        }

        setContentView(R.layout.algorithm_view);

        codeText = (TextView) findViewById(R.id.code);
        codeText.setText("Algorithm hex code: " + hex);
        binaryText = (TextView) findViewById(R.id.binary);
        binaryText.setText("Algorithm binary code: " + Integer.toBinaryString(Integer.parseInt(hex, 16)));
        explainText = (TextView) findViewById(R.id.explanation);
        try {
            explainText.setText(algorithm.toString());
            AlgorithmView algorithmView = (AlgorithmView) findViewById(R.id.algorithmView);
            algorithmView.setAlgorithm(algorithm);
        } catch (Exception e){
            Log.e("KevinRuntime", "Exception " + e.getMessage());
            finish();
        }
    }

}
