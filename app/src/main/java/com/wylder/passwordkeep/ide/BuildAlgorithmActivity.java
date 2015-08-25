package com.wylder.passwordkeep.ide;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.algorithm.Algorithm;
import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.storage.BasePassword;
import com.wylder.passwordkeep.storage.DatabaseOperator;

/**
 * Created by kevin on 8/17/15.
 *
 * A fragment to show a UI to create an algorithm
 */
public class BuildAlgorithmActivity extends Activity {

    DatabaseOperator databaseOperator;

    AlgorithmView algorithmView;
    EditText name;
    EditText testSite;
    TextView evalOutput;
    TextView syntaxOutput;
    Button submit;
    Button addAction;

    @Override
    public void onCreate(Bundle sis) {
        super.onCreate(sis);
        setContentView(R.layout.algorithm_build);
        databaseOperator = new DatabaseOperator(this);

        // setup actionbar
        ActionBar actionBar = getActionBar();
        name = new EditText(this);
        name.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        name.setHint("Algorithm Name");
        actionBar.setCustomView(name);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        // find and setup views
        algorithmView = (AlgorithmView) findViewById(R.id.algorithmView);
        testSite = (EditText) findViewById(R.id.editText3);
        evalOutput = (TextView) findViewById(R.id.textView7);
        syntaxOutput = (TextView) findViewById(R.id.textView9);
        submit = (Button) findViewById(R.id.button2);
        addAction = (Button) findViewById(R.id.button3);

        testSite.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    Algorithm algorithm = algorithmView.getAlgorithm();
                    String output = algorithm.generatePassword(BasePassword.getPassword(), s.toString());
                    evalOutput.setText(output);
                } catch (EvaluationError | SyntaxError evaluationError) {
                    evalOutput.setText(evaluationError.getMessage());
                }
            }
        });

        algorithmView.setTreeChangedListener(new AlgorithmView.OnTreeChanged() {
            @Override
            public void onTreeChanged() {
                try {
                    Algorithm algorithm = algorithmView.getAlgorithm();
                    syntaxOutput.setText("Hex code: " + algorithm.getHex());
                    if (testSite.getText().length() > 0) {
                        evalOutput.setText(algorithm.generatePassword(BasePassword.getPassword(), testSite.getText().toString()));
                    }
                    submit.setEnabled(true);
                } catch (SyntaxError error) {
                    syntaxOutput.setText(error.getMessage());
                    evalOutput.setText("");
                    submit.setEnabled(false);
                } catch (EvaluationError error) {
                    evalOutput.setText(error.getMessage());
                    submit.setEnabled(false);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().length() == 0){
                    name.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.showSoftInput(name, InputMethodManager.SHOW_IMPLICIT);
                    return;
                }
                try {
                    String hexCode = algorithmView.getAlgorithm().getHex();
                    String algName = name.getText().toString();
                    if(databaseOperator.databaseReady()) {
                        databaseOperator.addAlgorithm(algName, hexCode);
                        finish();
                    }
                } catch (SyntaxError error) {
                    syntaxOutput.setText(error.getMessage());
                }
            }
        });

        addAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                algorithmView.actions.add(new TokenBox(DataType.ACTION));
                algorithmView.treeChanged();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.ide, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_documenatation:
                //
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
