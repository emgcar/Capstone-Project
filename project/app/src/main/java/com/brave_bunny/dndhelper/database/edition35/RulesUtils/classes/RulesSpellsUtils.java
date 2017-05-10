package com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;

/**
 * Handles all of the spell data.
 */

public class RulesSpellsUtils {

    /* LABELS */

    private static String getTableName() {
        return RulesContract.SpellsEntry.TABLE_NAME;
    }

    private static String spellIdLabel() {
        return RulesContract.SpellsEntry._ID;
    }

    private static String spellNameLabel() {
        return RulesContract.SpellsEntry.COLUMN_NAME;
    }

    private static String spellLevelLabel() {
        return RulesContract.SpellsEntry.COLUMN_LEVEL;
    }

    /* PARSE VALUES*/

    public static long getSpellId(ContentValues values) {
        return values.getAsLong(spellIdLabel());
    }

    public static String getSpellName(ContentValues values) {
        return values.getAsString(spellNameLabel());
    }

    public static int getSpellLevel(ContentValues values) {
        return values.getAsInteger(spellLevelLabel());
    }

    /* DATABASE FUNCTIONS */

    public static ContentValues getSpell(Context context, long spellId) {
        String query = "SELECT * FROM " + getTableName() + " WHERE " + spellIdLabel() + " = ?";
        return getStats(context, query, spellId);
    }

    private static ContentValues getStats(Context context, String query, long index) {
        ContentValues values = null;

        RulesDbHelper dbHelper = new RulesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(index)});

            cursor.moveToFirst();

            if(cursor.getCount() > 0) {
                values = Utility.cursorRowToContentValues(cursor);
            }
            cursor.close();
        } finally {
            db.close();
        }

        return values;
    }

    public static Cursor getAllSpells(Context context) {
        String query = "SELECT * FROM " + getTableName();
        return getSpellList(context, query, null);
    }

    public static Cursor getSpellList(Context context, String query, String[] selectionArgs) {
        Cursor cursor;

        RulesDbHelper dbHelper = new RulesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            cursor = db.rawQuery(query, selectionArgs);

            cursor.moveToFirst();
        } finally {
            db.close();
        }

        return cursor;
    }
}
