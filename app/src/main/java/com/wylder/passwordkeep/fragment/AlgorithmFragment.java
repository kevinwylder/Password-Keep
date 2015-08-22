package com.wylder.passwordkeep.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wylder.passwordkeep.R;

/**
 * Created by kevin on 8/21/15.
 */
public class AlgorithmFragment extends Fragment {

    public static AlgorithmFragment newInstance(){
        return new AlgorithmFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sis) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.password_enter, container, false);
        return root;
    }

}
