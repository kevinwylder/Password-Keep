package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.C;
import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.I;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.algorithm.Token;

import java.util.Queue;

/**
 * Created by kevin on 8/12/15.
 *
 * matching case character rotated through the alphabet by the provided amount.
 */
public class rotate implements C {

    C startChar = null;
    I rotation = null;

    @Override
    public char evaluate(String siteName) throws EvaluationError {
        if(startChar == null || rotation == null) throw new EvaluationError("incomplete algorithm.");
        int value = startChar.evaluate(siteName);
        int amount = rotation.evaluate(siteName);
        int offset;
        if(value > 64 && value < 91){
            offset = 65; // capitals
        }else if(value > 96 && value < 123){
            offset = 97; // lowercase
        }else {
            throw new EvaluationError("rotating a non-alphabetic character");
        }
        value = (value + amount - offset) % 26;
        if(value < 0) value += 26;
        return (char) (value + 65);
    }

    @Override
    public Token[] getParameters() {
        if(startChar == null){
            return new Token[0];
        }else if (rotation == null){
            return new Token[]{startChar};
        }else {
            return new Token[]{startChar, rotation};
        }
    }

    @Override
    public DataType getNextParam() {
        if(startChar == null){
            return DataType.CHAR;
        }else if(rotation == null){
            return DataType.INT;
        }else {
            return DataType.VOID;
        }
    }


    @Override
    public void getBytecode(Queue<Boolean> bin) throws SyntaxError {
        if(startChar == null || rotation == null) throw new SyntaxError("Incomplete tree");
        bin.offer(true);
        bin.offer(false);
        startChar.getBytecode(bin);
        rotation.getBytecode(bin);
    }

    @Override
    public void giveParameter(Token child) throws SyntaxError {
        if(child instanceof C && startChar == null && rotation == null){
            startChar = (C) child;
        }else if(child instanceof I && startChar != null && rotation == null){
            rotation = (I) child;
        }else {
            throw new SyntaxError("Invalid rotation parameters");
        }
    }

    @Override
    public String getOperatorName() {
        return "rotate";
    }
}
