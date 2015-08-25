package com.wylder.passwordkeep.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.adapter.MainAdapter;
import com.wylder.passwordkeep.algorithm.Algorithm;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.storage.BasePassword;
import com.wylder.passwordkeep.storage.DatabaseOperator;

/**
 * Created by kevin on 8/9/15.
 *
 * Activity showing the website history and password evaluation user interface
 */
public class MainActivity extends Activity {

    public static int typeCardSize;

    TextView passwordOutput;
    EditText websiteInput;
    RecyclerView scroller;
    LinearLayoutManager scrollingManager;
    MainAdapter adapter;
    Algorithm selectedAlgorithm;
    DatabaseOperator operator;

    @Override
    protected void onCreate(Bundle sis){
        super.onCreate(sis);
        setContentView(R.layout.main);

        typeCardSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());

        scroller = (RecyclerView) findViewById(R.id.recyclerView);
        scrollingManager = new LinearLayoutManager(this);
        scroller.setLayoutManager(scrollingManager);
        operator = new DatabaseOperator(this, new DatabaseOperator.OnDatabaseReady() {
            @Override
            public void databaseReady(SQLiteDatabase database) {
                selectedAlgorithm = operator.getSelectedAlgorithm();
                adapter = new MainAdapter(operator, MainActivity.this);
                scroller.setAdapter(adapter);
            }
        });

        passwordOutput = (TextView) findViewById(R.id.textView8);
        passwordOutput.setText(BasePassword.getPassword());

        websiteInput = (EditText) findViewById(R.id.editText4);
        websiteInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0 && !adapter.isEmpty()) {
                    String website = websiteInput.getText().toString();
                    int index = adapter.getClosestIndex(website);
                    scrollingManager.scrollToPositionWithOffset(index, 20);
                    try {
                        passwordOutput.setText(selectedAlgorithm.generatePassword(BasePassword.getPassword(), website));
                    } catch (SyntaxError | EvaluationError error) {
                        passwordOutput.setText(error.getMessage());
                    }
                }
            }
        });
        websiteInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER &&
                        keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                        websiteInput.getText().length() != 0) {
                    adapter.addWebsite(websiteInput.getText().toString());
                    adapter.notifyDataSetChanged();
                    websiteInput.setText("");
                    passwordOutput.setText(BasePassword.getPassword());
                }
                return false;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_history:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("Clear History");
                builder.setMessage("Do you want to clear your website history? (this cannot be undone)");
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("Clear Histroy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        operator.clearHistory();
                        adapter.readDatabase();
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.create().show();
                return true;
            default:
                Intent intent = new Intent(this, EditActivity.class);
                intent.putExtra(EditActivity.EXTRA_EDIT, item.getItemId());
                startActivity(intent);
                return super.onOptionsItemSelected(item);
        }
    }

}
