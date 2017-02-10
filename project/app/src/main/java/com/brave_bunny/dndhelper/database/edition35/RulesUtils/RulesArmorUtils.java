package com.brave_bunny.dndhelper.database.edition35.RulesUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;

/**
 * Handles all of the armor data.
 */

public class RulesArmorUtils {

    /* LABELS */

    private static String getTableName() {
        return RulesContract.ArmorEntry.TABLE_NAME;
    }

    private static String armorIdLabel() {
        return RulesContract.ArmorEntry._ID;
    }

    private static String armorNameLabel() {
        return RulesContract.ArmorEntry.COLUMN_NAME;
    }

    private static String armorWeightLabel() {
        return RulesContract.ArmorEntry.COLUMN_ARMOR_WEIGHT;
    }

    private static String armorCostLabel() {
        return RulesContract.ArmorEntry.COLUMN_ARMOR_COST;
    }

    /* PARSE VALUES*/

    public static long getArmorId(ContentValues values) {
        return values.getAsLong(armorIdLabel());
    }

    public static String getArmorName(ContentValues values) {
        return values.getAsString(armorNameLabel());
    }

    public static float getArmorWeight(ContentValues values) {
        return values.getAsFloat(armorWeightLabel());
    }

    public static float getArmorCost(ContentValues values) {
        return values.getAsFloat(armorCostLabel());
    }

    /* DATABASE FUNCTIONS */

    public static ContentValues getArmor(Context context, long armorId) {
        String query = "SELECT * FROM " + getTableName() + " WHERE " + armorIdLabel() + " = ?";
        return getStats(context, query, armorId);
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

    public static Cursor getAllArmor(Context context) {
        Cursor cursor;

        RulesDbHelper dbHelper = new RulesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + RulesContract.ArmorEntry.TABLE_NAME;
            cursor = db.rawQuery(query, null);

            cursor.moveToFirst();
        } finally {
            db.close();
        }

        return cursor;
    }
}
