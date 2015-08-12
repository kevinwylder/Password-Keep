package com.wylder.passwordkeep.algorithm;

/**
 * Created by kevin on 8/12/15.
 *
 * An interface to describe an integer that can be evaluated.
 */
public interface I extends Token{

    int evaluate() throws EvaluationError;

}
