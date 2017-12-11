package cpsc356.characterpicker.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;

import cpsc356.characterpicker.Activities.ViewSingleCharacterPagerActivity;
import cpsc356.characterpicker.Models.CharacterCollection;
import cpsc356.characterpicker.Models.CharacterEntity;
import cpsc356.characterpicker.R;

/**
 * Created by matthewshiroma on 12/1/17.
 *
 *  This displays all of the actions that a fragment displaying a character's attributes does, including
 *  how the logic flow works on them
 *
 */

public class ViewSingleCharacterFragment extends Fragment {

    // All of these are used to get specific values from the arguments passed in
    public static final String CHARACTER_ID_KEY = "character_id";
    public static final String CHARACTER_NAME_KEY = "character_name";
    public static final String CHARACTER_AGE_KEY = "character_age";
    public static final String CHARACTER_DESC_KEY = "character_desc";
    public static final String CHARACTER_RATING_KEY = "character_rating";
    public static final String CHARACTER_PIC_BITMAP = "character_pic_bitmap";
    public static final String CHARACTER_SEX_PIC_ID = "character_sex_pic_id";
    public static final String CHARACTER_SEX_INDEX = "character_sex_index";

    // A reference to all of the widgets
    private ImageView characterImage;
    private TextView characterName;
    private TextView characterAverageRating;
    private TextView characterAge;
    private ImageView characterSexPic;
    private TextView characterDescription;
    private RatingBar characterCurrentRating;

    // Private variables for this fragment only
    private CharacterEntity currentCharacter;

    // Allows us to make an Intent for this fragment
    public static Intent BuildIntent(CharacterEntity character, Context ctx)
    {
        Intent characterViewIntent = new Intent(ctx, ViewSingleCharacterPagerActivity.class);

        // After making the intent, we then pass in all of the necessary values to our Intent.
        // The bitmap is too large, so we need to downsize it so that it can be passed
        try
        {
            byte[] picData = CharacterEntity.returnBitMapArray(character.getProfilePictureBitmap());
            characterViewIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_PIC_BITMAP, picData);
        }
        catch (IOException e)
        {
            Toast.makeText(ctx, "An error with the image has occurred.", Toast.LENGTH_SHORT).show();
        }

        characterViewIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_ID_KEY, character.getId());
        characterViewIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_NAME_KEY, character.getName());
        characterViewIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_RATING_KEY, character.getAverageRating());
        characterViewIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_AGE_KEY, character.getAge());
        characterViewIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_SEX_PIC_ID, character.getSexImageID());
        characterViewIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_DESC_KEY, character.getDescription());

        return characterViewIntent;
    }

    // We first get the character data from our passed in arguments
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String currentCharacterID = getArguments().getString(CHARACTER_ID_KEY);
        currentCharacter = CharacterCollection.GetInstance().getCharacterById(currentCharacterID);

        // This NEEDS to have this so that the menu bar can be shown
        setHasOptionsMenu(true);
    }

    // We then prepare the view with all of the necessary data and return it out.
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View currView = inflater.inflate(R.layout.fragment_view_character, container, false);

        // First step is to initialize all of the private variables
        characterImage = (ImageView) currView.findViewById(R.id.fragment_profileImageView);
        characterName = (TextView) currView.findViewById(R.id.fragment_nameTextView);
        characterAverageRating = (TextView) currView.findViewById(R.id.fragment_ratingTextView);
        characterAge = (TextView) currView.findViewById(R.id.fragment_ageTextView);
        characterSexPic = (ImageView) currView.findViewById(R.id.sex_ImageView);
        characterDescription = (TextView) currView.findViewById(R.id.fragment_descTextView);
        characterCurrentRating = (RatingBar) currView.findViewById(R.id.fragment_ratingRatingBar);

        // Then, we get the data from the passed in arguments and put them into the Widgets
        // For a bitmap, since we compressed it as a byte array, we need to do this extra step
        byte[] picData = getArguments().getByteArray(CHARACTER_PIC_BITMAP);
        if(picData != null)
        {
            Bitmap picBitmap = BitmapFactory.decodeByteArray(picData, 0, picData.length);
            characterImage.setImageBitmap(picBitmap);
        }

        int sexPicId = getArguments().getInt(CHARACTER_SEX_PIC_ID);
        String name = getArguments().getString(CHARACTER_NAME_KEY);
        String desc = getArguments().getString(CHARACTER_DESC_KEY);
        float avRating = getArguments().getFloat(CHARACTER_RATING_KEY);
        int age = getArguments().getInt(CHARACTER_AGE_KEY);

        characterSexPic.setImageResource(sexPicId);
        characterName.setText(name);
        characterDescription.setText(desc);
        characterAverageRating.setText(String.format(Locale.US,"Rating: %3.2f", avRating));
        characterAge.setText(String.format(Locale.US,"Age: %d", age));

        // Now we need to add in the listeners for the rating, since you can change it here
        characterCurrentRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if(v != 0 && b == true)
                {
                    // Once the user sets a rating, the rating will be saved and update the rating value
                    currentCharacter.setNewRating((int)v);
                    float newAvgRating = currentCharacter.getAverageRating();

                    characterAverageRating.setText(String.format(Locale.US,"Rating: %3.2f", newAvgRating));
                    Toast.makeText(getContext(), "Thanks for the rating!", Toast.LENGTH_SHORT).show();
                    ratingBar.setVisibility(View.GONE);
                }
            }
        });

        return currView;
    }

    // We create the menu to be specific to this fragment
    // The reason we are doing it here is because we have access to the character model, so it's easier to work with
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_view_character, menu);
    }

    // This runs whenever the menu is selected. This works by seeing what menu option is picked and depending what is picked,
    // something will happen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_deleteItem:
                // We delete the selected character and close this activity
                CharacterCollection.GetInstance().getListOfCharacters().remove(currentCharacter);

                Toast.makeText(getContext(), "Deleted the Character!", Toast.LENGTH_SHORT).show();
                getActivity().finish();

                return true;    // We did this because this lets the menu know we handled the action
            case R.id.menu_editItem:
                // We edit the current character in here
                Intent characterIntent = EditSingleCharacterFragment.BuildIntent(currentCharacter, getContext());
                startActivity(characterIntent);
                return true;
            default:
                return false;
        }
    }
}
