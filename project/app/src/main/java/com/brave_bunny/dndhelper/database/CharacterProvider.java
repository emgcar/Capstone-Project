package com.brave_bunny.dndhelper.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.brave_bunny.dndhelper.database.CharacterContract;
import com.brave_bunny.dndhelper.database.CharacterDbHelper;

/**
 * Created by Jemma on 8/7/2016.
 */
public class CharacterProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private CharacterDbHelper mOpenHelper;

    public static final int CHARACTER = 100;
    public static final int INPROGRESS = 101;
    public static final int CHARACTER_CLASSES = 102;
    public static final int CHARACTER_SPELLS = 103;
    public static final int CHARACTER_SKILLS = 104;
    public static final int CHARACTER_FEATS = 105;
    public static final int CHARACTER_ITEMS = 106;
    public static final int CHARACTER_ARMOR = 107;
    public static final int CHARACTER_WEAPONS = 108;


    @Override
    public boolean onCreate() {
        mOpenHelper = new CharacterDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        String tableName = null;

        switch (sUriMatcher.match(uri)) {
            case CHARACTER:
                tableName = CharacterContract.CharacterEntry.TABLE_NAME;
                break;
            case INPROGRESS:
                tableName = CharacterContract.InProgressEntry.TABLE_NAME;
                break;
            case CHARACTER_CLASSES:
                tableName = CharacterContract.CharacterClasses.TABLE_NAME;
                break;
            case CHARACTER_SPELLS:
                tableName = CharacterContract.CharacterSpells.TABLE_NAME;
                break;
            case CHARACTER_SKILLS:
                tableName = CharacterContract.CharacterSkills.TABLE_NAME;
                break;
            case CHARACTER_FEATS:
                tableName = CharacterContract.CharacterFeats.TABLE_NAME;
                break;
            case CHARACTER_ITEMS:
                tableName = CharacterContract.CharacterItems.TABLE_NAME;
                break;
            case CHARACTER_ARMOR:
                tableName = CharacterContract.CharacterArmor.TABLE_NAME;
                break;
            case CHARACTER_WEAPONS:
                tableName = CharacterContract.CharacterWeapons.TABLE_NAME;
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
            case CHARACTER:
                return CharacterContract.CharacterEntry.CONTENT_TYPE;
            case INPROGRESS:
                return CharacterContract.InProgressEntry.CONTENT_TYPE;
            case CHARACTER_CLASSES:
                return CharacterContract.CharacterClasses.CONTENT_TYPE;
            case CHARACTER_SPELLS:
                return CharacterContract.CharacterSpells.CONTENT_TYPE;
            case CHARACTER_SKILLS:
                return CharacterContract.CharacterSkills.CONTENT_TYPE;
            case CHARACTER_FEATS:
                return CharacterContract.CharacterFeats.CONTENT_TYPE;
            case CHARACTER_ITEMS:
                return CharacterContract.CharacterItems.CONTENT_TYPE;
            case CHARACTER_ARMOR:
                return CharacterContract.CharacterArmor.CONTENT_TYPE;
            case CHARACTER_WEAPONS:
                return CharacterContract.CharacterWeapons.CONTENT_TYPE;
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
            case CHARACTER: {
                long _id = db.insert(CharacterContract.CharacterEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = CharacterContract.CharacterEntry.buildCharacterUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case INPROGRESS: {
                long _id = db.insert(CharacterContract.InProgressEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = CharacterContract.InProgressEntry.buildCharacterUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CHARACTER_CLASSES: {
                long _id = db.insert(CharacterContract.CharacterClasses.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = CharacterContract.CharacterClasses.buildCharacterUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CHARACTER_SPELLS: {
                long _id = db.insert(CharacterContract.CharacterSpells.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = CharacterContract.CharacterSpells.buildCharacterUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CHARACTER_SKILLS: {
                long _id = db.insert(CharacterContract.CharacterSkills.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = CharacterContract.CharacterSkills.buildCharacterUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CHARACTER_FEATS: {
                long _id = db.insert(CharacterContract.CharacterFeats.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = CharacterContract.CharacterFeats.buildCharacterUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CHARACTER_ITEMS: {
                long _id = db.insert(CharacterContract.CharacterItems.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = CharacterContract.CharacterItems.buildCharacterUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CHARACTER_ARMOR: {
                long _id = db.insert(CharacterContract.CharacterArmor.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = CharacterContract.CharacterArmor.buildCharacterUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CHARACTER_WEAPONS: {
                long _id = db.insert(CharacterContract.CharacterWeapons.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = CharacterContract.CharacterWeapons.buildCharacterUri(_id);
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
            case CHARACTER:
                tableName = CharacterContract.CharacterEntry.TABLE_NAME;
                break;
            case INPROGRESS:
                tableName = CharacterContract.InProgressEntry.TABLE_NAME;
                break;
            case CHARACTER_CLASSES:
                tableName = CharacterContract.CharacterClasses.TABLE_NAME;
                break;
            case CHARACTER_SPELLS:
                tableName = CharacterContract.CharacterSpells.TABLE_NAME;
                break;
            case CHARACTER_SKILLS:
                tableName = CharacterContract.CharacterSkills.TABLE_NAME;
                break;
            case CHARACTER_FEATS:
                tableName = CharacterContract.CharacterFeats.TABLE_NAME;
                break;
            case CHARACTER_ITEMS:
                tableName = CharacterContract.CharacterItems.TABLE_NAME;
                break;
            case CHARACTER_ARMOR:
                tableName = CharacterContract.CharacterArmor.TABLE_NAME;
                break;
            case CHARACTER_WEAPONS:
                tableName = CharacterContract.CharacterWeapons.TABLE_NAME;
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
            case CHARACTER:
                tableName = CharacterContract.CharacterEntry.TABLE_NAME;
                break;
            case INPROGRESS:
                tableName = CharacterContract.InProgressEntry.TABLE_NAME;
                break;
            case CHARACTER_CLASSES:
                tableName = CharacterContract.CharacterClasses.TABLE_NAME;
                break;
            case CHARACTER_SPELLS:
                tableName = CharacterContract.CharacterSpells.TABLE_NAME;
                break;
            case CHARACTER_SKILLS:
                tableName = CharacterContract.CharacterSkills.TABLE_NAME;
                break;
            case CHARACTER_FEATS:
                tableName = CharacterContract.CharacterFeats.TABLE_NAME;
                break;
            case CHARACTER_ITEMS:
                tableName = CharacterContract.CharacterItems.TABLE_NAME;
                break;
            case CHARACTER_ARMOR:
                tableName = CharacterContract.CharacterArmor.TABLE_NAME;
                break;
            case CHARACTER_WEAPONS:
                tableName = CharacterContract.CharacterWeapons.TABLE_NAME;
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
        final String authority = CharacterContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, CharacterContract.PATH_CHARACTERS, CHARACTER);
        matcher.addURI(authority, CharacterContract.PATH_INPROGRESS, INPROGRESS);
        matcher.addURI(authority, CharacterContract.PATH_CHARACTER_CLASS, CHARACTER_CLASSES);
        matcher.addURI(authority, CharacterContract.PATH_CHARACTER_SPELLS, CHARACTER_SPELLS);
        matcher.addURI(authority, CharacterContract.PATH_CHARACTER_FEATS, CHARACTER_FEATS);
        matcher.addURI(authority, CharacterContract.PATH_CHARACTER_SKILLS, CHARACTER_SKILLS);
        matcher.addURI(authority, CharacterContract.PATH_CHARACTER_ITEMS, CHARACTER_ITEMS);
        matcher.addURI(authority, CharacterContract.PATH_CHARACTER_ARMOR, CHARACTER_ARMOR);
        matcher.addURI(authority, CharacterContract.PATH_CHARACTER_WEAPONS, CHARACTER_WEAPONS);

        return matcher;
    }

}
