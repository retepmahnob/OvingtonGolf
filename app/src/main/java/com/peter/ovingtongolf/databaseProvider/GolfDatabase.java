package com.peter.ovingtongolf.databaseProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.peter.ovingtongolf.databaseProvider.sqlcontractGolf;

/**
 * Created by peter on 14/12/14.
 */
public class GolfDatabase extends SQLiteOpenHelper{

//    private static final String TAG = makeLogTag(GolfDatabase.class);

    private static final int DATABASE_VERSION = 3;
//    private static final String DATABASE_NAME = "golf.db";
    private final Context mContext;


    interface Tables {
        String COURSES = "courses";
        String PLAYERS = "players";
    }


    public GolfDatabase(Context context) {
        super(context, sqlcontractGolf.DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       db.execSQL("CREATE TABLE " + Tables.COURSES + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + sqlcontractGolf.CourseColumns.COURSE_ID + " TEXT NOT NULL,"
                + sqlcontractGolf.CourseColumns.COURSE_NAME + " TEXT NOT NULL,"
                + sqlcontractGolf.CourseColumns.COURSE_ADDR1+ " TEXT NOT NULL,"
                + sqlcontractGolf.CourseColumns.COURSE_ADDR2+ " TEXT NOT NULL,"
                + sqlcontractGolf.CourseColumns.COURSE_PHONE+ " TEXT NOT NULL,"
                + sqlcontractGolf.CourseColumns.COURSE_EMAIL + " TEXT NOT NULL,"
                + sqlcontractGolf.CourseColumns.COURSE_RATING + " INTEGER NOT NULL,"
                + sqlcontractGolf.CourseColumns.COURSE_SLOPE + " INTEGER NOT NULL,"
                + "UNIQUE (" + sqlcontractGolf.CourseColumns.COURSE_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE " + Tables.PLAYERS + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + sqlcontractGolf.PlayerColumns.PLAYER_ID + " TEXT NOT NULL,"
                + sqlcontractGolf.PlayerColumns.PLAYER_NAME + " TEXT NOT NULL,"
                + sqlcontractGolf.PlayerColumns.PLAYER_ADDR1 + " TEXT NOT NULL,"
                + sqlcontractGolf.PlayerColumns.PLAYER_ADDR2 + " TEXT NOT NULL,"
                + sqlcontractGolf.PlayerColumns.PLAYER_EMAIL + " TEXT NOT NULL,"
                + sqlcontractGolf.PlayerColumns.PLAYER_PHONE + " TEXT NOT NULL,"
                + sqlcontractGolf.PlayerColumns.PLAYER_HANDICAP + " INTEGER NOT NULL,"
                + "UNIQUE (" + sqlcontractGolf.PlayerColumns.PLAYER_ID + ") ON CONFLICT REPLACE)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Tables.COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.PLAYERS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tables.COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.PLAYERS);
        onCreate(db);
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(sqlcontractGolf.DATABASE_NAME);
    }
}
