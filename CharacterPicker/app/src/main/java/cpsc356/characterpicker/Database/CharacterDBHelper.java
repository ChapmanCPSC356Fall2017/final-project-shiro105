package cpsc356.characterpicker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

import cpsc356.characterpicker.Models.CharacterCollection;
import cpsc356.characterpicker.Models.CharacterEntity;

/**
 * Created by matthewshiroma on 12/10/17.
 *  This class does the transactions on the database defined in CharacterContract
 */

public class CharacterDBHelper extends SQLiteOpenHelper {

    private static CharacterDBHelper dbHelper;
    private static SQLiteDatabase database;

    // These should be updated as needed
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Characters.db";

    // Needed to be implemented
    private CharacterDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static CharacterDBHelper GetInstance(Context ctx)
    {
        if(dbHelper == null)
        {
            dbHelper = new CharacterDBHelper(ctx);
            database = dbHelper.getWritableDatabase();
        }
        return dbHelper;
    }

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

        database.insert(CharacterContract.CharacterTuple.TABLE_NAME, null, values);
    }

    // Deletes a specific entry in the database
    public void deleteEntry(CharacterEntity newCharacter) throws SQLiteException
    {
        // We need to specify the WHERE criteria as well as what exactly we want to test
        // as well as the replacement values for the ? in the criteria (think of Prepared Statements in JdatabaseC)
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

        // And for the ratings, we need to extract the values from the dictionary
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

        database.update(CharacterContract.CharacterTuple.TABLE_NAME, values, criteria, selection);
    }

    // Reads in all of te values from the database and into our CharacterCollection
    public void readAllValues()
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
        String orderBy = CharacterContract.CharacterTuple.COLUMN_NAME + " DESC";

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

            CharacterEntity readCharacter = new CharacterEntity(id, name, age, sexId, desc, bm, ratings);
            CharacterCollection.GetInstance().getListOfCharacters().add(readCharacter);
        }

        selectionQuery.close();
    }

    // Checks if the given character is in the database
    public boolean checkIfExists(CharacterEntity characterToCheck)
    {
        String query = "SELECT * FROM " + CharacterContract.CharacterTuple.TABLE_NAME + " WHERE "
                + CharacterContract.CharacterTuple.COLUMN_ID + " = " + characterToCheck.getId();

        Cursor result = database.rawQuery(query, null);
        if(result.getCount() > 0)
        {
            result.close();
            return true;
        }
        result.close();
        return false;
    }
}
