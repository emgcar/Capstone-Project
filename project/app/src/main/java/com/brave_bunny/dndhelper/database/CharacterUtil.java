package com.brave_bunny.dndhelper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;

import static com.brave_bunny.dndhelper.Utility.getIntFromCursor;

/**
 * Created by Jemma on 1/11/2017.
 */

public class CharacterUtil {
    private static final String[] CHARACTER_COLUMNS = {
            CharacterContract.CharacterEntry.TABLE_NAME + "." + CharacterContract.CharacterEntry._ID,
            CharacterContract.CharacterEntry.COLUMN_NAME,
            CharacterContract.CharacterEntry.COLUMN_GENDER,
            CharacterContract.CharacterEntry.COLUMN_RACE,
            CharacterContract.CharacterEntry.COLUMN_AGE,
            CharacterContract.CharacterEntry.COLUMN_WEIGHT,
            CharacterContract.CharacterEntry.COLUMN_HEIGHT,
            CharacterContract.CharacterEntry.COLUMN_RELIGION_ID,
            CharacterContract.CharacterEntry.COLUMN_ALIGN,

            CharacterContract.CharacterEntry.COLUMN_STR,
            CharacterContract.CharacterEntry.COLUMN_DEX,
            CharacterContract.CharacterEntry.COLUMN_CON,
            CharacterContract.CharacterEntry.COLUMN_INT,
            CharacterContract.CharacterEntry.COLUMN_WIS,
            CharacterContract.CharacterEntry.COLUMN_CHA,

            CharacterContract.CharacterEntry.COLUMN_BASE_ATTACK,
            CharacterContract.CharacterEntry.COLUMN_FORT,
            CharacterContract.CharacterEntry.COLUMN_REF,
            CharacterContract.CharacterEntry.COLUMN_WILL,

            CharacterContract.CharacterEntry.COLUMN_MONEY,
            CharacterContract.CharacterEntry.COLUMN_LIGHT_LOAD,
            CharacterContract.CharacterEntry.COLUMN_MED_LOAD,
            CharacterContract.CharacterEntry.COLUMN_HEAVY_LOAD,

            CharacterContract.CharacterEntry.COLUMN_AC,
            CharacterContract.CharacterEntry.COLUMN_HP_MAX,
            CharacterContract.CharacterEntry.COLUMN_HP_CURR,
            CharacterContract.CharacterEntry.COLUMN_IN_BATTLE
    };

    public static final int COL_CHARACTER_ID = 0;
    public static final int COL_CHARACTER_NAME = 1;
    public static final int COL_CHARACTER_GENDER = 2;
    public static final int COL_CHARACTER_RACE_ID = 3;
    public static final int COL_CHARACTER_AGE = 4;
    public static final int COL_CHARACTER_WEIGHT = 5;
    public static final int COL_CHARACTER_HEIGHT = 6;
    public static final int COL_CHARACTER_RELIGION_ID = 7;
    public static final int COL_CHARACTER_ALIGN = 8;

    public static final int COL_CHARACTER_STRENGTH = 9;
    public static final int COL_CHARACTER_DEXTERITY = 10;
    public static final int COL_CHARACTER_CONSTITUTION = 11;
    public static final int COL_CHARACTER_INTELLIGENCE = 12;
    public static final int COL_CHARACTER_WISDOM = 13;
    public static final int COL_CHARACTER_CHARISMA = 14;

    public static final int COL_CHARACTER_BASE_ATTACK = 15;
    public static final int COL_CHARACTER_FORTITUDE = 16;
    public static final int COL_CHARACTER_REFLEX = 17;
    public static final int COL_CHARACTER_WILL = 18;

    public static final int COL_CHARACTER_MONEY = 19;
    public static final int COL_CHARACTER_LIGHT_LOAD = 20;
    public static final int COL_CHARACTER_MED_LOAD = 21;
    public static final int COL_CHARACTER_HEAVY_LOAD = 22;

    public static final int COL_CHARACTER_AC = 23;
    public static final int COL_CHARACTER_HP_MAX = 24;
    public static final int COL_CHARACTER_HP_CURR = 25;
    public static final int COL_CHARACTER_IN_BATTLE = 26;

    public static ContentValues getCharacterRow(Context context, long rowIndex) {
        ContentValues values;

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + CharacterContract.CharacterEntry.TABLE_NAME
                    + " WHERE " + CharacterContract.CharacterEntry._ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            cursor.moveToFirst();

            values = Utility.cursorRowToContentValues(cursor);
            cursor.close();
        } finally {
            db.close();
        }

        return values;
    }

    public int getCharacterValue(Context context, long rowIndex, int colIndex) {
        int value;

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + CharacterContract.CharacterEntry.TABLE_NAME
                    + " WHERE " + CharacterContract.CharacterEntry._ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            cursor.moveToFirst();

            value = getIntFromCursor(cursor, CHARACTER_COLUMNS[colIndex]);
            cursor.close();
        } finally {
            db.close();
        }

        return value;
    }


    // need to make sure no name duplication somehow
    public boolean isFinished(Context context, String name) {
        boolean value;

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + CharacterContract.CharacterEntry.TABLE_NAME
                    + " WHERE " + CharacterContract.CharacterEntry.COLUMN_NAME + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{name});

            cursor.moveToFirst();

            value = (cursor.getCount() != 0);
            cursor.close();
        } finally {
            db.close();
        }

        return value;
    }

    public static void updateCharacterTable(Context context, String tableName,
                                            ContentValues values, long index) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String id = CHARACTER_COLUMNS[COL_CHARACTER_ID];

            String query = "SELECT * FROM " + tableName + " WHERE " + id + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(index)});
            try {
                cursor.moveToFirst();
                db.update(tableName, values, id + " = ?",
                        new String[]{Long.toString(index)});
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }
    }

    public static long insertValuesInCharacterTable(Context context, String tableName, ContentValues values) {
        long index;
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            index = db.insert(tableName, null, values);
        } finally {
            db.close();
        }
        return index;
    }

    public static void deleteValuesFromCharacterTable(Context context, String tableName, long index) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String id = CHARACTER_COLUMNS[COL_CHARACTER_ID];

        try {
            db.delete(tableName, id + " = ?", new String[]{Long.toString(index)});
        } finally {
            db.close();
        }
    }
}
