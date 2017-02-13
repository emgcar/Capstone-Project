package com.brave_bunny.dndhelper.database.inprogress.InProgressUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesRacesUtils.RACE_HUMAN;

/**
 * Handles all of the selected feats for in-progress characters.
 */

public class InProgressFeatsUtil {

    /* LABELS */

    private static String getTableName() {
        return InProgressContract.FeatEntry.TABLE_NAME;
    }

    private static String characterIdLabel() {
        return InProgressContract.FeatEntry.COLUMN_CHARACTER_ID;
    }

    private static String featIdLabel() {
        return InProgressContract.FeatEntry.COLUMN_FEAT_ID;
    }

    /* PARSE VALUES*/

    public static long getCharacterId(ContentValues values) {
        return values.getAsLong(characterIdLabel());
    }

    public static void setCharacterId(ContentValues values, long charId) {
        values.put(characterIdLabel(), charId);
    }

    public static long getFeatId(ContentValues values) {
        return values.getAsLong(featIdLabel());
    }

    public static void setFeatId(ContentValues values, long featId) {
        values.put(featIdLabel(), featId);
    }

    /* DATABASE FUNCTIONS */

    public static int getNumberFeatsAllowed(ContentValues values) {
        int race = InProgressCharacterUtil.getCharacterRace(values);
        if (race == RACE_HUMAN) {
            return 2;
        } else {
            return 1;
        }
    }

    public static void removeAllInProgressFeats(Context context, long rowIndex) {
        String query = characterIdLabel() + " = ?";
        String[] selectionArgs = new String[]{Long.toString(rowIndex)};
        deleteFromTable(context, query, selectionArgs);
    }

    public static void removeFeatSelection(Context context, long rowIndex, long featId) {
        String query = characterIdLabel() + " = ? AND " +
                featIdLabel() + " = ?";
        String[] selectionArgs = new String[]{Long.toString(rowIndex), Long.toString(featId)};
        deleteFromTable(context, query, selectionArgs);
    }

    public static void deleteFromTable(Context context, String query, String[] selectionArgs) {
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            db.delete(getTableName(), query, selectionArgs);
        } finally {
            db.close();
        }
    }

    public static int getNumberFeatsSelected(Context context, long rowIndex) {
        int numDomains = 0;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            numDomains = cursor.getCount();
            cursor.close();
        } finally {
            db.close();
        }
        return numDomains;
    }

    public static boolean isFeatSelected(Context context, long rowIndex, long featId) {
        boolean isSelected = false;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    featIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(featId)});
            if (cursor.getCount() > 0) {
                isSelected = true;
            }
            cursor.close();
        } finally {
            db.close();
        }
        return isSelected;
    }

    public static void addFeatSelection(Context context, long rowIndex, long featId) {
        ContentValues values = new ContentValues();
        values.put(characterIdLabel(), rowIndex);
        values.put(featIdLabel(), featId);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(getTableName(), null, values);
        } finally {
            db.close();
        }
    }

    public static ContentValues[] getAllFeatsForCharacter(Context context, long rowIndex) {
        ContentValues[] allFeats;

        InProgressDbHelper inProgressDbHelper = new InProgressDbHelper(context);
        SQLiteDatabase inProgressDb = inProgressDbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName() + " WHERE "
                    + characterIdLabel() + " = ?";
            Cursor cursor = inProgressDb.rawQuery(query, new String[]{Long.toString(rowIndex)});

            int numFeats = cursor.getCount();
            cursor.moveToFirst();
            allFeats = new ContentValues[numFeats];

            for (int i = 0; i < numFeats; i++) {
                allFeats[i] = cursorRowToContentValues(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        } finally {
            inProgressDb.close();
        }
        return allFeats;
    }
}
