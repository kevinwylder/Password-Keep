package com.wylder.passwordkeep.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.fragment.AlgorithmFragment;
import com.wylder.passwordkeep.fragment.PasswordFragment;
import com.wylder.passwordkeep.storage.DatabaseOperator;

/**
 * Created by kevin on 8/21/15.
 *
 * Activity that edits either the password or selected algorithm. Called from MainActivity only
 */
public class EditActivity extends Activity {

    public static final String EXTRA_EDIT = "editparam";

    Fragment currentFragment;
    DatabaseOperator operator;

    @Override
    public void onCreate(Bundle sis){
        super.onCreate(sis);
        setContentView(R.layout.display_fragment);

        operator = new DatabaseOperator(this);

        if(!getIntent().hasExtra(EXTRA_EDIT)) {
            finish();   // no param, no activity
        }
        if(getIntent().getIntExtra(EXTRA_EDIT, -1) == R.id.menu_password) {
            // show passwordfragment
            setViewFragment(PasswordFragment.newInstance());
        } else {
            // show algorithmfragment
            setViewFragment(AlgorithmFragment.newInstance(operator));
        }
    }

    /**
     * set the main view of this activity to the given fragment
     * @param fragment the fragment to show
     */
    private void setViewFragment(Fragment fragment) {
        currentFragment = fragment;
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

}
