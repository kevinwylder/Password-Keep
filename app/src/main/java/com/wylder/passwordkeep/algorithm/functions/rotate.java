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
 * matching case character rotated through the alphabet by the provided amount.
 */
public class rotate extends C {

    @Override
    public char evaluate(String siteName) throws EvaluationError, SyntaxError {
        int value = ((C) getParameter(DataType.CHAR, 0)).evaluate(siteName);
        int amount = ((I) getParameter(DataType.INT, 1)).evaluate(siteName);
        int offset;
        if(value > 64 && value < 91){
            offset = 65; // capitals
        }else if(value > 96 && value < 123){
            offset = 97; // lowercase
        }else {
            throw new EvaluationError("rotating a non-alphabetic character");
        }
        value = (value + amount - offset) % 26;
        if(value < 0) value += 26;
        return (char) (value + offset);
    }

    @Override
    public DataType[] getParameterTypes() {
        return new DataType[]{
                DataType.CHAR,
                DataType.INT
        };
    }

    @Override
    public DataType getDataType() {
        return DataType.CHAR;
    }


    @Override
    public void getBytecode(Stack<Boolean> bin) throws SyntaxError {
        bin.push(true);
        bin.push(false);
        super.getBytecode(bin);
    }

    @Override
    public String getOperatorName() {
        return "rotate";
    }


    @Override
    public void explainOperation(StringBuilder string) throws SyntaxError {
        string.append("the character [");
        getParameter(DataType.CHAR, 0).explainOperation(string);
        string.append("] rotated (");
        getParameter(DataType.INT, 1).explainOperation(string);
        string.append(") letters through the alphabet");
    }
}
