package com.wylder.passwordkeep.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wylder.passwordkeep.algorithm.Algorithm;
import com.wylder.passwordkeep.algorithm.AlgorithmFactory;
import com.wylder.passwordkeep.algorithm.SyntaxError;

/**
 * Created by kevin on 8/21/15.
 *
 * A class that holds methods to be called on the DatabaseHelper without dealing with the asynchronous
 * calls to
 */
public class DatabaseOperator {

    private boolean databaseReady = false;
    private SQLiteDatabase database = null;
    private OnDatabaseReady listener = null;

    /**
     * Constructor that will spawn a thread and open a database asynchronously for use with the rest
     * of the methods
     * @param ctx the application's context used to open the database
     */
    public DatabaseOperator(final Context ctx, @Nullable final OnDatabaseReady listener){
        this.listener = listener;
        new Thread(){
            @Override
            public void run(){
                DatabaseHelper helper = new DatabaseHelper(ctx);
                database = helper.getWritableDatabase();
                databaseReady = true;
                if (listener != null) {
                    listener.databaseReady(database);
                }
            }
        }.start();
    }

    public DatabaseOperator(Context ctx) {
        this(ctx, null);
    }

    public interface OnDatabaseReady {
        void databaseReady(SQLiteDatabase database);
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
        row.put(DatabaseContract.Algorithms.COLUMN_SELECTED, 0);
        database.insert(DatabaseContract.Algorithms.TABLE_NAME, null, row);
    }

    /**
     * get the algorithm marked as selected in the database
     * @return the algorithm or null if none selected
     */
    public Algorithm getSelectedAlgorithm(){
        if(database == null) {
            Log.e("KevinRuntime", "Cannot get algorithm from database, database not ready");
            return null;
        }
        Cursor cursor = database.query(
                DatabaseContract.Algorithms.TABLE_NAME,                 // table name
                new String[]{                                           // columns
                        DatabaseContract.Algorithms.COLUMN_HEX
                },
                DatabaseContract.Algorithms.COLUMN_SELECTED + " = 1",   // what to match
                null,                                                   // group by
                null,                                                   // having
                null,                                                   // order by
                "1"                                                       // limit
        );
        if(cursor.getCount() == 0){
            return null;
        }
        cursor.moveToFirst();
        String hex = cursor.getString(0);
        try{
            AlgorithmFactory factory = new AlgorithmFactory();
            Algorithm algorithm = factory.buildAlgorithm(hex);
            return algorithm;
        } catch (SyntaxError error) {
            Log.e("KevinRuntime", "Syntax error on selected algorithm: " + error.getMessage());
            return null;
        }
    }

    /**
     * getter method for when it's best to just have the cursor itself.
     * @return the database
     */
    public SQLiteDatabase getDatabase(){
        return database;
    }

    public void setSelected(String algorithmCode) {
        database.execSQL(
                "UPDATE " +
                        DatabaseContract.Algorithms.TABLE_NAME +
                        " SET " + DatabaseContract.Algorithms.COLUMN_SELECTED + " = 0;"
        );
        database.execSQL(
                "UPDATE " +
                        DatabaseContract.Algorithms.TABLE_NAME +
                        " SET " + DatabaseContract.Algorithms.COLUMN_SELECTED + " = 1 " +
                        " WHERE " + DatabaseContract.Algorithms.COLUMN_HEX + " = \"" + algorithmCode + "\""
        );
    }

    public void addWebsite(String name) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.History.COLUMN_SITE, name);
        database.insert(DatabaseContract.History.TABLE_NAME, null, values);
    }

    public void clearHistory() {
        database.execSQL("DELETE FROM " + DatabaseContract.History.TABLE_NAME);
    }

}
