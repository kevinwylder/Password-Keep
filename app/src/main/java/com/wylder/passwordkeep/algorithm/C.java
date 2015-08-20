package com.wylder.passwordkeep.algorithm;

/**
 * Created by kevin on 8/12/15.
 *
 * an interface that defines a char that can be evaluated for.
 */
public abstract class C extends Token {

    public abstract char evaluate(String siteName) throws EvaluationError, SyntaxError;

}
