package com.wylder.passwordkeep.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.wylder.passwordkeep.R;

/**
 * Created by kevin on 8/9/15.
 *
 * Guides the user through the setup process if not ready and logs them in
 */
public class SetupActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle sis){
        super.onCreate(sis);
        setContentView(R.layout.main);
    }

}
