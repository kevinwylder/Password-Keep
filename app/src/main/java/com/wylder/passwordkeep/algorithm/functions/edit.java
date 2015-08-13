package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.A;
import com.wylder.passwordkeep.algorithm.C;
import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.I;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.algorithm.Token;

/**
 * Created by kevin on 8/12/15.
 *
 * An action that edits the character at the given position (second parameter) to be the character
 * of the first parameter. If the second parameter is longer than the password, it will modulate it
 * by the length of the string.
 */
public class edit implements A {

    C character;
    I position;

    @Override
    public void perform(StringBuilder basePassword, String siteName) throws EvaluationError {

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
