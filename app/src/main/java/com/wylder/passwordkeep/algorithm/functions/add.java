package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.A;
import com.wylder.passwordkeep.algorithm.C;
import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.SyntaxError;

import java.util.Stack;

/**
 * Created by kevin on 8/12/15.
 *
 * Action that adds its only parameter (a C token) to the end of the currently constructed password
 */
public class add extends A {

    /**
     * adds the evaluated parameter to the end of the password
     * @throws EvaluationError if the char cannot be evaluated
     */
    @Override
    public void perform(StringBuilder basePassword, String siteName) throws EvaluationError, SyntaxError {
        C parameter = (C) getParameter(DataType.CHAR, 0);
        basePassword.append(parameter.evaluate(siteName));
    }

    @Override
    public DataType[] getParameterTypes() {
        return new DataType[]{
                DataType.CHAR
        };
    }

    @Override
    public DataType getDataType() {
        return DataType.ACTION;
    }

    @Override
    public String getOperatorName() {
        return "add";
    }

    @Override
    public void getBytecode(Stack<Boolean> bin) throws SyntaxError {
        bin.push(true);
        bin.push(true);
        super.getBytecode(bin);
    }


}
