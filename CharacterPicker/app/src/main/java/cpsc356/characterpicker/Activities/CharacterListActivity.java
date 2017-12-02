package cpsc356.characterpicker.Activities;

import android.support.v4.app.Fragment;

import cpsc356.characterpicker.Fragments.CharacterListFragment;

/**
 * Created by matthewshiroma on 12/1/17.
 *
 *  This extends the SingleFragmentActivity class, allowing it to do its functions, along with class specific ones
 */

public class CharacterListActivity extends SingleFragmentActivity{

    // For this one, we want to set the SingleCopyAbilityFragment
    // We also get the parameters that was passed from CopyAbilityListFragment and pass it to SingleCopyAbilityFragment
    @Override
    public Fragment setFragmentAndAnimation() {
        useAnimations = false;
        return new CharacterListFragment();
    }
}
