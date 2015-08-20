package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.A;
import com.wylder.passwordkeep.algorithm.C;
import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.I;
import com.wylder.passwordkeep.algorithm.SyntaxError;

import java.util.Stack;

/**
 * Created by kevin on 8/12/15.
 *
 * An action that edits the character at the given position (second parameter) to be the character
 * of the first parameter. If the second parameter is longer than the password, it will modulate it
 * by the length of the string.
 */
public class edit extends A {

    @Override
    public void perform(StringBuilder basePassword, String siteName) throws EvaluationError, SyntaxError {
        if(siteName.length() == 0) throw new EvaluationError("Cannot edit an empty site name");
        char edit = ((C) getParameter(DataType.CHAR, 0)).evaluate(siteName);
        int pos = ((I) getParameter(DataType.INT, 1)).evaluate(siteName) % basePassword.length();
        basePassword.replace(pos, pos + 1, "" + edit);
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
        return DataType.ACTION;
    }

    @Override
    public String getOperatorName() {
        return "edit";
    }

    @Override
    public void getBytecode(Stack<Boolean> bin) throws SyntaxError {
        bin.push(false);
        bin.push(false);
        super.getBytecode(bin);
    }

}
