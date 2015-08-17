package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.I;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.algorithm.Token;

import java.util.Stack;

/**
 * Created by kevin on 8/12/15.
 *
 * An integer stored in the next 6 bits of the program
 */
public class constant implements I {

    private int value;

    public void setValue(int value){
        this.value = value;
    }

    @Override
    public int evaluate(String siteName) throws EvaluationError {
        return value;
    }

    @Override
    public Token[] getParameters() {
        return new Token[0];
    }

    @Override
    public DataType getNextParam() {
        return DataType.VOID;
    }

    @Override
    public void giveParameter(Token child) throws SyntaxError {
        throw new SyntaxError("Trying to give constant a parameter");
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

    @Override
    public String toString(){
        return "" + value;
    }
}
