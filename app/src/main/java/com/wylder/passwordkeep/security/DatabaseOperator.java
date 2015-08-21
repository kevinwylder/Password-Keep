package com.wylder.passwordkeep.security;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by kevin on 8/21/15.
 *
 * A class that holds methods to be called on the DatabaseHelper without dealing with the asynchronous
 * calls to
 */
public class DatabaseOperator {

    private boolean databaseReady = false;
    private SQLiteDatabase database = null;

    /**
     * Constructor that will spawn a thread and open a database asynchronously for use with the rest
     * of the methods
     * @param ctx the application's context used to open the database
     */
    public DatabaseOperator(final Context ctx){
        new Thread(){
            @Override
            public void run(){
                DatabaseHelper helper = new DatabaseHelper(ctx);
                database = helper.getWritableDatabase();
                databaseReady = true;
            }
        }.start();
    }

    /**
     * reports whether this object is ready to be used
     * @return true if methods are ready to be called
     */
    public boolean databaseReady(){
        return databaseReady;
    }

}
