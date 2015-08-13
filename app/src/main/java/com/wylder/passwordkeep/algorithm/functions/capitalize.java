package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.C;
import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.algorithm.Token;

/**
 * Created by kevin on 8/12/15.
 *
 * A character that is the capitalized version of its parameter
 */
public class capitalize implements C {

    C parameter = null;

    @Override
    public char evaluate() throws EvaluationError {
        return Character.toUpperCase(parameter.evaluate());
    }

    @Override
    public Token[] getParameters() {
        return new Token[]{parameter};
    }

    @Override
    public DataType getNextParam() {
        if(parameter == null){
            return DataType.C;
        }else{
            return DataType.V;
        }
    }

    @Override
    public void giveParameter(Token child) throws SyntaxError {
        if(child instanceof C){
            parameter = (C) child;
        }else{
            throw new SyntaxError("Incorrect parameter type for capitalize");
        }
    }

    @Override
    public String getOperatorName() {
        return "capitalize";
    }

    @Override
    public String toString(){
        return "the letter [" + parameter.toString() + "] capitalized";
    }
}
