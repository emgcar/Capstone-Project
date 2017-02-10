package com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;

/**
 * Handles all of the fighter data.
 */

public class RulesFighterUtils {

    /* LABELS */

    private static String getTableName() {
        return RulesContract.FighterEntry.TABLE_NAME;
    }

    private static String fighterLevelLabel() {
        return RulesContract.FighterEntry._ID;
    }

    private static String fighterBaseAttack1Label() {
        return RulesContract.FighterEntry.COLUMN_BASE_ATTACK_1;
    }

    private static String fighterBaseAttack2Label() {
        return RulesContract.FighterEntry.COLUMN_BASE_ATTACK_2;
    }

    private static String fighterBaseAttack3Label() {
        return RulesContract.FighterEntry.COLUMN_BASE_ATTACK_3;
    }

    private static String fighterBaseAttack4Label() {
        return RulesContract.FighterEntry.COLUMN_BASE_ATTACK_4;
    }

    private static String fighterFortLabel() {
        return RulesContract.FighterEntry.COLUMN_FORT;
    }

    private static String fighterRefLabel() {
        return RulesContract.FighterEntry.COLUMN_REF;
    }

    private static String fighterWillLabel() {
        return RulesContract.FighterEntry.COLUMN_WILL;
    }

    /* PARSE VALUES*/

    public static long getFighterLevel(ContentValues values) {
        return values.getAsLong(fighterLevelLabel());
    }

    public static long getFighterBaseAttack1(ContentValues values) {
        return values.getAsLong(fighterBaseAttack1Label());
    }

    public static long getFighterBaseAttack2(ContentValues values) {
        return values.getAsLong(fighterBaseAttack2Label());
    }

    public static long getFighterBaseAttack3(ContentValues values) {
        return values.getAsLong(fighterBaseAttack3Label());
    }

    public static long getFighterBaseAttack4(ContentValues values) {
        return values.getAsLong(fighterBaseAttack4Label());
    }

    public static long getFighterFort(ContentValues values) {
        return values.getAsLong(fighterFortLabel());
    }

    public static long getFighterRef(ContentValues values) {
        return values.getAsLong(fighterRefLabel());
    }

    public static long getFighterWill(ContentValues values) {
        return values.getAsLong(fighterWillLabel());
    }

    /* DATABASE FUNCTIONS */

    public static ContentValues getFighterStatsByLevel(Context context, int level) {
        String query = "SELECT * FROM " + getTableName() + " WHERE " +
                fighterLevelLabel() + " = ?";
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
