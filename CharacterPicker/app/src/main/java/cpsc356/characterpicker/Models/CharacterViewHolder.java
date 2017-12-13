package cpsc356.characterpicker.Models;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

import cpsc356.characterpicker.Activities.ViewSingleCharacterPagerActivity;
import cpsc356.characterpicker.Fragments.ViewSingleCharacterFragment;
import cpsc356.characterpicker.R;

/**
 * Created by matthewshiroma on 12/1/17.
 *
 *  This class keeps track of the individual cells that the ListAdapter has. This works in cotangent with
 *  the CharacterListAdapter.
 */

public class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    // All of the widgets this cell has
    private ImageView storedCharacterImage;
    private TextView storedCharacterName;
    private TextView storedCharacterRating;

    private CharacterEntity storedCharacter;        // The character that is currently stored in this cell

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
    // We also save an instance to this character
    public void DisplayCharacterProfile(CharacterEntity currentOne)
    {
        storedCharacter = currentOne;
        storedCharacterImage.setImageBitmap(storedCharacter.getProfilePictureBitmap());
        storedCharacterName.setText(storedCharacter.getName());
        storedCharacterRating.setText(String.format(Locale.US,"%3.2f", storedCharacter.getAverageRating()));
    }

    // When we click on this, we are taken to the ViewCharacterScreen.
    // Here, we set off a PagerActivity, which displays out ViewSingleCharacterFragment
    @Override
    public void onClick(View view) {
        Intent characterViewIntent = ViewSingleCharacterFragment.BuildIntent(storedCharacter, view.getContext());
        view.getContext().startActivity(characterViewIntent);
    }
}
