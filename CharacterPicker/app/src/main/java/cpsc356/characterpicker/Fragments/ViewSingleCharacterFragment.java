package cpsc356.characterpicker.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import cpsc356.characterpicker.Models.CharacterEntity;

/**
 * Created by matthewshiroma on 12/1/17.
 *
 *  This class will represent all of the actions that a fragment displaying a character's attributes.
 *
 */

public class ViewSingleCharacterFragment extends Fragment {

    public static final String CHARACTER_NAME_KEY = "character_name";
    public static final String CHARACTER_AGE_KEY = "character_age";
    public static final String CHARACTER_SEX_KEY = "character_sex";
    public static final String CHARACTER_DESC_KEY = "character_desc";
    public static final String CHARACTER_RATING_KEY = "character_rating";

    private ImageView characterImage;
    private TextView characterName;
    private TextView characterAverageRating;
    private TextView characterAge;
    private ImageView characterSex;
    private TextView characterDesctiption;
    private RatingBar characterCurrentRating;

    private CharacterEntity currentCharacter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
