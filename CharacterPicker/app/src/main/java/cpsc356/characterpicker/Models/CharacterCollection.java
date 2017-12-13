package cpsc356.characterpicker.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cpsc356.characterpicker.Database.CharacterDBHelper;
import cpsc356.characterpicker.R;

/**
 * Created by matthewshiroma on 12/1/17.
 *  This stores all of the CharacterEntities in the app.
 *  This will also work in cotangent with SQLite.
 */

public class CharacterCollection {

    private static CharacterCollection currentCollection;       // Singleton concept
    private List<CharacterEntity> listOfCharacters;             // The reference to all of the items

    // Declares a new set of characters, without initializing them.
    private CharacterCollection()
    {
        listOfCharacters = new ArrayList<CharacterEntity>();
    }

    // Upon calling this for the first time, this makes sure there's only one instance of this collection
    public static CharacterCollection GetInstance()
    {
        if(currentCollection == null)
        {
            currentCollection = new CharacterCollection();
        }
        return currentCollection;
    }

    // Returns the list of characters
    public List<CharacterEntity> getListOfCharacters()
    {
        return listOfCharacters;
    }

    // Obtains a single character from the list, by specifying its ID. Returns null if no character was found.
    public CharacterEntity getCharacterById(String id)
    {
        for(int i = 0; i < listOfCharacters.size(); ++i)
        {
            if(listOfCharacters.get(i).getId().equals(id))
            {
                return listOfCharacters.get(i);
            }
        }
        return null;
    }

    // Returns the index of the character in the list with the given ID. Returns -1 if it can't find the specified one.
    public int findCharacterIndexInList(String id)
    {
        for(int i = 0; i < listOfCharacters.size(); ++i)
        {
            if(listOfCharacters.get(i).getId().equals(id))
            {
                return i;
            }
        }
        return -1;
    }
}
