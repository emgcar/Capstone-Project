package com.brave_bunny.dndhelper.database.character.CharacterUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.character.CharacterDbHelper;
import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;

/**
 * Created by Jemma on 1/11/2017.
 */

public class CharacterUtil {

    private static final int TRUE = 1;
    private static final int FALSE = 0;

    /* LABELS - Should be private */
    private static String characterIdLabel() {
        return CharacterContract.CharacterEntry._ID;
    }

    private static String characterNameLabel() {
        return CharacterContract.CharacterEntry.COLUMN_NAME;
    }

    private static String characterLevelLabel() {
        return CharacterContract.CharacterEntry.COLUMN_TOTAL_LEVEL;
    }

    private static String characterExperienceLabel() {
        return CharacterContract.CharacterEntry.COLUMN_EXPERIENCE;
    }

    private static String characterMoneyLabel() {
        return CharacterContract.CharacterEntry.COLUMN_MONEY;
    }

    private static String characterInBattleLabel() {
        return CharacterContract.CharacterEntry.COLUMN_IN_BATTLE;
    }

    /* PARSE VALUES*/

    private static String getTableName() {
        return CharacterContract.CharacterEntry.TABLE_NAME;
    }

    public static String getCharacterName(ContentValues values) {
        if (values.get(characterNameLabel()) == null) return "";
        return values.getAsString(characterNameLabel());
    }

    public static long getId(ContentValues values) {
        return values.getAsLong(characterIdLabel());
    }

    public static boolean getIsInBattle(ContentValues values) {
        if (values.getAsInteger(characterInBattleLabel()) == TRUE) {
            return true;
        } else {
            return false;
        }
    }

    public static int getCharacterLevel(ContentValues values) {
        return values.getAsInteger(characterLevelLabel());
    }

    public static int getCharacterExperience(ContentValues values) {
        return values.getAsInteger(characterExperienceLabel());
    }

    public static float getCharacterMoney(ContentValues values) {
        return values.getAsFloat(characterMoneyLabel());
    }


    /*
     *      CHARACTER util functions
     */

    public static Cursor getFinishedCharacterList(Context context) {
        Cursor cursor;

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName();
            cursor = db.rawQuery(query, null);

            cursor.moveToFirst();
        } finally {
            db.close();
        }

        return cursor;
    }

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

    public static int getCharacterValue(Context context, long rowIndex, int colIndex) {
        int value;

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + CharacterContract.CharacterEntry.TABLE_NAME
                    + " WHERE " + CharacterContract.CharacterEntry._ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            cursor.moveToFirst();

            value = cursor.getInt(colIndex);
            cursor.close();
        } finally {
            db.close();
        }

        return value;
    }

    // need to make sure no name duplication somehow
    public static boolean isInBattle(Context context, long index) {
        ContentValues values = getCharacterRow(context, index);
        return getIsInBattle(values);
    }

    public static int getCharacterLevel(Context context, long index) {
        ContentValues values = getCharacterRow(context, index);
        return getCharacterLevel(values);
    }

    // need to make sure no name duplication somehow
    public static boolean isCompleted(Context context, String name) {
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
            String id = characterIdLabel();

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

    public static long insertValuesInCharacterTable(Context context, ContentValues values) {
        long index;
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName = CharacterContract.CharacterEntry.TABLE_NAME;

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

        String id = characterIdLabel();

        try {
            db.delete(tableName, id + " = ?", new String[]{Long.toString(index)});
        } finally {
            db.close();
        }
    }
}
