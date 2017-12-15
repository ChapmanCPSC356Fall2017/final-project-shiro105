package cpsc356.characterpicker.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import cpsc356.characterpicker.Activities.EditSingleCharacterActivity;
import cpsc356.characterpicker.Database.CharacterDBHelper;
import cpsc356.characterpicker.Models.CharacterCollection;
import cpsc356.characterpicker.Models.CharacterEntity;
import cpsc356.characterpicker.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by matthewshiroma on 12/8/17.
 *  This displays the edit character screen, allowing the user to edit the character.
 *  Similar to ViewSingleCharacterFragment
 */

public class EditSingleCharacterFragment extends Fragment {

    // This is the arbitrary value we are checking to see if we managed to select a photo
    private static final int SELECT_PHOTO = 100;

    // All of the widgets that are on this fragment
    private ImageView editCharacterProfile;
    private EditText editCharacterName;
    private EditText editCharacterAge;
    private EditText editCharacterDescription;
    private Spinner editCharacterSex;

    private boolean isNewCharacter;             // Is the current character just made?
    private CharacterEntity currentCharacter;   // A reference to the character in here

    // Allows us to build an Intent for this fragment
    // The third parameter tells us if the character is brand new
    public static Intent BuildIntent(CharacterEntity character, Context ctx, boolean isNewCharacter)
    {
        Intent newIntent = new Intent(ctx, EditSingleCharacterActivity.class);

        // The bitmap is too large, so we need to downsize it so that it can be passed
        try
        {
            byte[] picData = CharacterEntity.convertBitMapToByteArray(character.getProfilePictureBitmap());
            newIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_PIC_BITMAP, picData);
        }
        catch (IOException e)
        {
            Toast.makeText(ctx, "An error with the image has occurred.", Toast.LENGTH_SHORT).show();
        }

        // We make sure we pass in all of the important information into this fragment
        newIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_ID_KEY, character.getId());
        newIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_NAME_KEY, character.getName());
        newIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_AGE_KEY, character.getAge());
        newIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_DESC_KEY, character.getDescription());
        newIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_SEX_INDEX, character.getSexIndex());

        // We do a special item placement if the character is new or not, in order to handle discarding
        // new changes
        if(isNewCharacter == true)
        {
            newIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_IS_NEW, true);
        }
        else
        {
            newIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_IS_NEW, false);
        }

        return newIntent;
    }

    // We first get the character data and save it for later
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String currentCharacterID = getArguments().getString(ViewSingleCharacterFragment.CHARACTER_ID_KEY);
        currentCharacter = CharacterCollection.GetInstance().getCharacterById(currentCharacterID);
    }

    // We prepare the view with all of the necessary data and return it.
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View currView = inflater.inflate(R.layout.fragment_edit_character, container, false);

        // First step is to initialize all of the private variables
        editCharacterProfile = (ImageView) currView.findViewById(R.id.fragment_profileImageEdit);
        editCharacterName = (EditText) currView.findViewById(R.id.fragment_nameEditText);
        editCharacterAge = (EditText) currView.findViewById(R.id.fragment_ageEditText);
        editCharacterSex = (Spinner) currView.findViewById(R.id.fragment_sexSpinner);
        editCharacterDescription = (EditText) currView.findViewById(R.id.fragment_descEditText);

        // Then, we get the data from the passed in arguments and put them into the Widgets
        // For a bitmap, since we compressed it as a byte array, we need to do this extra step to display it.
        byte[] picData = getArguments().getByteArray(ViewSingleCharacterFragment.CHARACTER_PIC_BITMAP);
        if(picData != null)
        {
            Bitmap picBitmap = BitmapFactory.decodeByteArray(picData, 0, picData.length);
            editCharacterProfile.setImageBitmap(picBitmap);
        }

        String name = getArguments().getString(ViewSingleCharacterFragment.CHARACTER_NAME_KEY);
        String desc = getArguments().getString(ViewSingleCharacterFragment.CHARACTER_DESC_KEY);
        int age = getArguments().getInt(ViewSingleCharacterFragment.CHARACTER_AGE_KEY);
        int sexIndex = getArguments().getInt(ViewSingleCharacterFragment.CHARACTER_SEX_INDEX);

        editCharacterName.setText(name);
        editCharacterDescription.setText(desc);
        editCharacterAge.setText(String.format(Locale.US,"%d", age));
        editCharacterSex.setSelection(sexIndex);

        isNewCharacter = getArguments().getBoolean(ViewSingleCharacterFragment.CHARACTER_IS_NEW);

        return currView;
    }

    // When we get a request code that represents our ImageCode, we process it
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK)
                {
                    try
                    {
                        // We take advantage of the dialogue that Android has built in
                        Uri selectedImage = data.getData();
                        InputStream imageStream = getContext().getContentResolver().openInputStream(selectedImage);
                        Bitmap yourChosenImage = BitmapFactory.decodeStream(imageStream);
                        editCharacterProfile.setImageBitmap(yourChosenImage);
                    }
                    catch (FileNotFoundException e)
                    {
                        Toast.makeText(getContext(), "Unable to find picture...", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    // Saves all of the character's attributes back to the character with this call and finished this activity
    // We then make sure we get those changes propagated to the ViewSingleCharacterFragment
    public void saveCharacterAttributes()
    {
        Bitmap newProfile = ((BitmapDrawable)editCharacterProfile.getDrawable()).getBitmap();
        String newName = editCharacterName.getEditableText().toString();
        int newAge = Integer.parseInt(editCharacterAge.getEditableText().toString());
        String newDesc = editCharacterDescription.getEditableText().toString();
        String newSex = editCharacterSex.getSelectedItem().toString();

        currentCharacter.setProfilePictureBitmap(newProfile);
        currentCharacter.setName(newName);
        currentCharacter.setAge(newAge);
        currentCharacter.setDescription(newDesc);
        currentCharacter.setSexImageID(newSex);

        // We also put those changes in our database
        try
        {
            CharacterDBHelper.GetInstance().updateEntry(currentCharacter);
            Toast.makeText(getContext(), "Saved changes!", Toast.LENGTH_SHORT).show();
        }
        catch(IOException e)
        {
            Toast.makeText(getContext(), "Unable to update entry!", Toast.LENGTH_SHORT).show();
        }

        // We then fire a new activity so our changes are reflected in the ViewSingleCharacterFragment
        Intent characterViewIntent = ViewSingleCharacterFragment.BuildIntent(currentCharacter, getContext());
        getContext().startActivity(characterViewIntent);
        getActivity().finish();
    }

    // This asks the user to select an image from their Gallery to use for the character
    public void getNewImage()
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    // Removes the current character, only if they are brand new
    public void removeNewCharacter()
    {
        // If we made a new character, we delete them
        if(isNewCharacter == true)
        {
            CharacterDBHelper.GetInstance().deleteEntry(currentCharacter);
            CharacterCollection.GetInstance().getListOfCharacters().remove(currentCharacter);
        }
    }

}
