package com.wylder.passwordkeep.security;

import android.provider.BaseColumns;

/**
 * Created by kevin on 8/21/15.
 *
 * contract that defines the schema of DatabaseHelper
 */
public final class DatabaseContract {

    public DatabaseContract(){}

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PasswordKeep.db";

    /**
     * class that defines the Algorithm table. This table will hold all the hex codes that
     * are built into the app or created.
     */
    public static abstract class Algorithms implements BaseColumns {
        public static final String TABLE_NAME = "algorithms";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_HEX = "hex";
        public static final String COLUMN_CREATED = "userMade";
    }

    /**
     * defines the history table. This is basically a list of sites the user has previously tested
     * against their algorithm
     */
    public static abstract class History implements BaseColumns {
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_SITE = "siteName";
        public static final String COLUMN_LAST_USED = "lastUsed";
    }


}
