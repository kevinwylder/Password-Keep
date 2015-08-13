package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.A;
import com.wylder.passwordkeep.algorithm.C;
import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.algorithm.Token;

/**
 * Created by kevin on 8/12/15.
 *
 * Action that adds its only parameter (a C token) to the end of the currently constructed password
 */
public class add implements A {

    C parameter = null;

    /**
     * adds the evaluated parameter to the end of the password
     * @return the new password, ready for more actions
     * @throws EvaluationError if the char cannot be evaluated
     */
    @Override
    public void perform(StringBuilder basePassword, String siteName) throws EvaluationError {
        basePassword.append(parameter.evaluate());
    }

    @Override
    public Token[] getParameters() {
        return new Token[]{parameter};
    }

    @Override
    public DataType getNextParam() {
        if(parameter == null){
            return DataType.C;
        }else{
            return DataType.V;
        }
    }

    @Override
    public void giveParameter(Token child) throws SyntaxError{
        if(child instanceof C){
            parameter = (C) child;
        }else {
            throw new SyntaxError("error giving add token child parameter");
        }
    }

    @Override
    public String getOperatorName() {
        return "add";
    }

    @Override
    public String toString() {
        return "add [" + parameter.toString() + "] to the end of the password";
    }


}
