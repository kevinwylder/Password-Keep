package com.wylder.passwordkeep.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.fragment.AlgorithmFragment;
import com.wylder.passwordkeep.fragment.LoginFragment;
import com.wylder.passwordkeep.fragment.PasswordFragment;
import com.wylder.passwordkeep.storage.BasePassword;
import com.wylder.passwordkeep.storage.DatabaseOperator;

/**
 * Created by kevin on 8/9/15.
 *
 * Guides the user through the setup process if not ready and logs them in
 */
public class SetupActivity extends Activity implements PasswordFragment.OnPasswordSelect {

    BasePassword password;
    DatabaseOperator operator;

    @Override
    protected void onCreate(Bundle sis) {
        super.onCreate(sis);
        operator = new DatabaseOperator(this);
        password = new BasePassword(this);
        setContentView(R.layout.setup_login);
        // show a different screen depending on what needs to be set up
        if(password.passwordNotSet()){
            getActionBar().setTitle("Welcome to Password Keep");
            PasswordFragment fragment = PasswordFragment.newInstance();
            fragment.setOnPasswordSelect(this);
            setViewFragment(fragment);
        } else if (operator.getSelectedAlgorithm() == null) {
            getActionBar().setTitle("Select an Algorithm");
            AlgorithmFragment fragment = AlgorithmFragment.newInstance(operator);
            setViewFragment(fragment);
        } else {
            LoginFragment fragment = LoginFragment.newInstance();
            setViewFragment(fragment);
        }
    }

    /**
     * set the main view of this activity to the given fragment
     * @param fragment the fragment to show
     */
    private void setViewFragment(Fragment fragment) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    /**
     * called if there is a PasswordFragment on screen ready to be turned into an AlgorithmFragment
     */
    @Override
    public void passwordSelected() {
        AlgorithmFragment fragment = AlgorithmFragment.newInstance(operator);
        FragmentManager manager = getFragmentManager();
        manager.popBackStack();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left);
        transaction.replace(R.id.container, fragment);
        transaction.commit();
        getActionBar().setTitle("Select an Algorithm");
    }
}
