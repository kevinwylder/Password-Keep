package com.wylder.passwordkeep.algorithm;

/**
 * Created by kevin on 8/12/15.
 *
 * An interface that describes a function token. All two bit operaters have a corresponding token
 */
public interface Token {

    /**
     * Return any subtoken parameters.
     * @return
     */
    Token[] getParameters();

    int describeSelf();

}
