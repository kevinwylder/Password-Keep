package com.wylder.passwordkeep.security;

/**
 * Created by kevin on 8/9/15.
 *
 * an algorithm that takes the site name and base password to create a unique password
 */
public interface Algorithm {

    String evaluatePassword(String basePassword, String siteName);

}
