package com.brave_bunny.dndhelper.database.character.CharacterUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.character.CharacterDbHelper;

/**
 * Created by Jemma on 2/3/2017.
 */

public class CharacterArmorUtil {
    private static final String[] ARMOR_COLUMNS = {
            CharacterContract.CharacterArmor.TABLE_NAME + "." + CharacterContract.CharacterArmor._ID,
            CharacterContract.CharacterArmor.COLUMN_CHARACTER_ID,
            CharacterContract.CharacterArmor.COLUMN_ARMOR_ID,
            CharacterContract.CharacterArmor.COLUMN_COUNT
    };

    public static final int COL_ARMOR_INPUT_ID = 0;
    public static final int COL_ARMOR_CHARACTER_ID = 1;
    public static final int COL_ARMOR_ARMOR_ID = 2;
    public static final int COL_ARMOR_COUNT = 3;

    private static final String tableName = CharacterContract.CharacterArmor.TABLE_NAME;


    public static boolean isArmorListed(Context context, long rowIndex, long armorId) {
        boolean isSelected = false;
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName
                    + " WHERE " + ARMOR_COLUMNS[COL_ARMOR_CHARACTER_ID] + " = ? AND " +
                    ARMOR_COLUMNS[COL_ARMOR_ARMOR_ID] + " = ?";
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

    public static int getArmorCount(Context context, long rowIndex, long armorId) {
        int count = 0;
        if (isArmorListed(context, rowIndex, armorId)) {
            CharacterDbHelper dbHelper = new CharacterDbHelper(context);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            try {
                String query = "SELECT * FROM " + tableName
                        + " WHERE " + ARMOR_COLUMNS[COL_ARMOR_CHARACTER_ID] + " = ? AND " +
                        ARMOR_COLUMNS[COL_ARMOR_ARMOR_ID] + " = ?";
                Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(armorId)});
                cursor.moveToFirst();
                count = cursor.getInt(COL_ARMOR_COUNT);
                cursor.close();
            } finally {
                db.close();
            }
        }
        return count;
    }

    public static void addArmorSelection(Context context, long rowIndex, long armorId, int count) {
        ContentValues values = new ContentValues();
        values.put(ARMOR_COLUMNS[COL_ARMOR_CHARACTER_ID], rowIndex);
        values.put(ARMOR_COLUMNS[COL_ARMOR_ARMOR_ID], armorId);
        values.put(ARMOR_COLUMNS[COL_ARMOR_COUNT], count);

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(tableName, null, values);
        } finally {
            db.close();
        }
    }

    public static void updateArmorSelection(Context context, long rowIndex, long armorId, int count) {
        ContentValues values = new ContentValues();
        values.put(ARMOR_COLUMNS[COL_ARMOR_CHARACTER_ID], rowIndex);
        values.put(ARMOR_COLUMNS[COL_ARMOR_ARMOR_ID], armorId);
        values.put(ARMOR_COLUMNS[COL_ARMOR_COUNT], count);

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName
                    + " WHERE " + ARMOR_COLUMNS[COL_ARMOR_CHARACTER_ID] + " = ? AND " +
                    ARMOR_COLUMNS[COL_ARMOR_ARMOR_ID] + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(armorId)});
            try {
                cursor.moveToFirst();
                db.update(tableName, values, ARMOR_COLUMNS[COL_ARMOR_CHARACTER_ID]
                                + " = ? AND " + ARMOR_COLUMNS[COL_ARMOR_ARMOR_ID] + " = ?",
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

    public static void removeAllCharacterArmor(Context context, long rowIndex) {

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "DELETE FROM " + tableName
                    + " WHERE " + ARMOR_COLUMNS[COL_ARMOR_CHARACTER_ID] + " = ?";
            db.rawQuery(query, new String[]{Long.toString(rowIndex)});
        } finally {
            db.close();
        }
    }
}
