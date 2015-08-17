package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.C;
import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.I;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.algorithm.Token;

import java.util.Stack;

/**
 * Created by kevin on 8/12/15.
 *
 * Operation to evaluate the position in the alphabet of a character.
 */
public class eval implements I {

    C parameter;

    @Override
    public int evaluate(String siteName) throws EvaluationError {
        if(parameter == null) throw new EvaluationError("no character to evaluate");
        int number = (int) parameter.evaluate(siteName);
        if(number > 64 && number < 91){
            return number - 64;     // capitals
        } else if(number > 96 && number < 123){
            return number - 96;     // lowercase
        } else{
            throw new EvaluationError("invalid character to evaluate");
        }
    }

    @Override
    public Token[] getParameters() {
        if(parameter == null) return new Token[0];
        else return new Token[]{parameter};
    }

    @Override
    public DataType getNextParam() {
        if(parameter == null) return DataType.CHAR;
        else return DataType.VOID;
    }

    @Override
    public void getBytecode(Stack<Boolean> bin) throws SyntaxError {
        if(parameter == null) throw new SyntaxError("Incomplete tree");
        bin.push(true);
        bin.push(false);
        parameter.getBytecode(bin);
    }

    @Override
    public void giveParameter(Token child) throws SyntaxError {
        if(child instanceof C){
            parameter = (C) child;
        }else{
            throw new SyntaxError("evaluating a non-character");
        }
    }

    @Override
    public String getOperatorName() {
        return "eval";
    }
}
