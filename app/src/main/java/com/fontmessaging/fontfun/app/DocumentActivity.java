package com.fontmessaging.fontfun.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuItem;


public class DocumentActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        String strValue = simpleEditText.getText().toString();
    }

    final EditText simpleEditText = (EditText) findViewById(R.id.DocumentText);
    simpleEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
        public void onFocusChange(View v, boolean hasFocus) {
            String strValue = simpleEditText.getText().toString();
            Log.d(DEBUG_TAG, "User set EditText value to " + strValue);
        }
    });



}
