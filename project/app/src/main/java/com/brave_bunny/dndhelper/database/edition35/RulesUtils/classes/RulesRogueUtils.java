package com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;

/**
 * Handles all of the rogue data.
 */

public class RulesRogueUtils {

    /* LABELS */

    private static String getTableName() {
        return RulesContract.RogueEntry.TABLE_NAME;
    }

    private static String rogueLevelLabel() {
        return RulesContract.RogueEntry._ID;
    }

    private static String rogueBaseAttack1Label() {
        return RulesContract.RogueEntry.COLUMN_BASE_ATTACK_1;
    }

    private static String rogueBaseAttack2Label() {
        return RulesContract.RogueEntry.COLUMN_BASE_ATTACK_2;
    }

    private static String rogueBaseAttack3Label() {
        return RulesContract.RogueEntry.COLUMN_BASE_ATTACK_3;
    }

    private static String rogueFortLabel() {
        return RulesContract.RogueEntry.COLUMN_FORT;
    }

    private static String rogueRefLabel() {
        return RulesContract.RogueEntry.COLUMN_REF;
    }

    private static String rogueWillLabel() {
        return RulesContract.RogueEntry.COLUMN_WILL;
    }

    /* PARSE VALUES*/

    public static long getRogueLevel(ContentValues values) {
        return values.getAsLong(rogueLevelLabel());
    }

    public static long getRogueBaseAttack1(ContentValues values) {
        return values.getAsLong(rogueBaseAttack1Label());
    }

    public static long getRogueBaseAttack2(ContentValues values) {
        return values.getAsLong(rogueBaseAttack2Label());
    }

    public static long getRogueBaseAttack3(ContentValues values) {
        return values.getAsLong(rogueBaseAttack3Label());
    }

    public static long getRogueFort(ContentValues values) {
        return values.getAsLong(rogueFortLabel());
    }

    public static long getRogueRef(ContentValues values) {
        return values.getAsLong(rogueRefLabel());
    }

    public static long getRogueWill(ContentValues values) {
        return values.getAsLong(rogueWillLabel());
    }

    /* DATABASE FUNCTIONS */

    public static ContentValues getRogueStatsByLevel(Context context, int level) {
        String query = "SELECT * FROM " + getTableName() + " WHERE " +
                rogueLevelLabel() + " = ?";
        return getStats(context, query, level);
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
}
