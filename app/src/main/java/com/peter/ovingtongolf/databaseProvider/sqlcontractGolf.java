package com.peter.ovingtongolf.databaseProvider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by peter on 15/12/14.
 */
public class sqlcontractGolf {

    public static final String QUERY_PARAMETER_DISTINCT = "distinct";
    public static final String DATABASE_NAME = "golf.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    
    interface CourseColumns {
        /** Unique string identifying this block of time. */
        String COURSE_ID = "course_id";
        String COURSE_NAME = "course_name";
        String COURSE_ADDR1 = "course_addr1";
        String COURSE_ADDR2 = "course_addr2";
        String COURSE_SLOPE = "course_slope";
        String COURSE_RATING = "course_rating";
        String COURSE_PHONE = "course_phone";
        String COURSE_EMAIL = "course_email";
    }

    interface PlayerColumns {
        String PLAYER_ID = "player_id";
        String PLAYER_NAME = "player_name";
        String PLAYER_ADDR1 = "player_addr1";
        String PLAYER_ADDR2 = "player_addr2";
        String PLAYER_HANDICAP = "player_handicap";
        String PLAYER_PHONE = "player_phone";
        String PLAYER_EMAIL = "player_email";
    }


    public static final String CONTENT_AUTHORITY = "com.peter.ovingtongolf.databaseProvider.golfContentProvider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_COURSE = "course";
    private static final String PATH_PLAYER = "player";

    public static final String[] TOP_LEVEL_PATHS = {
            PATH_COURSE,
            PATH_PLAYER,
    };

    public static class Course implements CourseColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_COURSE).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.golf.course";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.golf.course";

        /** Default "ORDER BY" clause. */
        public static final String DEFAULT_SORT = CourseColumns.COURSE_NAME + " COLLATE NOCASE ASC";

        public static Uri buildUri(String Id) {
            return CONTENT_URI.buildUpon().appendPath(Id).build();
        }
        public static Uri buildUri() {
            return CONTENT_URI;
        }
        public static String getId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class Player implements PlayerColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PLAYER).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.golf.player";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.golf.player";

        public static final String DEFAULT_SORT = PLAYER_NAME + " COLLATE NOCASE ASC";

        public static Uri buildUri(String Id) {
            return CONTENT_URI.buildUpon().appendPath(Id).build();
        }
        public static Uri buildUri() {
            return CONTENT_URI;
        }
        public static String getId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    private sqlcontractGolf ()  {

    }
}
