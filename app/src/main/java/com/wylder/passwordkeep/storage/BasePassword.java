package com.wylder.passwordkeep.storage;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by kevin on 8/21/15.
 *
 * Class to handle storage of the Base Password
 */
public class BasePassword {

    private static final String PREFERENCE_NAME = "prefs";
    private static final String PASSWORD = "password";
    private static final String IMPOSSIBLE_HASH = "%";
    private static final String SALT = "salt";

    private static String basePassword;

    private SharedPreferences preferences;

    public BasePassword(Context ctx){
        preferences = ctx.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if(passwordNotSet()){
            // generate a random salt one time
            SecureRandom random = new SecureRandom();
            BigInteger bigInt = new BigInteger(64, random);
            String salt = bigInt.toString(16);
            preferences.edit()
                    .putString(SALT, salt)
                    .commit();
        }
    }

    /**
     * Global method to get the password the user entered in SetupActivity.
     * @return the password's plain text
     */
    public static String getPassword(){
        return basePassword;
    }

    /**
     * Save the given password as the master base password
     * @param password the password to set
     * @return true if successful
     */
    public boolean setPassword(String password){
        if(password.length() == 0){
            return false;
        }
        try {
            String hash = hashPassword(password);
            return preferences
                    .edit()
                    .putString(PASSWORD, hash)
                    .commit();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return false;
        }
    }

    /**
     * check the given password against the basePassword by comparing salted hashes
     * @param password the password to check
     * @return true if the passwords match
     */
    public boolean checkPassword(String password){
        try {
            String givenHash = hashPassword(password);
            String setHash = preferences.getString(PASSWORD, null);
            return setHash != null && setHash.equals(givenHash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | NullPointerException e){
            return false;
        }
    }

    /**
     * check if setPassword has ever been called.
     * @return true if the password has NEVER been set
     */
    public boolean passwordNotSet(){
        return IMPOSSIBLE_HASH.equals(preferences.getString(PASSWORD, IMPOSSIBLE_HASH));
    }

    /**
     * convert the given password into a hex string salted hash
     * @param password the password to check
     * @return the salted hash
     */
    private String hashPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String salt = preferences.getString(SALT, null);
        password += salt;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes("UTF-16"));
        byte[] hash = md.digest();
        StringBuilder stringBuffer = new StringBuilder();
        for (byte aHash : hash) {
            String hex = Integer.toHexString(0xff & aHash);
            if (hex.length() == 1) stringBuffer.append('0');
            stringBuffer.append(hex);
        }
        return stringBuffer.toString();
    }
}
