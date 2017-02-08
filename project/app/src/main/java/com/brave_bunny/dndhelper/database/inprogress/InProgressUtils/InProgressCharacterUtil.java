package com.brave_bunny.dndhelper.database.inprogress.InProgressUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;

import java.util.Random;

import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesClassesUtils.CLASS_CLERIC;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesClassesUtils.CLASS_COLUMNS;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesClassesUtils.CLASS_FIGHTER;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesClassesUtils.CLASS_ROGUE;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesClassesUtils.CLASS_WIZARD;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesClassesUtils.COL_CLASS_STARTING_GOLD;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesClassesUtils.getClassStats;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesRacesUtils.COL_RACE_CHA;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesRacesUtils.COL_RACE_CON;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesRacesUtils.COL_RACE_DEX;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesRacesUtils.COL_RACE_INT;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesRacesUtils.COL_RACE_STR;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesRacesUtils.COL_RACE_WIS;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesRacesUtils.RACE_COLUMNS;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesRacesUtils.getRaceStats;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressArmorUtil.removeAllInProgressArmor;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressDomainsUtil.getNumberDomainsSelected;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressDomainsUtil.numberDomainsSelected;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressDomainsUtil.removeAllInProgressDomains;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressFeatsUtil.removeAllInProgressFeats;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressItemsUtil.removeAllInProgressItems;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSkillsUtil.removeAllInProgressSkills;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil.numberSpellsSelected;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil.removeAllInProgressSpells;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressWeaponsUtil.removeAllInProgressWeapons;

/**
 * Created by Jemma on 1/13/2017.
 */



public class InProgressCharacterUtil {

    public static final int STATE_EMPTY = 0;
    public static final int STATE_PARTIAL = 1;
    public static final int STATE_COMPLETE = 2;

    private static final String tableName = InProgressContract.CharacterEntry.TABLE_NAME;

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

    /*
     *      CHARACTER util functions
     */

    //TODO for all final tables
    public static void removeAllCharacterData(Context context, long rowIndex) {
        removeAllInProgressStats(context, rowIndex);
        removeAllInProgressDomains(context, rowIndex);
        removeAllInProgressSpells(context, rowIndex);
        removeAllInProgressSkills(context, rowIndex);
        removeAllInProgressFeats(context, rowIndex);
        removeAllInProgressArmor(context, rowIndex);
        removeAllInProgressWeapons(context, rowIndex);
        removeAllInProgressItems(context, rowIndex);
    }

    private static void removeAllInProgressStats(Context context, long rowIndex) {

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String id = INPROGRESS_COLUMNS[COL_CHARACTER_ID];
            db.delete(tableName, id + " = ?", new String[]{Long.toString(rowIndex)});
        } finally {
            db.close();
        }
    }

    public static ContentValues getInProgressRow(Context context, long rowIndex) {
        ContentValues values = null;

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName
                    + " WHERE " + INPROGRESS_COLUMNS[COL_CHARACTER_ID] + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                values = Utility.cursorRowToContentValues(cursor);
            }
            cursor.close();
        } finally {
            db.close();
        }

        return values;
    }

    public static int getInProgressValue(Context context, long rowIndex, int colIndex) {
        int value;

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName
                    + " WHERE " + INPROGRESS_COLUMNS[COL_CHARACTER_ID] + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            cursor.moveToFirst();

            value = cursor.getInt(colIndex);
            cursor.close();
        } finally {
            db.close();
        }

        return value;
    }

    public static void updateInProgressTable(Context context, ContentValues values, long index) {
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName + " WHERE "
                    + INPROGRESS_COLUMNS[COL_CHARACTER_ID] + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(index)});
            try {
                cursor.moveToFirst();
                db.update(tableName, values, INPROGRESS_COLUMNS[COL_CHARACTER_ID] + " = ?",
                        new String[]{Long.toString(index)});
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }
    }

    public static long insertValuesIntoInProgressTable(Context context, ContentValues values) {
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

    public static void deleteValuesFromInProgressTable(Context context, long index) {
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

        characterValues.put(INPROGRESS_COLUMNS[COL_CHARACTER_NAME], "");
        characterValues.put(INPROGRESS_COLUMNS[COL_CHARACTER_GENDER], CharacterContract.GENDER_MALE);
        characterValues.put(INPROGRESS_COLUMNS[COL_CHARACTER_CLASS_ID], 0);
        characterValues.put(INPROGRESS_COLUMNS[COL_CHARACTER_RACE_ID], 0);
        characterValues.put(INPROGRESS_COLUMNS[COL_CHARACTER_ALIGN], 0);

        characterValues.put(INPROGRESS_COLUMNS[COL_CHARACTER_STRENGTH], -1);
        characterValues.put(INPROGRESS_COLUMNS[COL_CHARACTER_DEXTERITY], -1);
        characterValues.put(INPROGRESS_COLUMNS[COL_CHARACTER_CONSTITUTION], -1);
        characterValues.put(INPROGRESS_COLUMNS[COL_CHARACTER_INTELLIGENCE], -1);
        characterValues.put(INPROGRESS_COLUMNS[COL_CHARACTER_WISDOM], -1);
        characterValues.put(INPROGRESS_COLUMNS[COL_CHARACTER_CHARISMA], -1);

        characterValues.put(INPROGRESS_COLUMNS[COL_CHARACTER_ABILITY1], generateAbilityScore(rand));
        characterValues.put(INPROGRESS_COLUMNS[COL_CHARACTER_ABILITY2], generateAbilityScore(rand));
        characterValues.put(INPROGRESS_COLUMNS[COL_CHARACTER_ABILITY3], generateAbilityScore(rand));
        characterValues.put(INPROGRESS_COLUMNS[COL_CHARACTER_ABILITY4], generateAbilityScore(rand));
        characterValues.put(INPROGRESS_COLUMNS[COL_CHARACTER_ABILITY5], generateAbilityScore(rand));
        characterValues.put(INPROGRESS_COLUMNS[COL_CHARACTER_ABILITY6], generateAbilityScore(rand));

        characterValues.put(INPROGRESS_COLUMNS[COL_CHARACTER_FAMILIAR], -1);

        return characterValues;
    }

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
        boolean isPartiallyFilled = isStringSet(values, INPROGRESS_COLUMNS[COL_CHARACTER_NAME]);
        isPartiallyFilled |= isOptionSelected(values, INPROGRESS_COLUMNS[COL_CHARACTER_RACE_ID]);
        return isPartiallyFilled;
    }

    private static boolean areClassSpecificsPartiallyFilled(Context context, ContentValues values) {
        int classValue = values.getAsInteger(INPROGRESS_COLUMNS[COL_CHARACTER_CLASS_ID]);
        boolean isPartiallyFilled = (classValue > 0);

        switch (classValue) {
            case CLASS_CLERIC:
                int numberDomain = numberDomainsSelected(context, values);
                isPartiallyFilled |= (numberDomain > 0);
                break;
            case CLASS_FIGHTER:
                break;
            case CLASS_ROGUE:
                break;
            case CLASS_WIZARD:
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
        boolean isFilled = isStringSet(values, INPROGRESS_COLUMNS[COL_CHARACTER_NAME]);
        isFilled &= isOptionSelected(values, INPROGRESS_COLUMNS[COL_CHARACTER_RACE_ID]);
        return isFilled;
    }

    private static boolean areClassSpecificsFilled(Context context, ContentValues values) {
        int classValue = values.getAsInteger(INPROGRESS_COLUMNS[COL_CHARACTER_CLASS_ID]);
        boolean isFilled = (classValue > 0);

        switch (classValue) {
            case CLASS_CLERIC:
                int numberDomain = numberDomainsSelected(context, values);
                isFilled &= (numberDomain == 2);
                break;
            case CLASS_FIGHTER:
                break;
            case CLASS_ROGUE:
                break;
            case CLASS_WIZARD:
                isFilled &= isFamiliarSelected(values);

                int numberSpells = numberSpellsSelected(context, values);
                Object intScore = values.get(INPROGRESS_COLUMNS[COL_CHARACTER_INTELLIGENCE]);
                if (intScore != null) {
                    long intMod = RulesCharacterUtils.scoreToModifier((long)intScore);
                    isFilled &= (numberSpells == (3 + intMod));
                }
                break;
        }
        return isFilled;
    }

    public static void updateClassValues(Context context, long rowIndex, int classSelection) {
        ContentValues values = getInProgressRow(context, rowIndex);
        long previousClass = values.getAsLong(INPROGRESS_COLUMNS[COL_CHARACTER_CLASS_ID]);

        long prevMoneyDiff = 0;
        ContentValues prevClassValues = getClassStats(context, previousClass);
        if (prevClassValues != null) {
            long prevMaxMoney = prevClassValues.getAsLong(CLASS_COLUMNS[COL_CLASS_STARTING_GOLD]);
            long prevMoney = values.getAsLong(INPROGRESS_COLUMNS[COL_CHARACTER_MONEY]);
            prevMoneyDiff = prevMaxMoney - prevMoney;
        }

        ContentValues currClassValues = getClassStats(context, classSelection);
        long currMoney = 0;
        if (currClassValues != null) {
            long currMaxMoney = currClassValues.getAsLong(CLASS_COLUMNS[COL_CLASS_STARTING_GOLD]);
            currMoney = currMaxMoney - prevMoneyDiff;
        }

        values.put(INPROGRESS_COLUMNS[COL_CHARACTER_MONEY], currMoney);

        updateInProgressTable(context, values, rowIndex);
    }

    /*
     *      FAMILIAR util functions
     */

    public static boolean isFamiliarSameAsSelected(Context context, long rowIndex, long familiarId) {
        long chosenFamiliar = getInProgressValue(context, rowIndex, COL_CHARACTER_FAMILIAR);

        if (familiarId == chosenFamiliar) {
            return true;
        }
        return false;
    }

    public static void changeFamiliarSelection(Context context, long rowIndex, long familiarId) {
        ContentValues values = getInProgressRow(context, rowIndex);
        values.put(INPROGRESS_COLUMNS[COL_CHARACTER_FAMILIAR], familiarId);
        updateInProgressTable(context, values, rowIndex);
    }

    private static boolean isFamiliarSelected(ContentValues values) {
        return isIntegerSet(values, INPROGRESS_COLUMNS[COL_CHARACTER_FAMILIAR]);
    }

    /*
     *      ABILITY util functions
     */

    private static int getTotalAbilityScore(Context context, long rowIndex,
                                     String inProgressAbilityColumn, String raceAbilityColumn) {

        ContentValues inProgressValues = getInProgressRow(context, rowIndex);
        int baseScore = inProgressValues.getAsInteger(inProgressAbilityColumn);
        if (baseScore == -1) return baseScore;

        int raceId = inProgressValues.getAsInteger(INPROGRESS_COLUMNS[COL_CHARACTER_RACE_ID]);
        if (raceId != 0) {
            ContentValues raceValues = getRaceStats(context, raceId);
            baseScore += raceValues.getAsInteger(raceAbilityColumn);
        }

        return baseScore;
    }

    public static int getTotalStrengthScore(Context context, long rowIndex) {
        return getTotalAbilityScore(context, rowIndex,
                INPROGRESS_COLUMNS[COL_CHARACTER_STRENGTH],
                RACE_COLUMNS[COL_RACE_STR]);
    }

    public static int getTotalDexterityScore(Context context, long rowIndex) {
        return getTotalAbilityScore(context, rowIndex,
                INPROGRESS_COLUMNS[COL_CHARACTER_DEXTERITY],
                RACE_COLUMNS[COL_RACE_DEX]);
    }

    public static int getTotalConstitutionScore(Context context, long rowIndex) {
        return getTotalAbilityScore(context, rowIndex,
                INPROGRESS_COLUMNS[COL_CHARACTER_CONSTITUTION],
                RACE_COLUMNS[COL_RACE_CON]);
    }

    public static int getTotalIntelligenceScore(Context context, long rowIndex) {
        return getTotalAbilityScore(context, rowIndex,
                INPROGRESS_COLUMNS[COL_CHARACTER_INTELLIGENCE],
                RACE_COLUMNS[COL_RACE_INT]);
    }

    public static int getTotalWisdomScore(Context context, long rowIndex) {
        return getTotalAbilityScore(context, rowIndex,
                INPROGRESS_COLUMNS[COL_CHARACTER_WISDOM],
                RACE_COLUMNS[COL_RACE_WIS]);
    }

    public static int getTotalCharismaScore(Context context, long rowIndex) {
        return getTotalAbilityScore(context, rowIndex,
                INPROGRESS_COLUMNS[COL_CHARACTER_CHARISMA],
                RACE_COLUMNS[COL_RACE_CHA]);
    }

    private static boolean areAbilitiesFilled(ContentValues values) {
        boolean isFilled = isIntegerSet(values, INPROGRESS_COLUMNS[COL_CHARACTER_STRENGTH]);
        isFilled &= isIntegerSet(values, INPROGRESS_COLUMNS[COL_CHARACTER_DEXTERITY]);
        isFilled &= isIntegerSet(values, INPROGRESS_COLUMNS[COL_CHARACTER_CONSTITUTION]);
        isFilled &= isIntegerSet(values, INPROGRESS_COLUMNS[COL_CHARACTER_INTELLIGENCE]);
        isFilled &= isIntegerSet(values, INPROGRESS_COLUMNS[COL_CHARACTER_WISDOM]);
        isFilled &= isIntegerSet(values, INPROGRESS_COLUMNS[COL_CHARACTER_CHARISMA]);
        return isFilled;
    }

    private static boolean areAbilitiesPartiallyFilled(ContentValues values) {
        boolean isPartiallyFilled = isIntegerSet(values, INPROGRESS_COLUMNS[COL_CHARACTER_STRENGTH]);
        isPartiallyFilled |= isIntegerSet(values, INPROGRESS_COLUMNS[COL_CHARACTER_DEXTERITY]);
        isPartiallyFilled |= isIntegerSet(values, INPROGRESS_COLUMNS[COL_CHARACTER_CONSTITUTION]);
        isPartiallyFilled |= isIntegerSet(values, INPROGRESS_COLUMNS[COL_CHARACTER_INTELLIGENCE]);
        isPartiallyFilled |= isIntegerSet(values, INPROGRESS_COLUMNS[COL_CHARACTER_WISDOM]);
        isPartiallyFilled |= isIntegerSet(values, INPROGRESS_COLUMNS[COL_CHARACTER_CHARISMA]);
        return isPartiallyFilled;
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
