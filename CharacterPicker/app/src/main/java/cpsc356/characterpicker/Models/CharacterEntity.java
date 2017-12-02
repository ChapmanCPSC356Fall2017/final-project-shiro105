package cpsc356.characterpicker.Models;

import java.util.UUID;

/**
 * Created by matthewshiroma on 12/1/17.
 *
 * This data model holds all of the necessary information regarding what's in a character
 */

public class CharacterEntity {

    private String id;
    private String name;
    private int age;
    private char sex;
    private String description;
    private float averageRating;
    private int pictureID;

    public CharacterEntity(String name, int age, char sex, String description)
    {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.description = description;

        id = UUID.randomUUID().toString();
        averageRating = 0.0f;
        pictureID = 0;
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

    public char getSex() {
        return sex;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public int getPictureID() {
        return pictureID;
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

    public void setSex(char sex) {
        switch(sex)
        {
            case 'm':
            case 'f':
            case 'b':
                this.sex = sex;
                break;
            default:
                this.sex = 'b';
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

    public void setPictureID(int pictureID) {
        this.pictureID = pictureID;
    }
}
