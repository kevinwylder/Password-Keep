package com.wylder.passwordkeep.ide;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.Token;

import java.util.ArrayList;

/**
 * Created by kevin on 8/17/15.
 *
 * describes a token and it's geometric location on the AlgorithmView
 */
public class TokenBox  {

    int row;
    int column;

    DataType type;
    Token token;
    ArrayList<TokenBox> children;

    String text;
    Paint boxPaint = new Paint();
    Paint boxOutlinePaint = new Paint();
    Paint textPaint = new Paint();
    Paint linePaint = new Paint();

    public TokenBox(DataType type){
        this.type = type;
        int boxColor = Color.BLACK;
        switch(type){
            case ACTION:
                boxColor = 0xfff44336;
                break;
            case CHAR:
                boxColor = 0xff009688;
                break;
            case INT:
                boxColor = 0xff3f51b5;
        }
        boxPaint.setColor(boxColor);
        boxPaint.setStyle(Paint.Style.FILL);
        boxOutlinePaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(0xffffffff);
        linePaint.setStrokeWidth(5);
        deleteSelf();
    }

    /**
     * Method for AlgorithmView to set this box's token
     * @param token
     */
    public void setToken(Token token){
        this.token = token;
        this.text = token.getOperatorName();
    }

    /**
     * reset the text and children of this TokenBox
     */
    public void deleteSelf(){
        if(type == DataType.ACTION){
            text = "Add Action";
        }else if(type == DataType.CHAR){
            text = "Add Character";
        }else{
            text = "Add Number";
        }
        children = new ArrayList<>();
    }

    /**
     * Method to draw this box on the given canvas given the state of the AlgorithmView
     * @param canvas the canvas to draw to
     * @param view the algorithm view that called holder.lockCanvas();
     */
    public void drawSelf(Canvas canvas, AlgorithmView view){
        float x = (column * view.width) / view.columns + view.padding;
        float y = (row * view.height) / view.rows + view.padding;
        float width = (view.width) / view.columns - (2 * view.padding);
        float height = (view.height * rowSpan()) / view.rows - (2 * view.padding);
        for(TokenBox child : children){
            float childX = (child.column * view.width) / view.columns + view.padding;
            float childY = (child.row * view.height) / view.rows + view.padding;
            float childHeight = (view.height * child.rowSpan()) / view.rows - (2 * view.padding);
            // draw line from the center of this box to all its children
            canvas.drawLine(x + (width / 2.0f), y + (height / 2.0f), childX + (width / 2.0f), childY + (childHeight / 2.0f), linePaint);
        }
        RectF box = new RectF(x, y, x + width, y + height);
        canvas.drawRoundRect(box, width / 10.0f, height / 10.0f, boxPaint);
        canvas.drawRoundRect(box, width / 10.0f, height / 10.0f, boxOutlinePaint);
        canvas.drawText(text, x + view.padding, y + view.padding, textPaint);
        for(TokenBox child : children){
            child.drawSelf(canvas, view);
        }
    }

    /**
     * Measure the height of this node by traversing the tree and finding it's total rows
     * @return the number of rows this tokenbox spans
     */
    public int rowSpan(){
        if(children.size() == 0){
            return 1;
        } else {
            int size = 0;
            for(TokenBox box : children){
                size += box.rowSpan();
            }
            return size;
        }
    }

}
