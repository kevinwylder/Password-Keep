package com.wylder.passwordkeep.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.adapter.MainAdapter;

/**
 * Created by kevin on 8/9/15.
 *
 * Activity showing the website history and password evaluation user interface
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle sis){
        super.onCreate(sis);
        setContentView(R.layout.main);

        RecyclerView scroller = (RecyclerView) findViewById(R.id.recyclerView);
        scroller.setHasFixedSize(true);
        scroller.setLayoutManager(new LinearLayoutManager(this));
        scroller.setAdapter(new MainAdapter());


    }

}
