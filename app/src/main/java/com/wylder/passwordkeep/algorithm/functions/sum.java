package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.I;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.algorithm.Token;

/**
 * Created by kevin on 8/12/15.
 *
 * the sum of each chararcter in the siteName's eval
 */
public class sum implements I {

    private int eval(char character) throws EvaluationError{
        int number = (int) character;
        if(number > 64 && number < 91){
            return number - 64;     // capitals
        } else if(number > 96 && number < 123){
            return number - 96;     // lowercase
        } else {
            throw new EvaluationError("sum could not evaluate char(s) in siteName");
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
    public Token[] getParameters() {
        return new Token[0];
    }

    @Override
    public DataType getNextParam() {
        return DataType.V;
    }

    @Override
    public void giveParameter(Token child) throws SyntaxError {
        throw new SyntaxError("Sum doesn't take parameters");
    }

    @Override
    public String getOperatorName() {
        return "sum";
    }
}
