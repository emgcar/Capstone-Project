package com.brave_bunny.dndhelper.database.inprogress.InProgressUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;

/**
 *  Handles all of the selected armor for in-progress characters.
 */

public class InProgressArmorUtil {

    /* LABELS - Should be private */

    private static String getTableName() {
        return InProgressContract.ArmorEntry.TABLE_NAME;
    }

    private static String idLabel() {
        return InProgressContract.ArmorEntry._ID;
    }

    private static String characterIdLabel() {
        return InProgressContract.ArmorEntry.COLUMN_CHARACTER_ID;
    }

    private static String armorIdLabel() {
        return InProgressContract.ArmorEntry.COLUMN_ARMOR_ID;
    }

    private static String armorCountLabel() {
        return InProgressContract.ArmorEntry.COLUMN_COUNT;
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

    public static long getArmorId(ContentValues values) {
        return values.getAsLong(armorIdLabel());
    }

    public static long getArmorId(Context context, long rowIndex, int armorId) {
        ContentValues values = getStats(context, rowIndex, armorId);
        return getArmorId(values);
    }

    public static void setArmorId(ContentValues values, long armorId) {
        values.put(armorIdLabel(), armorId);
    }

    public static int getArmorCount(ContentValues values) {
        return values.getAsInteger(armorCountLabel());
    }

    public static int getArmorCount(Context context, long rowIndex, long armorId) {
        ContentValues values = getStats(context, rowIndex, armorId);
        if (values == null) return 0;
        return getArmorCount(values);
    }

    public static void setArmorCount(ContentValues values, int armorCount) {
        values.put(armorCountLabel(), armorCount);
    }

    /* DATABASE FUNCTIONS */

    public static boolean isArmorListed(Context context, long rowIndex, long armorId) {
        boolean isSelected = false;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    armorIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(armorId)});
            if (cursor.getCount() > 0) {
                isSelected = true;
            }
            cursor.close();
        } finally {
            db.close();
        }
        return isSelected;
    }

    public static void addArmorSelection(Context context, long rowIndex, long armorId, int count) {
        ContentValues values = new ContentValues();
        values.put(characterIdLabel(), rowIndex);
        values.put(armorIdLabel(), armorId);
        values.put(armorCountLabel(), count);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(getTableName(), null, values);
        } finally {
            db.close();
        }
    }

    public static void updateArmorSelection(Context context, long rowIndex, long armorId, int count) {
        ContentValues values = new ContentValues();
        values.put(characterIdLabel(), rowIndex);
        values.put(armorIdLabel(), armorId);
        values.put(armorCountLabel(), count);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    armorIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(armorId)});
            try {
                cursor.moveToFirst();
                db.update(getTableName(), values, characterIdLabel()
                        + " = ? AND " + armorIdLabel() + " = ?",
                        new String[]{Long.toString(rowIndex), Long.toString(armorId)});
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }
    }

    public static void addOrUpdateArmorSelection(Context context, long rowIndex, long armorId, int count) {
        if (isArmorListed(context, rowIndex, armorId)) {
            updateArmorSelection(context, rowIndex, armorId, count);
        } else {
            addArmorSelection(context, rowIndex, armorId, count);
        }
    }

    public static void removeAllInProgressArmor(Context context, long rowIndex) {

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "DELETE FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ?";
            db.rawQuery(query, new String[]{Long.toString(rowIndex)});
        } finally {
            db.close();
        }
    }

    public static ContentValues getStats(Context context, long rowIndex, long armorId) {
        ContentValues values = null;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    armorIdLabel() + " = ?";
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

    public static ContentValues[] getAllArmorForCharacter(Context context, long rowIndex) {
        ContentValues[] allArmor;

        InProgressDbHelper inProgressDbHelper = new InProgressDbHelper(context);
        SQLiteDatabase inProgressDb = inProgressDbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName() + " WHERE "
                    + characterIdLabel() + " = ?";
            Cursor cursor = inProgressDb.rawQuery(query, new String[]{Long.toString(rowIndex)});

            int numSpells = cursor.getCount();
            cursor.moveToFirst();
            allArmor = new ContentValues[numSpells];

            for (int i = 0; i < numSpells; i++) {
                allArmor[i] = cursorRowToContentValues(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        } finally {
            inProgressDb.close();
        }
        return allArmor;
    }
}
