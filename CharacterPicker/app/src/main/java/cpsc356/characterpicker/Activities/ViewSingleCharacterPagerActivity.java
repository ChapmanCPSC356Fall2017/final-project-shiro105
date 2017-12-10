package cpsc356.characterpicker.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import cpsc356.characterpicker.Adapters.CharacterPagerAdapter;
import cpsc356.characterpicker.Fragments.ViewSingleCharacterFragment;
import cpsc356.characterpicker.Models.CharacterCollection;
import cpsc356.characterpicker.R;

/**
 * Created by matthewshiroma on 12/9/17.
 * This activity allows the user to perform the swipe maneuver that the PagerFragment allows for.
 * This took over the duties of the ViewSingleCharacterFragment.
 */

public class ViewSingleCharacterPagerActivity extends AppCompatActivity {

    private ViewPager characterPager;       // A reference to the viewPager on the screen

    // We set up the private variables and set up the current character in the activity
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_pager);

        // We set the adapter of the ViewPager with our CharacterPagerAdapter
        characterPager = (ViewPager) findViewById(R.id.viewCharacter_ViewPager);
        CharacterPagerAdapter adapter = new CharacterPagerAdapter(getSupportFragmentManager());
        characterPager.setAdapter(adapter);

        // We then pass in the corresponding location of the next character item in our list.
        String characterID = getIntent().getStringExtra(ViewSingleCharacterFragment.CHARACTER_ID_KEY);
        int characterIndex = CharacterCollection.GetInstance().findCharacterIndexInList(characterID);
        characterPager.setCurrentItem(characterIndex);
    }
}
