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
 * the nth character of the site name.
 */
public class select extends C {

    @Override
    public char evaluate(String siteName) throws EvaluationError, SyntaxError {
        int position = ((I) getParameter(DataType.INT, 0)).evaluate(siteName);
        position %= siteName.length();
        if(position < 0) position += siteName.length();
        return siteName.charAt(position);
    }

    @Override
    public DataType[] getParameterTypes() {
        return new DataType[]{
                DataType.INT
        };
    }

    @Override
    public void getBytecode(Stack<Boolean> bin) throws SyntaxError {
        bin.push(true);
        bin.push(true);
        super.getBytecode(bin);
    }

    @Override
    public DataType getDataType() {
        return DataType.CHAR;
    }

    @Override
    public String getOperatorName() {
        return "select";
    }

}
