package cpsc356.characterpicker.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cpsc356.characterpicker.Fragments.ViewSingleCharacterFragment;
import cpsc356.characterpicker.Models.CharacterCollection;
import cpsc356.characterpicker.Models.CharacterEntity;

/**
 * Created by matthewshiroma on 12/9/17.
 * This class allows for the ViewCharacterFragment to be able to swipe left and right on them.
 */

public class CharacterPagerAdapter extends FragmentStatePagerAdapter {

    // Need to implement this.
    public CharacterPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // We take out the character from that position, put it in a fragment and return it.
    @Override
    public Fragment getItem(int position) {
        CharacterEntity currCharacter = CharacterCollection.GetInstance().getListOfCharacters().get(position);
        ViewSingleCharacterFragment newFragment = new ViewSingleCharacterFragment();

        // We extract all of the data we need and send it off
        Bundle data = new Bundle();
        data.putString(ViewSingleCharacterFragment.CHARACTER_ID_KEY, currCharacter.getId());
        data.putString(ViewSingleCharacterFragment.CHARACTER_NAME_KEY, currCharacter.getName());
        data.putString(ViewSingleCharacterFragment.CHARACTER_DESC_KEY, currCharacter.getDescription());
        data.putInt(ViewSingleCharacterFragment.CHARACTER_AGE_KEY, currCharacter.getAge());
        data.putFloat(ViewSingleCharacterFragment.CHARACTER_RATING_KEY, currCharacter.getAverageRating());
        data.putInt(ViewSingleCharacterFragment.CHARACTER_PIC_ID, currCharacter.getProfilePictureID());
        data.putInt(ViewSingleCharacterFragment.CHARACTER_SEX_PIC_ID, currCharacter.getSexImageID());

        newFragment.setArguments(data);
        return newFragment;
    }

    // Returns the size of the pager (which is the size of the collection)
    @Override
    public int getCount() {
        return CharacterCollection.GetInstance().getListOfCharacters().size();
    }
}
