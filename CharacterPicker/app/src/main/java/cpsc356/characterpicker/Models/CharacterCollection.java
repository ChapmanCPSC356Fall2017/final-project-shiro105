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

    // An overload method that takes in a context, which is used to initially set up the list values.
    private CharacterCollection(Context ctx)
    {
        listOfCharacters = new ArrayList<CharacterEntity>();
        SetUpInitialSet(ctx);
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

    // Same as above call, except this one takes in a context, which is used to initialize the set of characters
    // Should be called when the app is opened for the first time
    public static CharacterCollection GetInstance(Context ctx)
    {
        if(currentCollection == null)
        {
            currentCollection = new CharacterCollection(ctx);
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

    // Sets up default entities in the list, using the passed in context to convert the resource id to a bitmap
    private void SetUpInitialSet(Context ctx)
    {
        CharacterEntity c1 = new CharacterEntity("Kirby", 1, "m", "Super Tuff Pink Puff");
        CharacterEntity c2 = new CharacterEntity("Batman", 45, "m", "I'm batman");
        CharacterEntity c3 = new CharacterEntity("Jennifer Ly", 21, "f", "A student at Chapman University");
        CharacterEntity c4 = new CharacterEntity("Kylo Ren", 24, "m", "I am your father?");
        CharacterEntity c5 = new CharacterEntity("Lara Croft", 21, "f", "A badass of a woman");
        CharacterEntity c6 = new CharacterEntity("Link", 21, "m", "HAIYAAAAAAA");
        CharacterEntity c7 = new CharacterEntity("Marc Karam", 21, "m", "Another student at Chapman University");
        CharacterEntity c8 = new CharacterEntity("Matthew Shiroma", 21, "m", "The creator of this beautiful app.");
        CharacterEntity c9 = new CharacterEntity("Barack Obama", 56, "m", "Thanks, Obama.");
        CharacterEntity c10 = new CharacterEntity("Mario", 40, "m", "It's-a-mi");
        CharacterEntity c11 = new CharacterEntity("Pikachu", 1, "b", "PIKA PIKA");
        CharacterEntity c12 = new CharacterEntity("Pit", 21, "m", "HIYAYAYAYAYAY!");
        CharacterEntity c13 = new CharacterEntity("Ryan Burns", 25, "m", "The professor of Android Development");
        CharacterEntity c14 = new CharacterEntity("Donald Trump", 71, "m", "MAKE AMERICA GREAT AGAIN?????");
        CharacterEntity c15 = new CharacterEntity("Tommy Wiseau", 62, "m", "Ahahaha, what a story. OH HAI MARK.");

        c1.setProfilePictureBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_kirby));
        c2.setProfilePictureBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_batman));
        c3.setProfilePictureBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_jen));
        c4.setProfilePictureBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_kylo));
        c5.setProfilePictureBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_lara));
        c6.setProfilePictureBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_link));
        c7.setProfilePictureBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_marc));
        c8.setProfilePictureBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_matthew));
        c9.setProfilePictureBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_obama));
        c10.setProfilePictureBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_mario));
        c11.setProfilePictureBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_pikachu));
        c12.setProfilePictureBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_pit));
        c13.setProfilePictureBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_ryan));
        c14.setProfilePictureBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_trump));
        c15.setProfilePictureBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_tommy));

        listOfCharacters.add(c1);
        listOfCharacters.add(c2);
        listOfCharacters.add(c3);
        listOfCharacters.add(c4);
        listOfCharacters.add(c5);
        listOfCharacters.add(c6);
        listOfCharacters.add(c7);
        listOfCharacters.add(c8);
        listOfCharacters.add(c9);
        listOfCharacters.add(c10);
        listOfCharacters.add(c11);
        listOfCharacters.add(c12);
        listOfCharacters.add(c13);
        listOfCharacters.add(c14);
        listOfCharacters.add(c15);

        try
        {
            CharacterDBHelper.GetInstance(ctx).addEntry(c1);
            CharacterDBHelper.GetInstance(ctx).addEntry(c2);
            CharacterDBHelper.GetInstance(ctx).addEntry(c3);
            CharacterDBHelper.GetInstance(ctx).addEntry(c4);
            CharacterDBHelper.GetInstance(ctx).addEntry(c5);
            CharacterDBHelper.GetInstance(ctx).addEntry(c6);
            CharacterDBHelper.GetInstance(ctx).addEntry(c7);
            CharacterDBHelper.GetInstance(ctx).addEntry(c8);
            CharacterDBHelper.GetInstance(ctx).addEntry(c9);
            CharacterDBHelper.GetInstance(ctx).addEntry(c10);
            CharacterDBHelper.GetInstance(ctx).addEntry(c11);
            CharacterDBHelper.GetInstance(ctx).addEntry(c12);
            CharacterDBHelper.GetInstance(ctx).addEntry(c13);
            CharacterDBHelper.GetInstance(ctx).addEntry(c14);
            CharacterDBHelper.GetInstance(ctx).addEntry(c15);
        }
        catch (IOException e)
        {
            Toast.makeText(ctx, "An error has occured adding in the default set", Toast.LENGTH_SHORT).show();
        }
    }
}
