package cpsc356.characterpicker.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.UUID;

import cpsc356.characterpicker.R;

/**
 * Created by matthewshiroma on 12/1/17.
 *
 * This data model holds all of the necessary information regarding what's in a character.
 * Also contains helper methods that help modify the values for specific uses.
 */

public class CharacterEntity {

    // Private keys that reference the various spots in the dictionary
    private static final String FIVE_STAR_KEY = "Five";
    private static final String FOUR_STAR_KEY = "Four";
    private static final String THREE_STAR_KEY = "Three";
    private static final String TWO_STAR_KEY = "Two";
    private static final String ONE_STAR_KEY = "One";

    private Dictionary<String, Integer> categoricalRatings;    // Holds all of the data for the character's ratings

    // Attributes of a character
    private String id;                      // A unique string is assigned to each character
    private String name;                    // The name of the character. Can't be empty
    private int age;                        // The age of the character. Can't be a negative number.
    private String description;             // The description of the character.
    private Bitmap profilePictureBitmap;    // Holds the current bitmap for the character's profile picture
    private int sexImageID;                 // Holds the current image resource of the character's sex. Also defines what sex they are

    // Returns the passed in BitMap as a byte array. Used for passing in Intents, bundles and storing bitmaps into the database
    // Throws an IOException if something went wrong.
    public static byte[] convertBitMapToByteArray(Bitmap bm) throws IOException
    {
        // We need to do this because bitmaps are too large. We need to downsize it so that it can be passed
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] picData = stream.toByteArray();
        stream.close();
        return picData;
    }

    // Initializes a character with default values, given a context.
    public CharacterEntity(Context ctx)
    {
        name = "New Character";
        age = 0;
        description = "A new Character";
        profilePictureBitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_defaultpic);
        setSexImageID("m");

        id = UUID.randomUUID().toString();
        categoricalRatings = new Hashtable<String, Integer>();
    }

    // Takes in values to make a custom character.
    public CharacterEntity(String name, int age, String sex, String description, Bitmap bm)
    {
        setName(name);
        setAge(age);
        setSexImageID(sex);
        setDescription(description);
        profilePictureBitmap = bm;

        id = UUID.randomUUID().toString();
        categoricalRatings = new Hashtable<String, Integer>();
    }

    // Reconstructs a character from the database.
    public CharacterEntity(String id, String name, int age, int sexId, String description, Bitmap bm, int[] ratings)
    {
        this.id = id;
        setName(name);
        setAge(age);
        setSexImageID(sexId);
        setDescription(description);
        setProfilePictureBitmap(bm);

        categoricalRatings = new Hashtable<String, Integer>();
        categoricalRatings.put(ONE_STAR_KEY, ratings[0]);
        categoricalRatings.put(TWO_STAR_KEY, ratings[1]);
        categoricalRatings.put(THREE_STAR_KEY, ratings[2]);
        categoricalRatings.put(FOUR_STAR_KEY, ratings[3]);
        categoricalRatings.put(FIVE_STAR_KEY, ratings[4]);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }

    // Returns the weighted average of all of the ratings. Returns 0 if a rating cannot be calculated
    public float getAverageRating() {
        float fiveStarAmount = 0f;
        float fourStarAmount = 0f;
        float threeStarAmount = 0f;
        float twoStarAmount = 0f;
        float oneStarAmount = 0f;
        float fiveStarWeight = 0f;
        float fourStarWeight = 0f;
        float threeStarWeight = 0f;
        float twoStarWeight = 0f;
        float oneStarWeight = 0f;

        // We do a check to see if the values exist in the dictionary
        if(categoricalRatings.get(FIVE_STAR_KEY) != null)
        {
            fiveStarAmount = categoricalRatings.get(FIVE_STAR_KEY);
            fiveStarWeight = fiveStarAmount * 5.0f;
        }
        if(categoricalRatings.get(FOUR_STAR_KEY) != null)
        {
            fourStarAmount = categoricalRatings.get(FOUR_STAR_KEY);
            fourStarWeight = fourStarAmount * 4.0f;
        }
        if(categoricalRatings.get(THREE_STAR_KEY) != null)
        {
            threeStarAmount = categoricalRatings.get(THREE_STAR_KEY);
            threeStarWeight = threeStarAmount * 3.0f;
        }
        if(categoricalRatings.get(TWO_STAR_KEY) != null)
        {
            twoStarAmount = categoricalRatings.get(TWO_STAR_KEY);
            twoStarWeight = twoStarAmount * 2.0f;
        }
        if(categoricalRatings.get(ONE_STAR_KEY) != null)
        {
            oneStarAmount = categoricalRatings.get(ONE_STAR_KEY);
            oneStarWeight = oneStarAmount;
        }

        // We check to see if the amounts give a proper value.
        if((fiveStarAmount + fourStarAmount + threeStarAmount + twoStarAmount + oneStarAmount) == 0)
        {
            return 0f;
        }
        else
        {
            float result = (fiveStarWeight + fourStarWeight + threeStarWeight + twoStarWeight + oneStarWeight) / (fiveStarAmount + fourStarAmount + threeStarAmount + twoStarAmount + oneStarAmount);
            if(result >= 5.0f)
            {
                return 5.0f;
            }
            return result;
        }
    }

    public Bitmap getProfilePictureBitmap() {
        return profilePictureBitmap;
    }

    public int getSexImageID()
    {
        return sexImageID;
    }

    // For both setting the name and age, they return true if the change was successful.
    public boolean setName(String name) {
        if(!name.isEmpty())
        {
            this.name = name;
            return true;
        }
        return false;
    }

    public boolean setAge(int age) {
        if(age > 0)
        {
            this.age = age;
            return true;
        }
        return false;
    }

    // Only allows for the sex to be three things. Takes in a String as an argument
    public void setSexImageID(String sex) {
        if(sex.toLowerCase().equals("male"))
        {
            sexImageID = R.drawable.icon_male;
        }
        else if(sex.toLowerCase().equals("female"))
        {
            sexImageID = R.drawable.icon_female;
        }
        else
        {
            sexImageID = R.drawable.icon_nonbinary;
        }
    }

    // Another way to set the sexId, but with an id number instead
    public void setSexImageID(int sexId)
    {
        sexImageID = sexId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProfilePictureBitmap(Bitmap profilePictureBitmap) {
        this.profilePictureBitmap = profilePictureBitmap;
    }

    // Takes in a rating and stores it in the dictionary
    public void setNewRating(int newRating) {
       int newAmount = 0;

       // We have to check if this key exists. If not, we need to do a different logic step
       switch(newRating)
       {
           case 5:
               if(categoricalRatings.get(FIVE_STAR_KEY) != null)
               {
                   newAmount = categoricalRatings.get(FIVE_STAR_KEY) + 1;
                   categoricalRatings.put(FIVE_STAR_KEY, newAmount);
               }
               else
               {
                   categoricalRatings.put(FIVE_STAR_KEY, 1);
               }
               break;
           case 4:
               if(categoricalRatings.get(FOUR_STAR_KEY) != null)
               {
                   newAmount = categoricalRatings.get(FOUR_STAR_KEY) + 1;
                   categoricalRatings.put(FOUR_STAR_KEY, newAmount);
               }
               else
               {
                   categoricalRatings.put(FOUR_STAR_KEY, 1);
               }
               break;
           case 3:
               if(categoricalRatings.get(THREE_STAR_KEY) != null)
               {
                   newAmount = categoricalRatings.get(THREE_STAR_KEY) + 1;
                   categoricalRatings.put(THREE_STAR_KEY, newAmount);
               }
               else
               {
                   categoricalRatings.put(THREE_STAR_KEY, 1);
               }
               break;
           case 2:
               if(categoricalRatings.get(TWO_STAR_KEY) != null)
               {
                   newAmount = categoricalRatings.get(TWO_STAR_KEY) + 1;
                   categoricalRatings.put(TWO_STAR_KEY, newAmount);
               }
               else
               {
                   categoricalRatings.put(TWO_STAR_KEY, 1);
               }
               break;
           case 1:
               if(categoricalRatings.get(ONE_STAR_KEY) != null)
               {
                   newAmount = categoricalRatings.get(ONE_STAR_KEY) + 1;
                   categoricalRatings.put(ONE_STAR_KEY, newAmount);
               }
               else
               {
                   categoricalRatings.put(ONE_STAR_KEY, 1);
               }
               newAmount = categoricalRatings.get(ONE_STAR_KEY) + 1;
               categoricalRatings.put(ONE_STAR_KEY, newAmount);
               break;
       }
    }

    // Returns a number position that the sex_string is declared in strings.xml, by looking at the sex_icon. Used in the spinner
    public int getSexIndex()
    {
        switch(sexImageID)
        {
            case R.drawable.icon_male:
                return 0;
            case R.drawable.icon_female:
                return 1;
            case R.drawable.icon_nonbinary:
                return 2;
            default:
                return 2;
        }
    }

    // Returns all of the ratings that a character has in an integer array. Used for the database
    // Goes in order from 1-5 stars
    public int[] returnAllRatings()
    {
        int oneS = 0;
        int twoS = 0;
        int threeS = 0;
        int fourS = 0;
        int fiveS = 0;

        if(categoricalRatings.get(ONE_STAR_KEY) != null)
        {
            oneS = categoricalRatings.get(ONE_STAR_KEY);
        }
        if(categoricalRatings.get(TWO_STAR_KEY) != null)
        {
            twoS = categoricalRatings.get(TWO_STAR_KEY);
        }
        if(categoricalRatings.get(THREE_STAR_KEY) != null)
        {
            threeS = categoricalRatings.get(THREE_STAR_KEY);
        }
        if(categoricalRatings.get(FOUR_STAR_KEY) != null)
        {
            fourS = categoricalRatings.get(FOUR_STAR_KEY);
        }
        if(categoricalRatings.get(FIVE_STAR_KEY) != null)
        {
            fiveS = categoricalRatings.get(FIVE_STAR_KEY);
        }

        return new int[] {oneS, twoS, threeS, fourS, fiveS};
    }
}
