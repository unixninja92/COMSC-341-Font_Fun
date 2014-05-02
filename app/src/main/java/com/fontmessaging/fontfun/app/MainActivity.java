package com.fontmessaging.fontfun.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.TextView;

import java.io.File;

/*
* font name based on http://www.raywenderlich.com/56109/make-first-android-app-part-2
*
* cursor and cursor adapter based on
* https://thinkandroid.wordpress.com/2010/01/09/simplecursoradapters-and-listviews/
* and http://www.vogella.com/tutorials/AndroidListView/article.html#cursor
*
* context menus base on
* http://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/
 */
public class MainActivity extends Activity{
    private FontDbHelper db = FontDbHelper.getInstance(this);
    private SQLiteDatabase rdb;
    private SQLiteDatabase wdb;
    protected AlertDialog.Builder notExists;
    protected ListView fontListView;
    protected ListView docListView;
    protected Cursor listOfFonts;
    protected Cursor listOfDocs;
    protected SimpleCursorAdapter fontAdapter;
    protected SimpleCursorAdapter docAdapter;
    private int numFonts;
    private int contextId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rdb = db.getReadableDatabase();
        wdb = db.getWritableDatabase();
        notExists = new AlertDialog.Builder(this);

        notExists.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        //gets list of fonts
        listOfFonts = rdb.query(FontEntry.TABLE_NAME_FONT, new String[] {FontEntry._ID, FontEntry.COLUMN_NAME_FONT_NAME}, null, null, null, null, null);

        //gets list of documents
        listOfDocs = rdb.query(FontEntry.TABLE_NAME_DOC, new String[] {FontEntry._ID, FontEntry.COLUMN_NAME_DOC_NAME}, null, null, null, null, null);

        //puts list of fonts in ListView
        fontAdapter = new SimpleCursorAdapter(this, R.layout.list_entry, listOfFonts, new String[]{FontEntry.COLUMN_NAME_FONT_NAME}, new int[] {R.id.name_entry}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        fontListView = (ListView) this.findViewById(R.id.fontListView);
        fontListView.setAdapter(fontAdapter);
        registerForContextMenu(fontListView);
        fontListView.invalidate();
        numFonts = fontListView.getCount();
        //puts list of docs in ListView
        docAdapter = new SimpleCursorAdapter(this, R.layout.list_entry, listOfDocs, new String[]{FontEntry.COLUMN_NAME_DOC_NAME}, new int[] {R.id.name_entry}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        docListView = (ListView) this.findViewById(R.id.docListView);
        docListView.setAdapter(docAdapter);
        registerForContextMenu(docListView);


        //opens fonts clicked
        fontListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listOfFonts.moveToPosition(i);
                openFont(listOfFonts.getString(1), i);
            }
        });

        docListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (fontListView.getCount() == 0){
                    notExists.setMessage("Please create a font before starting or editing a document.");
                    notExists.show();
                }else {
                    listOfDocs.moveToPosition(i);
                    openDoc(listOfDocs.getString(1), i);
                }
            }
        });
    }

    public void openFont(String selectedItem, int pos) {
        Log.d("selected string", selectedItem);
        Intent draw = new Intent(MainActivity.this, DrawingActivity.class);
        draw.putExtra("currentFont", selectedItem);
        draw.putExtra("fontID", getFontId(selectedItem));
        Log.d("fontID", getFontId(selectedItem) + "");
        startActivity(draw);
    }

    public void openDoc(String selectedItem, int pos) {
        Log.d("selected string", selectedItem);
        Intent type = new Intent(MainActivity.this, DocumentActivity.class);
        type.putExtra("currentDoc", selectedItem);
        type.putExtra("docID", getDocId(selectedItem));
        startActivity(type);
    }

    public void deleteFont(int id) {
        Log.d("delete font id", id + "");
        wdb.delete(FontEntry.TABLE_NAME_FONT, FontEntry._ID + " = " + (id), null);
        queryFonts();
        String filep1 = id+"_";
        String filep2 = ".png";
        for(int c = 48; c <= 122; c++){
            File f = new File(getFilesDir(),filep1+c+filep2);
            if(f.exists()) {
                Log.d("Font Delete", f.getName());
                f.delete();
            }
        }
        numFonts --;
    }

    public void deleteDoc(int id) {
        wdb.delete(FontEntry.TABLE_NAME_DOC, FontEntry._ID + " = " + (id), null);
        queryDocs();
    }

    /*
    *   Starts DrawingActivity and quires user for new font name. If font already exists user is
    *   notified and kept at main screen.
     */
    public void createFont(View view) {
        AlertDialog.Builder nameFont = new AlertDialog.Builder(this);

        nameFont.setTitle("New Font");
        nameFont.setMessage("Name of new font:");

        final EditText nameInput = new EditText(this);
        nameFont.setView(nameInput);

        //checks if font name already exists. if it doesn't then the new font is created.
        // If it does already exist, then a dialog is opened to notify user and take them
        // back to main screen.
        nameFont.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = nameInput.getText().toString();

                Cursor cur = rdb.query(FontEntry.TABLE_NAME_FONT,
                        new String[]{FontEntry.COLUMN_NAME_FONT_NAME},
                        FontEntry.COLUMN_NAME_FONT_NAME+" = '"+name+"'",
                        null, null, null, null);

                if(cur.getCount() == 0) {
                    ContentValues fontName = new ContentValues();
                    fontName.put(FontEntry.COLUMN_NAME_FONT_NAME, name);
                    wdb.insert("font", null, fontName);
                    numFonts ++;

                    queryFonts();

                    int fontID = getFontId(name);
                    Log.d(name, fontID+"");
                    Intent draw = new Intent(MainActivity.this, DrawingActivity.class);
                    draw.putExtra("currentFont", name);
                    draw.putExtra("fontID", fontID);
                    startActivity(draw);
                }
                else {
                    notExists.setMessage("Font Already Exists");
                    notExists.show();
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

    //checks if document name already exists. if it doesn't then the new document is created.
    // If it does already exist, then a dialog is opened to notify user and take them
    // back to main screen.
    public void createDocument(View view){
        if (numFonts == 0){
            notExists.setMessage("Please create a font before starting a document.");
            notExists.show();
            return;
        }

        //create Dialog box New Document
        AlertDialog.Builder nameDoc = new AlertDialog.Builder(this);
        nameDoc.setTitle("New Document");
        nameDoc.setMessage("Name of new document:");

        final EditText nameInput = new EditText(this); //textbox for new doc name
        nameDoc.setView(nameInput);

        nameDoc.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String docNameGiven = nameInput.getText().toString();
                        Cursor cur = rdb.query(FontEntry.TABLE_NAME_DOC,
                                new String[]{FontEntry.COLUMN_NAME_DOC_NAME},
                                FontEntry.COLUMN_NAME_DOC_NAME + " = '" + docNameGiven + "'",
                                null, null, null, null);
                        if (cur.getCount() == 0) {
                            //add to table.
                            ContentValues docEntry = new ContentValues();
                            docEntry.put(FontEntry.COLUMN_NAME_DOC_NAME, docNameGiven);
                            docEntry.put(FontEntry.COLUMN_NAME_FONT_ID, 0);
                            docEntry.put(FontEntry.COLUMN_NAME_DOC_CONTENTS, "");
                            wdb.insert(FontEntry.TABLE_NAME_DOC, null, docEntry);
                            queryDocs();
                            //open Doc from all documents
//                            Cursor id = rdb.query(FontEntry.TABLE_NAME_DOC,
//                                    new String[]{FontEntry.COLUMN_NAME_DOC_NAME},
//                                    FontEntry.COLUMN_NAME_DOC_NAME + " = '" + docNameGiven + "'",
//                                    null, null, null, null);
//                            id.moveToFirst();
                            int docID = getDocId(docNameGiven);
                            Intent type = new Intent(MainActivity.this, DocumentActivity.class);
                            type.putExtra("currentDoc", docNameGiven);
                            type.putExtra("docID", docID);
                            startActivity(type);
                        }else{
                            notExists.setMessage("Document Already Exists");
                            notExists.show();
                        }
                    }
                });

        nameDoc.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        nameDoc.show();
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        String[] menuItems = getResources().getStringArray(R.array.menu_array);
        contextId = v.getId();
        switch (contextId){
            case R.id.fontListView:
                String font = ((TextView)((LinearLayout) info.targetView).getChildAt(0)).getText().toString();
                menu.setHeaderTitle("Font: "+font);
                for (int i = 0; i<menuItems.length; i++) {
                    menu.add(Menu.NONE, i, i, menuItems[i]);
                }
                break;
            case R.id.docListView:
                String doc = ((TextView)((LinearLayout) info.targetView).getChildAt(0)).getText().toString();
                menu.setHeaderTitle("Document: "+doc);
                for (int i = 0; i<menuItems.length; i++) {
                    menu.add(Menu.NONE, i, i, menuItems[i]);
                }
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String name = ((TextView) ((LinearLayout) info.targetView).getChildAt(0)).getText().toString();
        switch (contextId) {
            case R.id.fontListView:
                switch (item.getItemId()) {
                    case 0://rename
                        Log.d("context menu", info.position + "");
                        break;
                    case 1://delete
//                        Log.d("context menu", "delete");
                        deleteFont(getFontId(name));
                        break;
                }
                break;
            case R.id.docListView:
                switch (item.getItemId()) {
                    case 0://rename
                        break;
                    case 1://delete
                        deleteDoc(getDocId(name));
                        break;
                }
                break;
        }
//        }
        return true;
    }

    private void queryFonts(){
        listOfFonts = rdb.query(FontEntry.TABLE_NAME_FONT, new String[] {FontEntry._ID, FontEntry.COLUMN_NAME_FONT_NAME}, null, null, null, null, null);
        fontAdapter.swapCursor(listOfFonts);
    }

    private void queryDocs(){
        listOfDocs = rdb.query(FontEntry.TABLE_NAME_DOC, new String[] {FontEntry._ID, FontEntry.COLUMN_NAME_DOC_NAME}, null, null, null, null, null);
        docAdapter.swapCursor(listOfDocs);
    }

    private int getFontId(String name){
        Cursor id = rdb.query(FontEntry.TABLE_NAME_FONT,
                new String[]{FontEntry._ID},
                FontEntry.COLUMN_NAME_FONT_NAME + " = '" + name + "'",
                null, null, null, null);
        id.moveToFirst();
        return id.getInt(0);
    }

    private int getDocId(String name){
        Cursor id = rdb.query(FontEntry.TABLE_NAME_DOC,
                new String[]{FontEntry._ID},
                FontEntry.COLUMN_NAME_DOC_NAME + " = '" + name + "'",
                null, null, null, null);
        id.moveToFirst();
        return id.getInt(0);
    }
}

