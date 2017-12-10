package cpsc356.characterpicker.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.UUID;

import cpsc356.characterpicker.R;

/**
 * Created by matthewshiroma on 12/1/17.
 *
 * This data model holds all of the necessary information regarding what's in a character
 */

public class CharacterEntity {

    // Private keys that reference the various spots in the dictionary
    private static final String FIVE_STAR_KEY = "Five";
    private static final String FOUR_STAR_KEY = "Four";
    private static final String THREE_STAR_KEY = "Three";
    private static final String TWO_STAR_KEY = "Two";
    private static final String ONE_STAR_KEY = "One";

    private Dictionary<String, Integer> categoricalRatings;    // Holds all of the data for the character's ratings

    private String id;
    private String name;
    private int age;
    private String description;
    private int profilePictureID;   // Holds the id of the image resource that is used
    private int sexImageID;         // Holds the current image resource of the character's sex. Also defines what sex they are

    // Initializes a character with default values
    public CharacterEntity()
    {
        name = "New Character";
        age = 0;
        description = "A new Character";
        setSexImageID("m");

        id = UUID.randomUUID().toString();
        profilePictureID =  R.drawable.image_defaultpic;
        categoricalRatings = new Hashtable<String, Integer>();
    }

    // Takes in values to make a custom character
    public CharacterEntity(String name, int age, String sex, String description, int picId)
    {
        setName(name);
        setAge(age);
        setSexImageID(sex);
        setDescription(description);

        id = UUID.randomUUID().toString();
        profilePictureID = picId;
        categoricalRatings = new Hashtable<String, Integer>();
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

    public int getProfilePictureID() {
        return profilePictureID;
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

    // Only allows for the sex to be three things
    public void setSexImageID(String sex) {
        switch(sex)
        {
            case "m":
                sexImageID = R.drawable.icon_male;
                break;
            case "f":
                sexImageID = R.drawable.icon_female;
                break;
            case "b":
                sexImageID = R.drawable.icon_nonbinary;
                break;
            default:
                sexImageID = R.drawable.icon_nonbinary;
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProfilePictureID(int profilePictureID) {
        this.profilePictureID = profilePictureID;
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

    // Returns a number position that the sex_string is declared in strings.xml, by looking at the sex_icon
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

}
