package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.I;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.algorithm.Token;

import java.util.Stack;

/**
 * Created by kevin on 8/12/15.
 *
 * returns the number of characters in the site name
 */
public class len extends I {

    @Override
    public int evaluate(String siteName) {
        return siteName.length();
    }

    @Override
    public DataType[] getParameterTypes() {
        return new DataType[0];
    }

    @Override
    public DataType getNextParameterType() {
        return DataType.VOID;
    }

    @Override
    public DataType getDataType() {
        return DataType.INT;
    }

    @Override
    public void giveParameter(Token child) throws SyntaxError {
        throw new SyntaxError("Cannot give len a parameter");
    }

    @Override
    public String getOperatorName() {
        return "len";
    }

    @Override
    public void getBytecode(Stack<Boolean> bin) {
        bin.push(true);
        bin.push(true);
    }
}
