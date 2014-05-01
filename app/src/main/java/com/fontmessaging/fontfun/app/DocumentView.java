package com.fontmessaging.fontfun.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by Nicole on 4/30/2014.
 */
public class DocumentView extends View {
    //private Hashmap of <String, Bitmap> not needed?
    public DocumentView(Context context){
        super(context);
        //TODO Auto-generated constructor stub??

        init();
    }

    @Override
    protected void onDraw(Canvas canvas){
        //TODO Auto-generated method stub??

        int caller = getIntent().getIntExtra("button", 0); //is this for finding character?
        Bitmap bMap = null;
        switch (caller){
            case ...:
                //hashmapped bMap.mStore.get(tag);
                canvas.drawBitmap(bMap,screenPts.x, screenPts.y-50, null);
                bMap.recycle();
                bMap = null;
                break;
            //more cases...
        }
        super.onDraw(canvas);
    }

    public void init(){
        //loop through ASCII values and decode all non-empty files?
        Bitmap bMap = BitmapFactory.decodeFile("fontId+\"_\"+(int)currentLetter+\".png\"");
        //example code adds to hashmap here.
    }
}
