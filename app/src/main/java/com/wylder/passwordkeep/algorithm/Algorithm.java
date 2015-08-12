package com.wylder.passwordkeep.algorithm;

import java.util.ArrayList;

/**
 * Created by kevin on 8/9/15.
 *
 * an algorithm that takes the site name and base password to create a unique password
 */
public class Algorithm {

    private ArrayList<A> actions;

    /**
     * generate a password variation based of the website's name
     * @param basePassword the password entered by the user
     * @param siteName the website's name
     * @return a password string
     * @throws EvaluationError when the
     */
    public String generatePassword(String basePassword, String siteName) throws EvaluationError {
        for(A action : actions){
            basePassword = action.perform(basePassword, siteName);
        }
        return basePassword;
    }

    /**
     * Used by AlgorithmFactory to create the behaivor
     * @param action
     */
    public void addAction(A action){
        actions.add(action);
    }

    /**
     * Method to construct a human readable string from the list of actions
     */
    @Override
    public String toString(){
        return "";
    }

    /**
     * method to create
     */

}
