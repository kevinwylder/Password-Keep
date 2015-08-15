package com.wylder.passwordkeep.algorithm.functions;

import com.wylder.passwordkeep.algorithm.A;
import com.wylder.passwordkeep.algorithm.C;
import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.EvaluationError;
import com.wylder.passwordkeep.algorithm.I;
import com.wylder.passwordkeep.algorithm.SyntaxError;
import com.wylder.passwordkeep.algorithm.Token;

/**
 * Created by kevin on 8/12/15.
 *
 * Action to insert a character into the password at a given index
 */
public class insert implements A {

    C character;
    I position;

    @Override
    public void perform(StringBuilder basePassword, String siteName) throws EvaluationError {
        if(character == null || position == null) throw new EvaluationError("insert needs two parameters");
        int pos = position.evaluate(siteName);
        if(siteName.length() == 0){
            pos = 0;
        }else{
            pos %= basePassword.length();
        }
        basePassword.insert(pos, character.evaluate(siteName));
    }

    @Override
    public Token[] getParameters() {
        if(character == null){
            return new Token[0];
        }else if(position == null){
            return new Token[]{character};
        }else{
            return new Token[]{character, position};
        }
    }

    @Override
    public DataType getNextParam() {
        if(character == null){
            return DataType.C;
        }else if(position == null){
            return DataType.I;
        }else{
            return DataType.V;
        }
    }

    @Override
    public void giveParameter(Token child) throws SyntaxError {
        if(child instanceof C){
            character = (C) child;
        }else if(child instanceof I){
            position = (I) child;
        }else{
            throw new SyntaxError("incorrect parameter ([" + child.toString() + "])");
        }
    }

    @Override
    public String getOperatorName() {
        return "insert";
    }

    @Override
    public String toString(){
        return "";
    }

}
