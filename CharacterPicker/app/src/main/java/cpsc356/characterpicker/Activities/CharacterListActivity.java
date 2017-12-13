package cpsc356.characterpicker.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;

import cpsc356.characterpicker.Database.CharacterDBHelper;
import cpsc356.characterpicker.Fragments.CharacterListFragment;
import cpsc356.characterpicker.Fragments.EditSingleCharacterFragment;
import cpsc356.characterpicker.Models.CharacterCollection;
import cpsc356.characterpicker.Models.CharacterEntity;
import cpsc356.characterpicker.R;

/**
 * Created by matthewshiroma on 12/1/17.
 *
 *  This extends the SingleFragmentActivity class, allowing it to do its functions,
 *  along with class specific ones. This also contains a menu option for adding a new character into
 *  the list.
 */

public class CharacterListActivity extends SingleFragmentActivity{

    private CharacterListFragment listFragment;     // A reference to the fragment that this Activity is showing

    // Here, we initialize the database for our use in here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null)
        {
            // When this activity is first created, we read in all of the values from the database
            CharacterDBHelper.GetInstance(this).readAllValues();
        }
    }

    // For this one, we want to set the SingleCopyAbilityFragment
    // We also get the parameters that was passed from CopyAbilityListFragment and
    // pass it to SingleCopyAbilityFragment
    @Override
    public Fragment setFragmentAndAnimation() {
        useAnimations = false;
        listFragment = new CharacterListFragment();
        return listFragment;
    }

    // This creates the menu bar stuff. Here we inflate our custom menu bar layout for the activity.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_character_list, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // This runs whenever the menu is selected. This works by seeing what menu option
    // is picked and depending what is picked, something will happen.
    // In this case, we are seeing if the user selected the "add_character" option.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_addCharacter:
                // We create a new character and set the new Fragment to use that character
                CharacterEntity newCharacter = new CharacterEntity(this);
                CharacterCollection.GetInstance().getListOfCharacters().add(0, newCharacter);

                // We also add this character to the database.
                try
                {
                    CharacterDBHelper.GetInstance(this).addEntry(newCharacter);
                }
                catch(IOException e)
                {
                    Toast.makeText(this, "Unable to add in a character", Toast.LENGTH_SHORT).show();
                }

                // We create our Intent, using a EditSingleCharacterFragment and start it
                // Since this is a brand new character, we make tell the Intent that it is new
                Intent characterIntent = EditSingleCharacterFragment.BuildIntent(newCharacter, this, true);
                startActivity(characterIntent);

                // We return true because this lets the menu know we handled the action.
                // If we didn't do this, the menu button won't do anything!
                return true;
            default:
                return false;
        }
    }
}
