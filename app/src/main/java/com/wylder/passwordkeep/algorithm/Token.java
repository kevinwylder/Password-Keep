package com.wylder.passwordkeep.algorithm;

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
}
