package cpsc356.characterpicker.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import cpsc356.characterpicker.Fragments.CharacterListFragment;
import cpsc356.characterpicker.Fragments.EditSingleCharacterFragment;
import cpsc356.characterpicker.Models.CharacterCollection;
import cpsc356.characterpicker.Models.CharacterEntity;
import cpsc356.characterpicker.R;

/**
 * Created by matthewshiroma on 12/1/17.
 *
 *  This extends the SingleFragmentActivity class, allowing it to do its functions, along with class specific ones.
 *
 *  This also contains the menu options when the user wants to add in a new character
 */

public class CharacterListActivity extends SingleFragmentActivity{

    private CharacterListFragment listFragment;


    // For this one, we want to set the SingleCopyAbilityFragment
    // We also get the parameters that was passed from CopyAbilityListFragment and pass it to SingleCopyAbilityFragment
    @Override
    public Fragment setFragmentAndAnimation() {
        useAnimations = false;
        listFragment = new CharacterListFragment();
        return listFragment;
    }

    // This creates the menu bar stuff. Here we inflate our custom menu bar layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_character_list, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // This runs whenever the menu is selected. This works by seeing what menu option is picked and depending what is picked,
    // something will happen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_addCharacter:
                // We create a new character and set the new Fragment to use that character
                CharacterEntity newCharacter = new CharacterEntity();
                CharacterCollection.GetInstance().getListOfCharacters().add(0, newCharacter);

                Intent characterIntent = EditSingleCharacterFragment.BuildIntent(newCharacter, this);
                startActivity(characterIntent);

                return true;    // We did this because this lets the menu know we handled the action
            default:
                return false;
        }
    }
}
