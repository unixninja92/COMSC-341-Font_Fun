package com.fontmessaging.fontfun.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.nio.DoubleBuffer;
import java.util.ArrayList;

/*
* font name based on http://www.raywenderlich.com/56109/make-first-android-app-part-2
* cursor and cursor adapter based on
* https://thinkandroid.wordpress.com/2010/01/09/simplecursoradapters-and-listviews/
* and http://www.vogella.com/tutorials/AndroidListView/article.html#cursor
 */
public class MainActivity extends Activity {
    private FontDbHelper db = new FontDbHelper(this);
    private SQLiteDatabase rdb;
    private SQLiteDatabase wdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rdb = db.getReadableDatabase();
        wdb = db.getWritableDatabase();

        Cursor cursor = rdb.query(FontEntry.TABLE_NAME_FONT, new String[] {FontEntry._ID, FontEntry.COLUMN_NAME_FONT_NAME}, null, null, null, null, null);
        startManagingCursor(cursor);

        SimpleCursorAdapter cAdapter = new SimpleCursorAdapter(this, R.layout.list_entry, cursor, new String[]{FontEntry.COLUMN_NAME_FONT_NAME}, new int[] {R.id.name_entry}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        ListView list = (ListView) this.findViewById(R.id.listView);
        list.setAdapter(cAdapter);

    }

    public void createFont(View view) {
        AlertDialog.Builder nameFont = new AlertDialog.Builder(this);
        final AlertDialog.Builder fontExists = new AlertDialog.Builder(this);


        nameFont.setTitle("New Font");
        nameFont.setMessage("Name of new font:");

        final EditText nameInput = new EditText(this);
        nameFont.setView(nameInput);


        nameFont.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = nameInput.getText().toString();
                try{//checks if font name already exists. if it dosen't it throws an error which is caught that creates new font. If it does already exist, then opens dialog to notify user and take back to main screen.
                    Cursor cur = rdb.query(FontEntry.TABLE_NAME_FONT,new String[]{FontEntry.COLUMN_NAME_FONT_NAME},FontEntry.COLUMN_NAME_FONT_NAME+" = '"+name+"'", null, null, null, null);
                    fontExists.setMessage("Font Already Exists");
                    fontExists.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {}
                    });
                    fontExists.show();
                }catch (SQLiteException e){
                    ContentValues fontName = new ContentValues();
                    fontName.put(FontEntry.COLUMN_NAME_FONT_NAME, name);
                    wdb.insert("font", null, fontName);

                    Intent draw = new Intent(MainActivity.this, DrawingActivity.class);
                    draw.putExtra("currentFont", name);
                    startActivity(draw);
                }
            }
        });

        nameFont.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        nameFont.show();


    }

    public void createDocument(View view){

        AlertDialog.Builder nameDoc = new AlertDialog.Builder(this);

        //on create section of main activity-populate doc part **
        nameDoc.setTitle("New Document");
        nameDoc.setMessage("Name of new document:");

        final EditText nameInput = new EditText(this);
        nameDoc.setView(nameInput);

        nameDoc.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String docName = nameInput.getText().toString();

                        ContentValues docEntry = new ContentValues();
                        docEntry.put(FontEntry.COLUMN_NAME_DOC_NAME, docName);
                        docEntry.put(FontEntry.COLUMN_NAME_FONT_ID, 1);
                        docEntry.put(FontEntry.COLUMN_NAME_DOC_CONTENTS, "");
                        wdb.insert(FontEntry.TABLE_NAME_DOC, null, docEntry);

                        Intent doc = new Intent(MainActivity.this, DocumentActivity.class);
                        doc.putExtra("currentDoc",docName);
                        startActivity(doc);
                    }
                });

        nameDoc.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        nameDoc.show();

    }

}

