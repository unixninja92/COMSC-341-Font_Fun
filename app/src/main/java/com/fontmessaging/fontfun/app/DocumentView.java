package com.fontmessaging.fontfun.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.io.File;

/**
 * Created by Nicole on 4/30/2014.
 */
public class DocumentView extends View {
    static final int CHAR_WIDTH = 100;
    static final int CHAR_HEIGHT = 100;
    static final int FAKE_KERN = -50;
    Bitmap[] capMaps = new Bitmap[24]; //capital letter maps
    Bitmap[] lowMaps = new Bitmap[24]; //lower letter maps
    Bitmap[] numMaps = new Bitmap[10]; //numbers
    //the symbols bitmap array would be special to accommodate skipping through Asciis
    private Paint drawPaint;
   // private Canvas canvas;
    int fontID = 0;
    private String docText = "DOC TEXT HERE";

    public DocumentView(Context context, AttributeSet attrs){
        super(context, attrs);
        drawPaint = new Paint();
        drawPaint.setColor(0xFF000000);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
       // this.canvas = canvas;

        //how to find screen widths for given device? investigate later: int pixel=this.getWindowManager().getDefaultDisplay().getWidth()
        int screenWidth = 800;
        int screenMargin = screenWidth - (CHAR_WIDTH + FAKE_KERN);
        int xUnwrapped = 0;
        int paragraphSpacing = -30;
        int row = 0;

        for(int i = 0; i < docText.length(); i++) {
            Log.d("Document View", "we are at index " + i + " of the text");

            int currentAscii = (int) docText.charAt(i);
            Log.d("DocumentView", "ascii for " + docText.charAt(i) + " is... " + currentAscii);

            //numbers
            if (currentAscii >= 48 && currentAscii <= 57) {
                row = xUnwrapped / screenMargin;
                if (numMaps[currentAscii - 48] != null) {
                    canvas.drawBitmap(numMaps[currentAscii - 48], (xUnwrapped % screenMargin), (CHAR_HEIGHT * row + paragraphSpacing * row), null);
                }
                xUnwrapped = xUnwrapped + CHAR_WIDTH + FAKE_KERN;
            }

            //lowercase letters
            else if (currentAscii >= 97 && currentAscii <= 122) {
                row = xUnwrapped / screenMargin;
                if (lowMaps[currentAscii - 97] != null) {
                    canvas.drawBitmap(lowMaps[currentAscii - 97], (xUnwrapped % screenMargin), (CHAR_HEIGHT * row + paragraphSpacing * row), null);
                }
                xUnwrapped = xUnwrapped + CHAR_WIDTH + FAKE_KERN;
            }

            //capital letters
            else if (currentAscii >= 65 && currentAscii <= 90) {
                row = xUnwrapped / screenMargin;
                if (capMaps[currentAscii - 65] != null) {
                    canvas.drawBitmap(capMaps[currentAscii - 65], (xUnwrapped % screenMargin), (CHAR_HEIGHT * row + paragraphSpacing * row), null);
                }
                xUnwrapped = xUnwrapped + CHAR_WIDTH + FAKE_KERN;
            }
            else{ //if character is not supported, make a space
                Log.d("Document View", "space or unknown character");
                xUnwrapped = xUnwrapped + CHAR_WIDTH + FAKE_KERN;
            }
        }
    }

    public void init(){
        Log.d("Document view", "inside init. your font id is set to " + fontID);
        Character capA = 'A';
        Character lowA = 'a';
        Character zero = '0';
        String fileName;

        for(int i = 0; i < capMaps.length; i++) {
            fileName = fontID + "_" + (((int) capA) + i) + ".png";
            File letter = new File("/data/data/com.fontmessaging.fontfun.app/files/" + fileName);
            if (letter.exists()){
                capMaps[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeFile("/data/data/com.fontmessaging.fontfun.app/files/" + fileName), CHAR_WIDTH, CHAR_HEIGHT, false);
            }
            if (capMaps[i] == null){
                Log.d("Document View", "bMap null");
            }else{
                Log.d("Document View", "bMap filled");
            }
        }
        for(int i = 0; i < lowMaps.length; i++) {
            fileName = fontID + "_" + (((int) lowA) + i) + ".png";
            File letter = new File("/data/data/com.fontmessaging.fontfun.app/files/" + fileName);
            if (letter.exists()){
                lowMaps[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeFile("/data/data/com.fontmessaging.fontfun.app/files/" + fileName), CHAR_WIDTH, CHAR_HEIGHT, false);
            }
            if (lowMaps[i] == null){
                Log.d("Document View", "bMap null");
            }else{
                Log.d("Document View", "bMap filled");
            }
        }
        for(int i = 0; i < numMaps.length; i++) {
            fileName = fontID + "_" + (((int) zero) + i) + ".png";
            File letter = new File("/data/data/com.fontmessaging.fontfun.app/files/" + fileName);
            if (letter.exists()){
                numMaps[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeFile("/data/data/com.fontmessaging.fontfun.app/files/" + fileName), CHAR_WIDTH, CHAR_HEIGHT, false);
            }
            if (numMaps[i] == null){
                Log.d("Document View", "bMap null");
            }else{
                Log.d("Document View", "bMap filled");
            }
        }
    }

    public void printFont (int newFontID){
        fontID = newFontID;
        Log.d("Document view", "font id set to " + fontID);
        init();
        invalidate();
    }

    public void printString(String newDocText, int newFontID){
        fontID = newFontID;
        docText = newDocText;
        Log.d("Document view", "font id set to " + fontID);
        invalidate();
    }





}
