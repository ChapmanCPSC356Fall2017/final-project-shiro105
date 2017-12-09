package cpsc356.characterpicker.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import cpsc356.characterpicker.Fragments.EditSingleCharacterFragment;

/**
 * Created by matthewshiroma on 12/8/17.
 *  This brings up a seperate screen that allows the user to edit the character.
 */

public class EditSingleCharacterActivity extends SingleFragmentActivity {

    // All we need to do is to override this method
    // We get the data from the CharacterListActivity and pass it to the ViewSingleCharacterFragment class
    @Override
    public Fragment setFragmentAndAnimation() {
        Bundle data = getIntent().getExtras();
        EditSingleCharacterFragment fragment = new EditSingleCharacterFragment();
        fragment.setArguments(data);

        useAnimations = true;
        return fragment;
    }
}
