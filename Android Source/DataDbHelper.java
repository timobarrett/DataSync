package fivetwentysix.ware.com.actotracker.appData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fivetwentysix.ware.com.actotracker.appData.ErrorEntry;
/**
 * Created by tim on 12/12/2016.
 * Provides the database helper functions
 *
 */

public class DataDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "data.db";

    public ActoTrackerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * onCreate call for the database
     * @param sqLiteDatabase for persisting data
     *
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
   
        //then a table to collect/persist errors encountered
        final String SQL_CREATE_ERROR_TABLE = "CREATE TABLE " + ErrorEntry.TABLE_NAME + " (" +
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                ErrorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data
                ErrorEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                ErrorEntry.COLUMN_MODULE + " TEXT NOT NULL, " +
                ErrorEntry.COLUMN_LINE_NUMBER + " INTEGER NOT NULL, " +
                ErrorEntry.COLUMN_ERROR_MESSAGE + " TEXT NOT NULL," +
                // To assure the application have just one weather entry per day
                // per location, it's created a UNIQUE constraint with REPLACE strategy
                " UNIQUE (" + ErrorEntry.COLUMN_MODULE + ", " +
                ErrorEntry.COLUMN_LINE_NUMBER + ") ON CONFLICT REPLACE);";
     
        sqLiteDatabase.execSQL(SQL_CREATE_ERROR_TABLE);
    }

    //TODO - custom to save data on update
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        //TODO need a custom process so data is not just dropped.
      
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ErrorEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
