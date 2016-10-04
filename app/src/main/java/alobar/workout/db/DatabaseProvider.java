package alobar.workout.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import alobar.util.Assert;

/**
 * Content provider for the database
 */
public class DatabaseProvider extends ContentProvider {

    private final static int ROUTE_EXERCISE_DIR = 1;

    private static final String TYPE_EXERCISE_DIR = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.alobarproductions.exercise";

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.Exercise.ENTITY_NAME, ROUTE_EXERCISE_DIR);
    }

    private DatabaseHelper helper;
    private ContentResolver resolver;

    @Override
    public boolean onCreate() {
        Context application = Assert.assigned(getContext()).getApplicationContext();
        helper = new DatabaseHelper(application);
        resolver = application.getContentResolver();
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ROUTE_EXERCISE_DIR:
                return TYPE_EXERCISE_DIR;
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor result;
        switch (uriMatcher.match(uri)) {
            case ROUTE_EXERCISE_DIR:
                result = Exercise.query(helper.getReadableDatabase(), projection, selection, selectionArgs, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        result.setNotificationUri(resolver, uri);
        return result;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        Uri result;
        switch (uriMatcher.match(uri)) {
            case ROUTE_EXERCISE_DIR:
                result = Exercise.insert(helper.getWritableDatabase(), values);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        resolver.notifyChange(result, null);
        return result;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int affected;
        switch (uriMatcher.match(uri)) {
            case ROUTE_EXERCISE_DIR:
                affected = Exercise.delete(helper.getWritableDatabase(), selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        if (affected > 0)
            resolver.notifyChange(uri, null);
        return affected;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case ROUTE_EXERCISE_DIR:
                return 0;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private static class Exercise {
        public static Cursor query(SQLiteDatabase db, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            return db.query(DatabaseContract.Exercise.ENTITY_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        }

        public static Uri insert(SQLiteDatabase db, ContentValues values) {
            long id = db.insert(DatabaseContract.Exercise.ENTITY_NAME, null, values);
            Assert.check(id >= 0, "Exercise insert failed");
            return Uri.withAppendedPath(DatabaseContract.Exercise.CONTENT_URI, Long.toString(id));
        }

        public static int delete(SQLiteDatabase db, String whereClause, String[] whereArgs) {
            return db.delete(DatabaseContract.Exercise.ENTITY_NAME, whereClause, whereArgs);
        }
    }
}