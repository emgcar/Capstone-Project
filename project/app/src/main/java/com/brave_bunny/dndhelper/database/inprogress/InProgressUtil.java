package com.brave_bunny.dndhelper.database.inprogress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils;

import java.util.Objects;
import java.util.Random;

import static com.brave_bunny.dndhelper.Utility.getIntFromCursor;
import static com.brave_bunny.dndhelper.Utility.scoreToModifier;

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
            InProgressContract.CharacterEntry.COLUMN_HP,

            InProgressContract.CharacterEntry.COLUMN_FAMILIAR_ID
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

    private static final int COL_CHARACTER_FAMILIAR = 28;

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


    private static int getInProgressValue(Context context, long rowIndex, int colIndex) {
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

    public static long insertValuesIntoInProgressTable(Context context, String tableName, ContentValues values) {
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

        characterValues.put(InProgressContract.CharacterEntry.COLUMN_FAMILIAR_ID, -1);

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

        if (isCompletelyFilled(context, values)) {
            return STATE_COMPLETE;
        } else if (isAtLeastPartiallyFilled(context, values)) {
            return STATE_PARTIAL;
        } else {
            return STATE_EMPTY;
        }
    }

    private static boolean isAtLeastPartiallyFilled(Context context, ContentValues values) {
        boolean isPartiallyFilled = areDetailsPartiallyFilled(values);
        isPartiallyFilled |= areAbilitiesPartiallyFilled(values);
        isPartiallyFilled |= areClassSpecificsPartiallyFilled(context, values);
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

    private static boolean areClassSpecificsPartiallyFilled(Context context, ContentValues values) {
        int classValue = values.getAsInteger(InProgressContract.CharacterEntry.COLUMN_CLASS_ID);
        boolean isPartiallyFilled = (classValue > 0);

        switch (classValue) {
            case RulesUtils.CLASS_CLERIC:
                int numberDomain = numberDomainsSelected(context, values);
                isPartiallyFilled |= (numberDomain > 0);
                break;
            case RulesUtils.CLASS_FIGHTER:
                break;
            case RulesUtils.CLASS_ROGUE:
                break;
            case RulesUtils.CLASS_WIZARD:
                isPartiallyFilled |= isFamiliarSelected(values);
                int numberSpells = numberSpellsSelected(context, values);
                isPartiallyFilled |= (numberSpells > 0);
                break;
        }
        return isPartiallyFilled;
    }

    private static boolean isCompletelyFilled(Context context, ContentValues values) {
        boolean isCompletelyFilled = areDetailsFilled(values);
        isCompletelyFilled &= areAbilitiesFilled(values);
        isCompletelyFilled &= areClassSpecificsFilled(context, values);
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

    private static boolean areClassSpecificsFilled(Context context, ContentValues values) {
        int classValue = values.getAsInteger(InProgressContract.CharacterEntry.COLUMN_CLASS_ID);
        boolean isFilled = (classValue > 0);

        switch (classValue) {
            case RulesUtils.CLASS_CLERIC:
                int numberDomain = numberDomainsSelected(context, values);
                isFilled &= (numberDomain == 2);
                break;
            case RulesUtils.CLASS_FIGHTER:
                break;
            case RulesUtils.CLASS_ROGUE:
                break;
            case RulesUtils.CLASS_WIZARD:
                isFilled &= isFamiliarSelected(values);

                int numberSpells = numberSpellsSelected(context, values);
                Object intScore = values.get(InProgressContract.CharacterEntry.COLUMN_INT);
                if (intScore != null) {
                    int intMod = scoreToModifier((int)intScore);
                    isFilled &= (numberSpells == (3 + intMod));
                }
                break;
        }
        return isFilled;
    }

    private static int numberDomainsSelected(Context context, ContentValues values) {
        long rowIndex = values.getAsLong(InProgressContract.CharacterEntry._ID);
        int numberDomains;

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + InProgressContract.ClericDomainEntry.TABLE_NAME
                    + " WHERE " + InProgressContract.ClericDomainEntry.COLUMN_CHARACTER_ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            numberDomains = cursor.getCount();
            cursor.close();
        } finally {
            db.close();
        }

        return numberDomains;
    }

    private static boolean isFamiliarSelected(ContentValues values) {
        return isIntegerSet(values, InProgressContract.CharacterEntry.COLUMN_FAMILIAR_ID);
    }

    private static int numberSpellsSelected(Context context, ContentValues values) {
        long rowIndex = values.getAsLong(InProgressContract.CharacterEntry._ID);
        int numberSpells;

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + InProgressContract.SpellEntry.TABLE_NAME
                    + " WHERE " + InProgressContract.SpellEntry.COLUMN_CHARACTER_ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            numberSpells = cursor.getCount();
            cursor.close();
        } finally {
            db.close();
        }

        return numberSpells;
    }

    private static boolean isOptionSelected(ContentValues values, String column) {
        int inProgressValue = values.getAsInteger(column);
        return (inProgressValue > 0);
    }

    private static boolean isIntegerSet(ContentValues values, String column) {
        int inProgressValue = values.getAsInteger(column);
        return (inProgressValue != -1);
    }

    private static boolean isStringSet(ContentValues values, String column) {
        String inProgressValue = values.getAsString(column);
        return (!inProgressValue.equals(""));
    }

    public void removeDomainSelection(Context context, long rowIndex, int domainId) {

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "DELETE FROM " + InProgressContract.ClericDomainEntry.TABLE_NAME
                    + " WHERE " + InProgressContract.ClericDomainEntry.COLUMN_CHARACTER_ID + " = ? AND " +
                    InProgressContract.ClericDomainEntry.COLUMN_DOMAIN_ID + " = ?";
            db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(domainId)});
        } finally {
            db.close();
        }
    }

    public void addDomainSelection(Context context, long rowIndex, int domainId) {
        ContentValues values = new ContentValues();
        values.put(InProgressContract.ClericDomainEntry.COLUMN_CHARACTER_ID, rowIndex);
        values.put(InProgressContract.ClericDomainEntry.COLUMN_DOMAIN_ID, domainId);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(InProgressContract.ClericDomainEntry.TABLE_NAME, null, values);
        } finally {
            db.close();
        }
    }

    public static boolean isDomainSelected(Context context, long rowIndex, long domainId) {
        boolean isSelected = false;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + InProgressContract.ClericDomainEntry.TABLE_NAME
                    + " WHERE " + InProgressContract.ClericDomainEntry.COLUMN_CHARACTER_ID + " = ? AND " +
                    InProgressContract.ClericDomainEntry.COLUMN_DOMAIN_ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(domainId)});
            if (cursor.getCount() > 0) {
                isSelected = true;
            }
            cursor.close();
        } finally {
            db.close();
        }
        return isSelected;
    }

    public static int getNumberDomainsSelected(Context context, long rowIndex) {
        int numDomains = 0;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + InProgressContract.ClericDomainEntry.TABLE_NAME
                    + " WHERE " + InProgressContract.ClericDomainEntry.COLUMN_CHARACTER_ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            numDomains = cursor.getCount();
            cursor.close();
        } finally {
            db.close();
        }
        return numDomains;
    }

    public void removeSpellSelection(Context context, long rowIndex, int spellId) {
        spellId += RulesUtils.numberSpellsUntilLevelOne;

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "DELETE FROM " + InProgressContract.SpellEntry.TABLE_NAME
                    + " WHERE " + InProgressContract.SpellEntry.COLUMN_CHARACTER_ID + " = ? AND " +
                    InProgressContract.SpellEntry.COLUMN_SPELL_ID + " = ?";
            db.rawQuery(query, new String[]{Long.toString(rowIndex), Integer.toString(spellId)});
        } finally {
            db.close();
        }
    }

    public void addSpellSelection(Context context, long rowIndex, int spellId) {
        spellId += RulesUtils.numberSpellsUntilLevelOne;
        ContentValues values = new ContentValues();
        values.put(InProgressContract.SpellEntry.COLUMN_CHARACTER_ID, rowIndex);
        values.put(InProgressContract.SpellEntry.COLUMN_SPELL_ID, spellId);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(InProgressContract.SpellEntry.TABLE_NAME, null, values);
        } finally {
            db.close();
        }
    }

    public static boolean isSpellSelected(Context context, long rowIndex, long spellId) {
        spellId += RulesUtils.numberSpellsUntilLevelOne;
        boolean isSelected = false;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + InProgressContract.SpellEntry.TABLE_NAME
                    + " WHERE " + InProgressContract.SpellEntry.COLUMN_CHARACTER_ID + " = ? AND " +
                    InProgressContract.SpellEntry.COLUMN_SPELL_ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(spellId)});
            if (cursor.getCount() > 0) {
                isSelected = true;
            }
            cursor.close();
        } finally {
            db.close();
        }
        return isSelected;
    }

    public static int getNumberSpellSelected(Context context, long rowIndex) {
        int numSpells = 0;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + InProgressContract.SpellEntry.TABLE_NAME
                    + " WHERE " + InProgressContract.SpellEntry.COLUMN_CHARACTER_ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            numSpells = cursor.getCount();
            cursor.close();
        } finally {
            db.close();
        }
        return numSpells;
    }

    public static boolean isFamiliarSameAsSelected(Context context, long rowIndex, long familiarId) {
        long chosenFamiliar = getInProgressValue(context, rowIndex, InProgressUtil.COL_CHARACTER_FAMILIAR);

        if (familiarId == chosenFamiliar) {
            return true;
        }
        return false;
    }

    public static void changeFamiliarSelection(Context context, long rowIndex, long familiarId) {
        ContentValues values = getInProgressRow(context, rowIndex);
        values.put(InProgressContract.CharacterEntry.COLUMN_FAMILIAR_ID, familiarId);
        updateInProgressTable(context, InProgressContract.CharacterEntry.TABLE_NAME, values, rowIndex);
    }

    //TODO for all final tables
    public void removeAllCharacterData(Context context, long rowIndex) {
        removeAllCharacterStats(context, rowIndex);
        removeAllCharacterDomains(context, rowIndex);
        removeAllCharacterSpells(context, rowIndex);
    }

    private void removeAllCharacterStats(Context context, long rowIndex) {

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "DELETE FROM " + InProgressContract.CharacterEntry.TABLE_NAME
                    + " WHERE " + InProgressContract.CharacterEntry._ID + " = ?";
            db.rawQuery(query, new String[]{Long.toString(rowIndex)});
        } finally {
            db.close();
        }
    }

    private void removeAllCharacterDomains(Context context, long rowIndex) {

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "DELETE FROM " + InProgressContract.ClericDomainEntry.TABLE_NAME
                    + " WHERE " + InProgressContract.ClericDomainEntry.COLUMN_CHARACTER_ID + " = ?";
            db.rawQuery(query, new String[]{Long.toString(rowIndex)});
        } finally {
            db.close();
        }
    }

    private void removeAllCharacterSpells(Context context, long rowIndex) {

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "DELETE FROM " + InProgressContract.SpellEntry.TABLE_NAME
                    + " WHERE " + InProgressContract.SpellEntry.COLUMN_CHARACTER_ID + " = ?";
            db.rawQuery(query, new String[]{Long.toString(rowIndex)});
        } finally {
            db.close();
        }
    }

    private static int getTotalAbilityScore(Context context, long rowIndex,
                                     String inProgressAbilityColumn, String raceAbilityColumn) {

        ContentValues inProgressValues = getInProgressRow(context, rowIndex);
        int baseScore = inProgressValues.getAsInteger(inProgressAbilityColumn);
        if (baseScore == -1) return baseScore;

        int raceId = inProgressValues.getAsInteger(InProgressContract.CharacterEntry.COLUMN_RACE_ID);
        if (raceId != 0) {
            ContentValues raceValues = RulesUtils.getRaceStats(context, raceId);
            baseScore += raceValues.getAsInteger(raceAbilityColumn);
        }

        return baseScore;
    }

    public static int getTotalStrengthScore(Context context, long rowIndex) {
        return getTotalAbilityScore(context, rowIndex,
                InProgressContract.CharacterEntry.COLUMN_STR,
                RulesContract.RaceEntry.COLUMN_STR);
    }

    public static int getTotalDexterityScore(Context context, long rowIndex) {
        return getTotalAbilityScore(context, rowIndex,
                InProgressContract.CharacterEntry.COLUMN_DEX,
                RulesContract.RaceEntry.COLUMN_DEX);
    }

    public static int getTotalConstitutionScore(Context context, long rowIndex) {
        return getTotalAbilityScore(context, rowIndex,
                InProgressContract.CharacterEntry.COLUMN_CON,
                RulesContract.RaceEntry.COLUMN_CON);
    }

    public static int getTotalIntelligenceScore(Context context, long rowIndex) {
        return getTotalAbilityScore(context, rowIndex,
                InProgressContract.CharacterEntry.COLUMN_INT,
                RulesContract.RaceEntry.COLUMN_INT);
    }

    public static int getTotalWisdomScore(Context context, long rowIndex) {
        return getTotalAbilityScore(context, rowIndex,
                InProgressContract.CharacterEntry.COLUMN_WIS,
                RulesContract.RaceEntry.COLUMN_WIS);
    }

    public static int getTotalCharismaScore(Context context, long rowIndex) {
        return getTotalAbilityScore(context, rowIndex,
                InProgressContract.CharacterEntry.COLUMN_CHA,
                RulesContract.RaceEntry.COLUMN_CHA);
    }

}
