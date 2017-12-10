package cpsc356.characterpicker.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import cpsc356.characterpicker.Fragments.EditSingleCharacterFragment;

/**
 * Created by matthewshiroma on 12/8/17.
 *  This brings up a separate screen that allows the user to edit the character.
 *  This also implements the SingleFragmentActivity.
 *  This class also handles calling the saving of the attributes of the new character.
 */

public class EditSingleCharacterActivity extends SingleFragmentActivity {

    private EditSingleCharacterFragment fragment;   // A reference to the fragment that is being modified.

    // All we need to do is to override this method
    // We get the data from the CharacterListActivity and pass it to the ViewSingleCharacterFragment class
    @Override
    public Fragment setFragmentAndAnimation() {
        Bundle data = getIntent().getExtras();
        fragment = new EditSingleCharacterFragment();
        fragment.setArguments(data);

        useAnimations = true;
        return fragment;
    }

    // Handles the SaveButton on the given fragment
    public void saveAttributes(View view)
    {
        fragment.saveCharacterAttributes();
    }

    // Handles getting a new picture for the character
    public void selectNewPicture(View view)
    {
        fragment.getNewImage();
    }
}
