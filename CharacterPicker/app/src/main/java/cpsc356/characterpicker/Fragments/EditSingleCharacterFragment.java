package cpsc356.characterpicker.Fragments;

import android.content.Context;
import android.content.Intent;
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

import java.util.Locale;

import cpsc356.characterpicker.Activities.EditSingleCharacterActivity;
import cpsc356.characterpicker.Models.CharacterCollection;
import cpsc356.characterpicker.Models.CharacterEntity;
import cpsc356.characterpicker.R;

/**
 * Created by matthewshiroma on 12/8/17.
 *  This displays the edit character screen, allowing the user to edit the character
 */

public class EditSingleCharacterFragment extends Fragment {

    public static final String CHARACTER_ID_KEY = "character_id";
    public static final String CHARACTER_NAME_KEY = "character_name";
    public static final String CHARACTER_AGE_KEY = "character_age";
    public static final String CHARACTER_DESC_KEY = "character_desc";
    public static final String CHARACTER_PIC_ID = "character_pic_id";
    public static final String CHARACTER_SEX_INDEX = "character_sex_index";

    private ImageView editCharacterProfile;
    private EditText editCharacterName;
    private EditText editCharacterAge;
    private EditText editCharacterDescription;
    private Spinner editCharacterSex;

    private CharacterEntity currentCharacter;

    // Allows us to build an Intent for this fragment
    public static Intent BuildIntent(CharacterEntity newCharacter, Context ctx)
    {
        Intent newIntent = new Intent(ctx, EditSingleCharacterActivity.class);
        newIntent.putExtra(CHARACTER_ID_KEY, newCharacter.getId());
        newIntent.putExtra(CHARACTER_NAME_KEY, newCharacter.getName());
        newIntent.putExtra(CHARACTER_AGE_KEY, newCharacter.getAge());
        newIntent.putExtra(CHARACTER_DESC_KEY, newCharacter.getDescription());
        newIntent.putExtra(CHARACTER_PIC_ID, newCharacter.getProfilePictureID());
        newIntent.putExtra(CHARACTER_SEX_INDEX, newCharacter.getSexIndex());

        return newIntent;
    }

    // We first get the character data
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String currentCharacterID = getArguments().getString(CHARACTER_ID_KEY);
        currentCharacter = CharacterCollection.GetInstance().getCharacterById(currentCharacterID);
    }

    // We then prepare the view with all of the necessary data and return it
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
        int profilePicId = getArguments().getInt(CHARACTER_PIC_ID);
        String name = getArguments().getString(CHARACTER_NAME_KEY);
        String desc = getArguments().getString(CHARACTER_DESC_KEY);
        int age = getArguments().getInt(CHARACTER_AGE_KEY);
        int sexIndex = getArguments().getInt(CHARACTER_SEX_INDEX);

        editCharacterProfile.setImageResource(profilePicId);
        editCharacterName.setText(name);
        editCharacterDescription.setText(desc);
        editCharacterAge.setText(String.format(Locale.US,"%d", age));
        editCharacterSex.setSelection(sexIndex);

        // We set the tag of theImageView so that we can get the image resource for it.
        // Whenever we make a change to this, WE NEED TO SET THE TAG TO REFLECT THAT CHANGE
        editCharacterProfile.setTag(profilePicId);

        return currView;
    }

    // Saves all of the character's attributes back to the character with this call and finished this activity
    public void saveCharacterAttributes()
    {
        int profileIndex = (int)editCharacterProfile.getTag();
        String newName = editCharacterName.getEditableText().toString();
        int newAge = Integer.parseInt(editCharacterAge.getEditableText().toString());
        String newDesc = editCharacterDescription.getEditableText().toString();
        String newSex = editCharacterSex.getSelectedItem().toString();

        currentCharacter.setProfilePictureID(profileIndex);
        currentCharacter.setName(newName);
        currentCharacter.setAge(newAge);
        currentCharacter.setDescription(newDesc);
        currentCharacter.setSexImageID(newSex);

        Toast.makeText(getContext(), "Saved changes!", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }
}
