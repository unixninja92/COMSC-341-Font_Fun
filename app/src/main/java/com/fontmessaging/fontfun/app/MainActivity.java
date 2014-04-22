package com.fontmessaging.fontfun.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/*
* font name based on http://www.raywenderlich.com/56109/make-first-android-app-part-2
 */
public class MainActivity extends Activity {
    private static final String PREFS = "prefs";
    private static final String PREF_CURRENT_FONT_NAME = "current_font";
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = getSharedPreferences(PREFS,0);
    }

    public void createFont(View view) {
        AlertDialog.Builder nameFont = new AlertDialog.Builder(this);


        nameFont.setTitle("New Font");
        nameFont.setMessage("Name of new font:");

        final EditText nameInput = new EditText(this);
        nameFont.setView(nameInput);

        nameFont.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = nameInput.getText().toString();

                SharedPreferences.Editor e = mSharedPreferences.edit();
                e.putString(PREF_CURRENT_FONT_NAME, name);
                e.commit();
                Intent draw = new Intent(MainActivity.this, DrawingActivity.class);
                startActivity(draw);
            }
        });

        nameFont.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        nameFont.show();


    }

}

