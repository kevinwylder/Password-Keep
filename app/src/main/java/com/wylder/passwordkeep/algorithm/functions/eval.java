package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.I;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.algorithm.Token;

/**
 * Created by kevin on 8/12/15.
 */
public class eval implements I {

    @Override
    public int evaluate() throws EvaluationError {
        return 0;
    }

    @Override
    public Token[] getParameters() {
        return new Token[0];
    }

    @Override
    public DataType getNextParam() {
        return null;
    }

    @Override
    public void giveParameter(Token child) throws SyntaxError {

    }

    @Override
    public String getOperatorName() {
        return null;
    }
}
