package cpsc356.characterpicker.Models;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import cpsc356.characterpicker.Activities.ViewSingleCharacterActivity;
import cpsc356.characterpicker.Fragments.ViewSingleCharacterFragment;
import cpsc356.characterpicker.R;

/**
 * Created by matthewshiroma on 12/1/17.
 *
 *  This class keeps track of the individual cells that the ListAdapter has. This works in cotangent with
 *  the CharacterListAdapter.
 */

public class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private ImageView storedCharacterImage;
    private TextView storedCharacterName;
    private TextView storedCharacterRating;

    private CharacterEntity storedCharacter;

    // This is required from the ViewHolder superclass
    public CharacterViewHolder(View itemView) {
        super(itemView);

        // This works, since we made this class implement an OnClick method
        itemView.setOnClickListener(this);

        // We use this moment to save all of the instances we need to know
        storedCharacterImage = (ImageView) itemView.findViewById(R.id.cell_profileImageView);
        storedCharacterName = (TextView) itemView.findViewById(R.id.cell_nameTextView);
        storedCharacterRating = (TextView) itemView.findViewById(R.id.cell_ratingTextView);

    }

    // This displays the information gotten from storedCharacter into the respective slots.
    public void DisplayCharacterProfile(CharacterEntity currentOne)
    {
        storedCharacter = currentOne;
        storedCharacterImage.setImageResource(storedCharacter.getProfilePictureID());
        storedCharacterName.setText(storedCharacter.getName());
        storedCharacterRating.setText(String.format(Locale.US,"%3.2f", storedCharacter.getAverageRating()));
    }

    // When we click on this, we are taken to the ViewCharacterScreen
    @Override
    public void onClick(View view) {
        Intent characterViewIntent = new Intent(view.getContext(), ViewSingleCharacterActivity.class);

        // After making the intent, we then pass in all of the nec
        characterViewIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_ID_KEY, storedCharacter.getId());
        characterViewIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_PIC_ID, storedCharacter.getProfilePictureID());
        characterViewIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_NAME_KEY, storedCharacter.getName());
        characterViewIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_RATING_KEY, storedCharacter.getAverageRating());
        characterViewIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_AGE_KEY, storedCharacter.getAge());
        characterViewIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_SEX_PIC_ID, storedCharacter.getSexImageID());
        characterViewIntent.putExtra(ViewSingleCharacterFragment.CHARACTER_DESC_KEY, storedCharacter.getDescription());

        view.getContext().startActivity(characterViewIntent);
    }
}