package cpsc356.characterpicker.Activities;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cpsc356.characterpicker.R;

/*
 *  This class is meant to be inherited by any Activity that is only planning to use one fragment.
 *  This will also contain the code for general Activity stuff, including starting a fragment and getting the specified
 *  fragment
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    public boolean useAnimations = false;       // Does this activity use animations?

    // If a class were to inherit this, it needs to implement this method
    // This dictates what kind of Fragment this Activity will show
    public abstract Fragment setFragmentAndAnimation();

    // Upon creating this Activity, we set the fragment and display it
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Fragment currentFragment = setFragmentAndAnimation();
        DisplayNewFragment(currentFragment);
    }

    // Displays the passed in fragment to the screen
    private void DisplayNewFragment(Fragment currFraction)
    {
        FragmentManager currFragManager = getSupportFragmentManager();
        FragmentTransaction currTransaction = currFragManager.beginTransaction();

        if(useAnimations == true)
        {
            currTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

        currTransaction.replace(R.id.FragmentHolder, currFraction);
        currTransaction.commit();
    }
}
