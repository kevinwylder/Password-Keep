package com.wylder.passwordkeep.algorithm;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by kevin on 8/9/15.
 *
 * an algorithm that takes the site name and base password to create a unique password
 */
public class Algorithm {

    private ArrayList<A> actions = new ArrayList<>();

    /**
     * generate a password variation based of the website's name
     * @param basePassword the password entered by the user
     * @param siteName the website's name
     * @return a password string
     * @throws EvaluationError when the
     */
    public String generatePassword(String basePassword, String siteName) throws EvaluationError, SyntaxError {
        if(siteName.length() == 0){
            throw new EvaluationError("Cannot have empty site name");
        }
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
        while (true){
            int value = 0;
            int iterateTo = Math.min(4, bits.size());
            for (int i = 0; i < iterateTo; i++) {
                value += (bits.pop()) ? Math.pow(2, i) : 0;
            }
            builder.append(Integer.toHexString(value));
            if(bits.size() == 0) break;
        }
        return builder.reverse().toString();
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
    public String getDescription() {
        try {
            if(actions.size() == 1){
                StringBuilder builder = new StringBuilder();
                actions.get(0).explainOperation(builder);
                return builder.toString();
            } else {
                StringBuilder builder = new StringBuilder("First, ");
                for(int i = 0; i < actions.size(); i++){
                    actions.get(i).explainOperation(builder);
                    if(i < actions.size() - 1){
                        builder.append(". Next ");
                    }
                }
                return builder.toString();
            }
        } catch (SyntaxError error) {
            return "Syntax error: " + error.getMessage();
        }
    }

    /**
     * return the actions in this algorithm
     * @return
     */
    public ArrayList<A> getActions(){
        return actions;
    }

}
