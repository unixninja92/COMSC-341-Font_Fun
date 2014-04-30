package com.fontmessaging.fontfun.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;

public class DocumentActivity extends Activity {
    private FontDbHelper db = new FontDbHelper(this);
    private SQLiteDatabase rdb;
    private int docID;
    private int fontID;
    protected File currentFile;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        //gets document from MainActivity
        Intent intent = getIntent();
        String docName = intent.getStringExtra("currentDoc");
        docID = intent.getIntExtra("docID", 1);
        rdb = db.getReadableDatabase();

        //respectively pulling font ID, doc Contents
        Cursor cur = rdb.query(FontEntry.TABLE_NAME_DOC,
                new String[]{FontEntry.COLUMN_NAME_FONT_ID, FontEntry.COLUMN_NAME_DOC_CONTENTS},
                FontEntry._ID+" = " +docID,
                null, null, null, null);
        cur.moveToFirst();
        fontID = cur.getInt(0);

        TextView name = (TextView)this.findViewById(R.id.documentName);
        name.setText(docName);

        final EditText simpleEditText = (EditText) findViewById(R.id.DocumentText);
        simpleEditText.setText(cur.getString(1));

        simpleEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                String strValue = simpleEditText.getText().toString();
//                Log.d(DEBUG_TAG, "User set EditText value to " + strValue);
            }
        });
        String strValue = simpleEditText.getText().toString();

        //spinner listing all fonts.
        Cursor cursor = rdb.query(FontEntry.TABLE_NAME_FONT, new String[] {FontEntry._ID, FontEntry.COLUMN_NAME_FONT_NAME}, null, null, null, null, null);
        startManagingCursor(cursor);
        SimpleCursorAdapter cAdapter = new SimpleCursorAdapter(this, R.layout.list_entry,cursor,
                new String[]{FontEntry.COLUMN_NAME_FONT_NAME}, new int[] {R.id.name_entry}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        Spinner spinner = (Spinner) findViewById(R.id.fontSpinner);
        spinner.setAdapter(cAdapter);   // Apply the adapter to the spinner

    }

}
