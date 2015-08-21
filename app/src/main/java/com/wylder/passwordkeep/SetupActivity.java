package com.wylder.passwordkeep;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by kevin on 8/9/15.
 *
 * Guides the user through the setup process if not ready
 */
public class SetupActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle sis){
        super.onCreate(sis);
        setContentView(R.layout.build_algorithm);
    }

}
