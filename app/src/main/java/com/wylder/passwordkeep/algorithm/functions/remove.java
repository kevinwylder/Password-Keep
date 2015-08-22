package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.A;
import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.I;
import com.wylder.passwordkeep.algorithm.SyntaxError;

import java.util.Stack;

/**
 * Created by kevin on 8/12/15.
 *
 * action to remove a character from the base password
 */
public class remove extends A {

    @Override
    public void perform(StringBuilder basePassword, String siteName) throws EvaluationError, SyntaxError {
        int position = ((I) getParameter(DataType.INT, 0)).evaluate(siteName) % siteName.length();
        if(position < 0){
            position += siteName.length();
        }
        basePassword.deleteCharAt(position);
    }

    @Override
    public DataType[] getParameterTypes() {
        return new DataType[]{
                DataType.INT
        };
    }

    @Override
    public DataType getDataType() {
        return DataType.ACTION;
    }

    @Override
    public void getBytecode(Stack<Boolean> bin) throws SyntaxError {
        bin.push(false);
        bin.push(true);
        super.getBytecode(bin);
    }

    @Override
    public String getOperatorName() {
        return "remove";
    }


    @Override
    public void explainOperation(StringBuilder string) throws SyntaxError {
        string.append("remove the character at position (");
        getParameter(DataType.INT, 0).explainOperation(string);
        string.append(")");
    }
}
