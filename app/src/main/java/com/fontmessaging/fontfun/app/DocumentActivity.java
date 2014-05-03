package com.fontmessaging.fontfun.app;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.view.View;
import android.widget.ShareActionProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class DocumentActivity extends Activity {
    private FontDbHelper db = FontDbHelper.getInstance(this);
    private SQLiteDatabase rdb;
    private SQLiteDatabase wdb;
    protected DocumentView documentImage;
    private int docID;
    private int fontID;
    private String documentText;
    private EditText simpleEditText;
    private ShareActionProvider mShareActionProvider;
    private ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        bar = getActionBar();

        //gets document from MainActivity
        Intent intent = getIntent();
        String docName = intent.getStringExtra("currentDoc");
        docID = intent.getIntExtra("docID", 1);
        rdb = db.getReadableDatabase();
        wdb = db.getWritableDatabase();

        //respectively pulling font ID, doc Contents
        Cursor cur = rdb.query(FontEntry.TABLE_NAME_DOC,
                new String[]{FontEntry.COLUMN_NAME_FONT_ID, FontEntry.COLUMN_NAME_DOC_CONTENTS},
                FontEntry._ID+" = " +docID,
                null, null, null, null);
        cur.moveToFirst();
        Log.d("above error", "cur's first int = " + cur.getInt(0));
        fontID = cur.getInt(0);
        documentText = cur.getString(1);

        
        simpleEditText = (EditText) findViewById(R.id.DocumentText);
        simpleEditText.setText(documentText);


        documentImage = (DocumentView)findViewById(R.id.documentView);
        Log.d("Document Activity", "documentText = " + documentText);
        documentImage.printString(documentText, fontID);
        Log.d("opening document in Doc Activity", "fontID = " + fontID);

        //simpleEditText.setKey/OnKey Listener?


        //spinner listing all fonts.
        final Cursor cursor = rdb.query(FontEntry.TABLE_NAME_FONT, new String[] {FontEntry._ID, FontEntry.COLUMN_NAME_FONT_NAME}, null, null, null, null, null);
        startManagingCursor(cursor);
        SimpleCursorAdapter cAdapter = new SimpleCursorAdapter(this, R.layout.list_entry,cursor,
                new String[]{FontEntry.COLUMN_NAME_FONT_NAME}, new int[] {R.id.name_entry}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        Spinner spinner = (Spinner) findViewById(R.id.fontSpinner);
        spinner.setAdapter(cAdapter);   // Apply the adapter to the spinner
        spinner.setSelection(fontID);

        //changes font used in doc based on spinner item selected
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fontID = adapterView.getSelectedItemPosition();
                documentImage.printFont(fontID);
                Log.d("Saving new font for doc", "fontID = " + fontID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bar.setTitle(docName);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.document_activity_actions, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);

        mShareActionProvider = (ShareActionProvider) item.getActionProvider();

        return true;
    }

    public void saveDoc(MenuItem item){
        //saves documentText only on click of save button...for now
        documentText = simpleEditText.getText().toString();

        ContentValues updatedRow = new ContentValues();
        updatedRow.put(FontEntry.COLUMN_NAME_FONT_ID, fontID);
        updatedRow.put(FontEntry.COLUMN_NAME_DOC_CONTENTS, documentText);
        wdb.update(FontEntry.TABLE_NAME_DOC, updatedRow, FontEntry._ID + " = " + docID, null);

        documentImage.printString(documentText, fontID);
    }

    private void shareImage(){
        Intent image = new Intent(Intent.ACTION_SEND);
        image.setType("image/*");
        if(mShareActionProvider != null)
            mShareActionProvider.setShareIntent(image);
    }

}
