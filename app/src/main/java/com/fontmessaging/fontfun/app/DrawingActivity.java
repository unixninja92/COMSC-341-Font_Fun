package com.fontmessaging.fontfun.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class DrawingActivity extends Activity {
    private static final String PREFS = "prefs";
    SharedPreferences mSharedPreferences;
    private FontDbHelper db = new FontDbHelper(this);
    private SQLiteDatabase rdb;
    protected String currentLetter = "a";
    protected DrawingView draw;
    private int fontId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        mSharedPreferences = getSharedPreferences(PREFS, 0);
        rdb = db.getReadableDatabase();

        draw = (DrawingView)this.findViewById(R.id.drawingView);


        TextView name = (TextView)this.findViewById(R.id.fontName);
        Cursor currentFontCursor = rdb.query(
                FontEntry.TABLE_NAME_FONT,
                new String[] {FontEntry.COLUMN_NAME_FONT_NAME},
                FontEntry.COLUMN_CURRENT_FONT+" = 1",
                null, null, null, null);
        currentFontCursor.moveToFirst();
        name.setText(currentFontCursor.getString(0));

        Spinner spinner = (Spinner) findViewById(R.id.size);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.size_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                draw.setSize(adapterView.getSelectedItemPosition());
                Log.e("klkl", ""+adapterView.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void penSelect(View view){
        draw.setPen();
    }

    public void brushSelect(View view){
        draw.setBrush();
    }

    public void eraserSelect(View view){
        draw.setErase();
    }
}
