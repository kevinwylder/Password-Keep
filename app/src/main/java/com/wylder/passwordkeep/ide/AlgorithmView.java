package com.wylder.passwordkeep.ide;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by kevin on 8/17/15.
 *
 * A view that shows a bunch of cards in a tree pattern to show the user
 */
public class AlgorithmView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    int width;
    int height;
    float ONE_DIP;
    float padding;
    float rows = 1;
    float columns = 1;

    ArrayList<TokenBox> actions = new ArrayList<>();

    SurfaceHolder holder;

    public AlgorithmView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public AlgorithmView(Context ctx){
        super(ctx);
        init();
    }

    /**
     * helper method to initialize variables and set listeners
     */
    private void init(){
        holder = this.getHolder();
        this.setOnTouchListener(this);
        ONE_DIP = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        padding = ONE_DIP * 7;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int pixelFormat, int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    private void animateBox(boolean row, boolean column){

    }
}
