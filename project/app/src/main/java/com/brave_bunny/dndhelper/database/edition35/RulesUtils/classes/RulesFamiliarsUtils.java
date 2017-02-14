package com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;

/**
 * Handles all of the familiar data.
 */

public class RulesFamiliarsUtils {

    /* LABELS */

    private static String getTableName() {
        return RulesContract.FamiliarEntry.TABLE_NAME;
    }

    private static String familiarIdLabel() {
        return RulesContract.FamiliarEntry._ID;
    }

    private static String familiarNameLabel() {
        return RulesContract.FamiliarEntry.COLUMN_NAME;
    }

    private static String familiarSkillIdLabel() {
        return RulesContract.FamiliarEntry.COLUMN_SKILL_ID;
    }

    private static String familiarSkillBonusLabel() {
        return RulesContract.FamiliarEntry.COLUMN_SKILL_BONUS;
    }

    /* PARSE VALUES*/

    public static long getFamiliarId(ContentValues values) {
        return values.getAsLong(familiarIdLabel());
    }

    public static String getFamiliarName(ContentValues values) {
        return values.getAsString(familiarNameLabel());
    }

    public static float getFamiliarSkillId(ContentValues values) {
        return values.getAsFloat(familiarSkillIdLabel());
    }

    public static float getFamiliarSkillBonus(ContentValues values) {
        return values.getAsFloat(familiarSkillBonusLabel());
    }

    /* DATABASE FUNCTIONS */

    private static ContentValues getFamiliar(Context context, int index) {
        String query = "SELECT * FROM " + getTableName() + " WHERE " + familiarIdLabel() + " = ?";
        return getStats(context, query, index);
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

    public static Cursor getAllFamiliars(Context context) {
        String query = "SELECT * FROM " + getTableName();
        return getFamiliarList(context, query, null);
    }

    public static Cursor getFamiliarList(Context context, String query, String[] selectionArgs) {
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
