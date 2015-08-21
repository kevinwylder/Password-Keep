package com.wylder.passwordkeep.ide;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.algorithm.Token;
import com.wylder.passwordkeep.algorithm.functions.constant;

import java.util.ArrayList;

/**
 * Created by kevin on 8/17/15.
 *
 * describes a token and it's geometric location on the AlgorithmView
 */
public class TokenBox  {

    private int row;
    private int column;

    DataType type;
    Token token;
    ArrayList<TokenBox> children = new ArrayList<>();

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
     * @param token the token to give to this box
     */
    public void setToken(Token token){
        this.token = token;
        this.text = token.getOperatorName();
        if (token instanceof constant){
            text += " " + ((constant) token).evaluate("");
        }
        DataType[] parameterTypes = token.getParameterTypes();
        children.clear();
        for(DataType child : parameterTypes){
            children.add(new TokenBox(child));
        }
    }

    /**
     * method to construct this box's token representation
     * @return the Token subtree of this box
     */
    public Token getToken() throws SyntaxError {
        if(token == null){
            throw new SyntaxError("Incomplete tree");
        }
        token.removeAllParameters();
        for(TokenBox child : children){
            token.giveParameter(child.getToken());
        }
        return token;
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
        children.clear();
        token = null;
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
        // draw lines
        for(TokenBox child : children){
            float childX = (child.column * view.width) / view.columns + view.padding;
            float childY = (child.row * view.height) / view.rows + view.padding;
            float childHeight = (view.height * child.rowSpan()) / view.rows - (2 * view.padding);
            // draw line from the center of this box to all its children
            canvas.drawLine(x + (width / 2.0f), y + (height / 2.0f), childX + (width / 2.0f), childY + (childHeight / 2.0f), linePaint);
        }
        // draw box shape
        RectF box = new RectF(x, y, x + width, y + height);
        float rounded = Math.min(width / 10.0f, height / 10.0f);
        canvas.drawRoundRect(box, rounded, rounded, boxPaint);
        canvas.drawRoundRect(box, rounded, rounded, boxOutlinePaint);
        // draw text
        textPaint.setTextSize(100);
        float textRatio = (width - 2 * view.padding) / textPaint.measureText(text);
        float textSize = Math.min(height - 2 * view.padding, 100 * textRatio);
        textPaint.setTextSize(textSize);
        canvas.drawText(text, x + view.padding, y + (height + textSize) / 2.0f, textPaint);
        // draw children
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

    /**
     * method to set the row and its children's row
     * @param row the row to set
     */
    public void setRow(int row){
        this.row = row;
        int cumulative = 0;
        for (int i = 0; i < children.size(); i++) {
            TokenBox box = children.get(i);
            box.setRow(row + cumulative);
            cumulative += box.rowSpan();
        }
    }

    /**
     * method to set this box's column and its children's column
     * @param column the column to set this box to
     * @return the max column assigned
     */
    public int setColumn(int column){
        this.column = column;
        if(children.size() == 0){
            return column + 1;
        }
        int max = 0;
        for(TokenBox child : children){
            int depth = child.setColumn(column + 1);
            if(depth > max) max = depth;
        }
        return max;
    }

    /**
     * Find if this subtree contains a tokenbox with the given row and column
     * @param row the row to find
     * @param column the column to find
     * @return the tokenbox itself or null if it wasn't found
     */
    public TokenBox contains(int row, int column){
        if(this.column > column || this.row > row){
            return null;
        }
        if(column == this.column && row >= this.row && row < this.row + rowSpan()) {
            return this;
        }
        for(TokenBox child : children){
            TokenBox found = child.contains(row, column);
            if(found != null){
                return found;
            }
        }
        return null;    // if none of the children had it, the point was probably blank
    }

}
