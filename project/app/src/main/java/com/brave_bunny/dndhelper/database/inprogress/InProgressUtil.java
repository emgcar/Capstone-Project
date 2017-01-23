package com.brave_bunny.dndhelper.database.inprogress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils;

import java.util.Random;

import static com.brave_bunny.dndhelper.Utility.getIntFromCursor;

/**
 * Created by Jemma on 1/13/2017.
 */



public class InProgressUtil {

    public static final String[] INPROGRESS_COLUMNS = {
            InProgressContract.CharacterEntry.TABLE_NAME + "." + InProgressContract.CharacterEntry._ID,
            InProgressContract.CharacterEntry.COLUMN_NAME,
            InProgressContract.CharacterEntry.COLUMN_GENDER,
            InProgressContract.CharacterEntry.COLUMN_RACE_ID,
            InProgressContract.CharacterEntry.COLUMN_CLASS_ID,
            InProgressContract.CharacterEntry.COLUMN_AGE,
            InProgressContract.CharacterEntry.COLUMN_WEIGHT,
            InProgressContract.CharacterEntry.COLUMN_HEIGHT,
            InProgressContract.CharacterEntry.COLUMN_RELIGION_ID,
            InProgressContract.CharacterEntry.COLUMN_ALIGN,

            InProgressContract.CharacterEntry.COLUMN_STR,
            InProgressContract.CharacterEntry.COLUMN_DEX,
            InProgressContract.CharacterEntry.COLUMN_CON,
            InProgressContract.CharacterEntry.COLUMN_INT,
            InProgressContract.CharacterEntry.COLUMN_WIS,
            InProgressContract.CharacterEntry.COLUMN_CHA,

            InProgressContract.CharacterEntry.COLUMN_ABILITY_1,
            InProgressContract.CharacterEntry.COLUMN_ABILITY_2,
            InProgressContract.CharacterEntry.COLUMN_ABILITY_3,
            InProgressContract.CharacterEntry.COLUMN_ABILITY_4,
            InProgressContract.CharacterEntry.COLUMN_ABILITY_5,
            InProgressContract.CharacterEntry.COLUMN_ABILITY_6,

            InProgressContract.CharacterEntry.COLUMN_MONEY,
            InProgressContract.CharacterEntry.COLUMN_LIGHT_LOAD,
            InProgressContract.CharacterEntry.COLUMN_MED_LOAD,
            InProgressContract.CharacterEntry.COLUMN_HEAVY_LOAD,

            InProgressContract.CharacterEntry.COLUMN_AC,
            InProgressContract.CharacterEntry.COLUMN_HP
    };

    public static final int COL_CHARACTER_ID = 0;
    public static final int COL_CHARACTER_NAME = 1;
    public static final int COL_CHARACTER_GENDER = 2;
    public static final int COL_CHARACTER_RACE_ID = 3;
    public static final int COL_CHARACTER_CLASS_ID = 4;
    public static final int COL_CHARACTER_AGE = 5;
    public static final int COL_CHARACTER_WEIGHT = 6;
    public static final int COL_CHARACTER_HEIGHT = 7;
    public static final int COL_CHARACTER_RELIGION_ID = 8;
    public static final int COL_CHARACTER_ALIGN = 9;

    public static final int COL_CHARACTER_STRENGTH = 10;
    public static final int COL_CHARACTER_DEXTERITY = 11;
    public static final int COL_CHARACTER_CONSTITUTION = 12;
    public static final int COL_CHARACTER_INTELLIGENCE = 13;
    public static final int COL_CHARACTER_WISDOM = 14;
    public static final int COL_CHARACTER_CHARISMA = 15;

    public static final int COL_CHARACTER_ABILITY1 = 16;
    public static final int COL_CHARACTER_ABILITY2 = 17;
    public static final int COL_CHARACTER_ABILITY3 = 18;
    public static final int COL_CHARACTER_ABILITY4 = 19;
    public static final int COL_CHARACTER_ABILITY5 = 20;
    public static final int COL_CHARACTER_ABILITY6 = 21;

    public static final int COL_CHARACTER_MONEY = 22;
    public static final int COL_CHARACTER_LIGHT_LOAD = 23;
    public static final int COL_CHARACTER_MED_LOAD = 24;
    public static final int COL_CHARACTER_HEAVY_LOAD = 25;

    public static final int COL_CHARACTER_AC = 26;
    public static final int COL_CHARACTER_HP = 27;

    public static ContentValues getInProgressRow(Context context, long rowIndex) {
        ContentValues values;

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + InProgressContract.CharacterEntry.TABLE_NAME
                    + " WHERE " + InProgressContract.CharacterEntry._ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            cursor.moveToFirst();

            values = Utility.cursorRowToContentValues(cursor);
            cursor.close();
        } finally {
            db.close();
        }

        return values;
    }


    public int getInProgressValue(Context context, long rowIndex, int colIndex) {
        int value;

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + InProgressContract.CharacterEntry.TABLE_NAME
                    + " WHERE " + InProgressContract.CharacterEntry._ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            cursor.moveToFirst();

            value = getIntFromCursor(cursor, INPROGRESS_COLUMNS[colIndex]);
            cursor.close();
        } finally {
            db.close();
        }

        return value;
    }

    public static void updateInProgressTable(Context context, String tableName,
                                            ContentValues values, long index) {
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String id = INPROGRESS_COLUMNS[COL_CHARACTER_ID];

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

    public static long insertValuesIntoInPrgoressTable(Context context, String tableName, ContentValues values) {
        long index;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            index = db.insert(tableName, null, values);
        } finally {
            db.close();
        }
        return index;
    }

    public static void deleteValuesFromInProgressTable(Context context, String tableName, long index) {
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String id = INPROGRESS_COLUMNS[COL_CHARACTER_ID];

        try {
            db.delete(tableName, id + " = ?", new String[]{Long.toString(index)});
        } finally {
            db.close();
        }
    }

    public static ContentValues setNewInProgressContentValues() {
        Random rand = new Random();

        ContentValues characterValues = new ContentValues();

        characterValues.put(InProgressContract.CharacterEntry.COLUMN_NAME, "");
        characterValues.put(InProgressContract.CharacterEntry.COLUMN_CLASS_ID, 0);
        characterValues.put(InProgressContract.CharacterEntry.COLUMN_RACE_ID, 0);
        characterValues.put(InProgressContract.CharacterEntry.COLUMN_ALIGN, 0);

        characterValues.put(InProgressContract.CharacterEntry.COLUMN_STR, -1);
        characterValues.put(InProgressContract.CharacterEntry.COLUMN_DEX, -1);
        characterValues.put(InProgressContract.CharacterEntry.COLUMN_CON, -1);
        characterValues.put(InProgressContract.CharacterEntry.COLUMN_INT, -1);
        characterValues.put(InProgressContract.CharacterEntry.COLUMN_WIS, -1);
        characterValues.put(InProgressContract.CharacterEntry.COLUMN_CHA, -1);

        characterValues.put(InProgressContract.CharacterEntry.COLUMN_ABILITY_1, generateAbilityScore(rand));
        characterValues.put(InProgressContract.CharacterEntry.COLUMN_ABILITY_2, generateAbilityScore(rand));
        characterValues.put(InProgressContract.CharacterEntry.COLUMN_ABILITY_3, generateAbilityScore(rand));
        characterValues.put(InProgressContract.CharacterEntry.COLUMN_ABILITY_4, generateAbilityScore(rand));
        characterValues.put(InProgressContract.CharacterEntry.COLUMN_ABILITY_5, generateAbilityScore(rand));
        characterValues.put(InProgressContract.CharacterEntry.COLUMN_ABILITY_6, generateAbilityScore(rand));
        return characterValues;
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

    public static final int STATE_EMPTY = 0;
    public static final int STATE_PARTIAL = 1;
    public static final int STATE_COMPLETE = 2;

    public static int checkStateOfCharacterChoices(Context context, long index) {
        ContentValues values = getInProgressRow(context, index);

        if (isCompletelyFilled(values)) {
            return STATE_COMPLETE;
        } else if (isAtLeastPartiallyFilled(values)) {
            return STATE_PARTIAL;
        } else {
            return STATE_EMPTY;
        }
    }

    private static boolean isAtLeastPartiallyFilled(ContentValues values) {
        boolean isPartiallyFilled = areDetailsPartiallyFilled(values);
        isPartiallyFilled |= areAbilitiesPartiallyFilled(values);
        isPartiallyFilled |= areClassSpecificsPartiallyFilled(values);
        return isPartiallyFilled;
    }

    private static boolean areDetailsPartiallyFilled(ContentValues values) {
        boolean isPartiallyFilled = isStringSet(values, InProgressContract.CharacterEntry.COLUMN_NAME);
        isPartiallyFilled |= isOptionSelected(values, InProgressContract.CharacterEntry.COLUMN_RACE_ID);
        return isPartiallyFilled;
    }

    private static boolean areAbilitiesPartiallyFilled(ContentValues values) {
        boolean isPartiallyFilled = isIntegerSet(values, InProgressContract.CharacterEntry.COLUMN_STR);
        isPartiallyFilled |= isIntegerSet(values, InProgressContract.CharacterEntry.COLUMN_DEX);
        isPartiallyFilled |= isIntegerSet(values, InProgressContract.CharacterEntry.COLUMN_CON);
        isPartiallyFilled |= isIntegerSet(values, InProgressContract.CharacterEntry.COLUMN_INT);
        isPartiallyFilled |= isIntegerSet(values, InProgressContract.CharacterEntry.COLUMN_WIS);
        isPartiallyFilled |= isIntegerSet(values, InProgressContract.CharacterEntry.COLUMN_CHA);
        return isPartiallyFilled;
    }

    private static boolean areClassSpecificsPartiallyFilled(ContentValues values) {
        int classValue = values.getAsInteger(InProgressContract.CharacterEntry.COLUMN_CLASS_ID);
        boolean isPartiallyFilled = (classValue > 0);

        switch (classValue) {
            case RulesUtils.CLASS_CLERIC:
                //TODO check for two selected domains
                break;
            case RulesUtils.CLASS_FIGHTER:
                break;
            case RulesUtils.CLASS_ROGUE:
                break;
            case RulesUtils.CLASS_WIZARD:
                //TODO check for selected familiar
                //TODO check for selected spells
                break;
        }
        return isPartiallyFilled;
    }

    private static boolean isCompletelyFilled(ContentValues values) {
        boolean isCompletelyFilled = areDetailsFilled(values);
        isCompletelyFilled &= areAbilitiesFilled(values);
        isCompletelyFilled &= areClassSpecificsFilled(values);
        return isCompletelyFilled;
    }

    private static boolean areDetailsFilled(ContentValues values) {
        boolean isFilled = isStringSet(values, InProgressContract.CharacterEntry.COLUMN_NAME);
        isFilled &= isOptionSelected(values, InProgressContract.CharacterEntry.COLUMN_RACE_ID);
        return isFilled;
    }

    private static boolean areAbilitiesFilled(ContentValues values) {
        boolean isFilled = isIntegerSet(values, InProgressContract.CharacterEntry.COLUMN_STR);
        isFilled &= isIntegerSet(values, InProgressContract.CharacterEntry.COLUMN_DEX);
        isFilled &= isIntegerSet(values, InProgressContract.CharacterEntry.COLUMN_CON);
        isFilled &= isIntegerSet(values, InProgressContract.CharacterEntry.COLUMN_INT);
        isFilled &= isIntegerSet(values, InProgressContract.CharacterEntry.COLUMN_WIS);
        isFilled &= isIntegerSet(values, InProgressContract.CharacterEntry.COLUMN_CHA);
        return isFilled;
    }

    private static boolean areClassSpecificsFilled(ContentValues values) {
        int classValue = values.getAsInteger(InProgressContract.CharacterEntry.COLUMN_CLASS_ID);
        boolean isPartiallyFilled = (classValue > 0);

        switch (classValue) {
            case RulesUtils.CLASS_CLERIC:
                //TODO check for two selected domains
                break;
            case RulesUtils.CLASS_FIGHTER:
                break;
            case RulesUtils.CLASS_ROGUE:
                break;
            case RulesUtils.CLASS_WIZARD:
                //TODO check for selected familiar
                //TODO check for selected spells
                break;
        }
        return isPartiallyFilled;
    }

    public static boolean isOptionSelected(ContentValues values, String column) {
        int inProgressValue = values.getAsInteger(column);
        return (inProgressValue > 0);
    }

    public static boolean isIntegerSet(ContentValues values, String column) {
        int inProgressValue = values.getAsInteger(column);
        return (inProgressValue != -1);
    }

    public static boolean isStringSet(ContentValues values, String column) {
        String inProgressValue = values.getAsString(column);
        return (!inProgressValue.equals(""));
    }
}
