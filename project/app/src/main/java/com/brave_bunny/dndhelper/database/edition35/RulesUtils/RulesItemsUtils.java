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

public class RulesItemsUtils {


    private static final String[] ITEMS_COLUMNS = {
            RulesContract.ItemEntry.TABLE_NAME + "." + RulesContract.ItemEntry._ID,
            RulesContract.ItemEntry.COLUMN_NAME,
            RulesContract.ItemEntry.COLUMN_ITEM_WEIGHT,
            RulesContract.ItemEntry.COLUMN_ITEM_COST
    };

    public static final int COL_ITEMS_ID = 0;
    public static final int COL_ITEMS_NAME = 1;
    public static final int COL_ITEMS_WEIGHT = 2;
    public static final int COL_ITEMS_COST = 3;


    private static final String tableName = RulesContract.ItemEntry.TABLE_NAME;

    private static ContentValues getItem(Context context, int itemId) {
        String query = "SELECT * FROM " + tableName + " WHERE " + ITEMS_COLUMNS[COL_ITEMS_ID] + " = ?";
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
