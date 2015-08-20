package com.wylder.passwordkeep.algorithm;

/**
 * Created by kevin on 8/12/15.
 *
 * an interface to define an action that can be performed to a password based off the site name.
 */
public abstract class A extends Token{

    public abstract void perform(StringBuilder basePassword, String siteName) throws EvaluationError;

}
