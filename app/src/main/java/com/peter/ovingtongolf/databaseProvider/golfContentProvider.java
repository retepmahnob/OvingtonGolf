package com.peter.ovingtongolf.databaseProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;

import java.util.List;

/**
 * Created by peter on 24/12/14.
 */
public class golfContentProvider extends ContentProvider {

    private GolfDatabase mOpenHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final int COURSES = 100;
    private static final int COURSES_ID = 101;

    private static final int PLAYERS = 200;
    private static final int PLAYERS_ID = 201;

    private static final int TEES = 300;
    private static final int TEES_ID = 301;

    private static final int HOLES = 400;
    private static final int HOLES_ID = 401;

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = sqlcontractGolf.CONTENT_AUTHORITY;

        matcher.addURI(authority, "courses", COURSES);
        matcher.addURI(authority, "courses/*", COURSES_ID);

        matcher.addURI(authority, "players", PLAYERS);
        matcher.addURI(authority, "players/*", PLAYERS_ID);

        matcher.addURI(authority, "tees", TEES);
        matcher.addURI(authority, "tees/*", TEES_ID);

        matcher.addURI(authority, "holes", PLAYERS);
        matcher.addURI(authority, "holes/*", PLAYERS_ID);
        return matcher;
    }

    @Override
    public boolean onCreate () {
        mOpenHelper = new GolfDatabase(getContext());
        return true;
    }

    private void deleteDatabase() {

         mOpenHelper.close();
        Context context = getContext();
        GolfDatabase.deleteDatabase(context);
        mOpenHelper = new GolfDatabase(getContext());
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case COURSES:
                return sqlcontractGolf.Course.CONTENT_TYPE;
            case PLAYERS:
                return sqlcontractGolf.Player.CONTENT_TYPE;
            case TEES:
                return sqlcontractGolf.Tees.CONTENT_TYPE;
            case HOLES:
                return sqlcontractGolf.Holes.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    /** Returns a tuple of question marks. For example, if count is 3, returns "(?,?,?)". */
    private String makeQuestionMarkTuple(int count) {
        if (count < 1) {
            return "()";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(?");
        for (int i = 1; i < count; i++) {
            stringBuilder.append(",?");
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private SelectionBuilder buildExpandedSelection(Uri uri, int match) {

        final SelectionBuilder builder = new SelectionBuilder();

        switch (match) {
            case COURSES: {
                return builder.table(GolfDatabase.Tables.COURSES);
            }
            case COURSES_ID: {
                final String courseId = sqlcontractGolf.Course.getId(uri);
                return builder.table(GolfDatabase.Tables.COURSES)
                        .where(sqlcontractGolf.Course.COURSE_ID + "=?", courseId);
            }
            case PLAYERS: {
                return builder.table(GolfDatabase.Tables.PLAYERS);
            }
            case PLAYERS_ID: {
                final String playerId = sqlcontractGolf.Player.getId(uri);
                return builder.table(GolfDatabase.Tables.PLAYERS)
                        .where(sqlcontractGolf.Player.PLAYER_ID + "=?", playerId);
            }
            case TEES: {
                return builder.table(GolfDatabase.Tables.TEES);
            }
            case TEES_ID: {
                final String teeId = sqlcontractGolf.Tees.getId(uri);
                return builder.table(GolfDatabase.Tables.TEES)
                        .where(sqlcontractGolf.Tees._ID + "=?", teeId);
            }
            case HOLES: {
                return builder.table(GolfDatabase.Tables.TEES);
            }
            case HOLES_ID: {
                final String holeId = sqlcontractGolf.Tees.getId(uri);
                return builder.table(GolfDatabase.Tables.HOLES)
                        .where(sqlcontractGolf.Holes.HOLE_ID + "=?", holeId);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }
    public Cursor rawQuery(String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selection, selectionArgs);

        return cursor;
    }

    public static String getTableName(Uri uri){
        String value = uri.getPath();
        value = value.replace("/", "");//we need to remove '/'
        return value;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Uisng SQLiteQueryBuilder instead of query() method

        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);

        final SelectionBuilder builder = buildExpandedSelection(uri, match);
        boolean distinct = !TextUtils.isEmpty(
                uri.getQueryParameter(sqlcontractGolf.QUERY_PARAMETER_DISTINCT));

        Cursor cursor = builder
                .where(selection, selectionArgs)
                .query(db, distinct, projection, sortOrder, null);


        Context context = getContext();
        if (null != context) {
            cursor.setNotificationUri(context.getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case COURSES: {
                db.insertOrThrow(GolfDatabase.Tables.COURSES, null, values);
                return sqlcontractGolf.Course.buildUri(values.getAsString(sqlcontractGolf.Course.COURSE_ID));
            }
            case PLAYERS: {
                db.insertOrThrow(GolfDatabase.Tables.PLAYERS, null, values);
                return sqlcontractGolf.Player.buildUri(values.getAsString(sqlcontractGolf.Player.PLAYER_ID));
            }
            case TEES: {
                db.insertOrThrow(GolfDatabase.Tables.TEES, null, values);
                return sqlcontractGolf.Tees.buildUri(values.getAsString(sqlcontractGolf.Tees._ID));
            }
            case HOLES: {
                db.insertOrThrow(GolfDatabase.Tables.HOLES, null, values);
                return sqlcontractGolf.Tees.buildUri(values.getAsString(sqlcontractGolf.Holes.HOLE_ID));
            }
            default: {
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
            }
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSimpleSelection(uri);
        final int match = sUriMatcher.match(uri);
        int retVal = builder
                .where(selection, selectionArgs)
                .delete(db);
        getContext().getContentResolver().notifyChange(uri, null);
        return retVal;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);


        final SelectionBuilder builder = buildSimpleSelection(uri);
        int retVal = builder.where(selection, selectionArgs).update(db, values);

        return retVal;
    }


    /**
     * Build a simple {@link SelectionBuilder} to match the requested
     * {@link android.net.Uri}. This is usually enough to support {@link #insert},
     * {@link #update}, and {@link #delete} operations.
     */
    private SelectionBuilder buildSimpleSelection(Uri uri) {
        final SelectionBuilder builder = new SelectionBuilder();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case COURSES: {
                return builder.table(GolfDatabase.Tables.COURSES);
            }
            case COURSES_ID: {
                final String courseId = sqlcontractGolf.Course.getId(uri);
                return builder.table(GolfDatabase.Tables.COURSES)
                        .where(sqlcontractGolf.Course.COURSE_ID + "=?", courseId);
            }
            case PLAYERS: {
                return builder.table(GolfDatabase.Tables.PLAYERS);
            }
            case PLAYERS_ID: {
                final String playerId = sqlcontractGolf.Player.getId(uri);
                return builder.table(GolfDatabase.Tables.PLAYERS)
                        .where(sqlcontractGolf.Player.PLAYER_ID + "=?", playerId);
            }
            case TEES: {
                return builder.table(GolfDatabase.Tables.TEES);
            }
            case TEES_ID: {
                final String teeId = sqlcontractGolf.Tees.getId(uri);
                return builder.table(GolfDatabase.Tables.TEES)
                        .where(sqlcontractGolf.Tees._ID + "=?", teeId);
            }
            case HOLES: {
                return builder.table(GolfDatabase.Tables.HOLES);
            }
            case HOLES_ID: {
                final String teeId = sqlcontractGolf.Holes.getId(uri);
                return builder.table(GolfDatabase.Tables.HOLES)
                        .where(sqlcontractGolf.Holes.HOLE_ID + "=?", teeId);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri for " + match + ": " + uri);
            }
        }
    }
}
