package com.wylder.passwordkeep.algorithm;

/**
 * Created by kevin on 8/12/15.
 *
 * An error thrown when the algorithm cannot be performed because the website name is abnormal
 */
public class EvaluationError extends Exception {

    public EvaluationError(String message){
        super(message);
    }

}
