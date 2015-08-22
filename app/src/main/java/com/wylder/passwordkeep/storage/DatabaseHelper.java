package com.wylder.passwordkeep.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kevin on 8/9/15.
 *
 * A helper for the database that stores the history and algorithm hexes
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * Constructor for a class that can call getWritableDatabase and getReadableDatabase. call async.
     * @param context the context of the application containing this database
     */
    public DatabaseHelper(Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + DatabaseContract.Algorithms.TABLE_NAME + " (" +
                DatabaseContract.Algorithms._ID + " INTEGER PRIMARY KEY, " +
                DatabaseContract.Algorithms.COLUMN_NAME + " TEXT, " +
                DatabaseContract.Algorithms.COLUMN_HEX + " TEXT, " +
                DatabaseContract.Algorithms.COLUMN_CREATED + " INTEGER, " +
                DatabaseContract.Algorithms.COLUMN_SELECTED + " INTEGER );"
        );
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + DatabaseContract.History.TABLE_NAME + " (" +
                DatabaseContract.History._ID + " INTEGER PRIMARY KEY, " +
                DatabaseContract.History.COLUMN_SITE + " TEXT, " +
                DatabaseContract.History.COLUMN_LAST_USED + " INTEGER );"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(
                "DROP TABLE IF EXISTS " + DatabaseContract.History.TABLE_NAME
        );
        sqLiteDatabase.execSQL(
                "DROP TABLE IF EXISTS " + DatabaseContract.Algorithms.TABLE_NAME
        );
        onCreate(sqLiteDatabase);
    }




}
