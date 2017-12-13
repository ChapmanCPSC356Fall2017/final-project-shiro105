package cpsc356.characterpicker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.IOException;

import cpsc356.characterpicker.Models.CharacterCollection;
import cpsc356.characterpicker.Models.CharacterEntity;
import cpsc356.characterpicker.R;

/**
 * Created by matthewshiroma on 12/10/17.
 *  This class does the transactions on the database defined in CharacterContract.
 *  It is also a singleton, meaning there's only one instance of this object in the program.
 *
 */

public class CharacterDBHelper extends SQLiteOpenHelper {

    // We need to make sure there's ONLY one of these in the program at a time
    private static CharacterDBHelper dbHelper;
    private static SQLiteDatabase database;

    // These should be updated as needed
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Characters.db";

    private static boolean alreadyRead = false;

    // Needed to be implemented
    private CharacterDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // This is called when the object is created for the first time.
    // It starts up the database and makes it writable.
    public static CharacterDBHelper GetInstance(Context ctx)
    {
        if(dbHelper == null)
        {
            dbHelper = new CharacterDBHelper(ctx);
            database = dbHelper.getWritableDatabase();

            // We also do a check to see if the table has anything in it. If not, we populate it with default values
            String query = "SELECT COUNT(*) AS result FROM " + CharacterContract.CharacterTuple.TABLE_NAME + ";";
            Cursor mcursor = database.rawQuery(query, null);
            if(mcursor.moveToNext() && mcursor.getInt(0) == 0)
            {
                SetUpInitialSet(ctx);
            }
            mcursor.close();
        }
        return dbHelper;
    }

    // This ONLY returns the instance if it has been made already, but the GetInstance(context) call
    // Otherwise, it returns nothing.
    public static CharacterDBHelper GetInstance()
    {
        if(dbHelper != null)
        {
            return dbHelper;
        }
        return null;
    }

    // When initializing the database, this should just create the table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CharacterContract.SQL_CREATE_ENTRIES);
        database = sqLiteDatabase;
    }

    // Our policy will be simply removing everything and starting new
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(CharacterContract.SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    // When we downgrade, we just do onUpgrade, but with the older version being the "new version"
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    // Adds a new entry to the database
    public void addEntry(CharacterEntity newCharacter) throws SQLiteException, IOException
    {
        // In order to add items to the database, we need to create a ContentValues object
        ContentValues values = new ContentValues();
        values.put(CharacterContract.CharacterTuple.COLUMN_ID, newCharacter.getId());
        values.put(CharacterContract.CharacterTuple.COLUMN_NAME, newCharacter.getName());
        values.put(CharacterContract.CharacterTuple.COLUMN_AGE, newCharacter.getAge());
        values.put(CharacterContract.CharacterTuple.COLUMN_DESC, newCharacter.getDescription());

        // For the bitmap, we need to convert it to a byte array
        byte[] data = CharacterEntity.convertBitMapToByteArray(newCharacter.getProfilePictureBitmap());
        values.put(CharacterContract.CharacterTuple.COLUMN_BITMAP, data);
        values.put(CharacterContract.CharacterTuple.COLUMN_SEX_ID, newCharacter.getSexImageID());

        // And for the ratings, we need to extract the values from the dictionary
        // It goes from 0-4 oneS-fiveS
        int[] ratings = newCharacter.returnAllRatings();
        values.put(CharacterContract.CharacterTuple.COLUMN_ONE_STAR_RATING, ratings[0]);
        values.put(CharacterContract.CharacterTuple.COLUMN_TWO_STAR_RATING, ratings[1]);
        values.put(CharacterContract.CharacterTuple.COLUMN_THREE_STAR_RATING, ratings[2]);
        values.put(CharacterContract.CharacterTuple.COLUMN_FOUR_STAR_RATING, ratings[3]);
        values.put(CharacterContract.CharacterTuple.COLUMN_FIVE_STAR_RATING, ratings[4]);

        // Once we are done, we then insert it into the table
        database.insert(CharacterContract.CharacterTuple.TABLE_NAME, null, values);
    }

    // Deletes a specific entry in the database
    public void deleteEntry(CharacterEntity newCharacter) throws SQLiteException
    {
        // We need to specify the WHERE criteria as well as what exactly we want to test
        // as well as the replacement values for the ? in the criteria (think of Prepared Statements in JDBC)
        String criteria = CharacterContract.CharacterTuple.COLUMN_ID + " = ?";
        String[] selection = {newCharacter.getId()};

        database.delete(CharacterContract.CharacterTuple.TABLE_NAME, criteria, selection);
    }

    // Updates a specific entry in the database
    public void updateEntry(CharacterEntity newCharacter) throws SQLiteException, IOException
    {
        // Like insertion, we specify the values we want to update
        ContentValues values = new ContentValues();
        values.put(CharacterContract.CharacterTuple.COLUMN_ID, newCharacter.getId());
        values.put(CharacterContract.CharacterTuple.COLUMN_NAME, newCharacter.getName());
        values.put(CharacterContract.CharacterTuple.COLUMN_AGE, newCharacter.getAge());
        values.put(CharacterContract.CharacterTuple.COLUMN_DESC, newCharacter.getDescription());

        // For the bitmap, we need to convert it to a byte array
        byte[] data = CharacterEntity.convertBitMapToByteArray(newCharacter.getProfilePictureBitmap());
        values.put(CharacterContract.CharacterTuple.COLUMN_BITMAP, data);
        values.put(CharacterContract.CharacterTuple.COLUMN_SEX_ID, newCharacter.getSexImageID());

        // For the ratings, we need to extract the values from the dictionary
        // It goes from 0-4 oneS-fiveS
        int[] ratings = newCharacter.returnAllRatings();
        values.put(CharacterContract.CharacterTuple.COLUMN_ONE_STAR_RATING, ratings[0]);
        values.put(CharacterContract.CharacterTuple.COLUMN_TWO_STAR_RATING, ratings[1]);
        values.put(CharacterContract.CharacterTuple.COLUMN_THREE_STAR_RATING, ratings[2]);
        values.put(CharacterContract.CharacterTuple.COLUMN_FOUR_STAR_RATING, ratings[3]);
        values.put(CharacterContract.CharacterTuple.COLUMN_FIVE_STAR_RATING, ratings[4]);

        // Like delete, we also need to specify what row to do his update on
        String criteria = CharacterContract.CharacterTuple.COLUMN_ID + " = ?";
        String[] selection = {newCharacter.getId()};

        // Once we have everything we need, we update the given tuple with the given values
        database.update(CharacterContract.CharacterTuple.TABLE_NAME, values, criteria, selection);
    }

    // Like update, but this one only saves the rating to the database
    public void updateRatingInDB(CharacterEntity newCharacter) throws SQLiteException, IOException
    {
        ContentValues values = new ContentValues();

        // For the ratings, we need to extract the values from the dictionary
        // It goes from 0-4 oneS-fiveS
        int[] ratings = newCharacter.returnAllRatings();
        values.put(CharacterContract.CharacterTuple.COLUMN_ONE_STAR_RATING, ratings[0]);
        values.put(CharacterContract.CharacterTuple.COLUMN_TWO_STAR_RATING, ratings[1]);
        values.put(CharacterContract.CharacterTuple.COLUMN_THREE_STAR_RATING, ratings[2]);
        values.put(CharacterContract.CharacterTuple.COLUMN_FOUR_STAR_RATING, ratings[3]);
        values.put(CharacterContract.CharacterTuple.COLUMN_FIVE_STAR_RATING, ratings[4]);

        // Like delete, we also need to specify what row to do his update on
        String criteria = CharacterContract.CharacterTuple.COLUMN_ID + " = ?";
        String[] selection = {newCharacter.getId()};

        // Once we have everything we need, we update the given tuple with the given values
        database.update(CharacterContract.CharacterTuple.TABLE_NAME, values, criteria, selection);
    }

    // Reads in all of te values from the database and into our CharacterCollection
    // This should only be done ONCE, at the very beginning of the program
    public void readAllValues()
    {
        if(alreadyRead == false)
        {
            // This is going to be a full read, only done at the beginning of the program
            String[] projection = {
                    CharacterContract.CharacterTuple.COLUMN_ID,
                    CharacterContract.CharacterTuple.COLUMN_NAME,
                    CharacterContract.CharacterTuple.COLUMN_AGE,
                    CharacterContract.CharacterTuple.COLUMN_DESC,
                    CharacterContract.CharacterTuple.COLUMN_BITMAP,
                    CharacterContract.CharacterTuple.COLUMN_SEX_ID,
                    CharacterContract.CharacterTuple.COLUMN_ONE_STAR_RATING,
                    CharacterContract.CharacterTuple.COLUMN_TWO_STAR_RATING,
                    CharacterContract.CharacterTuple.COLUMN_THREE_STAR_RATING,
                    CharacterContract.CharacterTuple.COLUMN_FOUR_STAR_RATING,
                    CharacterContract.CharacterTuple.COLUMN_FIVE_STAR_RATING
            };
            String orderBy = CharacterContract.CharacterTuple.COLUMN_NAME + " ASC";

            // We create our query and run it in our loop, reading in every character into CharacterCollection
            Cursor selectionQuery = database.query(CharacterContract.CharacterTuple.TABLE_NAME, projection, null, null, null, null, orderBy);
            while(selectionQuery.moveToNext())
            {
                String id = selectionQuery.getString(selectionQuery.getColumnIndex(CharacterContract.CharacterTuple.COLUMN_ID));
                String name = selectionQuery.getString(selectionQuery.getColumnIndex(CharacterContract.CharacterTuple.COLUMN_NAME));
                int age = selectionQuery.getInt(selectionQuery.getColumnIndex(CharacterContract.CharacterTuple.COLUMN_AGE));
                String desc = selectionQuery.getString(selectionQuery.getColumnIndex(CharacterContract.CharacterTuple.COLUMN_DESC));

                // In order to extract a bitmap, we need to use .getBlob
                byte[] data = selectionQuery.getBlob(selectionQuery.getColumnIndex(CharacterContract.CharacterTuple.COLUMN_BITMAP));
                Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);

                int sexId = selectionQuery.getInt(selectionQuery.getColumnIndex(CharacterContract.CharacterTuple.COLUMN_SEX_ID));
                int oneS = selectionQuery.getInt(selectionQuery.getColumnIndex(CharacterContract.CharacterTuple.COLUMN_ONE_STAR_RATING));
                int twoS = selectionQuery.getInt(selectionQuery.getColumnIndex(CharacterContract.CharacterTuple.COLUMN_TWO_STAR_RATING));
                int threeS = selectionQuery.getInt(selectionQuery.getColumnIndex(CharacterContract.CharacterTuple.COLUMN_THREE_STAR_RATING));
                int fourS = selectionQuery.getInt(selectionQuery.getColumnIndex(CharacterContract.CharacterTuple.COLUMN_FOUR_STAR_RATING));
                int fiveS = selectionQuery.getInt(selectionQuery.getColumnIndex(CharacterContract.CharacterTuple.COLUMN_FIVE_STAR_RATING));
                int[] ratings = new int[] {oneS, twoS, threeS, fourS, fiveS};

                // We form our character and put them into our CharacterCollection
                CharacterEntity readCharacter = new CharacterEntity(id, name, age, sexId, desc, bm, ratings);
                CharacterCollection.GetInstance().getListOfCharacters().add(readCharacter);
            }

            // It is important to close all cursors!
            selectionQuery.close();
            alreadyRead = true;
        }
    }

    // Sets up default entities in the app, whenever the database is cleared
    // Requires a Context and looks really ugly :(
    private static void SetUpInitialSet(Context ctx)
    {
        try
        {
            if(alreadyRead == false)
            {
                // We set up all of the default bitmaps for these characters
                Bitmap b1 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_kirby);
                Bitmap b2 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_batman);
                Bitmap b3 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_jen);
                Bitmap b4 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_kylo);
                Bitmap b5 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_lara);
                Bitmap b6 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_link);
                Bitmap b7 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_marc);
                Bitmap b8 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_matthew);
                Bitmap b9 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_obama);
                Bitmap b10 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_mario);
                Bitmap b11 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_pikachu);
                Bitmap b12 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_pit);
                Bitmap b13 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_ryan);
                Bitmap b14 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_trump);
                Bitmap b15 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_tommy);

                // Create the characters with their attributes
                CharacterEntity c1 = new CharacterEntity("Kirby", 1, "male", "Super Tuff Pink Puff", b1);
                CharacterEntity c2 = new CharacterEntity("Batman", 45, "male", "I'm batman", b2);
                CharacterEntity c3 = new CharacterEntity("Jennifer Ly", 21, "female", "A student at Chapman University", b3);
                CharacterEntity c4 = new CharacterEntity("Kylo Ren", 24, "male", "I am your father?", b4);
                CharacterEntity c5 = new CharacterEntity("Lara Croft", 21, "female", "A badass of a woman", b5);
                CharacterEntity c6 = new CharacterEntity("Link", 21, "male", "HAIYAAAAAAA", b6);
                CharacterEntity c7 = new CharacterEntity("Marc Karam", 21, "male", "Another student at Chapman University", b7);
                CharacterEntity c8 = new CharacterEntity("Matthew Shiroma", 21, "male", "The creator of this beautiful app.", b8);
                CharacterEntity c9 = new CharacterEntity("Barack Obama", 56, "male", "Thanks, Obama.", b9);
                CharacterEntity c10 = new CharacterEntity("Mario", 40, "male", "It's-a-mi", b10);
                CharacterEntity c11 = new CharacterEntity("Pikachu", 1, "non-binary", "PIKA PIKA", b11);
                CharacterEntity c12 = new CharacterEntity("Pit", 21, "male", "HIYAYAYAYAYAY!", b12);
                CharacterEntity c13 = new CharacterEntity("Ryan Burns", 25, "male", "The professor of Android Development", b13);
                CharacterEntity c14 = new CharacterEntity("Donald Trump", 71, "male", "MAKE AMERICA GREAT AGAIN?????", b14);
                CharacterEntity c15 = new CharacterEntity("Tommy Wiseau", 62, "male", "Ahahaha, what a story. OH HAI MARK.", b15);

                // Add them into the list
                CharacterCollection.GetInstance().getListOfCharacters().add(c1);
                CharacterCollection.GetInstance().getListOfCharacters().add(c2);
                CharacterCollection.GetInstance().getListOfCharacters().add(c3);
                CharacterCollection.GetInstance().getListOfCharacters().add(c4);
                CharacterCollection.GetInstance().getListOfCharacters().add(c5);
                CharacterCollection.GetInstance().getListOfCharacters().add(c6);
                CharacterCollection.GetInstance().getListOfCharacters().add(c7);
                CharacterCollection.GetInstance().getListOfCharacters().add(c8);
                CharacterCollection.GetInstance().getListOfCharacters().add(c9);
                CharacterCollection.GetInstance().getListOfCharacters().add(c10);
                CharacterCollection.GetInstance().getListOfCharacters().add(c11);
                CharacterCollection.GetInstance().getListOfCharacters().add(c12);
                CharacterCollection.GetInstance().getListOfCharacters().add(c13);
                CharacterCollection.GetInstance().getListOfCharacters().add(c14);
                CharacterCollection.GetInstance().getListOfCharacters().add(c15);

                // And then add them into the database
                CharacterDBHelper.GetInstance(ctx).addEntry(c1);
                CharacterDBHelper.GetInstance(ctx).addEntry(c2);
                CharacterDBHelper.GetInstance(ctx).addEntry(c3);
                CharacterDBHelper.GetInstance(ctx).addEntry(c4);
                CharacterDBHelper.GetInstance(ctx).addEntry(c5);
                CharacterDBHelper.GetInstance(ctx).addEntry(c6);
                CharacterDBHelper.GetInstance(ctx).addEntry(c7);
                CharacterDBHelper.GetInstance(ctx).addEntry(c8);
                CharacterDBHelper.GetInstance(ctx).addEntry(c9);
                CharacterDBHelper.GetInstance(ctx).addEntry(c10);
                CharacterDBHelper.GetInstance(ctx).addEntry(c11);
                CharacterDBHelper.GetInstance(ctx).addEntry(c12);
                CharacterDBHelper.GetInstance(ctx).addEntry(c13);
                CharacterDBHelper.GetInstance(ctx).addEntry(c14);
                CharacterDBHelper.GetInstance(ctx).addEntry(c15);

                alreadyRead = true;
            }
        }
        catch (IOException e)
        {
            Toast.makeText(ctx, "An error has occurred adding in the default set.", Toast.LENGTH_SHORT).show();
        }
    }
}
