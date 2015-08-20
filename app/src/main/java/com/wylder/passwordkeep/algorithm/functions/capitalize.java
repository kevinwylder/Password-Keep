package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.C;
import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.SyntaxError;

import java.util.Stack;

/**
 * Created by kevin on 8/12/15.
 *
 * A character that is the capitalized version of its parameter
 */
public class capitalize extends C {

    @Override
    public char evaluate(String siteName) throws EvaluationError, SyntaxError {
        C character = (C) getParameter(DataType.CHAR, 0);
        return Character.toUpperCase(character.evaluate(siteName));
    }

    @Override
    public DataType[] getParameterTypes() {
        return new DataType[]{
                DataType.CHAR
        };
    }

    @Override
    public void getBytecode(Stack<Boolean> bin) throws SyntaxError {
        bin.push(false);
        bin.push(false);
        super.getBytecode(bin);
    }

    @Override
    public DataType getDataType() {
        return DataType.CHAR;
    }

    @Override
    public String getOperatorName() {
        return "capitalize";
    }

}
