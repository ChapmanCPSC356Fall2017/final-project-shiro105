package cpsc356.characterpicker.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import cpsc356.characterpicker.Database.CharacterDBHelper;
import cpsc356.characterpicker.Fragments.EditSingleCharacterFragment;
import cpsc356.characterpicker.Models.CharacterCollection;

/**
 * Created by matthewshiroma on 12/8/17.
 *  This brings up a separate screen that allows the user to edit the character.
 *  This also implements the SingleFragmentActivity.
 *  This class also handles calling the saving of the attributes of the new character.
 */

public class EditSingleCharacterActivity extends SingleFragmentActivity {

    private EditSingleCharacterFragment fragment;   // A reference to the fragment that is being modified.
    private boolean hasUnsavedChanges = true;       // Used to run the logic on the back button

    // Here, we make sure our ActionBar can have its options selected out
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

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

    // Here, we only handle the up button being selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                // When the user hits this button, they are prompted to see if they want to proceed.
                createAndDisplayAlert();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // This is the back button on the bottom of the screen
    @Override
    public void onBackPressed() {
        // This is the same idea here
        createAndDisplayAlert();
        if(hasUnsavedChanges == false)
        {
            super.onBackPressed();
        }
    }

    // Handles what the SaveButton does on the given fragment
    public void saveAttributes(View view)
    {
        fragment.saveCharacterAttributes();
    }

    // Handles getting a new picture for the character.
    public void selectNewPicture(View view)
    {
        fragment.getNewImage();
    }

    // Displays a warning alert when exiting the screen with unsaved changes
    // If they say yes, the activity ends. If not, nothing happens.
    private void createAndDisplayAlert()
    {
        AlertDialog.Builder alertBuild = new AlertDialog.Builder(this);
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int whichOne) {
                switch(whichOne)
                {
                    case DialogInterface.BUTTON_POSITIVE:
                        hasUnsavedChanges = false;
                        finishActivity();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        hasUnsavedChanges = true;
                        dialogInterface.dismiss();
                        break;
                }
            }
        };

        alertBuild.setMessage("Discard changes?");
        alertBuild.setCancelable(false);
        alertBuild.setPositiveButton("Yes", listener);
        alertBuild.setNegativeButton("No", listener);
        AlertDialog newAlert = alertBuild.create();
        newAlert.show();
    }

    // Finished the current activity
    private void finishActivity()
    {
        fragment.removeNewCharacter();
        this.finish();
    }
}
