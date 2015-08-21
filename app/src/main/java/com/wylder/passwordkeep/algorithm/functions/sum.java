package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.I;
import com.wylder.passwordkeep.algorithm.SyntaxError;

import java.util.Stack;

/**
 * Created by kevin on 8/12/15.
 *
 * the sum of each chararcter in the siteName's eval
 */
public class sum extends I {

    /**
     * helper method to evaluate each char of the string
     * @param character the character selected
     * @return the integer position of that char in the alphabet
     * @throws EvaluationError the char isn't in a-z or A-Z
     */
    private int eval(char character) throws EvaluationError{
        int number = (int) character;
        if(number > 64 && number < 91){
            return number - 64;     // capitals
        } else if(number > 96 && number < 123){
            return number - 96;     // lowercase
        } else {
            throw new EvaluationError("sum could not evaluate char(s) " + character + " in siteName");
        }
    }

    @Override
    public int evaluate(String siteName) throws EvaluationError {
        int sum = 0;
        for (int i = 0; i < siteName.length(); i++) {
            sum += eval(siteName.charAt(i));
        }
        return sum;
    }

    @Override
    public DataType[] getParameterTypes() {
        return new DataType[0];
    }

    @Override
    public DataType getDataType() {
        return DataType.INT;
    }

    @Override
    public String getOperatorName() {
        return "sum";
    }

    @Override
    public void getBytecode(Stack<Boolean> bin) throws SyntaxError {
        bin.push(true);
        bin.push(false);
    }
}
