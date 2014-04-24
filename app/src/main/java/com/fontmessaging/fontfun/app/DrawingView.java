package com.fontmessaging.fontfun.app;

import android.content.SharedPreferences;
import android.os.Debug;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

/**
 * Created by charles on 4/20/14.
 * Based on http://code.tutsplus.com/tutorials/android-sdk-create-a-drawing-app-interface-creation--mobile-19021
 */
public class DrawingView extends View {
    private static final String PREFS = "prefs";
    SharedPreferences mSharedPreferences;

    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;//for later saving the image

    private boolean pen = true;
    private boolean brush = false;
    private boolean erase=false;

    private int size = 10;

    int fontName;

    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing(){
    //get drawing area setup for interaction
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(0xFF000000);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(size);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    //view given size
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
    //draw view
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    //detect user touch
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void setErase() {
        erase = true;
        pen = false;
        brush = false;

        drawPaint.setColor(0xFFFFFFFF);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setAntiAlias(true);
    }

    public void setBrush() {
        brush = true;
        pen = false;
        erase = false;

        drawPaint.setColor(0xFF000000);
        drawPaint.setStrokeJoin(Paint.Join.BEVEL);
        drawPaint.setStrokeCap(Paint.Cap.BUTT);
        drawPaint.setAntiAlias(false);
    }

    public void setPen() {
        pen = true;
        brush = false;
        erase = false;

        drawPaint.setColor(0xFF000000);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setAntiAlias(true);
    }

    public void setSize(int newSize) {
        switch (newSize){
            case 0: size = 10;
                break;
            case 1: size = 20;
                break;
            case 2: size = 40;
                break;
            default: size = 10;
        }
        drawPaint.setStrokeWidth(size);
    }
}
