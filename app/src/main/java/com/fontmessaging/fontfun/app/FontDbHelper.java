package com.fontmessaging.fontfun.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by charles on 4/24/14.
 * based on http://www.drdobbs.com/database/using-sqlite-on-android/232900584?pgno=2
 */
public class FontDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Font.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";
    private static final String SQL_CREATE_ENTRIES_FONT =
            "CREATE TABLE " + FontEntry.TABLE_NAME_FONT + " ( " +
                    FontEntry._ID + " INTEGER PRIMARY KEY, " +
                    FontEntry.COLUMN_NAME_FONT_NAME + TEXT_TYPE + COMMA_SEP +
                    FontEntry.COLUMN_CURRENT_FONT + " boolean " +
                    " )";
    private static final String SQL_CREATE_ENTRIES_CHAR =
            "CREATE TABLE " + FontEntry.TABLE_NAME_CHAR + " ( " +
                    FontEntry._ID + " INTEGER PRIMARY KEY, " +
                    FontEntry.COLUMN_NAME_FONT_ID + " INTEGER " + COMMA_SEP +
                    FontEntry.COLUMN_NAME_CHAR + TEXT_TYPE +
                    " )";


    public FontDbHelper(Context context){
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
