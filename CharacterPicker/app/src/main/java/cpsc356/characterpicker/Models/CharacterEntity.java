package cpsc356.characterpicker.Models;

import java.util.UUID;

import cpsc356.characterpicker.R;

/**
 * Created by matthewshiroma on 12/1/17.
 *
 * This data model holds all of the necessary information regarding what's in a character
 */

public class CharacterEntity {

    private String id;
    private String name;
    private int age;
    private String description;
    private float averageRating;
    private int profilePictureID;
    private int sexImageID;         // Holds the current sex of the character. Also defines what sex they are

    // Use this constructor if you don't want to set an image ID by default
    public CharacterEntity(String name, int age, String sex, String description)
    {
        setName(name);
        setAge(age);
        setSexImageID(sex);
        setDescription(description);

        id = UUID.randomUUID().toString();
        averageRating = 0.0f;
        profilePictureID = R.drawable.image_defaultPic;
    }

    // If you have an image to use for the profile pic, use this one
    public CharacterEntity(String name, int age, String sex, String description, int picId)
    {
        setName(name);
        setAge(age);
        setSexImageID(sex);
        setDescription(description);

        id = UUID.randomUUID().toString();
        averageRating = 0.0f;
        profilePictureID = picId;
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

    public float getAverageRating() {
        return averageRating;
    }

    public int getProfilePictureID() {
        return profilePictureID;
    }

    public int getSexImageID()
    {
        return sexImageID;
    }

    public void setName(String name) {
        if(!name.isEmpty())
        {
            this.name = name;
        }
    }

    public void setAge(int age) {
        if(age > 0)
        {
            this.age = age;
        }
    }

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

    public void setAverageRating(float averageRating) {
        if(averageRating > 0)
        {
            this.averageRating = averageRating;
        }
    }

    // Returns a string form of the sex
    public String getSexString()
    {
        switch(sexImageID)
        {
            case R.drawable.icon_male:
                return "m";
            case R.drawable.icon_female:
                return "f";
            case R.drawable.icon_nonbinary:
                return "b";
            default:
                return "b";
        }
    }

}
