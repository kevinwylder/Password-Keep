package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.C;
import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.I;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.algorithm.Token;

import java.util.Stack;

/**
 * Created by kevin on 8/12/15.
 *
 * returns the lowercase character corresponding to it's position in the alphabet.
 */
public class letter implements C {

    I letter;

    @Override
    public char evaluate(String siteName) throws EvaluationError {
        if(letter == null) throw new EvaluationError("Algorithm incomplete");
        int number = letter.evaluate(siteName) % 26;
        return (char) (number + 97);
    }

    @Override
    public Token[] getParameters() {
        if(letter == null) return new Token[0];
        else return new Token[]{letter};
    }

    @Override
    public DataType getNextParam() {
        if(letter == null){
            return DataType.INT;
        }else{
            return DataType.VOID;
        }
    }

    @Override
    public void getBytecode(Stack<Boolean> bin) throws SyntaxError {
        if(letter == null) throw new SyntaxError("Incomplete tree");
        bin.push(false);
        bin.push(true);
        letter.getBytecode(bin);
    }

    @Override
    public void giveParameter(Token child) throws SyntaxError {
        if(child instanceof I && letter == null){
            letter = (I) child;
        }else {
            throw new SyntaxError("asking for a non-number alphabet position");
        }
    }

    @Override
    public String getOperatorName() {
        return "letter";
    }
}
