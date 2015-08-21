package com.wylder.passwordkeep.security;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kevin on 8/9/15.
 *
 * A database that stores the history with optional usernames, and the BCrypt hash of the basepassword
 */
public class StorageDatabase extends SQLiteOpenHelper {

    public static final int version = 1;
    public static final String name = "database";

    public StorageDatabase(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
