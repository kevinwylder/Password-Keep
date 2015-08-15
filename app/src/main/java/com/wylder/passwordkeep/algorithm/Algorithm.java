package com.wylder.passwordkeep.algorithm;

import java.util.ArrayList;

/**
 * Created by kevin on 8/9/15.
 *
 * an algorithm that takes the site name and base password to create a unique password
 */
public class Algorithm {

    private ArrayList<A> actions = new ArrayList<A>();

    /**
     * generate a password variation based of the website's name
     * @param basePassword the password entered by the user
     * @param siteName the website's name
     * @return a password string
     * @throws EvaluationError when the
     */
    public String generatePassword(StringBuilder basePassword, String siteName) throws EvaluationError {
        StringBuilder builder = new StringBuilder(basePassword);
        for(A action : actions){
            action.perform(basePassword, siteName);
        }
        return builder.toString();
    }

    /*
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
        if(actions.size() == 1){
            return actions.get(0).toString();
        } else {
            StringBuilder builder = new StringBuilder("First, ");
            for(int i = 0; i < actions.size(); i++){
                builder.append(actions.get(i).toString());
                if(i < actions.size() - 1){
                    builder.append(", then ");
                }
            }
            return builder.toString();
        }
    }

    /**
     *
     */
    public A[] getActions(){
        return (A[]) actions.toArray();
    }

}
