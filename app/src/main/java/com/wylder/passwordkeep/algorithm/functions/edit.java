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
 * An action that edits the character at the given position (second parameter) to be the character
 * of the first parameter. If the second parameter is longer than the password, it will modulate it
 * by the length of the string.
 */
public class edit implements A {

    C character;
    I position;

    @Override
    public void perform(StringBuilder basePassword, String siteName) throws EvaluationError {
        if(character == null || position == null) throw new EvaluationError("Incomplete edit action");
        if(siteName.length() == 0) throw new EvaluationError("Cannot edit an empty site name");
        char edit = character.evaluate(siteName);
        int pos = position.evaluate(siteName) % basePassword.length();
        basePassword.replace(pos, pos + 1, "" + edit);
    }

    @Override
    public Token[] getParameters() {
        if(character == null){
            return new Token[0];
        }else if(position == null){
            return new Token[]{character};
        }else {
            return new Token[]{character, position};
        }
    }

    @Override
    public DataType getNextParam() {
        if(character == null) {
            return DataType.CHAR;
        } else if(position == null) {
            return DataType.INT;
        } else {
            return DataType.VOID;
        }
    }

    @Override
    public void giveParameter(Token child) throws SyntaxError {
        if(child instanceof C && character == null && position == null) {
            character = (C) child;
        }else if(child instanceof I && character != null && position == null){
            position = (I) child;
        }else{
            throw new SyntaxError("invalid parameter ([" + child.toString() + "]) for edit");
        }
    }

    @Override
    public String getOperatorName() {
        return "edit";
    }
}
