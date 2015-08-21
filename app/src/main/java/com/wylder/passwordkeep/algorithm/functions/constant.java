package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.I;
import com.wylder.passwordkeep.algorithm.SyntaxError;

import java.util.Stack;

/**
 * Created by kevin on 8/12/15.
 *
 * An integer stored in the next 6 bits of the program
 */
public class constant extends I {

    private int value = 0;

    public void setValue(int value){
        this.value = value;
    }

    @Override
    public int evaluate(String siteName) {
        return value;
    }

    @Override
    public DataType getDataType(){
        return DataType.INT;
    }

    @Override
    public DataType[] getParameterTypes() {
        return new DataType[0];
    }

    @Override
    public String getOperatorName() {
        return "constant";
    }

    @Override
    public void getBytecode(Stack<Boolean> bin) throws SyntaxError {
        bin.push(false);
        bin.push(false);
        bin.push(value > -1);
        for (int i = 4; i >= 0; i--) {
            bin.push(((value >> i) & 0x1) == 1);
        }
    }

}
