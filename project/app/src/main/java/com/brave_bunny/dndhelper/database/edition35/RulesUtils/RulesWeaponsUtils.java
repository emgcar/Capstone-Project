package com.brave_bunny.dndhelper.database.edition35.RulesUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;

/**
 * Created by Jemma on 2/3/2017.
 */

public class RulesWeaponsUtils {


    private static final String[] WEAPONS_COLUMNS = {
            RulesContract.WeaponEntry.TABLE_NAME + "." + RulesContract.WeaponEntry._ID,
            RulesContract.WeaponEntry.COLUMN_NAME,
            RulesContract.WeaponEntry.COLUMN_WEAPON_WEIGHT,
            RulesContract.WeaponEntry.COLUMN_WEAPON_COST
    };

    public static final int COL_WEAPON_ID = 0;
    public static final int COL_WEAPON_NAME = 1;
    public static final int COL_WEAPON_WEIGHT = 2;
    public static final int COL_WEAPON_COST = 3;


    private static final String tableName = RulesContract.WeaponEntry.TABLE_NAME;

    private static ContentValues getWeapon(Context context, int weaponId) {
        String query = "SELECT * FROM " + tableName + " WHERE " + WEAPONS_COLUMNS[COL_WEAPON_ID] + " = ?";
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
