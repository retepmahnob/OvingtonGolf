package com.peter.ovingtongolf.databaseProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

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

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = sqlcontractGolf.CONTENT_AUTHORITY;

        matcher.addURI(authority, "course", COURSES);
        matcher.addURI(authority, "course/*", COURSES_ID);

        matcher.addURI(authority, "player", PLAYERS);
        matcher.addURI(authority, "player/*", PLAYERS_ID);
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
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Uisng SQLiteQueryBuilder instead of query() method

        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        final int match = sUriMatcher.match(uri);
        // Most cases are handled with simple SelectionBuilder
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
        int retVal = builder.where(selection, selectionArgs).delete(db);
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
            default: {
                throw new UnsupportedOperationException("Unknown uri for " + match + ": " + uri);
            }
        }
    }
}