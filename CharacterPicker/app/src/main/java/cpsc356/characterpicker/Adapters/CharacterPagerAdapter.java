package cpsc356.characterpicker.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.io.IOException;

import cpsc356.characterpicker.Fragments.ViewSingleCharacterFragment;
import cpsc356.characterpicker.Models.CharacterCollection;
import cpsc356.characterpicker.Models.CharacterEntity;

/**
 * Created by matthewshiroma on 12/9/17.
 * This allows for the ViewSingleCharacterFragment to be able to swipe left and right on them to toggle
 * through them.
 */

public class CharacterPagerAdapter extends FragmentStatePagerAdapter {

    // Need to implement this.
    public CharacterPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // We need to make sure we put the proper data into the fragment
    @Override
    public Fragment getItem(int position) {
        // We first get the character that is at this current spot in our list
        CharacterEntity currCharacter = CharacterCollection.GetInstance().getListOfCharacters().get(position);
        ViewSingleCharacterFragment fragment = new ViewSingleCharacterFragment();
        Bundle data = new Bundle();

        // The bitmap is too large, so we need to downsize it so that it can be passed into the fragment
        try
        {
            byte[] picData = CharacterEntity.convertBitMapToByteArray(currCharacter.getProfilePictureBitmap());
            data.putByteArray(ViewSingleCharacterFragment.CHARACTER_PIC_BITMAP, picData);
        }
        catch (IOException e) {}

        // Then, we extract it into the bundle and send it off.
        data.putString(ViewSingleCharacterFragment.CHARACTER_ID_KEY, currCharacter.getId());
        data.putString(ViewSingleCharacterFragment.CHARACTER_NAME_KEY, currCharacter.getName());
        data.putString(ViewSingleCharacterFragment.CHARACTER_DESC_KEY, currCharacter.getDescription());
        data.putInt(ViewSingleCharacterFragment.CHARACTER_AGE_KEY, currCharacter.getAge());
        data.putFloat(ViewSingleCharacterFragment.CHARACTER_RATING_KEY, currCharacter.getAverageRating());
        data.putInt(ViewSingleCharacterFragment.CHARACTER_SEX_PIC_ID, currCharacter.getSexImageID());
        fragment.setArguments(data);

        return fragment;
    }

    // Returns the size of the pager (which is the size of the collection)
    @Override
    public int getCount() {
        return CharacterCollection.GetInstance().getListOfCharacters().size();
    }
}
