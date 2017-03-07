package fr.univ_reims.julien.healthassistant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Julien on 24/02/2017.
 */

public class HealthAppDbHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + HealthAppContract.HealthAppEntry.TABLE_NAME + " (" +
                    HealthAppContract.HealthAppEntry._ID + " INTEGER PRIMARY KEY," +
                    HealthAppContract.HealthAppEntry.COLUMN_LOGIN + " TEXT," +
                    HealthAppContract.HealthAppEntry.COLUMN_EMAIL + " TEXT," +
                    HealthAppContract.HealthAppEntry.COLUMN_FIRST_NAME + " TEXT," +
                    HealthAppContract.HealthAppEntry.COLUMN_LAST_NAME + " TEXT," +
                    HealthAppContract.HealthAppEntry.COLUMN_BIRTHDAY + " DATE," +
                    HealthAppContract.HealthAppEntry.COLUMN_TOKEN_VALIDITY + " TIMESTAMP)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HealthAppContract.HealthAppEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "HealthApp.db";

    public HealthAppDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
