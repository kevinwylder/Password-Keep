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
 * Action to insert a character into the password at a given index
 */
public class insert extends A {

    @Override
    public void perform(StringBuilder basePassword, String siteName) throws EvaluationError, SyntaxError {
        int pos = ((I) getParameter(DataType.INT, 1)).evaluate(siteName);
        if(siteName.length() == 0){
            pos = 0;
        }else{
            pos %= basePassword.length();
        }
        basePassword.insert(pos, ((C) getParameter(DataType.CHAR, 0)).evaluate(siteName));
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
    public void getBytecode(Stack<Boolean> bin) throws SyntaxError {
        bin.push(true);
        bin.push(false);
        super.getBytecode(bin);
    }

    @Override
    public String getOperatorName() {
        return "insert";
    }


}
