package com.wylder.passwordkeep.algorithm;

import com.wylder.passwordkeep.algorithm.functions.*;

import java.util.Stack;

/**
 * Created by kevin on 8/11/15.
 *
 * A class that will generate the algoritm object you're looking for
 */
public class AlgorithmFactory {

    private int programCode;

    /**
     * bit shift the int one bit and return whether or not it was a 1
     * @return the value of the next bit
     */
    private boolean readBit(){
        boolean bit = (programCode & 0x01) == 1;
        programCode = programCode << 1;
        return bit;
    }

    /**
     * given a data type and two bits, determine what token is being described
     * @param data the data type of the token
     * @param bit1 the first bit of information.
     * @param bit2 the second bit of information
     * @return the token in the program
     */
    private static Token lookupToken(DataType data, boolean bit1, boolean bit2) throws SyntaxError {
        if(data == DataType.A){         // Datatype A
            if(bit1 && bit2){
                return new add();           // 11 add
            }else if(bit1 && !bit2){
                return new insert();        // 10 insert
            }else if(!bit1 && bit2){
                return new remove();        // 01 remove
            }else {
                return new edit();          // 00 edit
            }
        }else if(data == DataType.C){   // Datatype C
            if(bit1 && bit2){
                return new select();        // 11 select
            }else if(bit1 && !bit2){
                return new rotate();        // 10 rotate
            }else if(!bit1 && bit2){
                return new letter();        // 01 letter
            }else {
                return new capitalize();    // 00 capitalize
            }
        }else if(data == DataType.I){   // Datatype I
            if(bit1 && bit2){
                return new len();           // 11 len
            }else if(bit1 && !bit2){
                return new sum();           // 10 sum
            }else if(!bit1 && bit2){
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
        while(programCode > 1){
            // lookup the first action
            boolean bit1 = readBit();
            boolean bit2 = readBit();
            A action = (A) lookupToken(DataType.A, bit1, bit2);
            // start the stack
            Stack<Token> tokenStack = new Stack<>();
            tokenStack.push(action);
            // we'll parse until the action closes
            while(!tokenStack.empty()){
                Token token = tokenStack.peek();
                bit1 = readBit();
                bit2 = readBit();
                DataType dataType = token.getNextParam();
                if(dataType != DataType.V) {
                    // this token is looking for a child
                    Token child = lookupToken(dataType, bit1, bit2);
                    tokenStack.add(child);
                } else {
                    // this token is ready to be added to its parent
                    // first check the special case of a constant
                    if(token instanceof constant){
                        // we need to read the next 5 bits to make an int from 0 to 31
                        int value = 0;
                        for(int i = 0; i < 5; i++){
                            value += (readBit()) ? Math.pow(2, i) : 0;
                        }
                        // the 6th bit determines the sign
                        value *= (readBit()) ? -1 : 1;
                        ((constant) token).setValue(value);
                    }
                    tokenStack.pop();                   // pop the current token off
                    Token parent = tokenStack.peek();   // lookup the parent token
                    if(parent != null){
                        parent.giveParameter(token);
                    }
                }
            }
            algorithm.addAction(action);
        }
        return algorithm;
    }

    /**
     * uses the compiler to create an Algorithm
     * @param programInt the program's binary converted to an integer
     * @return the Algorithm ready to be executed
     */
    public Algorithm buildAlgorithm(int programInt) throws SyntaxError {
        programCode = programInt;
        return compile();
    }

    /**
     * uses the compiler to create an Algorithm
     * @param hexCode the program's binary converted to hexadecimal
     * @return the Algorithm ready to be executed
     */
    public Algorithm buildAlgorithm(String hexCode) throws SyntaxError {
        programCode = 12;
        return compile();
    }

}
