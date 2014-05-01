package com.fontmessaging.fontfun.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Nicole on 4/30/2014.
 */
public class DocumentView extends View {
    static final int CHAR_WIDTH = 100;
    static final int CHAR_HEIGHT = 100;
    static final int FAKE_KERN = -50;
    Bitmap[] charMaps = new Bitmap[24];
    private Paint drawPaint;

    public DocumentView(Context context, AttributeSet attrs){
        super(context, attrs);
        drawPaint = new Paint();
        drawPaint.setColor(0xFF000000);

        init();
    }

    @Override
    protected void onDraw(Canvas canvas){
        //int caller = getIntent().getIntExtra("button", 0); //is this for finding character?
        //for tomorrow(setting up all asciis! and getting intent with info from database)

        super.onDraw(canvas);
        //how to find screen widths for given device? investigate later: int pixel=this.getWindowManager().getDefaultDisplay().getWidth()
        int screenWidth = 800;
        int screenMargin = screenWidth - (CHAR_WIDTH + FAKE_KERN);
        int xUnwrapped = 0;
        int paragraphSpacing = -30;
        int row = 0;

        for (int i = 0; i < charMaps.length; i++){
                row = xUnwrapped/screenMargin;
                //Log.d("Document View", xUnwrapped + "/" + screenMargin + "=" + row);
                canvas.drawBitmap(charMaps[i], (xUnwrapped % screenMargin), (CHAR_HEIGHT*row+paragraphSpacing*row), null);
                xUnwrapped = xUnwrapped + CHAR_WIDTH + FAKE_KERN;
                //Log.d("Document View", "you are on letter " + i + "and the cursor value is " + xUnwrapped);
        }

        //canvas.drawBitmap(charMaps[1], 0, 0, null);
    }

    public void init(){
        Character currentLetter = 'A';
        int fontID = 1;
        String fileName;

        for(int i = 0; i < charMaps.length; i++){
            fileName = fontID+"_"+(((int)currentLetter)+i)+".png";
            charMaps[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeFile("/data/data/com.fontmessaging.fontfun.app/files/"+fileName), CHAR_WIDTH, CHAR_HEIGHT, false);
            if (charMaps[i] == null){
                Log.d("Document View", "bMap null");
            }else{
                Log.d("Document View", "bMap filled");
            }
        }

        /*//loop through ASCII values and decode all non-empty files?
        Character currentLetter = 'A';
        int fontID = 1;
        String fileName = fontID+"_"+(int)currentLetter+".png";
        bMap = BitmapFactory.decodeFile("/data/data/com.fontmessaging.fontfun.app/files/"+fileName);
        //bMap = BitmapFactory.decodeFile(Context.getFilesDir().getPath());
        //example code adds to hashmap here.*/


    }
}
