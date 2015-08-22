package com.wylder.passwordkeep.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by kevin on 8/9/15.
 *
 * Guides the user through the setup process if not ready and logs them in
 */
public class SetupActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle sis){
        super.onCreate(sis);
        Button button = new Button(this);
        button.setText("Enter MainActivity");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        setContentView(button);
    }

}
