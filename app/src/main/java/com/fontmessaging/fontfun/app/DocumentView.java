package com.fontmessaging.fontfun.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Nicole on 4/30/2014.
 */
public class DocumentView extends View {
   // static String path = Context.getFilesDir().getPath();
    Bitmap[] charMaps = new Bitmap[2];
    private Paint drawPaint;

    public DocumentView(Context context, AttributeSet attrs){
        super(context, attrs);
        drawPaint = new Paint();
        drawPaint.setColor(0xFF000000);

        init();
    }

    @Override
    protected void onDraw(Canvas canvas){
        //TODO Auto-generated method stub??
//        canvas.drawCircle(50, 50, 50, null);

        //int caller = getIntent().getIntExtra("button", 0); //is this for finding character?
        //switch (caller){
            //case ...:
                //hashmapped bMap.mStore.get(tag);
         //       canvas.drawBitmap(bMap,100, 100, null);
                //bMap.recycle();
                //break;
            //more cases...
       // }
        super.onDraw(canvas);
       // canvas.drawCircle(50, 50, 50, drawPaint);

       // canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(charMaps[1], 0 / 2, 0 / 2, null);
        //canvas.drawBitmap(bMap, 100, 100, null);

    }

    public void init(){
        Character currentLetter = 'A';
        int fontID = 1;
        String fileName;

        for(int i = 0; i < charMaps.length; i++){
            fileName = fontID+"_"+(((int)currentLetter)+i)+".png";
            charMaps[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeFile("/data/data/com.fontmessaging.fontfun.app/files/"+fileName), 100, 100, false);

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
