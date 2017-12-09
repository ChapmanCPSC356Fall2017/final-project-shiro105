package cpsc356.characterpicker.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Locale;

import cpsc356.characterpicker.Models.CharacterCollection;
import cpsc356.characterpicker.Models.CharacterEntity;
import cpsc356.characterpicker.R;

/**
 * Created by matthewshiroma on 12/1/17.
 *
 *  This class will represent all of the actions that a fragment displaying a character's attributes.
 *
 */

public class ViewSingleCharacterFragment extends Fragment {

    public static final String LOGTAG = "ViewSingleCharacterFragment";

    public static final String CHARACTER_ID_KEY = "character_id";
    public static final String CHARACTER_NAME_KEY = "character_name";
    public static final String CHARACTER_AGE_KEY = "character_age";
    public static final String CHARACTER_DESC_KEY = "character_desc";
    public static final String CHARACTER_RATING_KEY = "character_rating";
    public static final String CHARACTER_PIC_ID = "character_pic_id";
    public static final String CHARACTER_SEX_PIC_ID = "character_sex_pic_id";

    private ImageView characterImage;
    private TextView characterName;
    private TextView characterAverageRating;
    private TextView characterAge;
    private ImageView characterSexPic;
    private TextView characterDescription;
    private RatingBar characterCurrentRating;

    private CharacterEntity currentCharacter;

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
        int profilePicId = getArguments().getInt(CHARACTER_PIC_ID);
        int sexPicId = getArguments().getInt(CHARACTER_SEX_PIC_ID);
        String name = getArguments().getString(CHARACTER_NAME_KEY);
        String desc = getArguments().getString(CHARACTER_DESC_KEY);
        float avRating = getArguments().getFloat(CHARACTER_RATING_KEY);
        int age = getArguments().getInt(CHARACTER_AGE_KEY);

        characterImage.setImageResource(profilePicId);
        characterSexPic.setImageResource(sexPicId);
        characterName.setText(name);
        characterDescription.setText(desc);
        characterAverageRating.setText(String.format(Locale.US,"Rating: %3.2f", avRating));
        characterAge.setText(String.format(Locale.US,"Age: %d", age));

        // Now we need to add in the listeners for the rating, since you can change it here
        characterCurrentRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                // TODO: IMPLEMENT USING THIS
            }
        });

        return currView;
    }
}
