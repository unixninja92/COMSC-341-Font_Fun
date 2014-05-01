package com.fontmessaging.fontfun.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;

import java.io.File;
import java.io.FileOutputStream;

public class DrawingActivity extends FragmentActivity {
    private FontDbHelper db = new FontDbHelper(this);
    private SQLiteDatabase rdb;
    protected Character currentLetter;
    protected DrawingView draw;
    protected TextView currentChar;
    private Button pen;
    private Button brush;
    private Button eraser;
    private final int selectedColor = 0xFF2FBBF7;
    private final int deselectedColor = 0xFFFFFFFF;
    private int fontId;
    protected File  cur;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
//        getActionBar().hide();
        //gets font name from MainActivity
        Intent intent = getIntent();
        String fontName = intent.getStringExtra("currentFont");
        fontId = intent.getIntExtra("fontID", 1);
        rdb = db.getReadableDatabase();

        draw = (DrawingView)this.findViewById(R.id.drawingView);
        currentChar = (TextView) this.findViewById(R.id.currentLetter);
        pen = (Button) this.findViewById(R.id.penButton);
        brush = (Button) this.findViewById(R.id.brushButton);
        eraser = (Button) this.findViewById(R.id.eraserButton);


        pen.setTextColor(selectedColor);

        //sets default for current letter


        //Displays font name
        TextView name = (TextView)this.findViewById(R.id.fontName);
//        Cursor currentFontCursor = rdb.query(
//                FontEntry.TABLE_NAME_FONT,
//                new String[] {FontEntry.COLUMN_NAME_FONT_NAME},
//                FontEntry.COLUMN_CURRENT_FONT+" = 1",
//                null, null, null, null);
//        currentFontCursor.moveToFirst();
        name.setText(fontName);

        //setup size spinner with different sizes
        Spinner spinner = (Spinner) findViewById(R.id.size);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.size_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //changes size of stroke of current tool based on spinner item selected
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                draw.setSize(adapterView.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        changeChar('A', false);
    }

    public void changeChar(char newChar, boolean keep) {
        if(keep)
            save(draw);
        currentLetter = newChar;
        currentChar.setText(currentLetter.toString());
        fileName = fontId+"_"+(int)currentLetter+".png";
        Log.d("drawing activity", "and the filename is " + fileName);
        try {
            cur = new File(getFilesDir(), fileName);
            if(cur.exists())
                draw.loadChar(cur.getPath(), true);
            else
                draw.loadChar(cur.getPath(), false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void save(View view){
        FileOutputStream curOut;
        try {
            curOut = openFileOutput(fileName, Context.MODE_PRIVATE);
            draw.setDrawingCacheEnabled(true);
            draw.saveBitmap(curOut);
            curOut.close();
            draw.setDrawingCacheEnabled(false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //On touch of tool buttons, sets that as current tool
    public void penSelect(View view){
        pen.setTextColor(selectedColor);
        brush.setTextColor(deselectedColor);
        eraser.setTextColor(deselectedColor);
        draw.setPen();
    }
    public void brushSelect(View view){
        brush.setTextColor(selectedColor);
        pen.setTextColor(deselectedColor);
        eraser.setTextColor(deselectedColor);
        draw.setBrush();
    }
    public void eraserSelect(View view){
        eraser.setTextColor(selectedColor);
        pen.setTextColor(deselectedColor);
        brush.setTextColor(deselectedColor);
        draw.setErase();
    }
    //TODO Have way of showing which tool is currently selected.
}
