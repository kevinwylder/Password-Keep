package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.I;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.algorithm.Token;

import java.util.Queue;

/**
 * Created by kevin on 8/12/15.
 *
 * returns the number of characters in the site name
 */
public class len implements I {


    @Override
    public int evaluate(String siteName) throws EvaluationError {
        return siteName.length();
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
        throw new SyntaxError("Cannot give len a parameter");
    }

    @Override
    public String getOperatorName() {
        return "len";
    }

    @Override
    public void getBytecode(Queue<Boolean> bin) throws SyntaxError {
        bin.offer(true);
        bin.offer(true);
    }
}
