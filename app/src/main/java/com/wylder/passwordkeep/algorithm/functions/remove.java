package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.A;
import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.I;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.algorithm.Token;

import java.util.Stack;

/**
 * Created by kevin on 8/12/15.
 *
 * action to remove a character from the base password
 */
public class remove implements A {

    I position;

    @Override
    public void perform(StringBuilder basePassword, String siteName) throws EvaluationError {
        if(position == null) throw new EvaluationError("removal position is not set");
        basePassword.deleteCharAt(position.evaluate(siteName));
    }

    @Override
    public Token[] getParameters() {
        if(position == null) return new Token[0];
        else return new Token[]{position};
    }

    @Override
    public DataType getNextParam() {
        if(position == null){
            return DataType.INT;
        }else {
            return DataType.VOID;
        }
    }

    @Override
    public void getBytecode(Stack<Boolean> bin) throws SyntaxError {
        if(position == null) throw new SyntaxError("Incomplete tree");
        bin.push(false);
        bin.push(true);
        position.getBytecode(bin);
    }

    @Override
    public void giveParameter(Token child) throws SyntaxError {
        if(child instanceof I && position == null){
            position = (I) child;
        }else{
            throw new SyntaxError("too many / invalid remove parameter");
        }
    }

    @Override
    public String getOperatorName() {
        return "remove";
    }
}
