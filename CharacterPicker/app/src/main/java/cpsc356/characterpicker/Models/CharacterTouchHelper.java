package cpsc356.characterpicker.Models;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by matthewshiroma on 12/8/17.
 * Allows for the use of swiping cells and moving cells.
 */

public class CharacterTouchHelper extends ItemTouchHelper.SimpleCallback {

    private CharacterListAdapter adapter;

    // Constructor that implements its super class's constructor
    public CharacterTouchHelper(CharacterListAdapter newAdapter)
    {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        adapter = newAdapter;
    }

    // This is used for the Drag and Drop.
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // We simply call on the Swap position and return true that we've moved
        adapter.Swap(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    // This is called when we swipe on a Cell in our ViewHolder
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.Remove(viewHolder.getAdapterPosition());
    }
}
