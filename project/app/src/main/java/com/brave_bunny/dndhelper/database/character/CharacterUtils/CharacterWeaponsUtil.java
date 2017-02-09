package com.brave_bunny.dndhelper.database.character.CharacterUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.character.CharacterDbHelper;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;

/**
 * Handles all of the selected weapons for created characters.
 */

public class CharacterWeaponsUtil {

    private static final int TRUE = 1;
    private static final int FALSE = 0;

    /* LABELS */

    private static String getTableName() {
        return CharacterContract.CharacterWeapons.TABLE_NAME;
    }

    private static String idLabel() {
        return CharacterContract.CharacterWeapons._ID;
    }

    private static String characterIdLabel() {
        return CharacterContract.CharacterWeapons.COLUMN_CHARACTER_ID;
    }

    private static String weaponIdLabel() {
        return CharacterContract.CharacterWeapons.COLUMN_WEAPON_ID;
    }

    private static String weaponCountLabel() {
        return CharacterContract.CharacterWeapons.COLUMN_COUNT;
    }

    private static String weaponEquippedLabel() {
        return CharacterContract.CharacterWeapons.COLUMN_EQUIPPED;
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

    public static boolean getWeaponEquipped(ContentValues values) {
        int inClass = values.getAsInteger(weaponEquippedLabel());
        return (inClass == TRUE);
    }

    public static void setWeaponEquipped(ContentValues values, boolean inClass) {
        if (inClass) {
            values.put(weaponEquippedLabel(), TRUE);
        } else {
            values.put(weaponEquippedLabel(), FALSE);
        }
    }

    /* DATABASE FUNCTIONS */

    public static ContentValues getStats(Context context, long rowIndex, long armorId) {
        ContentValues values = null;
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
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
