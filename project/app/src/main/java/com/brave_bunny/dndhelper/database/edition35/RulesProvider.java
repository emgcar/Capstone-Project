package com.brave_bunny.dndhelper.database.edition35;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;


/**
 * Created by Jemma on 1/10/2017.
 */

public class RulesProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private RulesDbHelper mOpenHelper;

    public static final int SKILLS = 100;
    public static final int CLERIC = 101;
    public static final int CLERIC_DOMAINS = 102;


    @Override
    public boolean onCreate() {
        mOpenHelper = new RulesDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        String tableName = null;

        switch (sUriMatcher.match(uri)) {
            case SKILLS:
                tableName = RulesContract.SkillsEntry.TABLE_NAME;
                break;
            case CLERIC:
                tableName = RulesContract.ClericEntry.TABLE_NAME;
                break;
            case CLERIC_DOMAINS:
                tableName = RulesContract.ClericDomainsEntry.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor = mOpenHelper.getReadableDatabase().query(
                tableName,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case SKILLS:
                return RulesContract.SkillsEntry.CONTENT_TYPE;
            case CLERIC:
                return RulesContract.ClericEntry.CONTENT_TYPE;
            case CLERIC_DOMAINS:
                return RulesContract.ClericDomainsEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return -1;
    }

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return -1;
    }

    public static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = RulesContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, RulesContract.PATH_SKILLS, SKILLS);
        matcher.addURI(authority, RulesContract.PATH_CLERIC, CLERIC);
        matcher.addURI(authority, RulesContract.PATH_CLERIC_DOMAINS, CLERIC_DOMAINS);

        return matcher;
    }

}