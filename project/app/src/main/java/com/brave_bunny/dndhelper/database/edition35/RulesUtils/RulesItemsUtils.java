package com.brave_bunny.dndhelper.database.edition35.RulesUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;

/**
 * Handles all of the item data.
 */

public class RulesItemsUtils {

    /* LABELS */

    private static String getTableName() {
        return RulesContract.ItemEntry.TABLE_NAME;
    }

    private static String itemIdLabel() {
        return RulesContract.ItemEntry._ID;
    }

    private static String itemNameLabel() {
        return RulesContract.ItemEntry.COLUMN_NAME;
    }

    private static String itemWeightLabel() {
        return RulesContract.ItemEntry.COLUMN_ITEM_WEIGHT;
    }

    private static String itemCostLabel() {
        return RulesContract.ItemEntry.COLUMN_ITEM_COST;
    }

    /* PARSE VALUES*/

    public static long getItemId(ContentValues values) {
        return values.getAsLong(itemIdLabel());
    }

    public static String getItemName(ContentValues values) {
        return values.getAsString(itemNameLabel());
    }

    public static float getItemWeight(ContentValues values) {
        return values.getAsFloat(itemWeightLabel());
    }

    public static float getItemCost(ContentValues values) {
        return values.getAsFloat(itemCostLabel());
    }

    public static float getItemCost(Context context, long itemId) {
        ContentValues values = getItem(context, itemId);
        return getItemCost(values);
    }

    /* DATABASE FUNCTIONS */

    public static ContentValues getItem(Context context, long itemId) {
        String query = "SELECT * FROM " + getTableName() + " WHERE " + itemIdLabel() + " = ?";
        return getStats(context, query, itemId);
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


    public static Cursor getAllItems(Context context) {
        Cursor cursor;

        RulesDbHelper dbHelper = new RulesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + RulesContract.ItemEntry.TABLE_NAME;
            cursor = db.rawQuery(query, null);

            cursor.moveToFirst();
        } finally {
            db.close();
        }

        return cursor;
    }
}
