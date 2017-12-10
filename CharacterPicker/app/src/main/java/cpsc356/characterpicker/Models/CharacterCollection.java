package cpsc356.characterpicker.Models;

import java.util.ArrayList;
import java.util.List;

import cpsc356.characterpicker.R;

/**
 * Created by matthewshiroma on 12/1/17.
 *  This stores all of the CharacterEntities in the app.
 *  This will also work in cotangent with SQLite.
 */

public class CharacterCollection {

    private static CharacterCollection currentCollection;
    private List<CharacterEntity> listOfCharacters;

    // Creates a new collection of characters and sets up their initial values
    private CharacterCollection()
    {
        listOfCharacters = new ArrayList<CharacterEntity>();
        SetUpInitialSet();
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

    // Sets up default entities in the list.
    private void SetUpInitialSet()
    {
        CharacterEntity c1 = new CharacterEntity("Kirby", 1, "m", "Super Tuff Pink Puff", R.drawable.image_kirby);
        CharacterEntity c2 = new CharacterEntity("Batman", 45, "m", "I'm batman", R.drawable.image_batman);
        CharacterEntity c3 = new CharacterEntity("Jennifer Ly", 21, "f", "A student at Chapman University", R.drawable.image_jen);
        CharacterEntity c4 = new CharacterEntity("Kylo Ren", 24, "m", "I am your father?", R.drawable.image_kylo);
        CharacterEntity c5 = new CharacterEntity("Lara Croft", 21, "f", "A badass of a woman", R.drawable.image_lara);
        CharacterEntity c6 = new CharacterEntity("Link", 21, "m", "HAIYAAAAAAA", R.drawable.image_link);
        CharacterEntity c7 = new CharacterEntity("Marc Karam", 21, "m", "Another student at Chapman University", R.drawable.image_marc);
        CharacterEntity c8 = new CharacterEntity("Matthew Shiroma", 21, "m", "The creator of this beautiful app.", R.drawable.image_matthew);
        CharacterEntity c9 = new CharacterEntity("Barack Obama", 56, "m", "Thanks, Obama.", R.drawable.image_obama);
        CharacterEntity c10 = new CharacterEntity("Mario", 40, "m", "It's-a-mi", R.drawable.image_mario);
        CharacterEntity c11 = new CharacterEntity("Pikachu", 1, "b", "PIKA PIKA", R.drawable.image_pikachu);
        CharacterEntity c12 = new CharacterEntity("Pit", 21, "m", "HIYAYAYAYAYAY!", R.drawable.image_pit);
        CharacterEntity c13 = new CharacterEntity("Ryan Burns", 25, "m", "The professor of Android Development", R.drawable.image_ryan);
        CharacterEntity c14 = new CharacterEntity("Donald Trump", 71, "m", "MAKE AMERICA GREAT AGAIN?????", R.drawable.image_trump);
        CharacterEntity c15 = new CharacterEntity("Tommy Wiseau", 62, "m", "Ahahaha, what a story. OH HAI MARK.", R.drawable.image_tommy);

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
    }


}
