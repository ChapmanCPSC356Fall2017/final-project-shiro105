package cpsc356.characterpicker.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by matthewshiroma on 12/10/17.
 *  This class does the transactions on the database defined in CharacterContract
 */

public class CharacterDBHelper extends SQLiteOpenHelper {

    // These should be updated as needed
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Characters.db";

    // Needed to be implemented
    public CharacterDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // When initializing the database, this should just create the table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CharacterContract.SQL_CREATE_ENTRIES);
    }

    // Needed to be implemented
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
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


}
