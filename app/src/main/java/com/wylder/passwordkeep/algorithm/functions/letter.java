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
 * returns the one's digit character of the integer.
 */
public class letter extends C {

    @Override
    public char evaluate(String siteName) throws EvaluationError, SyntaxError {
        int number = ((I) getParameter(DataType.INT, 0)).evaluate(siteName) % 10;
        if(number < 0){
            number += 10;
        }
        return (char) (48 + number);
    }

    @Override
    public DataType[] getParameterTypes() {
        return new DataType[]{
                DataType.INT
        };
    }

    @Override
    public DataType getDataType() {
        return DataType.CHAR;
    }

    @Override
    public void getBytecode(Stack<Boolean> bin) throws SyntaxError {
        bin.push(false);
        bin.push(true);
        super.getBytecode(bin);
    }

    @Override
    public String getOperatorName() {
        return "letter";
    }


    @Override
    public void explainOperation(StringBuilder string) throws SyntaxError {
        string.append("the literal character [");
        getParameter(DataType.CHAR, 1).explainOperation(string);
        string.append("]");
    }
}
