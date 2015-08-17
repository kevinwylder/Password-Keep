package com.wylder.passwordkeep.algorithm;

import android.util.Log;

import java.util.ArrayList;
import java.util.Stack;

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
    public String generatePassword(String basePassword, String siteName) throws EvaluationError {
        StringBuilder builder = new StringBuilder(basePassword);
        for(A action : actions){
            action.perform(builder, siteName);
        }
        return builder.toString();
    }

    /**
     * to generate the hex definition of this algorithm
     */
    public String getHex() throws SyntaxError {
        Stack<Boolean> bits = new Stack<>();
        // add the leading bit that signals a start to the code
        bits.push(true);
        for(A action: actions){
            // fill the stack with bits
            action.getBytecode(bits);
        }
        // convert the queue to hex code
        StringBuilder builder = new StringBuilder();
        StringBuilder log = new StringBuilder("binary encoded: ");
        while (true){
            int value = 0;
            int iterateTo = Math.min(4, bits.size());
            for (int i = 0; i < iterateTo; i++) {
                boolean poll = bits.pop();
                if(poll){
                    log.append("1");
                }else log.append("0");
                value += (poll) ? Math.pow(2, i) : 0;
            }
            builder.append(Integer.toHexString(value));
            if(iterateTo != 4) break;
        }
        Log.e("KevinRuntime",log.toString());
        return builder.toString();
    }

    /**
     * add a complete action to the end of the algorithm.
     * @param action the action to add to the end of the algorithm
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
