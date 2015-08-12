package com.wylder.passwordkeep.algorithm;

/**
 * Created by kevin on 8/12/15.
 *
 * an interface that defines a char that can be evaluated for.
 */
public interface C extends Token{

    char evaluate() throws EvaluationError;

}
