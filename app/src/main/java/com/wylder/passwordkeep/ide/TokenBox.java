package com.wylder.passwordkeep.ide;

import android.graphics.Canvas;

import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.Token;

import java.util.ArrayList;

/**
 * Created by kevin on 8/17/15.
 *
 * describes a token and it's geometric location on the AlgorithmView
 */
public class TokenBox {

    int row;
    int column;

    DataType type;
    Token token;
    ArrayList<TokenBox> children = new ArrayList<>();

    String text;
    int textColor = 0xffffffff;
    int boxColor;

    public TokenBox(int row, int column, DataType type){
        this.row = row;
        this.column = column;
        this.type = type;
        if(type == DataType.ACTION){
            text = "Add Action";
            boxColor = 0xfff44336;
        }else if(type == DataType.CHAR){
            text = "Add Character";
            boxColor = 0xff009688;
        }else{
            text = "Add Number";
            boxColor = 0xff3f51b5;
        }
    }

    public void onBoxClicked(){

    }

    public void drawSelf(Canvas canvas, AlgorithmView view){
        float x = (row * view.width) / view.rows + view.padding;
        float y = (column * view.height) / view.columns + view.padding;
        float width = (view.width / view.rows) - (2 * view.padding);
        float height = ()
    }

    public int columnSpan(){
        if(children.size() == 0){
            return 1;
        } else {
            int size = 0;
            for(TokenBox box : children){
                size += box.columnSpan();
            }
            return size;
        }
    }

}
