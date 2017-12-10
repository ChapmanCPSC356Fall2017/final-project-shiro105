package cpsc356.characterpicker.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cpsc356.characterpicker.Adapters.CharacterListAdapter;
import cpsc356.characterpicker.Models.CharacterTouchHelper;
import cpsc356.characterpicker.R;

/**
 * Created by matthewshiroma on 12/1/17.
 *
 *  The first fragment that gets displayed to the screen. Also handles the RecyclerView as well.
 */

public class CharacterListFragment extends Fragment {

    private CharacterListAdapter currentAdapter;
    private RecyclerView characterRecycleView;

    // When we create this fragment, we set it up with its own adapter
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_character_list, container, false);

        characterRecycleView = v.findViewById(R.id.Character_RecycleView);

        currentAdapter = new CharacterListAdapter();
        characterRecycleView.setAdapter(currentAdapter);
        characterRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // We also set out CharacterTouchHelper to our RecyclerView
        ItemTouchHelper.Callback callback = new CharacterTouchHelper(currentAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(characterRecycleView);

        return v;
    }

    // Whenever there is a dataset change, we update the list
    @Override
    public void onResume() {
        super.onResume();

        currentAdapter.notifyDataSetChanged();
    }
}
