package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.C;
import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.I;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.algorithm.Token;

/**
 * Created by kevin on 8/12/15.
 *
 * the nth character of the site name.
 */
public class select implements C {

    I position;

    @Override
    public char evaluate(String siteName) throws EvaluationError {
        if(position == null) throw new EvaluationError("Incomplete Algorithm");
        return siteName.charAt(position.evaluate(siteName));
    }

    @Override
    public Token[] getParameters() {
        if(position == null) {
            return new Token[0];
        } else {
            return new Token[]{position};
        }
    }

    @Override
    public DataType getNextParam() {
        if(position == null){
            return DataType.I;
        }else{
            return DataType.V;
        }
    }

    @Override
    public void giveParameter(Token child) throws SyntaxError {
        if(child instanceof I && position == null){
            position = (I) child;
        }else{
            throw new SyntaxError("Invalid select index");
        }
    }

    @Override
    public String getOperatorName() {
        return "select";
    }

}
