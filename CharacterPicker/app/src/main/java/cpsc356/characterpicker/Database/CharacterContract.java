package cpsc356.characterpicker.Database;

import android.provider.BaseColumns;

/**
 * Created by matthewshiroma on 12/10/17.
 * This class creates a new table for the characters. It defines the schema of it as well.
 */

public final class CharacterContract {

    // The SQL statement that creates the table
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CharacterTuple.TABLE_NAME + "(" + CharacterTuple.COLUMN_NAME_ID + " TEXT PRIMARY KEY, "
            + CharacterTuple.COLUMN_NAME_NAME + " TEXT, " + CharacterTuple.COLUMN_NAME_AGE + " INT, "
            + CharacterTuple.COLUMN_NAME_DESC + " TEXT, " + CharacterTuple.COLUMN_NAME_BITMAP + " BLOB, "
            + CharacterTuple.COLUMN_NAME_SEX_ID + " INT, " + CharacterTuple.COLUMN_NAME_ONE_RATING + " TEXT, "
            + CharacterTuple.COLUMN_NAME_TWO_RATING + " INT, " + CharacterTuple.COLUMN_NAME_THREE_RATING + " TEXT, "
            + CharacterTuple.COLUMN_NAME_FOUT_RATING + " INT, " + CharacterTuple.COLUMN_NAME_FIVE_RATING + " INT); ";

    // The SQL statement that drops the table
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + CharacterTuple.TABLE_NAME + ";";

    private CharacterContract(){}

    // An inner class that keeps the schema of our table
    public static class CharacterTuple implements BaseColumns
    {
        public static final String TABLE_NAME = "Characters";
        public static final String COLUMN_NAME_ID = "Character_id";     // Primary Key
        public static final String COLUMN_NAME_NAME = "Character_name";
        public static final String COLUMN_NAME_AGE = "Character_age";
        public static final String COLUMN_NAME_DESC = "Character_desc";
        public static final String COLUMN_NAME_BITMAP = "Character_bitmap";
        public static final String COLUMN_NAME_SEX_ID = "Character_sex_id";
        public static final String COLUMN_NAME_ONE_RATING = "Character_one_ratings";
        public static final String COLUMN_NAME_TWO_RATING = "Character_two_ratings";
        public static final String COLUMN_NAME_THREE_RATING = "Character_three_ratings";
        public static final String COLUMN_NAME_FOUT_RATING = "Character_four_ratings";
        public static final String COLUMN_NAME_FIVE_RATING = "Character_five_ratings";
    }
}
