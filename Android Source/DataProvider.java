package fivetwentysix.ware.com.actotracker.appData;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import android.support.annotation.NonNull;

/**
 * Created by tim on 12/12/2016.
 * provides the database content provider
 *
 */

public class ActoTrackerProvider extends ContentProvider {
    private final String LOG_TAG = "data - " + DataProvider.class.getSimpleName();
    private DataDbHelper mOpenHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
   
    private static final int ERROR = 400;
   
    private static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ActoTrackerContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
  
        matcher.addURI(authority, DataContract.PATH_ERROR, ERROR);
 
        return matcher;
    }

    /**
     *  query method for persisted data
     * @param uri - generated uri for the query
     * @param projection - data projected
     * @param selection - selection criteria
     * @param selectionArgs - args to be applied to selection
     * @param sortOrder - ascending or descending
     * @return - cursor to data
     *
     */
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Log.d(LOG_TAG,"QUERY URI = " + sUriMatcher.match(uri));
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

          
            case ERROR: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DataContract.ErrorEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    /**
     *  insert data into database
     * @param uri - identifies table for insertion
     * @param values - values to insert
     * @return - URI or null
     *
     */
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        //   Log.d(LOG_TAG, "IN insert" + match);
        switch (match) {
            case ERROR: {
                //  normalizeDate(values);  //this blocks multiple daily records for weather.
                long _id = db.insert(DataContract.ErrorEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = DataContract.ErrorEntry.buildErrorUri(_id);
                else {
                    
                    throw new android.database.SQLException("Failed to insert row into " + uri);

                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    /**
     * triggered to create database
     * @return true or false
     *
     */
    @Override
    public boolean onCreate() {
        Log.d(LOG_TAG, "IN onCreate");
        mOpenHelper = new DataDbHelper(getContext());
        return true;
    }

    /**
     * get type of URI
     * @param uri - uri
     * @return String
     *
     */
    @Override
    public String getType(@NonNull Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
        
            case ERROR:
                return DataContract.ErrorEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    /**
     * delete record(s) from database
     * @param uri - for deletion
     * @param selection - sql selection statement
     * @param selectionArgs - args to apply to sql statement
     * @return # of records deleted
     *
     */
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case ERROR:
                rowsDeleted = db.delete(
                        DataContract.ErrorEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
            if (rowsDeleted != 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return rowsDeleted;
        }

    /**
     * update records in database
     * @param uri - uri to use in sql
     * @param values - applied to sql
     * @param selection - formed statement
     * @param selectionArgs - applied to selection
     * @return - count of records updated
     *
     */
    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case ERROR:
                rowsUpdated = db.update(DataContract.ErrorEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
            if (rowsUpdated != 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return rowsUpdated;
    }


        // You do not need to call this method. This is a method specifically to assist the testing
        // framework in running smoothly. You can read more at:
        // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
        @Override
        @TargetApi(11)
        public void shutdown() {
            mOpenHelper.close();
            super.shutdown();
        }
}
