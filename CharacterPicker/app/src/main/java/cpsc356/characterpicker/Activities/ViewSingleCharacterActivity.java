package cpsc356.characterpicker.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import cpsc356.characterpicker.Fragments.ViewSingleCharacterFragment;

/**
 * Created by matthewshiroma on 12/8/17.
 *  This activity holds the View for a Single Character, once the user clicks on the cell with the character
 */

public class ViewSingleCharacterActivity extends SingleFragmentActivity {

    // All we need to do is to override this method
    // We get the data from the CharacterListActivity and pass it to the ViewSingleCharacterFragment class
    @Override
    public Fragment setFragmentAndAnimation() {
        Bundle data = getIntent().getExtras();
        ViewSingleCharacterFragment fragment = new ViewSingleCharacterFragment();
        fragment.setArguments(data);

        useAnimations = true;
        return fragment;
    }
}
