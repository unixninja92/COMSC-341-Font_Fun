package com.fontmessaging.fontfun.app;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;


public class DocumentActivity extends ActionBarActivity {
    private static final String PREFS = "prefs";
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        mSharedPreferences = getSharedPreferences(PREFS,0);

        TextView name = (TextView)this.findViewById(R.id.documentName);
        name.setText(mSharedPreferences.getString("current_doc", "New Document"));


        final EditText simpleEditText = (EditText) findViewById(R.id.DocumentText);


        simpleEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                String strValue = simpleEditText.getText().toString();
//                Log.d(DEBUG_TAG, "User set EditText value to " + strValue);
            }
        });

        String strValue = simpleEditText.getText().toString();



        Spinner spinner = (Spinner) findViewById(R.id.fontSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
                // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

}
