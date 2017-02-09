package com.brave_bunny.dndhelper.database.inprogress.InProgressUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;

/**
 * Handles all of the selected spells for in-progress characters.
 */

public class InProgressSpellsUtil {

    /* LABELS */

    private static String getTableName() {
        return InProgressContract.SpellEntry.TABLE_NAME;
    }

    private static String characterIdLabel() {
        return InProgressContract.SpellEntry.COLUMN_CHARACTER_ID;
    }

    private static String spellIdLabel() {
        return InProgressContract.SpellEntry.COLUMN_SPELL_ID;
    }

    /* PARSE VALUES*/

    public static long getCharacterId(ContentValues values) {
        return values.getAsLong(characterIdLabel());
    }

    public static void setCharacterId(ContentValues values, long charId) {
        values.put(characterIdLabel(), charId);
    }

    public static long getSpellId(ContentValues values) {
        return values.getAsLong(spellIdLabel());
    }

    public static void setSpellId(ContentValues values, long weaponId) {
        values.put(spellIdLabel(), weaponId);
    }

    /* DATABASE FUNCTIONS */

    public static void removeAllInProgressSpells(Context context, long rowIndex) {
        String query = characterIdLabel() + " = ?";
        String[] selectionArgs = new String[]{Long.toString(rowIndex)};
        deleteFromTable(context, query, selectionArgs);
    }

    public static void removeSpellSelection(Context context, long rowIndex, int domainId) {
        String query = characterIdLabel() + " = ? AND " +
                spellIdLabel() + " = ?";
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

    public static void addSpellSelection(Context context, long rowIndex, int spellId) {
        //spellId += numberSpellsUntilLevelOne;
        ContentValues values = new ContentValues();
        values.put(characterIdLabel(), rowIndex);
        values.put(spellIdLabel(), spellId);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(getTableName(), null, values);
        } finally {
            db.close();
        }
    }

    public static boolean isSpellSelected(Context context, long rowIndex, long spellId) {
        boolean isSelected = false;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    spellIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(spellId)});
            if (cursor.getCount() > 0) {
                isSelected = true;
            }
            cursor.close();
        } finally {
            db.close();
        }
        return isSelected;
    }

    public static int getNumberSpellSelected(Context context, long rowIndex) {
        int numSpells = 0;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            numSpells = cursor.getCount();
            cursor.close();
        } finally {
            db.close();
        }
        return numSpells;
    }

    public static int numberSpellsSelected(Context context, ContentValues values) {
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

    public static ContentValues[] getAllSpellsForCharacter(Context context, long rowIndex) {
        ContentValues[] allSpells;

        InProgressDbHelper inProgressDbHelper = new InProgressDbHelper(context);
        SQLiteDatabase inProgressDb = inProgressDbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName() + " WHERE "
                    + characterIdLabel() + " = ?";
            Cursor cursor = inProgressDb.rawQuery(query, new String[]{Long.toString(rowIndex)});

            int numSpells = cursor.getCount();
            cursor.moveToFirst();
            allSpells = new ContentValues[numSpells];

            for (int i = 0; i < numSpells; i++) {
                allSpells[i] = cursorRowToContentValues(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        } finally {
            inProgressDb.close();
        }
        return allSpells;
    }
}
