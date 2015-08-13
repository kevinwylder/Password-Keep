package com.wylder.passwordkeep.algorithm;

/**
 * Created by kevin on 8/12/15.
 * An error that is the result of invalid program code.
 */
public class SyntaxError extends Exception {

    public SyntaxError(String message){
        super(message);
    }

}
