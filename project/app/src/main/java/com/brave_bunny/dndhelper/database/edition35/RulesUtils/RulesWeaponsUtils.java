package com.brave_bunny.dndhelper.database.edition35.RulesUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;

/**
 * Handles all of the weapon data.
 */

public class RulesWeaponsUtils {

    /* LABELS */

    private static String getTableName() {
        return RulesContract.WeaponEntry.TABLE_NAME;
    }

    private static String weaponIdLabel() {
        return RulesContract.WeaponEntry._ID;
    }

    private static String weaponNameLabel() {
        return RulesContract.WeaponEntry.COLUMN_NAME;
    }

    private static String weaponWeightLabel() {
        return RulesContract.WeaponEntry.COLUMN_WEAPON_WEIGHT;
    }

    private static String weaponCostLabel() {
        return RulesContract.WeaponEntry.COLUMN_WEAPON_COST;
    }

    /* PARSE VALUES*/

    public static long getWeaponId(ContentValues values) {
        return values.getAsLong(weaponIdLabel());
    }

    public static String getWeaponName(ContentValues values) {
        return values.getAsString(weaponNameLabel());
    }

    public static float getWeaponWeight(ContentValues values) {
        return values.getAsFloat(weaponWeightLabel());
    }

    public static float getWeaponCost(ContentValues values) {
        return values.getAsFloat(weaponCostLabel());
    }

    /* DATABASE FUNCTIONS */

    public static ContentValues getWeapon(Context context, long weaponId) {
        String query = "SELECT * FROM " + getTableName() + " WHERE " + weaponIdLabel() + " = ?";
        return getStats(context, query, weaponId);
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

    public static Cursor getAllWeapons(Context context) {
        Cursor cursor;

        RulesDbHelper dbHelper = new RulesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + RulesContract.WeaponEntry.TABLE_NAME;
            cursor = db.rawQuery(query, null);

            cursor.moveToFirst();
        } finally {
            db.close();
        }

        return cursor;
    }
}
