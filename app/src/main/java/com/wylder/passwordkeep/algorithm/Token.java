package com.wylder.passwordkeep.algorithm;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by kevin on 8/12/15.
 *
 * An interface that describes a function token. All two bit operaters have a corresponding token
 */
public abstract class Token {

    private int nextParamIndex = 0;
    protected DataType[] parameters;
    protected ArrayList<Token> children = new ArrayList<>();

    protected Token(){
        parameters = getParameters();
    }

    /**
     * Return any subtoken parameters.
     * @return 0, 1, or 2 tokens passed in as parameters to this token
     */
    public abstract DataType[] getParameters();

    /**
     * Gets the datatype of the next parameter.
     * @return the DataType enum representing the bitcode type (A, I, o
     */
    public DataType getNextParam() {
        if(nextParamIndex >= parameters.length){
            return DataType.VOID;
        } else {
            return parameters[nextParamIndex];
        }
    }

    /**
     * Give another parameter token to this token
     * @param child the token parameter to add
     */
    public void giveParameter(Token child) throws SyntaxError {
        if(nextParamIndex >= parameters.length){
            throw new SyntaxError("too many parameters for " + getOperatorName());
        }
        if(child.getDatatype() == parameters[nextParamIndex]){
            children.add(child);
            nextParamIndex++;
        } else {
            throw new SyntaxError("incorrect parameter type for " + getOperatorName());
        }
    }

    public abstract DataType getDatatype();

    /**
     * create a human readable string of the algorithm description
     * @return a constructed string explanation
     */
    public abstract String toString();

    /**
     * @return a string that describes the operation
     */
    public abstract String getOperatorName();

    /**
     * push the binary representation of this token to the queue
     * @param bin the location to write the data
     * @throws SyntaxError if the tree is incomplete
     */
    public abstract void getBytecode(Stack<Boolean> bin) throws SyntaxError;

}
