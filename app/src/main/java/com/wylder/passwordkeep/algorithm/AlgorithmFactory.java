package com.wylder.passwordkeep.algorithm;

import android.util.Log;

import com.wylder.passwordkeep.algorithm.functions.add;
import com.wylder.passwordkeep.algorithm.functions.capitalize;
import com.wylder.passwordkeep.algorithm.functions.constant;
import com.wylder.passwordkeep.algorithm.functions.edit;
import com.wylder.passwordkeep.algorithm.functions.eval;
import com.wylder.passwordkeep.algorithm.functions.insert;
import com.wylder.passwordkeep.algorithm.functions.len;
import com.wylder.passwordkeep.algorithm.functions.letter;
import com.wylder.passwordkeep.algorithm.functions.remove;
import com.wylder.passwordkeep.algorithm.functions.rotate;
import com.wylder.passwordkeep.algorithm.functions.select;
import com.wylder.passwordkeep.algorithm.functions.sum;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by kevin on 8/11/15.
 *
 * A class that will generate the algoritm object you're looking for
 */
public class AlgorithmFactory {

    private Stack<Boolean> programCode = new Stack<>();

    /**
     * method to find the next token on the stack
     * @param type the type of the token
     * @return a new instance of the token
     * @throws SyntaxError if the stack runs empty
     */
    private Token readToken(DataType type) throws SyntaxError {
        try {
            boolean bit1 = programCode.pop();
            boolean bit2 = programCode.pop();
            return lookupToken(type, bit1, bit2);
        }catch (EmptyStackException exception){
            throw new SyntaxError("Invalid number of bits, cannot read token");
        }
    }

    /**
     * reads the next 6 bits as a signed int from -31 to 31
     * @return the integer value
     * @throws SyntaxError if the stack runs out
     */
    private int readInt() throws SyntaxError {
        try{
            int value = 0;
            boolean sign = programCode.pop();
            for(int i = 4; i >= 0; i--){
                value += (programCode.pop()) ? Math.pow(2, i) : 0;
            }
            if(!sign) value *= -1;
            return value;
        }catch (EmptyStackException exception){
            throw new SyntaxError("Cannot read int, invalid number of bits");
        }
    }

    /**
     * given a data type and two bits, determine what token is being described
     * @param data the data type of the token
     * @param bit1 the first bit of information.
     * @param bit2 the second bit of information
     * @return the token in the program
     */
    private static Token lookupToken(DataType data, boolean bit1, boolean bit2) throws SyntaxError {
        if(data == DataType.ACTION){         // Datatype A
            if(bit1 && bit2){
                return new add();           // 11 add
            }else if(bit1){
                return new insert();        // 10 insert
            }else if(bit2){
                return new remove();        // 01 remove
            }else {
                return new edit();          // 00 edit
            }
        }else if(data == DataType.CHAR){   // Datatype C
            if(bit1 && bit2){
                return new select();        // 11 select
            }else if(bit1){
                return new rotate();        // 10 rotate
            }else if(bit2){
                return new letter();        // 01 letter
            }else {
                return new capitalize();    // 00 capitalize
            }
        }else if(data == DataType.INT){   // Datatype I
            if(bit1 && bit2){
                return new len();           // 11 len
            }else if(bit1){
                return new sum();           // 10 sum
            }else if(bit2){
                return new eval();          // 01 eval
            }else {
                return new constant();      // 00 constant
            }
        }
        return null;
    }

    /**
     * compiles the programCode int into an Algorithm object
     * @return the Algorithm
     * @throws SyntaxError when the syntax doesn't allow a complete algorithm
     */
    private Algorithm compile() throws SyntaxError {
        Algorithm algorithm = new Algorithm();
        // continue looping over the actions
        while(!programCode.isEmpty()){
            // lookup the first action
            A action = (A) readToken(DataType.ACTION);
            // start the action's token stack
            Stack<Token> tokenStack = new Stack<>();
            tokenStack.push(action);
            // we'll parse until the action closes
            while(!tokenStack.empty()){
                Token token = tokenStack.peek();
                DataType dataType = token.getNextParam();
                if(dataType != DataType.VOID) {
                    // this token is looking for a child
                    Token child = readToken(dataType);
                    tokenStack.push(child);
                } else {
                    // this token is ready to be added to its parent
                    // first check the special case of a constant
                    if(token instanceof constant){
                        ((constant) token).setValue(readInt());
                    }
                    tokenStack.pop();
                    if(tokenStack.empty()){
                        break;
                    }
                    tokenStack.peek().giveParameter(token);
                }
            }
            algorithm.addAction(action);
        }
        return algorithm;
    }

    /**
     * uses the compiler to create an Algorithm
     * @param hexCode the program's binary converted to hexadecimal
     * @return the Algorithm ready to be executed
     */
    public Algorithm buildAlgorithm(String hexCode) throws SyntaxError {
        for (int i = hexCode.length() - 1; i >= 0; i--) {
            int value = Integer.parseInt("" + hexCode.charAt(i), 16);
            for(int j = 0; j < 4; j++){
                programCode.push((value & 0x1) == 1);
                value = value >> 1;
            }
        }
        while(!programCode.pop()){}
        Log.e("KevinRuntime","code length: " + programCode.size());
        Iterator<Boolean> iterator = programCode.iterator();
        StringBuilder builder = new StringBuilder("binary decoded: ");
        while(iterator.hasNext()){
            if(iterator.next()){
                builder.append("1");
            }else{
                builder.append("0");
            }
        }
        Log.e("KevinRuntime", builder.toString());
        return compile();
    }

}
