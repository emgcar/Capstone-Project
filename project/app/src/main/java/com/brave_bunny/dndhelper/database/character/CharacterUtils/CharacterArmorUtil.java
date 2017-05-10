package com.brave_bunny.dndhelper.database.character.CharacterUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.character.CharacterDbHelper;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;

/**
 * Handles all of the selected armor for created characters.
 */

public class CharacterArmorUtil {

    private static final int TRUE = 1;
    private static final int FALSE = 0;

    /* LABELS - Should be private */

    private static String getTableName() {
        return CharacterContract.CharacterArmor.TABLE_NAME;
    }

    private static String idLabel() {
        return CharacterContract.CharacterArmor._ID;
    }

    private static String characterIdLabel() {
        return CharacterContract.CharacterArmor.COLUMN_CHARACTER_ID;
    }

    private static String armorIdLabel() {
        return CharacterContract.CharacterArmor.COLUMN_ARMOR_ID;
    }

    private static String armorCountLabel() {
        return CharacterContract.CharacterArmor.COLUMN_COUNT;
    }

    private static String armorEquippedLabel() {
        return CharacterContract.CharacterArmor.COLUMN_EQUIPPED;
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

    public static boolean getArmorEquipped(ContentValues values) {
        int inClass = values.getAsInteger(armorEquippedLabel());
        return (inClass == TRUE);
    }

    public static void setArmorEquipped(ContentValues values, boolean inClass) {
        if (inClass) {
            values.put(armorEquippedLabel(), TRUE);
        } else {
            values.put(armorEquippedLabel(), FALSE);
        }
    }

    /* DATABASE FUNCTIONS */


    public static boolean isArmorListed(Context context, long rowIndex, long armorId) {
        boolean isSelected = false;
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
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

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
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

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
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

    public static void removeAllCharacterArmor(Context context, long rowIndex) {

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
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
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
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

    public static void insertArmorIntoCharacterTable(Context context, ContentValues values) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(getTableName(), null, values);
        } finally {
            db.close();
        }
    }

    public static void deleteArmorFromCharacterTable(Context context, long characterId) {
        String query = characterIdLabel() + " = ?";
        String[] selectionArgs = new String[]{Long.toString(characterId)};
        deleteFromTable(context, query, selectionArgs);
    }

    public static void deleteFromTable(Context context, String query, String[] selectionArgs) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            db.delete(getTableName(), query, selectionArgs);
        } finally {
            db.close();
        }
    }
}
