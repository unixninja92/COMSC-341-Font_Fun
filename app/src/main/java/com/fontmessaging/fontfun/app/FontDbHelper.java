package com.fontmessaging.fontfun.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by charles on 4/24/14.
 */
public class FontDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Font.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";
    private static final String SQL_CREATE_ENTRIES_FONT =
            "CREATE TABLE " + FontEntry.TABLE_NAME_FONT + " ( " +
                    FontEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FontEntry.COLUMN_NAME_FONT_ID + " INTEGER AUTOINCREMENT" + COMMA_SEP +
                    FontEntry.COLUMN_NAME_FONT_NAME + TEXT_TYPE +
                    " )";
    private static final String SQL_CREATE_ENTRIES_CHAR =
            "CREATE TABLE " + FontEntry.TABLE_NAME_CHAR + " ( " +
                    FontEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FontEntry.COLUMN_NAME_CHAR_ID + " INTEGER AUTOINCREMENT " + COMMA_SEP +
                    FontEntry.COLUMN_NAME_FONT_ID + " INTEGER AUTOINCREMENT " + COMMA_SEP +
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
