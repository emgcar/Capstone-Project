package com.brave_bunny.dndhelper.database.inprogress.InProgressUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_CLERIC;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_WIZARD;

/**
 * Handles all of the selected domains for in-progress characters.
 */

public class InProgressDomainsUtil {

    /* LABELS - Should be private */

    private static String getTableName() {
        return InProgressContract.ClericDomainEntry.TABLE_NAME;
    }

    private static String characterIdLabel() {
        return InProgressContract.ClericDomainEntry.COLUMN_CHARACTER_ID;
    }

    private static String domainIdLabel() {
        return InProgressContract.ClericDomainEntry.COLUMN_DOMAIN_ID;
    }

    /* PARSE VALUES */

    public static long getCharacterId(ContentValues values) {
        return values.getAsLong(characterIdLabel());
    }

    public static void setCharacterId(ContentValues values, long charId) {
        values.put(characterIdLabel(), charId);
    }

    public static long getDomainId(ContentValues values) {
        return values.getAsLong(domainIdLabel());
    }

    public static void setDomainId(ContentValues values, long domainId) {
        values.put(domainIdLabel(), domainId);
    }

    /* DATABASE FUNCTIONS */

    public static void removeAllInProgressDomains(Context context, long rowIndex) {
        String query = characterIdLabel() + " = ?";
        String[] selectionArgs = new String[]{Long.toString(rowIndex)};
        deleteFromTable(context, query, selectionArgs);
    }

    public static void removeDomainSelection(Context context, long rowIndex, long domainId) {
        String query = characterIdLabel() + " = ? AND " +
                domainIdLabel() + " = ?";
        String[] selectionArgs = new String[]{Long.toString(rowIndex), Long.toString(domainId)};
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

    public static void addDomainSelection(Context context, long rowIndex, long domainId) {
        ContentValues values = new ContentValues();
        values.put(characterIdLabel(), rowIndex);
        values.put(domainIdLabel(), domainId);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(getTableName(), null, values);
        } finally {
            db.close();
        }
    }

    public static boolean isDomainSelected(Context context, long rowIndex, long domainId) {
        boolean isSelected = false;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    domainIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(domainId)});
            if (cursor.getCount() > 0) {
                isSelected = true;
            }
            cursor.close();
        } finally {
            db.close();
        }
        return isSelected;
    }

    public static int getNumberDomainsSelected(Context context, long rowIndex) {
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

    public static int numberDomainsSelected(Context context, ContentValues values) {
        long rowIndex = values.getAsLong(InProgressContract.CharacterEntry._ID);
        int numberSpells;

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            numberSpells = cursor.getCount();
            cursor.close();
        } finally {
            db.close();
        }

        return numberSpells;
    }

    public static ContentValues[] getAllDomainsForCharacter(Context context, long rowIndex) {
        ContentValues[] allDomains;

        InProgressDbHelper inProgressDbHelper = new InProgressDbHelper(context);
        SQLiteDatabase inProgressDb = inProgressDbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName() + " WHERE "
                    + characterIdLabel() + " = ?";
            Cursor cursor = inProgressDb.rawQuery(query, new String[]{Long.toString(rowIndex)});

            int numDomains = cursor.getCount();
            cursor.moveToFirst();
            allDomains = new ContentValues[numDomains];

            for (int i = 0; i < numDomains; i++) {
                allDomains[i] = cursorRowToContentValues(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        } finally {
            inProgressDb.close();
        }
        return allDomains;
    }

    public static int getNumberDomainsAllowed(ContentValues inProgressCharacter) {
        int classId = InProgressCharacterUtil.getCharacterClass(inProgressCharacter);
        if (classId == CLASS_CLERIC) {
            return 2;
        }
        return 0;
    }
}
