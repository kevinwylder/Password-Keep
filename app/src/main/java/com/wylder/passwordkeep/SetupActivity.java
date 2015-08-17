package com.wylder.passwordkeep;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.wylder.passwordkeep.algorithm.Algorithm;
import com.wylder.passwordkeep.algorithm.AlgorithmFactory;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.SyntaxError;

/**
 * Created by kevin on 8/9/15.
 *
 * Guides the user through the setup process if not ready
 */
public class SetupActivity extends Activity{

    @Override
    protected void onCreate(Bundle sis){
        super.onCreate(sis);
        AlgorithmFactory factory = new AlgorithmFactory();
        TextView tv = new TextView(this);
        setContentView(tv);
        try {
            Algorithm test1 = factory.buildAlgorithm("1ac8f24");
            tv.setText(test1.generatePassword("password", "facebook"));
        } catch (SyntaxError error) {
            tv.setText("Syntax error: " + error.getMessage());
            error.printStackTrace();
        }  catch (EvaluationError error) {
            tv.setText("Eval error: " + error.getMessage());
        }
    }

}
