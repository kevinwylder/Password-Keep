package com.wylder.passwordkeep.ide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.wylder.passwordkeep.algorithm.A;
import com.wylder.passwordkeep.algorithm.Algorithm;
import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.algorithm.Token;

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

    /**
     * Constructor to create an algorithm starting from nothing
     * @param context
     * @param attributeSet
     */
    public AlgorithmView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        holder = this.getHolder();
        holder.addCallback(this);
        this.setOnTouchListener(this);

        ONE_DIP = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        padding = ONE_DIP * 7;

        actions.add(new TokenBox(DataType.ACTION));
    }

    /**
     * construct this AlgorithmView into an Algorithm
     * @return the constructed Algorithm
     * @throws SyntaxError if the algorithm is incomplete or imperfect
     */
    public Algorithm getAlgorithm() throws SyntaxError {
        Algorithm algorithm = new Algorithm();
        for(TokenBox action : actions) {
            Token token = action.getToken();
            if(token instanceof A){
                algorithm.addAction((A) token);
            } else {
                throw new SyntaxError("root tokens must be actions");
            }
        }
        return algorithm;
    }

    /**
     * Interface to be called when this AlgorithmView's tree changes
     */
    protected interface OnTreeChanged {
        void onTreeChanged();
    }

    private OnTreeChanged treeChangedListener = null;

    /**
     * Method to set a listener that will be called in the event this tree changes
     * @param listener
     */
    protected void setTreeChangedListener(OnTreeChanged listener){
        treeChangedListener = listener;
    }

    /**
     * Method that must be called when the tree changes. It does 3 things<br/>
     * 1. sets the rows and columns of each box in the tree and the whole view<br/>
     * 2. draws the tree to the screen<br/>
     * 3. calls the OnTreeChanged listener if necessaryT
     */
    protected void treeChanged(){
        // set rows and columns
        int cumulative = 0;
        int maxCol = 0;
        for (int i = 0; i < actions.size(); i++) {
            TokenBox actionBox = actions.get(i);
            actionBox.setRow(cumulative);
            int depth = actionBox.setColumn(0);
            if(depth > maxCol) maxCol = depth;
            cumulative += actionBox.rowSpan();
        }
        rows = cumulative;
        columns = maxCol;

        // draw to the screen
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        for(TokenBox box : actions){
            box.drawSelf(canvas, this);
        }
        holder.unlockCanvasAndPost(canvas);

        // call the listener
        if(treeChangedListener != null){
            treeChangedListener.onTreeChanged();
        }
    }

    private Point location = new Point();

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getPointerCount() != 1){
            return false;   // no reason to overcomplicate with multitouch
        }
        int row = (int) ((motionEvent.getY() - padding) * rows / height);
        int column = (int) ((motionEvent.getX() - padding) * columns / width);
        if(row >= rows || row < 0 || column >= columns || column < 0) {
            return false;   // touching the padding around the screen
        }
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                location.set(row, column);
                break;
            case MotionEvent.ACTION_UP:
                if(row == location.x && column == location.y){
                    onTokenClick(row, column);
                }
        }
        return true;
    }

    /**
     * Method called when onTouch has determined a cell has been clicked
     * @param row the touched row
     * @param column the touched column
     */
    private void onTokenClick(int row, int column){
        TokenBox clicked = null;
        for(TokenBox action : actions){
            TokenBox test = action.contains(row, column);
            if(test != null){
                clicked = test;
                break;
            }
        }
        if(clicked == null) return;

        // use separate class to handle this UI
        TokenSelector selector = new TokenSelector(getContext(), clicked, this);
        selector.showDialog();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int pixelFormat, int width, int height) {
        this.width = width;
        this.height = height;
        treeChanged();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

}
