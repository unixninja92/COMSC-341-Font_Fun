package com.fontmessaging.fontfun.app;

import android.provider.BaseColumns;

/**
 * Created by charles on 4/24/14.
 */
public abstract class FontEntry implements BaseColumns {
    public static final String TABLE_NAME_FONT = "font";
    public static final String COLUMN_NAME_FONT_NAME = "fontname";

    public static final String TABLE_NAME_DOC = "docs";
    public static final String COLUMN_NAME_FONT_ID = "fontid";
    public static final String COLUMN_NAME_DOC_NAME = "docname";
    public static final String COLUMN_NAME_DOC_CONTENTS = "doccontents";
}
