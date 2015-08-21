package com.wylder.passwordkeep.security;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    /**
     * Adds the given algorithm to the database as a user created Algorithm. If the database is not
     * yet ready this function does nothing and logs the action
     * @param name the name of the algorithm
     * @param hex the hex of the algorithm
     */
    public void addAlgorithm(String name, String hex) {
        if(database == null) {
            Log.e("KevinRuntime", "Cannot add algorithm to database, database not ready");
            return ;
        }
        ContentValues row = new ContentValues();
        row.put(DatabaseContract.Algorithms.COLUMN_NAME, name);
        row.put(DatabaseContract.Algorithms.COLUMN_HEX, hex);
        row.put(DatabaseContract.Algorithms.COLUMN_CREATED, 1);
        database.insert(DatabaseContract.Algorithms.TABLE_NAME, null, row);
    }

}
