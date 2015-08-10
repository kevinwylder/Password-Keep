package com.wylder.passwordkeep.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by kevin on 8/9/15.
 *
 * Adapter to show the setup steps fragments
 */
public class SetupPagerAdapter extends FragmentPagerAdapter {

    public SetupPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
