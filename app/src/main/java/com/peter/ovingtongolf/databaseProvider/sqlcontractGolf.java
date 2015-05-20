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
        String COURSE_STATUS = "course_status";
        String COURSE_DATE_DELETED = "course_delete_date";
        String COURSE_ADDR1 = "course_addr1";
        String COURSE_ADDR2 = "course_addr2";
        String COURSE_SLOPE = "course_slope";
        String COURSE_RATING = "course_rating";
        String COURSE_PHONE = "course_phone";
        String COURSE_EMAIL = "course_email";
    }

    interface TeesColumns {
        /** Unique string identifying this block of time. */
        String TEE_ID = "tee_id";
        String TEE_COURSE_ID = "tee_course_id";
        String TEE_COLOUR = "tee_colour";
        String TEE_SEX = "tee_sex";
        String TEE_SLOPE = "tee_slope";
    }

    interface HolesColumns {
        /** Unique string identifying this block of time. */
        String HOLE_ID = "hole_id";
        String HOLE_TEE_ID = "hole_tee_id";
        String HOLE_NUMBER = "hole_number";
        String HOLE_PAR = "hole_par";
        String HOLE_STROKE = "hole_stroke";
        String HOLE_PRIZE = "hole_prize";
        String HOLE_MAP_LATITUDE = "hole_map_latitude";
        String HOLE_MAP_LONGITUDE = "hole_map_longitude";
    }

    interface PlayerColumns {
        String PLAYER_ID = "player_id";
        String PLAYER_NAME = "player_name";
        String PLAYER_STATUS = "player_status";
        String PLAYER_DATE_DELETED = "player_delete_date";
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
    private static final String PATH_HOLES = "holes";
    private static final String PATH_TEES = "tees";

    public static final String[] TOP_LEVEL_PATHS = {
            PATH_COURSE,
            PATH_PLAYER,
            PATH_HOLES,
            PATH_TEES
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

    public static class Tees implements TeesColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TEES).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.golf.course";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.golf.course";

        /** Default "ORDER BY" clause. */
        public static final String DEFAULT_SORT = TeesColumns.TEE_COLOUR+ " COLLATE NOCASE ASC";

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


    public static class Holes implements HolesColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_HOLES).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.golf.course";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.golf.course";

        /** Default "ORDER BY" clause. */
        public static final String DEFAULT_SORT = HolesColumns.HOLE_NUMBER + " COLLATE NOCASE ASC";

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
