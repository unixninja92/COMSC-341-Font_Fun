package com.fontmessaging.fontfun.app;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by charles on 4/23/14.
 */
public class Char {

    private String folder;
    private char character;
    private Bitmap image;

    public Char(String folder, char newChar){
        this.folder = folder;
        character = newChar;
    }

    public char getCharacter() {
        return character;
    }

    public Bitmap getImage(){
        return image;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }


    public void saveImage(Bitmap newImage){
        newImage.compress(Bitmap.CompressFormat.PNG, 100, null);
        //save Bitmap to file named char's ascii value.png or some other format
    }

}
