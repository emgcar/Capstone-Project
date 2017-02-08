package com.brave_bunny.dndhelper.database.character.CharacterUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.character.CharacterDbHelper;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil;

import static com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterSpellsUtil.transferSpells;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesClassesUtils.CLASS_CLERIC;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesClassesUtils.CLASS_FIGHTER;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesClassesUtils.CLASS_ROGUE;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesClassesUtils.CLASS_WIZARD;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesClassesUtils.getClassStats;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesClassesUtils.getFirstLevelStats;

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

    // need to make sure no name duplication somehow
    public static boolean isInBattle(Context context, long index) {
        boolean value;

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + CharacterContract.CharacterEntry.TABLE_NAME
                    + " WHERE " + CharacterContract.CharacterEntry._ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(index)});

            cursor.moveToFirst();
            int isInBattle = cursor.getInt(COL_CHARACTER_IN_BATTLE);

            value = (isInBattle == TRUE);
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
        String tableName = CharacterContract.CharacterEntry.TABLE_NAME;

        try {
            index = db.insert(tableName, null, values);
        } finally {
            db.close();
        }
        return index;
    }

    //TODO add all chosen content to all tables (deity, spells, familiar, skills, feats, ...)
    public static long createNewCharacter(Context context, long inProgressIndex) {
        ContentValues inProgressValues = InProgressCharacterUtil.getInProgressRow(context, inProgressIndex);
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
        int strTotal = InProgressCharacterUtil.getTotalStrengthScore(context, inProgressIndex);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_STR, strTotal);

        // adding character dexterity
        int dexTotal = InProgressCharacterUtil.getTotalDexterityScore(context, inProgressIndex);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_DEX, dexTotal);

        // adding character constitution
        int conTotal = InProgressCharacterUtil.getTotalConstitutionScore(context, inProgressIndex);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_CON, conTotal);

        // adding character intelligence
        int intTotal = InProgressCharacterUtil.getTotalIntelligenceScore(context, inProgressIndex);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_INT, intTotal);

        // adding character wisdom
        int wisTotal = InProgressCharacterUtil.getTotalWisdomScore(context, inProgressIndex);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_WIS, wisTotal);

        // adding character charisma
        int chaTotal = InProgressCharacterUtil.getTotalCharismaScore(context, inProgressIndex);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_CHA, chaTotal);

        // adding character base attack bonus, fortitude, reflex, and will
        int classChoice = inProgressValues.getAsInteger(InProgressContract.CharacterEntry.COLUMN_CLASS_ID);
        ContentValues classLevelOneStats = getFirstLevelStats(context, classChoice);

        int baseAttack = 0;
        int fortitude = RulesCharacterUtils.scoreToModifier(conTotal);
        int reflex = RulesCharacterUtils.scoreToModifier(dexTotal);
        int will = RulesCharacterUtils.scoreToModifier(wisTotal);

        switch (classChoice) {
            case CLASS_CLERIC:
                baseAttack = classLevelOneStats.getAsInteger(RulesContract.ClericEntry.COLUMN_BASE_ATTACK_1);
                fortitude += classLevelOneStats.getAsInteger(RulesContract.ClericEntry.COLUMN_FORT);
                reflex += classLevelOneStats.getAsInteger(RulesContract.ClericEntry.COLUMN_REF);
                will += classLevelOneStats.getAsInteger(RulesContract.ClericEntry.COLUMN_WILL);
                break;
            case CLASS_FIGHTER:
                baseAttack = classLevelOneStats.getAsInteger(RulesContract.FighterEntry.COLUMN_BASE_ATTACK_1);
                fortitude += classLevelOneStats.getAsInteger(RulesContract.FighterEntry.COLUMN_FORT);
                reflex += classLevelOneStats.getAsInteger(RulesContract.FighterEntry.COLUMN_REF);
                will += classLevelOneStats.getAsInteger(RulesContract.FighterEntry.COLUMN_WILL);
                break;
            case CLASS_ROGUE:
                baseAttack = classLevelOneStats.getAsInteger(RulesContract.RogueEntry.COLUMN_BASE_ATTACK_1);
                fortitude += classLevelOneStats.getAsInteger(RulesContract.RogueEntry.COLUMN_FORT);
                reflex += classLevelOneStats.getAsInteger(RulesContract.RogueEntry.COLUMN_REF);
                will += classLevelOneStats.getAsInteger(RulesContract.RogueEntry.COLUMN_WILL);
                break;
            case CLASS_WIZARD:
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

        // adding character money
        int money = inProgressValues.getAsInteger(InProgressContract.CharacterEntry.COLUMN_MONEY);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_MONEY, money);


        //TODO get  load levels
        // adding character light load
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_LIGHT_LOAD, 0);

        // adding character medium load
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_MED_LOAD, 0);

        // adding character heavy load
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_HEAVY_LOAD, 0);

        //TODO: add armor bonus, shield bonus, and size modifier
        // adding character AC
        int armor_class = 10 + RulesCharacterUtils.scoreToModifier(dexTotal);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_AC, armor_class);

        // adding character HP
        ContentValues classStats = getClassStats(context, classChoice);
        int hpDie = classStats.getAsInteger(RulesContract.ClassEntry.COLUMN_HIT_DIE);
        hpDie += RulesCharacterUtils.scoreToModifier(conTotal);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_HP_CURR, hpDie);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_HP_MAX, hpDie);

        //TODO: check for Improved Initiative feat
        // adding character initiative
        int initiative = RulesCharacterUtils.scoreToModifier(dexTotal);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_INITIATIVE, initiative);


        // adding character in battle
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_IN_BATTLE, FALSE);

        long characterIndex = insertValuesInCharacterTable(context, characterValues);

        //TODO transfer spells
        transferSpells(context, inProgressIndex, characterIndex);

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
