package com.brave_bunny.dndhelper.database.inprogress.InProgressUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;

/**
 * Handles all of the selected weapons for in-progress characters.
 */

public class InProgressWeaponsUtil {

    /* LABELS */

    private static String getTableName() {
        return InProgressContract.WeaponEntry.TABLE_NAME;
    }

    private static String idLabel() {
        return InProgressContract.WeaponEntry._ID;
    }

    private static String characterIdLabel() {
        return InProgressContract.WeaponEntry.COLUMN_CHARACTER_ID;
    }

    private static String weaponIdLabel() {
        return InProgressContract.WeaponEntry.COLUMN_WEAPON_ID;
    }

    private static String weaponCountLabel() {
        return InProgressContract.WeaponEntry.COLUMN_COUNT;
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

    public static long getWeaponId(ContentValues values) {
        return values.getAsLong(weaponIdLabel());
    }

    public static void setWeaponId(ContentValues values, long weaponId) {
        values.put(weaponIdLabel(), weaponId);
    }

    public static int getWeaponCount(ContentValues values) {
        return values.getAsInteger(weaponCountLabel());
    }

    public static int getWeaponCount(Context context, long rowIndex, long weaponId) {
        ContentValues values = getStats(context, rowIndex, weaponId);
        if (values == null) return 0;
        return getWeaponCount(values);
    }

    public static void setWeaponCount(ContentValues values, long weaponCount) {
        values.put(weaponCountLabel(), weaponCount);
    }

    /* DATABASE FUNCTIONS */

    public static void removeAllInProgressWeapons(Context context, long rowIndex) {
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


    public static boolean isWeaponListed(Context context, long rowIndex, long weaponId) {
        boolean isSelected = false;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    weaponIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(weaponId)});
            if (cursor.getCount() > 0) {
                isSelected = true;
            }
            cursor.close();
        } finally {
            db.close();
        }
        return isSelected;
    }

    public static void addWeaponSelection(Context context, long rowIndex, long weaponId, int count) {
        ContentValues values = new ContentValues();
        values.put(characterIdLabel(), rowIndex);
        values.put(weaponIdLabel(), weaponId);
        values.put(weaponCountLabel(), count);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(getTableName(), null, values);
        } finally {
            db.close();
        }
    }

    public static void updateWeaponSelection(Context context, long rowIndex, long weaponId, int count) {
        ContentValues values = new ContentValues();
        values.put(characterIdLabel(), rowIndex);
        values.put(weaponIdLabel(), weaponId);
        values.put(weaponCountLabel(), count);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    weaponIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(weaponId)});
            try {
                cursor.moveToFirst();
                db.update(getTableName(), values, characterIdLabel() + " = ? AND "
                        + weaponIdLabel() + " = ?",
                        new String[]{Long.toString(rowIndex), Long.toString(weaponId)});
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }
    }

    public static void addOrUpdateWeaponSelection(Context context, long rowIndex, long skillId, int newRank) {
        if (isWeaponListed(context, rowIndex, skillId)) {
            updateWeaponSelection(context, rowIndex, skillId, newRank);
        } else {
            addWeaponSelection(context, rowIndex, skillId, newRank);
        }
    }

    public static ContentValues getStats(Context context, long rowIndex, long armorId) {
        ContentValues values = null;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    weaponIdLabel() + " = ?";
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
}
