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
 * returns the lowercase character corresponding to the int parameter's position in the alphabet.
 */
public class letter extends C {

    @Override
    public char evaluate(String siteName) throws EvaluationError, SyntaxError {
        int number = ((I) getParameter(DataType.INT, 0)).evaluate(siteName) % 26;
        if(number < 0){
            number += 26;
        }
        return (char) (number + 97);
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
}
