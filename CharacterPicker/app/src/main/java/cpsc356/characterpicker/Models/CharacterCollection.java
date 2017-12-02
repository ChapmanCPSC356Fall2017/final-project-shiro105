package cpsc356.characterpicker.Models;

import java.util.ArrayList;
import java.util.HashMap;
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

    private CharacterCollection()
    {
        listOfCharacters = new ArrayList<CharacterEntity>();
        SetUpInitialSet();
    }

    public static CharacterCollection GetInstance()
    {
        if(currentCollection == null)
        {
            currentCollection = new CharacterCollection();
        }
        return currentCollection;
    }

    public List<CharacterEntity> getListOfCharacters()
    {
        return listOfCharacters;
    }

    private void SetUpInitialSet()
    {
        CharacterEntity c1 = new CharacterEntity("Kirby", 1, 'm', "Super Tuff Pink Puff");
        c1.setPictureID(R.drawable.image_kirby);
        listOfCharacters.add(c1);
    }


}
