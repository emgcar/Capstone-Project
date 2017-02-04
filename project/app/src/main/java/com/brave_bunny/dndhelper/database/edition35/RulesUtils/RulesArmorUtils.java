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

public class RulesArmorUtils {
    private static final String[] ARMOR_COLUMNS = {
            RulesContract.ArmorEntry.TABLE_NAME + "." + RulesContract.ArmorEntry._ID,
            RulesContract.ArmorEntry.COLUMN_NAME
    };

    public static final int COL_ARMOR_ID = 0;
    public static final int COL_ARMOR_NAME = 1;
    public static final int COL_ARMOR_WEIGHT = 2;
    public static final int COL_ARMOR_COST = 3;

    public static final String tableName = RulesContract.ArmorEntry.TABLE_NAME;

    public static Cursor getAllArmor(Context context) {
        Cursor cursor;

        RulesDbHelper dbHelper = new RulesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName;
            cursor = db.rawQuery(query, null);

            cursor.moveToFirst();
        } finally {
            db.close();
        }

        return cursor;
    }

    public static ContentValues getArmorData(Context context, long armorId) {
        ContentValues values = null;

        RulesDbHelper dbHelper = new RulesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName + " WHERE " + ARMOR_COLUMNS[COL_ARMOR_ID] + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(armorId)});

            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                values = Utility.cursorRowToContentValues(cursor);
            }
            cursor.close();
        } finally {
            db.close();
        }

        return values;
    }
}
