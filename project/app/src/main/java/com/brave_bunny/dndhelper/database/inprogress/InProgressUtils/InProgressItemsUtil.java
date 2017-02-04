package com.brave_bunny.dndhelper.database.inprogress.InProgressUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;

/**
 * Created by Jemma on 2/3/2017.
 */

public class InProgressItemsUtil {

    private static final String[] ITEM_COLUMNS = {
            InProgressContract.ItemEntry.TABLE_NAME + "." + InProgressContract.ItemEntry._ID,
            InProgressContract.ItemEntry.COLUMN_CHARACTER_ID,
            InProgressContract.ItemEntry.COLUMN_ITEM_ID,
            InProgressContract.ItemEntry.COLUMN_COUNT
    };

    public static final int COL_ITEM_INPUT_ID = 0;
    public static final int COL_ITEM_CHARACTER_ID = 1;
    public static final int COL_ITEM_ITEM_ID = 2;
    public static final int COL_ITEM_COUNT = 3;

    private static final String tableName = InProgressContract.ItemEntry.TABLE_NAME;

    public static void removeAllInProgressItems(Context context, long rowIndex) {
        String query = ITEM_COLUMNS[COL_ITEM_CHARACTER_ID] + " = ?";
        String[] selectionArgs = new String[]{Long.toString(rowIndex)};
        deleteFromTable(context, query, selectionArgs);
    }

    public static void deleteFromTable(Context context, String query, String[] selectionArgs) {
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            db.delete(tableName, query, selectionArgs);
        } finally {
            db.close();
        }
    }

    public static boolean isItemListed(Context context, long rowIndex, long itemId) {
        boolean isSelected = false;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName
                    + " WHERE " + ITEM_COLUMNS[COL_ITEM_CHARACTER_ID] + " = ? AND " +
                    ITEM_COLUMNS[COL_ITEM_ITEM_ID] + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(itemId)});
            if (cursor.getCount() > 0) {
                isSelected = true;
            }
            cursor.close();
        } finally {
            db.close();
        }
        return isSelected;
    }

    public static int getItemCount(Context context, long rowIndex, long itemId) {
        int count = 0;
        if (isItemListed(context, rowIndex, itemId)) {
            InProgressDbHelper dbHelper = new InProgressDbHelper(context);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            try {
                String query = "SELECT * FROM " + tableName
                        + " WHERE " + ITEM_COLUMNS[COL_ITEM_CHARACTER_ID] + " = ? AND " +
                        ITEM_COLUMNS[COL_ITEM_ITEM_ID] + " = ?";
                Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(itemId)});
                cursor.moveToFirst();
                count = cursor.getInt(COL_ITEM_COUNT);
                cursor.close();
            } finally {
                db.close();
            }
        }
        return count;
    }

    public static void addItemSelection(Context context, long rowIndex, long itemId, int count) {
        ContentValues values = new ContentValues();
        values.put(ITEM_COLUMNS[COL_ITEM_CHARACTER_ID], rowIndex);
        values.put(ITEM_COLUMNS[COL_ITEM_ITEM_ID], itemId);
        values.put(ITEM_COLUMNS[COL_ITEM_COUNT], count);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(tableName, null, values);
        } finally {
            db.close();
        }
    }

    public static void updateItemSelection(Context context, long rowIndex, long itemId, int count) {
        ContentValues values = new ContentValues();
        values.put(ITEM_COLUMNS[COL_ITEM_CHARACTER_ID], rowIndex);
        values.put(ITEM_COLUMNS[COL_ITEM_ITEM_ID], itemId);
        values.put(ITEM_COLUMNS[COL_ITEM_COUNT], count);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName
                    + " WHERE " + ITEM_COLUMNS[COL_ITEM_CHARACTER_ID] + " = ? AND " +
                    ITEM_COLUMNS[COL_ITEM_ITEM_ID] + " = ?";
            String[] selectionArgs = new String[]{Long.toString(rowIndex), Long.toString(itemId)};
            db.update(tableName, values, query, selectionArgs);
        } finally {
            db.close();
        }
    }

    public static void addOrUpdateItemSelection(Context context, long rowIndex, long itemId, int count) {
        if (isItemListed(context, rowIndex, itemId)) {
            updateItemSelection(context, rowIndex, itemId, count);
        } else {
            addItemSelection(context, rowIndex, itemId, count);
        }
    }
}
