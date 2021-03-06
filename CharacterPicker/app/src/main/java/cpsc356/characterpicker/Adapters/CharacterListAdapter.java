package cpsc356.characterpicker.Adapters;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;

import cpsc356.characterpicker.Database.CharacterDBHelper;
import cpsc356.characterpicker.Models.CharacterCollection;
import cpsc356.characterpicker.Models.CharacterEntity;
import cpsc356.characterpicker.Models.CharacterViewHolder;
import cpsc356.characterpicker.R;

/**
 * Created by matthewshiroma on 12/1/17.
 *
 *  This class helps display the cells that the RecycleView displays.
 *  It also handles swipe to remove and moving cells around.
 */

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterViewHolder>{

    // Inflates each cell into each position
    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.cell_character_list, parent, false);
        CharacterViewHolder holder = new CharacterViewHolder(v);

        return holder;
    }

    // We display the information each cell has to each CopyAbility
    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        CharacterEntity currentCharacter = CharacterCollection.GetInstance().getListOfCharacters().get(position);
        holder.DisplayCharacterProfile(currentCharacter);
    }

    // This gets the size of the RecycleList
    @Override
    public int getItemCount() {
        return CharacterCollection.GetInstance().getListOfCharacters().size();
    }

    // Used to implement swipe removal
    public void Remove(int position)
    {
        CharacterEntity currChar = CharacterCollection.GetInstance().getListOfCharacters().get(position);

        // When we remove this entry, we want to also remove it from the database
        CharacterDBHelper.GetInstance().deleteEntry(currChar);
        CharacterCollection.GetInstance().getListOfCharacters().remove(position);

        // Tells the adapter that an item has been removed
        notifyItemRemoved(position);
    }

    // Used to implement Drag and Drop
    public void Swap(int firstPos, int secondPos)
    {
        // We can use the Collections method to handle the swapping.
        Collections.swap(CharacterCollection.GetInstance().getListOfCharacters(), firstPos, secondPos);

        // We then tell the adapter what spots changed
        notifyItemMoved(firstPos, secondPos);
    }
}
