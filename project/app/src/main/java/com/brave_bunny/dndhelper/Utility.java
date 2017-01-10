package com.brave_bunny.dndhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.TextView;

import com.brave_bunny.dndhelper.database.CharacterContract;
import com.brave_bunny.dndhelper.database.CharacterDbHelper;

import java.util.Random;

/**
 * Created by Jemma on 8/14/2016.
 */
public class Utility {

    private static final String[] CHARACTER_COLUMNS = {
            CharacterContract.CharacterEntry.TABLE_NAME + "." + CharacterContract.CharacterEntry._ID,
            CharacterContract.CharacterEntry.COLUMN_NAME,
            CharacterContract.CharacterEntry.COLUMN_GENDER,
            CharacterContract.CharacterEntry.COLUMN_STR,
            CharacterContract.CharacterEntry.COLUMN_DEX,
            CharacterContract.CharacterEntry.COLUMN_CON,
            CharacterContract.CharacterEntry.COLUMN_INT,
            CharacterContract.CharacterEntry.COLUMN_WIS,
            CharacterContract.CharacterEntry.COLUMN_CHA
    };

    public static final int COL_CHARACTER_ID = 0;
    public static final int COL_CHARACTER_NAME = 1;
    public static final int COL_CHARACTER_STRENGTH = 2;
    public static final int COL_CHARACTER_DEXTERITY = 3;
    public static final int COL_CHARACTER_CONSTITUTION = 4;
    public static final int COL_CHARACTER_INTELLIGENCE = 5;
    public static final int COL_CHARACTER_WISDOM = 6;
    public static final int COL_CHARACTER_CHARISMA = 7;

    private static final String[] INPROGRESS_COLUMNS = {
            CharacterContract.InProgressEntry.TABLE_NAME + "." + CharacterContract.InProgressEntry._ID,
            CharacterContract.InProgressEntry.COLUMN_NAME,
            CharacterContract.InProgressEntry.COLUMN_STR,
            CharacterContract.InProgressEntry.COLUMN_DEX,
            CharacterContract.InProgressEntry.COLUMN_CON,
            CharacterContract.InProgressEntry.COLUMN_INT,
            CharacterContract.InProgressEntry.COLUMN_WIS,
            CharacterContract.InProgressEntry.COLUMN_CHA,
            CharacterContract.InProgressEntry.COLUMN_ABILITY_1,
            CharacterContract.InProgressEntry.COLUMN_ABILITY_2,
            CharacterContract.InProgressEntry.COLUMN_ABILITY_3,
            CharacterContract.InProgressEntry.COLUMN_ABILITY_4,
            CharacterContract.InProgressEntry.COLUMN_ABILITY_5,
            CharacterContract.InProgressEntry.COLUMN_ABILITY_6
    };

    public static final int COL_INPROGRESS_ID = 0;
    public static final int COL_INPROGRESS_NAME = 1;
    public static final int COL_INPROGRESS_STRENGTH = 2;
    public static final int COL_INPROGRESS_DEXTERITY = 3;
    public static final int COL_INPROGRESS_CONSTITUTION = 4;
    public static final int COL_INPROGRESS_INTELLIGENCE = 5;
    public static final int COL_INPROGRESS_WISDOM = 6;
    public static final int COL_INPROGRESS_CHARISMA = 7;
    public static final int COL_INPROGRESS_ABILITY_1 = 8;
    public static final int COL_INPROGRESS_ABILITY_2 = 9;
    public static final int COL_INPROGRESS_ABILITY_3 = 10;
    public static final int COL_INPROGRESS_ABILITY_4 = 11;
    public static final int COL_INPROGRESS_ABILITY_5 = 12;
    public static final int COL_INPROGRESS_ABILITY_6 = 13;

    public static TextView findTextView(View view, int id) {
        return (TextView) view.findViewById(id);
    }

    public static void updateValuesInTable(Context context, String tableName,
                                           ContentValues values, long index) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String id;
        if (tableName.equals(CharacterContract.InProgressEntry.TABLE_NAME)) {
            id = INPROGRESS_COLUMNS[COL_INPROGRESS_ID];
        } else {
            id = CHARACTER_COLUMNS[COL_CHARACTER_ID];
        }

        try {
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

    public static long insertValuesInTable(Context context, String tableName, ContentValues values) {
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

    public static void deleteValuesFromTable(Context context, String tableName, long index) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String id;
        if (tableName.equals(CharacterContract.InProgressEntry.TABLE_NAME)) {
            id = INPROGRESS_COLUMNS[COL_INPROGRESS_ID];
        } else {
            id = CHARACTER_COLUMNS[COL_CHARACTER_ID];
        }

        try {
            db.delete(tableName, id + " = ?", new String[]{Long.toString(index)});
        } finally {
            db.close();
        }
    }

    public static ContentValues setNewInProgressContentValues() {
        Random rand = new Random();

        ContentValues characterValues = new ContentValues();

        characterValues.put(CharacterContract.InProgressEntry.COLUMN_NAME, "");

        characterValues.put(CharacterContract.InProgressEntry.COLUMN_STR, -1);
        characterValues.put(CharacterContract.InProgressEntry.COLUMN_DEX, -1);
        characterValues.put(CharacterContract.InProgressEntry.COLUMN_CON, -1);
        characterValues.put(CharacterContract.InProgressEntry.COLUMN_INT, -1);
        characterValues.put(CharacterContract.InProgressEntry.COLUMN_WIS, -1);
        characterValues.put(CharacterContract.InProgressEntry.COLUMN_CHA, -1);

        characterValues.put(CharacterContract.InProgressEntry.COLUMN_ABILITY_1, generateAbilityScore(rand));
        characterValues.put(CharacterContract.InProgressEntry.COLUMN_ABILITY_2, generateAbilityScore(rand));
        characterValues.put(CharacterContract.InProgressEntry.COLUMN_ABILITY_3, generateAbilityScore(rand));
        characterValues.put(CharacterContract.InProgressEntry.COLUMN_ABILITY_4, generateAbilityScore(rand));
        characterValues.put(CharacterContract.InProgressEntry.COLUMN_ABILITY_5, generateAbilityScore(rand));
        characterValues.put(CharacterContract.InProgressEntry.COLUMN_ABILITY_6, generateAbilityScore(rand));
        return characterValues;
    }

    public static final int STATE_EMPTY = 0;
    public static final int STATE_PARTIAL = 1;
    public static final int STATE_COMPLETE = 2;

    public static int checkStateOfCharacterChoices(Context context, long index) {
        ContentValues values = getInProgressContentValues(context, index);

        if (isCompletelyFilled(values)) {
            return STATE_COMPLETE;
        } else if (isAtLeastPartiallyFilled(values)) {
            return STATE_PARTIAL;
        } else {
            return STATE_EMPTY;
        }
    }

    private static boolean isAtLeastPartiallyFilled(ContentValues values) {
        boolean isAtLeastPartiallyFilled = false;

        isAtLeastPartiallyFilled = isAtLeastPartiallyFilled ||
                isStringSet(values.get(CharacterContract.InProgressEntry.COLUMN_NAME).toString());
        isAtLeastPartiallyFilled = isAtLeastPartiallyFilled ||
                isIntegerSet((Integer)values.get(CharacterContract.InProgressEntry.COLUMN_STR));
        isAtLeastPartiallyFilled = isAtLeastPartiallyFilled ||
                isIntegerSet((Integer)values.get(CharacterContract.InProgressEntry.COLUMN_DEX));
        isAtLeastPartiallyFilled = isAtLeastPartiallyFilled ||
                isIntegerSet((Integer)values.get(CharacterContract.InProgressEntry.COLUMN_CON));
        isAtLeastPartiallyFilled = isAtLeastPartiallyFilled ||
                isIntegerSet((Integer)values.get(CharacterContract.InProgressEntry.COLUMN_INT));
        isAtLeastPartiallyFilled = isAtLeastPartiallyFilled ||
                isIntegerSet((Integer)values.get(CharacterContract.InProgressEntry.COLUMN_WIS));
        isAtLeastPartiallyFilled = isAtLeastPartiallyFilled ||
                isIntegerSet((Integer)values.get(CharacterContract.InProgressEntry.COLUMN_CHA));

        return isAtLeastPartiallyFilled;
    }

    private static boolean isCompletelyFilled(ContentValues values) {
        boolean isCompletelyFilled = true;

        isCompletelyFilled = isCompletelyFilled &&
                isStringSet(values.get(CharacterContract.InProgressEntry.COLUMN_NAME).toString());
        isCompletelyFilled = isCompletelyFilled &&
                isIntegerSet((Integer)values.get(CharacterContract.InProgressEntry.COLUMN_STR));
        isCompletelyFilled = isCompletelyFilled &&
                isIntegerSet((Integer)values.get(CharacterContract.InProgressEntry.COLUMN_DEX));
        isCompletelyFilled = isCompletelyFilled &&
                isIntegerSet((Integer)values.get(CharacterContract.InProgressEntry.COLUMN_CON));
        isCompletelyFilled = isCompletelyFilled &&
                isIntegerSet((Integer)values.get(CharacterContract.InProgressEntry.COLUMN_INT));
        isCompletelyFilled = isCompletelyFilled &&
                isIntegerSet((Integer)values.get(CharacterContract.InProgressEntry.COLUMN_WIS));
        isCompletelyFilled = isCompletelyFilled &&
                isIntegerSet((Integer)values.get(CharacterContract.InProgressEntry.COLUMN_CHA));

        return isCompletelyFilled;
    }


    public static boolean isStringSet(String value) {
        return (!value.equals(""));
    }

    public static boolean isIntegerSet(int value) {
        return (value != -1);
    }

    public static ContentValues getCharacterContentValues(Context context, long index) {
        ContentValues values;

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + CharacterContract.CharacterEntry.TABLE_NAME
                    + " WHERE " + CharacterContract.CharacterEntry._ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(index)});

            cursor.moveToFirst();
            values = new ContentValues();

            String name = getStringFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_NAME);
            int gender = getIntFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_GENDER);
            int raceId = getIntFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_RACE_ID);
            int age = getIntFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_AGE);
            float weight = getFloatFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_WEIGHT);
            float height = getFloatFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_HEIGHT);
            int religionId = getIntFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_RELIGION_ID);
            int alignment = getIntFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_ALIGN);

            int strength = getIntFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_STR);
            int dexterity = getIntFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_DEX);
            int constitution = getIntFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_CON);
            int intelligence = getIntFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_INT);
            int wisdom = getIntFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_WIS);
            int charisma = getIntFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_CHA);

            int baseAttack = getIntFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_BASE_ATTACK);
            int fort = getIntFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_FORT);
            int ref = getIntFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_REF);
            float will = getFloatFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_WILL);

            float money = getFloatFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_MONEY);
            float light_load = getFloatFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_LIGHT_LOAD);
            float medium_load = getFloatFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_MED_LOAD);
            float heavy_load = getFloatFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_HEAVY_LOAD);

            int ac = getIntFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_AC);
            int hp_max = getIntFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_HP_MAX);
            int hp_curr = getIntFromCursor(cursor, CharacterContract.CharacterEntry.COLUMN_HP_CURR);

            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_NAME, name);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_GENDER, gender);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_RACE_ID, raceId);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_AGE, age);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_WEIGHT, weight);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_HEIGHT, height);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_RELIGION_ID, religionId);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_ALIGN, alignment);

            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_STR, strength);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_DEX, dexterity);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_CON, constitution);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_INT, intelligence);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_WIS, wisdom);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_CHA, charisma);

            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_BASE_ATTACK, baseAttack);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_FORT, fort);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_REF, ref);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_WILL, will);

            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_MONEY, money);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_LIGHT_LOAD, light_load);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_MED_LOAD, medium_load);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_HEAVY_LOAD, heavy_load);

            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_AC, ac);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_HP_MAX, hp_max);
            putInContentValue(values, CharacterContract.CharacterEntry.COLUMN_HP_CURR, hp_curr);
        } finally {
            db.close();
        }

        return values;
    }

    public static ContentValues getInProgressContentValues(Context context, long index) {
        ContentValues values;

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + CharacterContract.InProgressEntry.TABLE_NAME
                    + " WHERE " + CharacterContract.InProgressEntry._ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(index)});

            cursor.moveToFirst();
            values = new ContentValues();

            String name = getStringFromCursor(cursor, CharacterContract.InProgressEntry.COLUMN_NAME);
            int strength = getIntFromCursor(cursor, CharacterContract.InProgressEntry.COLUMN_STR);
            int dexterity = getIntFromCursor(cursor, CharacterContract.InProgressEntry.COLUMN_DEX);
            int constitution = getIntFromCursor(cursor, CharacterContract.InProgressEntry.COLUMN_CON);
            int intelligence = getIntFromCursor(cursor, CharacterContract.InProgressEntry.COLUMN_INT);
            int wisdom = getIntFromCursor(cursor, CharacterContract.InProgressEntry.COLUMN_WIS);
            int charisma = getIntFromCursor(cursor, CharacterContract.InProgressEntry.COLUMN_CHA);
            int ability1 = getIntFromCursor(cursor, CharacterContract.InProgressEntry.COLUMN_ABILITY_1);
            int ability2 = getIntFromCursor(cursor, CharacterContract.InProgressEntry.COLUMN_ABILITY_2);
            int ability3 = getIntFromCursor(cursor, CharacterContract.InProgressEntry.COLUMN_ABILITY_3);
            int ability4 = getIntFromCursor(cursor, CharacterContract.InProgressEntry.COLUMN_ABILITY_4);
            int ability5 = getIntFromCursor(cursor, CharacterContract.InProgressEntry.COLUMN_ABILITY_5);
            int ability6 = getIntFromCursor(cursor, CharacterContract.InProgressEntry.COLUMN_ABILITY_6);

            putInContentValue(values, CharacterContract.InProgressEntry.COLUMN_NAME, name);
            putInContentValue(values, CharacterContract.InProgressEntry.COLUMN_STR, strength);
            putInContentValue(values, CharacterContract.InProgressEntry.COLUMN_DEX, dexterity);
            putInContentValue(values, CharacterContract.InProgressEntry.COLUMN_CON, constitution);
            putInContentValue(values, CharacterContract.InProgressEntry.COLUMN_INT, intelligence);
            putInContentValue(values, CharacterContract.InProgressEntry.COLUMN_WIS, wisdom);
            putInContentValue(values, CharacterContract.InProgressEntry.COLUMN_CHA, charisma);
            putInContentValue(values, CharacterContract.InProgressEntry.COLUMN_ABILITY_1, ability1);
            putInContentValue(values, CharacterContract.InProgressEntry.COLUMN_ABILITY_2, ability2);
            putInContentValue(values, CharacterContract.InProgressEntry.COLUMN_ABILITY_3, ability3);
            putInContentValue(values, CharacterContract.InProgressEntry.COLUMN_ABILITY_4, ability4);
            putInContentValue(values, CharacterContract.InProgressEntry.COLUMN_ABILITY_5, ability5);
            putInContentValue(values, CharacterContract.InProgressEntry.COLUMN_ABILITY_6, ability6);
        } finally {
            db.close();
        }

        return values;
    }

    public static String getStringFromCursor(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.getString(index);
    }

    public static int getIntFromCursor(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.getInt(index);
    }

    public static float getFloatFromCursor(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.getFloat(index);
    }

    public static void putInContentValue(ContentValues values,
                                               String columnName, String val) {

        values.put(columnName, val);
    }

    public static void putInContentValue(ContentValues values,
                                         String columnName, int val) {

        values.put(columnName, val);
    }

    public static void putInContentValue(ContentValues values,
                                         String columnName, float val) {

        values.put(columnName, val);
    }

    private static int generateAbilityScore(Random rand) {
        int sum = 0;
        int minimum = -1;

        for (int i = 0; i < 4; i++) {
            int dieValue = rand.nextInt(6) + 1;
            if (minimum == -1 || minimum > dieValue) {
                minimum = dieValue;
            }
            sum += dieValue;
        }

        return sum - minimum;
    }
}
