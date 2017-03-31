package fivetwentysix.ware.com.actotracker.appData;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import fivetwentysix.ware.com.actotracker.Utility;

/**
 * Created by tim on 12/12/2016.
 * Contains the database contract
 *
 */

public class ActoTrackerContract {
    static final String CONTENT_AUTHORITY = "fivetwentysix.ware.com.actotracker";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    static final String PATH_ERROR = "error";
    static final String PATH_SUMMARY = "summary";
    /* Inner class that defines the table contents of the weather table
    *   Thought is need to support multiple runs per day
    *   Question is do I need to support multiple locations?*/
    public static final class LocationEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATION).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
        // Table name
     
    }
   
    public static final class ErrorEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ERROR).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ERROR;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ERROR;
        public static final String TABLE_NAME = "error";
        // Date, stored as long in milliseconds since the epoch
        public static final String COLUMN_DATE = "date";
        // Weather id as returned by API, to identify the icon to be used
        public static final String COLUMN_MODULE = "module";
        public static final String COLUMN_LINE_NUMBER = "line_number";
        public static final String COLUMN_ERROR_MESSAGE = "error_message";

        public static Uri buildErrorUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static Uri buildErrorWithDate(String locationSetting, long date) {
            return CONTENT_URI.buildUpon().appendPath(locationSetting)
                    .appendPath(Long.toString(Utility.normalizeDate())).build();
        }
        public static long getStartDateFromUri(Uri uri) {
            String dateString = uri.getQueryParameter(COLUMN_DATE);
            if (null != dateString && dateString.length() > 0)
                return Long.parseLong(dateString);
            else
                return 0;
        }
    }

 
}
