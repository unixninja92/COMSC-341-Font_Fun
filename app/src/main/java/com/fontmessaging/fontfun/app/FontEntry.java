package com.fontmessaging.fontfun.app;

import android.provider.BaseColumns;

/**
 * Created by charles on 4/24/14.
 */
public abstract class FontEntry implements BaseColumns {
    public static final String TABLE_NAME_FONT = "font";
    public static final String COLUMN_NAME_FONT_ID = "fontid";
    public static final String COLUMN_NAME_FONT_NAME = "fontname";
    public static final String TABLE_NAME_CHAR = "chars";
//    public static final String COLUMN_NAME_CHAR_ID = "charid";
    public static final String COLUMN_NAME_CHAR = "char";
}
