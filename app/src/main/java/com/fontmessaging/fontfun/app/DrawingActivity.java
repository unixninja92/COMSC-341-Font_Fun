package com.fontmessaging.fontfun.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DrawingActivity extends Activity {
    private static final String PREFS = "prefs";
    SharedPreferences mSharedPreferences;
    protected String currentLetter = "a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        mSharedPreferences = getSharedPreferences(PREFS,0);

        TextView name = (TextView)this.findViewById(R.id.fontName);
        name.setText(mSharedPreferences.getString("current_font", "New Font"));
    }

    public void penSelect(View view){

    }

    public void brushSelect(View view){

    }

    public void eraserSelect(View view){

    }
}
