package com.brave_bunny.dndhelper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtil;

import static com.brave_bunny.dndhelper.Utility.getIntFromCursor;

/**
 * Created by Jemma on 1/11/2017.
 */

public class CharacterUtil {

    private static final int TRUE = 1;
    private static final int FALSE = 0;

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

    public static long insertValuesInCharacterTable(Context context, ContentValues values) {
        long index;
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName = InProgressContract.CharacterEntry.TABLE_NAME;

        try {
            index = db.insert(tableName, null, values);
        } finally {
            db.close();
        }
        return index;
    }

    //TODO add all chosen content to all tables (deity, spells, familiar, skills, feats, ...)
    public static long createNewCharacter(Context context, long inProgressIndex) {
        ContentValues inProgressValues = InProgressUtil.getInProgressRow(context, inProgressIndex);
        ContentValues characterValues = new ContentValues();

        // adding character name
        String nameString = inProgressValues.getAsString(InProgressContract.CharacterEntry.COLUMN_NAME);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_NAME, nameString);

        // adding character gender
        int genderChoice = inProgressValues.getAsInteger(InProgressContract.CharacterEntry.COLUMN_GENDER);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_GENDER, genderChoice);

        // adding character race
        int raceChoice = inProgressValues.getAsInteger(InProgressContract.CharacterEntry.COLUMN_RACE_ID);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_RACE, raceChoice);

        // adding character age
        String ageString = inProgressValues.getAsString(InProgressContract.CharacterEntry.COLUMN_AGE);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_AGE, ageString);

        // adding character weight
        String weightString = inProgressValues.getAsString(InProgressContract.CharacterEntry.COLUMN_WEIGHT);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_WEIGHT, weightString);

        // adding character height
        String heightString = inProgressValues.getAsString(InProgressContract.CharacterEntry.COLUMN_HEIGHT);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_HEIGHT, heightString);

        //TODO save religion?
        // adding character religion
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_RELIGION_ID, 0);

        // adding character alignment
        int alignChoice = inProgressValues.getAsInteger(InProgressContract.CharacterEntry.COLUMN_ALIGN);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_ALIGN, alignChoice);

        // adding character strength
        int strTotal = InProgressUtil.getTotalStrengthScore(context, inProgressIndex);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_STR, strTotal);

        // adding character dexterity
        int dexTotal = InProgressUtil.getTotalDexterityScore(context, inProgressIndex);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_DEX, dexTotal);

        // adding character constitution
        int conTotal = InProgressUtil.getTotalConstitutionScore(context, inProgressIndex);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_CON, conTotal);

        // adding character intelligence
        int intTotal = InProgressUtil.getTotalIntelligenceScore(context, inProgressIndex);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_INT, intTotal);

        // adding character wisdom
        int wisTotal = InProgressUtil.getTotalWisdomScore(context, inProgressIndex);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_WIS, wisTotal);

        // adding character charisma
        int chaTotal = InProgressUtil.getTotalCharismaScore(context, inProgressIndex);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_CHA, chaTotal);

        // adding character base attack bonus, fortitude, reflex, and will
        int classChoice = inProgressValues.getAsInteger(InProgressContract.CharacterEntry.COLUMN_CLASS_ID);
        ContentValues classLevelOneStats = RulesUtils.getFirstLevelStats(context, classChoice);

        int baseAttack = 0;
        int fortitude = Utility.scoreToModifier(conTotal);
        int reflex = Utility.scoreToModifier(dexTotal);
        int will = Utility.scoreToModifier(wisTotal);

        switch (classChoice) {
            case RulesUtils.CLASS_CLERIC:
                baseAttack = classLevelOneStats.getAsInteger(RulesContract.ClericEntry.COLUMN_BASE_ATTACK_1);
                fortitude += classLevelOneStats.getAsInteger(RulesContract.ClericEntry.COLUMN_FORT);
                reflex += classLevelOneStats.getAsInteger(RulesContract.ClericEntry.COLUMN_REF);
                will += classLevelOneStats.getAsInteger(RulesContract.ClericEntry.COLUMN_WILL);
                break;
            case RulesUtils.CLASS_FIGHTER:
                baseAttack = classLevelOneStats.getAsInteger(RulesContract.FighterEntry.COLUMN_BASE_ATTACK_1);
                fortitude += classLevelOneStats.getAsInteger(RulesContract.FighterEntry.COLUMN_FORT);
                reflex += classLevelOneStats.getAsInteger(RulesContract.FighterEntry.COLUMN_REF);
                will += classLevelOneStats.getAsInteger(RulesContract.FighterEntry.COLUMN_WILL);
                break;
            case RulesUtils.CLASS_ROGUE:
                baseAttack = classLevelOneStats.getAsInteger(RulesContract.RogueEntry.COLUMN_BASE_ATTACK_1);
                fortitude += classLevelOneStats.getAsInteger(RulesContract.RogueEntry.COLUMN_FORT);
                reflex += classLevelOneStats.getAsInteger(RulesContract.RogueEntry.COLUMN_REF);
                will += classLevelOneStats.getAsInteger(RulesContract.RogueEntry.COLUMN_WILL);
                break;
            case RulesUtils.CLASS_WIZARD:
                baseAttack = classLevelOneStats.getAsInteger(RulesContract.WizardEntry.COLUMN_BASE_ATTACK_1);
                fortitude += classLevelOneStats.getAsInteger(RulesContract.WizardEntry.COLUMN_FORT);
                reflex += classLevelOneStats.getAsInteger(RulesContract.WizardEntry.COLUMN_REF);
                will += classLevelOneStats.getAsInteger(RulesContract.WizardEntry.COLUMN_WILL);
                break;
        }
        // adding character base attack bonus
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_BASE_ATTACK, baseAttack);

        // adding character fortitude
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_FORT, fortitude);

        // adding character reflex
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_REF, reflex);

        // adding character will
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_WILL, will);

        //TODO get money and load levels
        // adding character money
        int money = inProgressValues.getAsInteger(InProgressContract.CharacterEntry.COLUMN_MONEY);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_MONEY, money);

        // adding character light load

        // adding character medium load

        // adding character heavy load

        //TODO: add armor bonus, shield bonus, and size modifier
        // adding character AC
        int armor_class = 10 + Utility.scoreToModifier(dexTotal);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_AC, armor_class);

        // adding character HP
        ContentValues classStats = RulesUtils.getClassStats(context, classChoice);
        int hpDie = classStats.getAsInteger(RulesContract.ClassEntry.COLUMN_HIT_DIE);
        hpDie += Utility.scoreToModifier(conTotal);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_HP_CURR, hpDie);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_HP_MAX, hpDie);

        //TODO: check for Improved Initiative feat
        // adding character initiative
        int initiative = Utility.scoreToModifier(dexTotal);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_INITIATIVE, initiative);


        // adding character in battle
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_IN_BATTLE, FALSE);

        long characterIndex = insertValuesInCharacterTable(context, characterValues);

        //TODO transfer spells
        //TODO transfer familiar
        //TODO transfer domains
        //TODO transfer skills
        //TODO transfer feats
        //TODO transfer items

        return characterIndex;
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
