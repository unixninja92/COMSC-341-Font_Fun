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
    Bitmap[] charMaps = new Bitmap[24];
    private Paint drawPaint;
   // private Canvas canvas;
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
            //int i = 0;
            Log.d("Document View", "we are at index i of the text: " + i);
            int currentAscii = (int) docText.charAt(i);
            Log.d("DocumentView", "ascii for " + docText.charAt(i) + " is... " + currentAscii);

            //currently, not numbers. however- using quick hack to make lowercase to uppercase for demo
            if (currentAscii >= 97 && currentAscii <= 122) {
                currentAscii = currentAscii - 32;
            }
            //capital letters
            if (currentAscii >= 65 && currentAscii <= 90) {
                Log.d("Document View", "you are on letter " + i + "and the cursor value is " + xUnwrapped);
                row = xUnwrapped / screenMargin;
                if (charMaps[currentAscii - 65] != null) {
                    canvas.drawBitmap(charMaps[currentAscii - 65], (xUnwrapped % screenMargin), (CHAR_HEIGHT * row + paragraphSpacing * row), null);
                }
                xUnwrapped = xUnwrapped + CHAR_WIDTH + FAKE_KERN;

            }
        }
    }

    public void init(){
        Character currentLetter = 'A';
        int fontID = 1;
        String fileName;

        for(int i = 0; i < charMaps.length; i++){
            fileName = fontID+"_"+(((int)currentLetter)+i)+".png";
            File letter = new File("/data/data/com.fontmessaging.fontfun.app/files/"+fileName);
            if(letter.exists())
                charMaps[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeFile("/data/data/com.fontmessaging.fontfun.app/files/"+fileName), CHAR_WIDTH, CHAR_HEIGHT, false);
            else
                charMaps[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeFile("/data/data/com.fontmessaging.fontfun.app/files/"+fontID+"_67.png"), CHAR_WIDTH, CHAR_HEIGHT, false);
            if (charMaps[i] == null){
                Log.d("Document View", "bMap null");
            }else{
                Log.d("Document View", "bMap filled");
            }
        }
    }

    public void printString(String newDocText){
        docText = newDocText;
        invalidate();
    }





}
