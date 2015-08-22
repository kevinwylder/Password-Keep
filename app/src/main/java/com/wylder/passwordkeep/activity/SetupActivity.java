package com.wylder.passwordkeep.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.storage.BasePassword;

/**
 * Created by kevin on 8/9/15.
 *
 * Guides the user through the setup process if not ready and logs them in
 */
public class SetupActivity extends FragmentActivity{

    BasePassword password;

    EditText passwordField;
    Toast toast = null;

    @Override
    protected void onCreate(Bundle sis){
        super.onCreate(sis);
        password = new BasePassword(this);
        if(password.passwordNotSet()){
            // TODO: Show ViewPager for setup
            password.setPassword("password");
        }
        initLogin();
    }

    /**
     * setup the activity to show a login prompt
     */
    private void initLogin(){
        setContentView(R.layout.enter_password);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
        passwordField = (EditText) findViewById(R.id.editText);
        passwordField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP){
                    checkLogin();
                    return true;
                }
                return false;
            }
        });
    }

    private void checkLogin(){
        if(toast != null) {
            toast.cancel();
        }
        if(password.checkPassword(passwordField.getText().toString())) {
            Intent intent = new Intent(SetupActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            passwordField.requestFocus();
            toast = Toast.makeText(SetupActivity.this, "Incorrect Password", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * setup the activity to guide the user through setup
     */

}
