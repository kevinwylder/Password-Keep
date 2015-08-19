package com.wylder.passwordkeep.ide;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.wylder.passwordkeep.algorithm.A;
import com.wylder.passwordkeep.algorithm.Algorithm;

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
    ArrayList<TokenBox> boxes = new ArrayList<>();

    SurfaceHolder holder;

    /**
     * Constructor to create an algorithm starting from nothing
     * @param context
     * @param attributeSet
     */
    public AlgorithmView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setOnTouchListener(this);
        init();
    }

    /**
     * Constructor to view an algorithm
     * @param ctx
     * @param algorithm
     */
    public AlgorithmView(Context ctx, Algorithm algorithm){
        super(ctx);
        A[] actions = algorithm.getActions();
        actions[0].get
        init();
    }

    /**
     * helper method to initialize variables
     */
    private void init(){
        holder = this.getHolder();
        ONE_DIP = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        padding = ONE_DIP * 7;
    }

    /**
     * Method called when onTouch has determined a cell has been clicked
     * @param row
     * @param column
     */
    private void onTokenClick(int row, int column){

    }

    private void findRowsAndColumns(){

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
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

}
