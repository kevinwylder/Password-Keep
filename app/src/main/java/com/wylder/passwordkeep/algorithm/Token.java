package com.wylder.passwordkeep.algorithm;

import java.util.Queue;

/**
 * Created by kevin on 8/12/15.
 *
 * An interface that describes a function token. All two bit operaters have a corresponding token
 */
public interface Token {

    /**
     * Return any subtoken parameters.
     * @return 0, 1, or 2 tokens passed in as parameters to this token
     */
    Token[] getParameters();

    /**
     * Gets the datatype of the next parameter.
     * @return the DataType enum representing the bitcode type (A, I, or C)
     */
    DataType getNextParam();

    /**
     * Give another parameter token to this token
     * @param child the token parameter to add
     */
    void giveParameter(Token child) throws SyntaxError;

    /**
     * create a human readable string of the algorithm description
     * @return a constructed string explanation
     */
    String toString();

    /**
     * @return a string that describes the operation
     */
    String getOperatorName();

    /**
     * push the binary representation of this token to the queue
     * @param bin the location to write the data
     * @throws SyntaxError if the tree is incomplete
     */
    void getBytecode(Queue<Boolean> bin) throws SyntaxError;
}
