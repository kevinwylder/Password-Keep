package com.wylder.passwordkeep.algorithm;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by kevin on 8/12/15.
 *
 * An class that describes a function token. All two bit operaters have a corresponding token
 */
public abstract class Token {

    public ArrayList<Token> parameters = new ArrayList<>();

    /**
     * method to get a parameter and ensure it will be the correct type
     * @param type the type of token to return
     * @param position the index of the token to retrieve
     * @return the token ready to be type casted (A, C, or I)
     */
    protected Token getParameter(DataType type, int position) throws SyntaxError {
        // check for proper size
        if(position >= parameters.size()){
            throw new SyntaxError("Not enough parameters in " + getOperatorName());
        }
        Token parameter = parameters.get(position);
        // check for proper type
        if(type == parameter.getDataType()){
            return parameter;
        } else {
            throw new SyntaxError("parameter " + position + " of " + getOperatorName() + " not proper type. expected " + type + " but returned " + parameter.getDataType() );
        }
    }

    /**
     * Give another parameter token to this token
     * @param child the token parameter to add
     */
    public void giveParameter(Token child) throws SyntaxError {
        if(parameters.size() >= getParameterTypes().length){
            throw new SyntaxError("too many parameters for " + getOperatorName());
        }
        if(child.getDataType() == getParameterTypes()[parameters.size()]){
            parameters.add(child);
        } else {
            throw new SyntaxError("incorrect parameter type for " + getOperatorName());
        }
    }

    /**
     * method to remove all the parameters in this token
     */
    public void removeAllParameters(){
        parameters.clear();
    }

    /**
     * Return any subtoken parameters.
     * @return 0, 1, or 2 tokens passed in as parameters to this token
     */
    public abstract DataType[] getParameterTypes();

    /**
     * Gets the datatype of the next parameter.
     * @return the DataType enum representing the bitcode type (A, I, o
     */
    public DataType getNextParameterType() {
        if(parameters.size() >= getParameterTypes().length){
            return DataType.VOID;
        } else {
            return getParameterTypes()[parameters.size()];
        }
    }

    /**
     * get the datatype of this token
     * @return the DataType enum of this token
     */
    public abstract DataType getDataType();

    /**
     * @return a string that describes the operation
     */
    public abstract String getOperatorName();

    /**
     * push the binary representation of this token to the queue. each subclass of Token should
     * override this method to add it's two bits, then call this method (super.getBytecode(bin))
     * @param bin the location to write the data
     * @throws SyntaxError if the tree is incomplete
     */
    public void getBytecode(Stack<Boolean> bin) throws SyntaxError {
        if(parameters.size() != getParameterTypes().length){
            throw new SyntaxError("Incomplete tree at " + getOperatorName());
        }
        for(Token child : parameters){
            child.getBytecode(bin);
        }
    }

    /**
     * explain this token by parsing the tree
     * @param string
     */
    public abstract void explainOperation(StringBuilder string) throws SyntaxError;

}
