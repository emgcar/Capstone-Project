package com.brave_bunny.dndhelper.database.inprogress;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Jemma on 1/11/2017.
 */

public class InProgressProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private InProgressDbHelper mOpenHelper;

    public static final int INPROGRESS_STATS = 100;
    public static final int INPROGRESS_CLERIC_DOMAIN = 101;
    public static final int INPROGRESS_SPELLS = 102;
    public static final int INPROGRESS_SKILLS = 103;
    public static final int INPROGRESS_FEATS = 104;


    @Override
    public boolean onCreate() {
        mOpenHelper = new InProgressDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        String tableName = null;

        switch (sUriMatcher.match(uri)) {
            case INPROGRESS_STATS:
                tableName = InProgressContract.CharacterEntry.TABLE_NAME;
                break;
            case INPROGRESS_CLERIC_DOMAIN:
                tableName = InProgressContract.ClericDomainEntry.TABLE_NAME;
                break;
            case INPROGRESS_SPELLS:
                tableName = InProgressContract.SpellEntry.TABLE_NAME;
                break;
            case INPROGRESS_SKILLS:
                tableName = InProgressContract.SkillEntry.TABLE_NAME;
                break;
            case INPROGRESS_FEATS:
                tableName = InProgressContract.FeatEntry.TABLE_NAME;
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
            case INPROGRESS_STATS:
                return InProgressContract.CharacterEntry.CONTENT_TYPE;
            case INPROGRESS_CLERIC_DOMAIN:
                return InProgressContract.ClericDomainEntry.CONTENT_TYPE;
            case INPROGRESS_SPELLS:
                return InProgressContract.SpellEntry.CONTENT_TYPE;
            case INPROGRESS_SKILLS:
                return InProgressContract.SkillEntry.CONTENT_TYPE;
            case INPROGRESS_FEATS:
                return InProgressContract.FeatEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case INPROGRESS_STATS: {
                long _id = db.insert(InProgressContract.CharacterEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = InProgressContract.CharacterEntry.buildCharacterUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case INPROGRESS_CLERIC_DOMAIN: {
                long _id = db.insert(InProgressContract.ClericDomainEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = InProgressContract.ClericDomainEntry.buildClericDomainUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case INPROGRESS_SPELLS: {
                long _id = db.insert(InProgressContract.SpellEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = InProgressContract.SpellEntry.buildSpellUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case INPROGRESS_SKILLS: {
                long _id = db.insert(InProgressContract.SkillEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = InProgressContract.SkillEntry.buildSkillUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case INPROGRESS_FEATS: {
                long _id = db.insert(InProgressContract.FeatEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = InProgressContract.FeatEntry.buildFeatUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        String tableName;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case INPROGRESS_STATS:
                tableName = InProgressContract.CharacterEntry.TABLE_NAME;
                break;
            case INPROGRESS_CLERIC_DOMAIN:
                tableName = InProgressContract.ClericDomainEntry.TABLE_NAME;
                break;
            case INPROGRESS_SPELLS:
                tableName = InProgressContract.SpellEntry.TABLE_NAME;
                break;
            case INPROGRESS_SKILLS:
                tableName = InProgressContract.SkillEntry.TABLE_NAME;
                break;
            case INPROGRESS_FEATS:
                tableName = InProgressContract.FeatEntry.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        rowsDeleted = db.delete(tableName, selection, selectionArgs);
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;
        String tableName;

        switch (match) {
            case INPROGRESS_STATS:
                tableName = InProgressContract.CharacterEntry.TABLE_NAME;
                break;
            case INPROGRESS_CLERIC_DOMAIN:
                tableName = InProgressContract.ClericDomainEntry.TABLE_NAME;
                break;
            case INPROGRESS_SPELLS:
                tableName = InProgressContract.SpellEntry.TABLE_NAME;
                break;
            case INPROGRESS_SKILLS:
                tableName = InProgressContract.SkillEntry.TABLE_NAME;
                break;
            case INPROGRESS_FEATS:
                tableName = InProgressContract.FeatEntry.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        rowsUpdated = db.update(tableName, values, selection, selectionArgs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    public static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = InProgressContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, InProgressContract.PATH_INPROGRESS_STAT, INPROGRESS_STATS);
        matcher.addURI(authority, InProgressContract.PATH_INPROGRESS_CLERIC_DOMAIN, INPROGRESS_CLERIC_DOMAIN);
        matcher.addURI(authority, InProgressContract.PATH_INPROGRESS_SPELL, INPROGRESS_SPELLS);
        matcher.addURI(authority, InProgressContract.PATH_INPROGRESS_SKILL, INPROGRESS_SKILLS);
        matcher.addURI(authority, InProgressContract.PATH_INPROGRESS_FEAT, INPROGRESS_FEATS);

        return matcher;
    }
}
