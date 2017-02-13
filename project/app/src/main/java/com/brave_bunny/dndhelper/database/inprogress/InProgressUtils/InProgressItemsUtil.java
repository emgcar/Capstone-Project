package com.brave_bunny.dndhelper.database.inprogress.InProgressUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;

/**
 * Handles all of the selected items for in-progress characters.
 */

public class InProgressItemsUtil {

    /* LABELS */

    private static String getTableName() {
        return InProgressContract.ItemEntry.TABLE_NAME;
    }

    private static String idLabel() {
        return InProgressContract.ItemEntry._ID;
    }

    private static String characterIdLabel() {
        return InProgressContract.ItemEntry.COLUMN_CHARACTER_ID;
    }

    private static String itemIdLabel() {
        return InProgressContract.ItemEntry.COLUMN_ITEM_ID;
    }

    private static String itemCountLabel() {
        return InProgressContract.ItemEntry.COLUMN_COUNT;
    }


    /* PARSE VALUES*/

    public static long getId(ContentValues values) {
        return values.getAsLong(idLabel());
    }

    public static long getCharacterId(ContentValues values) {
        return values.getAsLong(characterIdLabel());
    }

    public static void setCharacterId(ContentValues values, long charId) {
        values.put(characterIdLabel(), charId);
    }

    public static long getItemId(ContentValues values) {
        return values.getAsLong(itemIdLabel());
    }

    public static void setItemId(ContentValues values, long armorId) {
        values.put(itemIdLabel(), armorId);
    }

    public static int getItemCount(ContentValues values) {
        return values.getAsInteger(itemCountLabel());
    }

    public static int getItemCount(Context context, long rowIndex, long itemId) {
        ContentValues values = getStats(context, rowIndex, itemId);
        if (values == null) return 0;
        return getItemCount(values);
    }

    public static void setItemCount(ContentValues values, long itemCount) {
        values.put(itemCountLabel(), itemCount);
    }

    /* DATABASE FUNCTIONS */

    public static void removeAllInProgressItems(Context context, long rowIndex) {
        String query = characterIdLabel() + " = ?";
        String[] selectionArgs = new String[]{Long.toString(rowIndex)};
        deleteFromTable(context, query, selectionArgs);
    }

    public static void deleteFromTable(Context context, String query, String[] selectionArgs) {
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            db.delete(getTableName(), query, selectionArgs);
        } finally {
            db.close();
        }
    }

    public static boolean isItemListed(Context context, long rowIndex, long itemId) {
        boolean isSelected = false;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    itemIdLabel() + " = ?";
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

    public static void addItemSelection(Context context, long rowIndex, long itemId, int count) {
        ContentValues values = new ContentValues();
        values.put(characterIdLabel(), rowIndex);
        values.put(itemIdLabel(), itemId);
        values.put(itemCountLabel(), count);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(getTableName(), null, values);
        } finally {
            db.close();
        }
    }

    public static void updateItemSelection(Context context, long rowIndex, long itemId, int count) {
        ContentValues values = new ContentValues();
        values.put(characterIdLabel(), rowIndex);
        values.put(itemIdLabel(), itemId);
        values.put(itemCountLabel(), count);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    itemIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(itemId)});
            try {
                cursor.moveToFirst();
                db.update(getTableName(), values, characterIdLabel() + " = ? AND "
                                + itemIdLabel() + " = ?",
                        new String[]{Long.toString(rowIndex), Long.toString(itemId)});
            } finally {
                cursor.close();
            }
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

    public static ContentValues getStats(Context context, long rowIndex, long armorId) {
        ContentValues values = null;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    itemIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(armorId)});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                values = cursorRowToContentValues(cursor);
            }
            cursor.close();
        } finally {
            db.close();
        }
        return values;
    }

    public static ContentValues[] getAllItemsForCharacter(Context context, long rowIndex) {
        ContentValues[] allItems;

        InProgressDbHelper inProgressDbHelper = new InProgressDbHelper(context);
        SQLiteDatabase inProgressDb = inProgressDbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName() + " WHERE "
                    + characterIdLabel() + " = ?";
            Cursor cursor = inProgressDb.rawQuery(query, new String[]{Long.toString(rowIndex)});

            int numSpells = cursor.getCount();
            cursor.moveToFirst();
            allItems = new ContentValues[numSpells];

            for (int i = 0; i < numSpells; i++) {
                allItems[i] = cursorRowToContentValues(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        } finally {
            inProgressDb.close();
        }
        return allItems;
    }
}
