package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.A;
import com.wylder.passwordkeep.algorithm.C;
import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.algorithm.Token;

import java.util.Queue;

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
        basePassword.append(parameter.evaluate(siteName));
    }

    @Override
    public Token[] getParameters() {
        return new Token[]{parameter};
    }

    @Override
    public DataType getNextParam() {
        if(parameter == null){
            return DataType.CHAR;
        }else{
            return DataType.VOID;
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
    public void getBytecode(Queue<Boolean> bin) throws SyntaxError {
        if(parameter == null) throw new SyntaxError("Incomplete tree");
        bin.offer(true);
        bin.offer(true);
        parameter.getBytecode(bin);
    }

    @Override
    public String toString() {
        return "add [" + parameter.toString() + "] to the end of the password";
    }


}
