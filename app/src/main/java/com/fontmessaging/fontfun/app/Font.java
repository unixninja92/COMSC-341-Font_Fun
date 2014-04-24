package com.fontmessaging.fontfun.app;

import android.graphics.Bitmap;

import java.lang.reflect.Array;
import java.util.HashMap;

/**
 * Created by charles on 4/23/14.
 */
public class Font {
    private String name, folderName;
    private HashMap<Character, Char> characters;
    private int id;
    private static int lastID = 0;
    public Font(String newName){
        id = lastID;
        lastID++;
        name = newName;
        folderName = id+name;
        characters = new HashMap<Character, Char>();
    }

    public void saveChar(char ch, Bitmap image){
        Character c = (Character) ch;
        if(characters.containsKey(c))
            characters.get(c).saveImage(image);
        else {
            Char newC = new Char(folderName,ch);
            characters.put(c, newC);
        }
    }

    public Bitmap loadChar(char ch){
        return characters.get(((Character)ch)).getImage();
    }

    public void rename(String newName){
        name = newName;
        folderName = id+name;
        //rename folder
    }
}
