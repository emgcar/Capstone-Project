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

public class InProgressWeaponsUtil {

    private static final String[] WEAPON_COLUMNS = {
            InProgressContract.WeaponEntry.TABLE_NAME + "." + InProgressContract.WeaponEntry._ID,
            InProgressContract.WeaponEntry.COLUMN_CHARACTER_ID,
            InProgressContract.WeaponEntry.COLUMN_WEAPON_ID,
            InProgressContract.WeaponEntry.COLUMN_COUNT
    };

    public static final int COL_WEAPON_INPUT_ID = 0;
    public static final int COL_WEAPON_CHARACTER_ID = 1;
    public static final int COL_WEAPON_WEAPON_ID = 2;
    public static final int COL_WEAPON_COUNT = 3;

    private static final String tableName = InProgressContract.WeaponEntry.TABLE_NAME;

    public static void removeAllInProgressWeapons(Context context, long rowIndex) {
        String query = WEAPON_COLUMNS[COL_WEAPON_CHARACTER_ID] + " = ?";
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


    public static boolean isWeaponListed(Context context, long rowIndex, long weaponId) {
        boolean isSelected = false;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName
                    + " WHERE " + WEAPON_COLUMNS[COL_WEAPON_CHARACTER_ID] + " = ? AND " +
                    WEAPON_COLUMNS[COL_WEAPON_WEAPON_ID] + " = ?";
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

    public static int getWeaponCount(Context context, long rowIndex, long weaponId) {
        int count = 0;
        if (isWeaponListed(context, rowIndex, weaponId)) {
            InProgressDbHelper dbHelper = new InProgressDbHelper(context);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            try {
                String query = "SELECT * FROM " + tableName
                        + " WHERE " + WEAPON_COLUMNS[COL_WEAPON_CHARACTER_ID] + " = ? AND " +
                        WEAPON_COLUMNS[COL_WEAPON_WEAPON_ID] + " = ?";
                Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(weaponId)});
                cursor.moveToFirst();
                count = cursor.getInt(COL_WEAPON_COUNT);
                cursor.close();
            } finally {
                db.close();
            }
        }
        return count;
    }

    public static void addWeaponSelection(Context context, long rowIndex, long weaponId, int count) {
        ContentValues values = new ContentValues();
        values.put(WEAPON_COLUMNS[COL_WEAPON_CHARACTER_ID], rowIndex);
        values.put(WEAPON_COLUMNS[COL_WEAPON_WEAPON_ID], weaponId);
        values.put(WEAPON_COLUMNS[COL_WEAPON_COUNT], count);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(tableName, null, values);
        } finally {
            db.close();
        }
    }

    public static void updateWeaponSelection(Context context, long rowIndex, long weaponId, int count) {
        ContentValues values = new ContentValues();
        values.put(WEAPON_COLUMNS[COL_WEAPON_CHARACTER_ID], rowIndex);
        values.put(WEAPON_COLUMNS[COL_WEAPON_WEAPON_ID], weaponId);
        values.put(WEAPON_COLUMNS[COL_WEAPON_COUNT], count);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String charIndex = WEAPON_COLUMNS[COL_WEAPON_CHARACTER_ID];
            String skillIndex = WEAPON_COLUMNS[COL_WEAPON_WEAPON_ID];

            String query = "SELECT * FROM " + tableName
                    + " WHERE " + WEAPON_COLUMNS[COL_WEAPON_CHARACTER_ID] + " = ? AND " +
                    WEAPON_COLUMNS[COL_WEAPON_WEAPON_ID] + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(weaponId)});
            try {
                cursor.moveToFirst();
                db.update(tableName, values, charIndex + " = ? AND " + skillIndex + " = ?",
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
}
