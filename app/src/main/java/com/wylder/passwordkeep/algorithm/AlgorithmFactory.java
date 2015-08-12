package com.wylder.passwordkeep.algorithm;

/**
 * Created by kevin on 8/11/15.
 *
 * A class that will parse
 */
public class AlgorithmFactory {

    /**
     * compiles the program into an Algorithm object
     * @param programInt the program's binary converted to an integer
     * @return the Algorithm ready to be executed with
     * @throws SyntaxError
     */
    public Algorithm buildAlgorithm(int programInt) throws SyntaxError {
        return new Algorithm();
    }

    /**
     * compiles the program into an Algorithm object
     * @param hexCode the program's binary converted to hexadecimal
     * @return the Algorithm ready to be executed with
     * @throws SyntaxError
     */
    public Algorithm buildAlgorithm(String hexCode) throws SyntaxError {

        return buildAlgorithm(12);
    }
}
