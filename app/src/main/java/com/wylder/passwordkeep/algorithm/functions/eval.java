package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.C;
import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.I;
import com.wylder.passwordkeep.algorithm.SyntaxError;

import java.util.Stack;

/**
 * Created by kevin on 8/12/15.
 *
 * Operation to evaluate the position in the alphabet of a character.
 */
public class eval extends I {

    @Override
    public int evaluate(String siteName) throws EvaluationError, SyntaxError {
        int number = ((C) getParameter(DataType.CHAR, 0)).evaluate(siteName);
        if(number > 64 && number < 91){
            return number - 64;     // capitals
        } else if(number > 96 && number < 123){
            return number - 96;     // lowercase
        } else{
            throw new EvaluationError("invalid character to evaluate");
        }
    }

    @Override
    public DataType[] getParameterTypes() {
        return new DataType[]{
                DataType.CHAR
        };
    }

    @Override
    public DataType getDataType(){
        return DataType.INT;
    }

    @Override
    public void getBytecode(Stack<Boolean> bin) throws SyntaxError {
        bin.push(true);
        bin.push(false);
        super.getBytecode(bin);
    }

    @Override
    public void explainOperation(StringBuilder string) throws SyntaxError {
        string.append("the index [");
        getParameter(DataType.CHAR, 0).explainOperation(string);
        string.append("] is in the alphabet");
    }

    @Override
    public String getOperatorName() {
        return "eval";
    }
}
