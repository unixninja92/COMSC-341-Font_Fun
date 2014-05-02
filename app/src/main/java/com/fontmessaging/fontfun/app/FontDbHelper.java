package com.fontmessaging.fontfun.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by charles on 4/24/14.
 * based on http://www.drdobbs.com/database/using-sqlite-on-android/232900584?pgno=2
 *
 * getInstance method based on
 * https://stackoverflow.com/questions/21496221/sqlite-connection-object-leaked-android
 */
public class FontDbHelper extends SQLiteOpenHelper {
    private static FontDbHelper mInstance = null;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Font.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";
    private static final String SQL_CREATE_ENTRIES_FONT =
            "CREATE TABLE " + FontEntry.TABLE_NAME_FONT + " ( " +
                    FontEntry._ID + " INTEGER PRIMARY KEY, " +
                    FontEntry.COLUMN_NAME_FONT_NAME + TEXT_TYPE +
                    " )";
    private static final String SQL_CREATE_ENTRIES_CHAR =
            "CREATE TABLE " + FontEntry.TABLE_NAME_DOC + " ( " +
                    FontEntry._ID + " INTEGER PRIMARY KEY, " +
                    FontEntry.COLUMN_NAME_FONT_ID + " INTEGER " + COMMA_SEP +
                    FontEntry.COLUMN_NAME_DOC_NAME + TEXT_TYPE + COMMA_SEP +
                    FontEntry.COLUMN_NAME_DOC_CONTENTS + TEXT_TYPE +
                    " )";


    public static FontDbHelper getInstance(Context ctx) {
        if(mInstance == null) {
            mInstance = new FontDbHelper(ctx);
        }
        return mInstance;
    }

    private FontDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES_FONT);
        db.execSQL(SQL_CREATE_ENTRIES_CHAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }


}
