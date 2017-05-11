package com.brave_bunny.dndhelper.database.inprogress.InProgressUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesRacesUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;

import java.util.Random;

import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_CLERIC;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_FIGHTER;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_ROGUE;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_WIZARD;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.getClassStats;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesRacesUtils.getRaceStats;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressArmorUtil.removeAllInProgressArmor;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressDomainsUtil.numberDomainsSelected;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressDomainsUtil.removeAllInProgressDomains;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressFeatsUtil.removeAllInProgressFeats;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressItemsUtil.removeAllInProgressItems;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSkillsUtil.removeAllInProgressSkills;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil.numberSpellsSelected;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil.removeAllInProgressSpells;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressWeaponsUtil.removeAllInProgressWeapons;

/**
 *  Handles all of the selected domains for in-progress characters.
 */



public class InProgressCharacterUtil {

    public static final int ABILITY1 = 1;
    public static final int ABILITY2 = 2;
    public static final int ABILITY3 = 3;
    public static final int ABILITY4 = 4;
    public static final int ABILITY5 = 5;
    public static final int ABILITY6 = 6;

    public static final int ABILITYSTR = 7;
    public static final int ABILITYDEX = 8;
    public static final int ABILITYCON = 9;
    public static final int ABILITYINT = 10;
    public static final int ABILITYWIS = 11;
    public static final int ABILITYCHA = 12;

    public static Random rand;

    /* LABELS - Should be private */

    private static String getTableName() {
        return InProgressContract.CharacterEntry.TABLE_NAME;
    }

    private static String characterIdLabel() {
        return InProgressContract.CharacterEntry._ID;
    }

    private static String characterNameLabel() {
        return InProgressContract.CharacterEntry.COLUMN_NAME;
    }

    private static String characterGenderLabel() {
        return InProgressContract.CharacterEntry.COLUMN_GENDER;
    }

    private static String characterRaceLabel() {
        return InProgressContract.CharacterEntry.COLUMN_RACE_ID;
    }

    private static String characterClassLabel() {
        return InProgressContract.CharacterEntry.COLUMN_CLASS_ID;
    }

    private static String characterAgeLabel() {
        return InProgressContract.CharacterEntry.COLUMN_AGE;
    }

    private static String characterWeightLabel() {
        return InProgressContract.CharacterEntry.COLUMN_WEIGHT;
    }

    private static String characterHeightLabel() {
        return InProgressContract.CharacterEntry.COLUMN_HEIGHT;
    }

    private static String characterReligionLabel() {
        return InProgressContract.CharacterEntry.COLUMN_RELIGION_ID;
    }

    private static String characterAlignLabel() {
        return InProgressContract.CharacterEntry.COLUMN_ALIGN;
    }

    private static String characterStrengthLabel() {
        return InProgressContract.CharacterEntry.COLUMN_STR;
    }

    private static String characterDexterityLabel() {
        return InProgressContract.CharacterEntry.COLUMN_DEX;
    }

    private static String characterConstitutionLabel() {
        return InProgressContract.CharacterEntry.COLUMN_CON;
    }

    private static String characterIntelligenceLabel() {
        return InProgressContract.CharacterEntry.COLUMN_INT;
    }

    private static String characterWisdomLabel() {
        return InProgressContract.CharacterEntry.COLUMN_WIS;
    }

    private static String characterCharismaLabel() {
        return InProgressContract.CharacterEntry.COLUMN_CHA;
    }

    private static String characterAbility1Label() {
        return InProgressContract.CharacterEntry.COLUMN_ABILITY_1;
    }

    private static String characterAbility2Label() {
        return InProgressContract.CharacterEntry.COLUMN_ABILITY_2;
    }

    private static String characterAbility3Label() {
        return InProgressContract.CharacterEntry.COLUMN_ABILITY_3;
    }

    private static String characterAbility4Label() {
        return InProgressContract.CharacterEntry.COLUMN_ABILITY_4;
    }

    private static String characterAbility5Label() {
        return InProgressContract.CharacterEntry.COLUMN_ABILITY_5;
    }

    private static String characterAbility6Label() {
        return InProgressContract.CharacterEntry.COLUMN_ABILITY_6;
    }

    private static String characterMoneyLabel() {
        return InProgressContract.CharacterEntry.COLUMN_MONEY;
    }

    private static String characterLightLoadLabel() {
        return InProgressContract.CharacterEntry.COLUMN_LIGHT_LOAD;
    }

    private static String characterMediumLoadLabel() {
        return InProgressContract.CharacterEntry.COLUMN_MED_LOAD;
    }

    private static String characterHeavyLoadLabel() {
        return InProgressContract.CharacterEntry.COLUMN_HEAVY_LOAD;
    }

    private static String characterACLabel() {
        return InProgressContract.CharacterEntry.COLUMN_AC;
    }

    private static String characterHPLabel() {
        return InProgressContract.CharacterEntry.COLUMN_HP;
    }

    private static String characterFamiliarLabel() {
        return InProgressContract.CharacterEntry.COLUMN_FAMILIAR_ID;
    }

    /* PARSE VALUES*/

    public static long getId(ContentValues values) {
        return values.getAsLong(characterIdLabel());
    }

    public static String getCharacterName(ContentValues values) {
        return values.getAsString(characterNameLabel());
    }

    public static void setCharacterName(ContentValues values, String name) {
        values.put(characterNameLabel(), name);
    }

    public static int getCharacterGender(ContentValues values) {
        return values.getAsInteger(characterGenderLabel());
    }

    public static void setCharacterGender(ContentValues values, int gender) {
        values.put(characterGenderLabel(), gender);
    }

    public static int getCharacterRace(ContentValues values) {
        return values.getAsInteger(characterRaceLabel());
    }

    public static void setCharacterRace(ContentValues values, int race) {
        values.put(characterRaceLabel(), race);
    }

    public static int getCharacterClass(ContentValues values) {
        return values.getAsInteger(characterClassLabel());
    }

    public static void setCharacterClass(ContentValues values, int classId) {
        values.put(characterClassLabel(), classId);
    }

    public static String getCharacterAge(ContentValues values) {
        return values.getAsString(characterAgeLabel());
    }

    public static void setCharacterAge(ContentValues values, String age) {
        values.put(characterAgeLabel(), age);
    }

    public static String getCharacterWeight(ContentValues values) {
        return values.getAsString(characterWeightLabel());
    }

    public static void setCharacterWeight(ContentValues values, String weight) {
        values.put(characterWeightLabel(), weight);
    }

    public static String getCharacterHeight(ContentValues values) {
        return values.getAsString(characterHeightLabel());
    }

    public static void setCharacterHeight(ContentValues values, String height) {
        values.put(characterHeightLabel(), height);
    }

    public static int getCharacterReligion(ContentValues values) {
        return values.getAsInteger(characterReligionLabel());
    }

    public static void setCharacterReligion(ContentValues values, int religion) {
        values.put(characterReligionLabel(), religion);
    }

    public static int getCharacterAlign(ContentValues values) {
        return values.getAsInteger(characterAlignLabel());
    }

    public static void setCharacterAlign(ContentValues values, int religion) {
        values.put(characterAlignLabel(), religion);
    }

    public static int getCharacterStrConnection(ContentValues values) {
        return values.getAsInteger(characterStrengthLabel());
    }

    public static int getCharacterStr(ContentValues values) {
        int connect = getCharacterStrConnection(values);
        return getAbilityFromConnection(values, connect);
    }

    public static void setCharacterStr(ContentValues values, int str) {
        values.put(characterStrengthLabel(), str);
    }

    public static int getCharacterDexConnection(ContentValues values) {
        return values.getAsInteger(characterDexterityLabel());
    }

    public static int getCharacterDex(ContentValues values) {
        int connect = getCharacterDexConnection(values);
        return getAbilityFromConnection(values, connect);
    }

    public static void setCharacterDex(ContentValues values, int dex) {
        values.put(characterDexterityLabel(), dex);
    }

    public static int getCharacterConConnection(ContentValues values) {
        return values.getAsInteger(characterConstitutionLabel());
    }

    public static int getCharacterCon(ContentValues values) {
        int connect = getCharacterConConnection(values);
        return getAbilityFromConnection(values, connect);
    }

    public static void setCharacterCon(ContentValues values, int con) {
        values.put(characterConstitutionLabel(), con);
    }

    public static int getCharacterIntConnection(ContentValues values) {
        return values.getAsInteger(characterIntelligenceLabel());
    }

    public static int getCharacterInt(ContentValues values) {
        int connect = getCharacterIntConnection(values);
        return getAbilityFromConnection(values, connect);
    }

    public static void setCharacterInt(ContentValues values, int intScore) {
        values.put(characterIntelligenceLabel(), intScore);
    }

    public static int getCharacterWisConnection(ContentValues values) {
        return values.getAsInteger(characterWisdomLabel());
    }

    public static int getCharacterWis(ContentValues values) {
        int connect = getCharacterWisConnection(values);
        return getAbilityFromConnection(values, connect);
    }

    public static void setCharacterWis(ContentValues values, int wis) {
        values.put(characterWisdomLabel(), wis);
    }

    public static int getCharacterChaConnection(ContentValues values) {
        return values.getAsInteger(characterCharismaLabel());
    }

    public static int getCharacterCha(ContentValues values) {
        int connect = getCharacterChaConnection(values);
        return getAbilityFromConnection(values, connect);
    }

    public static void setCharacterCha(ContentValues values, int cha) {
        values.put(characterCharismaLabel(), cha);
    }

    public static int getAbilityFromConnection(ContentValues values, int connect) {
        switch(connect) {
            case ABILITY1:
                return getCharacterAbility1(values);
            case ABILITY2:
                return getCharacterAbility2(values);
            case ABILITY3:
                return getCharacterAbility3(values);
            case ABILITY4:
                return getCharacterAbility4(values);
            case ABILITY5:
                return getCharacterAbility5(values);
            case ABILITY6:
                return getCharacterAbility6(values);
        }
        return connect;
    }

    public static int getCharacterAbility1(ContentValues values) {
        return values.getAsInteger(characterAbility1Label());
    }

    public static void setCharacterAbility1(ContentValues values, int ability) {
        values.put(characterAbility1Label(), ability);
    }

    public static int getCharacterAbility2(ContentValues values) {
        return values.getAsInteger(characterAbility2Label());
    }

    public static void setCharacterAbility2(ContentValues values, int ability) {
        values.put(characterAbility2Label(), ability);
    }

    public static int getCharacterAbility3(ContentValues values) {
        return values.getAsInteger(characterAbility3Label());
    }

    public static void setCharacterAbility3(ContentValues values, int ability) {
        values.put(characterAbility3Label(), ability);
    }

    public static int getCharacterAbility4(ContentValues values) {
        return values.getAsInteger(characterAbility4Label());
    }

    public static void setCharacterAbility4(ContentValues values, int ability) {
        values.put(characterAbility4Label(), ability);
    }

    public static int getCharacterAbility5(ContentValues values) {
        return values.getAsInteger(characterAbility5Label());
    }

    public static void setCharacterAbility5(ContentValues values, int ability) {
        values.put(characterAbility5Label(), ability);
    }

    public static int getCharacterAbility6(ContentValues values) {
        return values.getAsInteger(characterAbility6Label());
    }

    public static void setCharacterAbility6(ContentValues values, int ability) {
        values.put(characterAbility6Label(), ability);
    }

    public static float getCharacterMoney(ContentValues values) {
        if (values.get(characterMoneyLabel()) == null) return -1;
        return values.getAsFloat(characterMoneyLabel());
    }

    public static float getCharacterMoney(Context context, long rowIndex) {
        ContentValues values = getInProgressRow(context, rowIndex);
        return getCharacterMoney(values);
    }

    public static void setCharacterMoney(ContentValues values, float money) {
        values.put(characterMoneyLabel(), money);
    }

    public static void setCharacterMoneyAndUpdateTable(Context context, long charId, float money) {
        ContentValues values = getInProgressRow(context, charId);
        values.put(characterMoneyLabel(), money);
        updateInProgressTable(context, values, charId);
    }

    public static float getCharacterLightLoad(ContentValues values) {
        return values.getAsFloat(characterLightLoadLabel());
    }

    public static void setCharacterLightLoad(ContentValues values, int lightLoad) {
        values.put(characterLightLoadLabel(), lightLoad);
    }

    public static float getCharacterMediumLoad(ContentValues values) {
        return values.getAsFloat(characterMediumLoadLabel());
    }

    public static void setCharacterMediumLoad(ContentValues values, int medLoad) {
        values.put(characterMediumLoadLabel(), medLoad);
    }

    public static float getCharacterHeavyLoad(ContentValues values) {
        return values.getAsFloat(characterHeavyLoadLabel());
    }

    public static void setCharacterHeavyLoad(ContentValues values, int heavyLoad) {
        values.put(characterHeavyLoadLabel(), heavyLoad);
    }

    public static int getCharacterAC(ContentValues values) {
        return values.getAsInteger(characterACLabel());
    }

    public static void setCharacterAC(ContentValues values, int ac) {
        values.put(characterACLabel(), ac);
    }

    public static int getCharacterHP(ContentValues values) {
        return values.getAsInteger(characterHPLabel());
    }

    public static void setCharacterHP(ContentValues values, int hp) {
        values.put(characterHPLabel(), hp);
    }

    public static long getCharacterFamiliar(ContentValues values) {
        return values.getAsLong(characterFamiliarLabel());
    }

    public static long getCharacterFamiliar(Context context, long rowIndex) {
        ContentValues values = getInProgressRow(context, rowIndex);
        return getCharacterFamiliar(values);
    }

    public static void setCharacterFamiliar(ContentValues values, long familiar) {
        values.put(characterFamiliarLabel(), familiar);
    }

    /* DATABASE FUNCTIONS */

    private static boolean isOptionSelected(ContentValues values, String column) {
        int inProgressValue = values.getAsInteger(column);
        return (inProgressValue > 0);
    }

    private static boolean isIntegerSet(int value) {
        return (value != -1);
    }

    private static boolean isStringSet(ContentValues values, String column) {
        String inProgressValue = values.getAsString(column);
        return (!inProgressValue.equals(""));
    }

    private static boolean isLongSet(long value) {
        return (value != -1);
    }

    /*
     *      CHARACTER util functions
     */

    public static Cursor getInProgressCharacterList(Context context) {
        Cursor cursor;

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
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

    public static void removeAllInProgressStats(Context context, long rowIndex) {

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            db.delete(getTableName(), characterIdLabel() + " = ?", new String[]{Long.toString(rowIndex)});
        } finally {
            db.close();
        }
    }

    public static ContentValues getInProgressRow(Context context, long rowIndex) {
        ContentValues values = null;

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ?";
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

    public static void updateInProgressTable(Context context, ContentValues values, long index) {
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName() + " WHERE "
                    + characterIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(index)});
            try {
                cursor.moveToFirst();
                db.update(getTableName(), values, characterIdLabel() + " = ?",
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
            index = db.insert(getTableName(), null, values);
        } finally {
            db.close();
        }
        return index;
    }

    //TODO implement option to delete characters
    public static void deleteValuesFromInProgressTable(Context context, long index) {
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String id = characterIdLabel();

        try {
            db.delete(getTableName(), id + " = ?", new String[]{Long.toString(index)});
        } finally {
            db.close();
        }
    }

    public static ContentValues setNewInProgressContentValues() {
        rand = new Random();

        ContentValues characterValues = new ContentValues();
        setCharacterName(characterValues, "");
        setCharacterGender(characterValues, CharacterContract.GENDER_MALE);
        setCharacterClass(characterValues, 0);
        setCharacterRace(characterValues, 0);
        setCharacterAlign(characterValues, 0);
        setCharacterFamiliar(characterValues, -1);

        setCharacterStr(characterValues, -1);
        setCharacterDex(characterValues, -1);
        setCharacterCon(characterValues, -1);
        setCharacterInt(characterValues, -1);
        setCharacterWis(characterValues, -1);
        setCharacterCha(characterValues, -1);

        setCharacterAbility1(characterValues, generateAbilityScore(rand));
        setCharacterAbility2(characterValues, generateAbilityScore(rand));
        setCharacterAbility3(characterValues, generateAbilityScore(rand));
        setCharacterAbility4(characterValues, generateAbilityScore(rand));
        setCharacterAbility5(characterValues, generateAbilityScore(rand));
        setCharacterAbility6(characterValues, generateAbilityScore(rand));

        return characterValues;
    }

    public static void setNewAbilityChoices(Context context, long rowIndex) {

        ContentValues characterValues = getInProgressRow(context, rowIndex);

        setCharacterStr(characterValues, -1);
        setCharacterDex(characterValues, -1);
        setCharacterCon(characterValues, -1);
        setCharacterInt(characterValues, -1);
        setCharacterWis(characterValues, -1);
        setCharacterCha(characterValues, -1);

        setCharacterAbility1(characterValues, generateAbilityScore(rand));
        setCharacterAbility2(characterValues, generateAbilityScore(rand));
        setCharacterAbility3(characterValues, generateAbilityScore(rand));
        setCharacterAbility4(characterValues, generateAbilityScore(rand));
        setCharacterAbility5(characterValues, generateAbilityScore(rand));
        setCharacterAbility6(characterValues, generateAbilityScore(rand));

        updateInProgressTable(context, characterValues, rowIndex);

    }

    public static void updateClassValues(Context context, long rowIndex, int classSelection) {
        ContentValues values = getInProgressRow(context, rowIndex);
        long previousClass = getCharacterClass(values);

        float prevMoneyDiff = 0;
        ContentValues prevClassValues = getClassStats(context, previousClass);
        if (prevClassValues != null) {
            float prevMaxMoney = RulesClassesUtils.getClassStartingGold(prevClassValues);
            float prevMoney = getCharacterMoney(values);
            prevMoneyDiff = prevMaxMoney - prevMoney;
        }

        ContentValues currClassValues = getClassStats(context, classSelection);
        float currMoney = 0;
        if (currClassValues != null) {
            float currMaxMoney = RulesClassesUtils.getClassStartingGold(currClassValues);
            currMoney = currMaxMoney - prevMoneyDiff;
        }

        setCharacterMoney(values, currMoney);

        updateInProgressTable(context, values, rowIndex);
    }

    /*
     *      FAMILIAR util functions
     */

    public static boolean isFamiliarSameAsSelected(Context context, long rowIndex, long familiarId) {
        long chosenFamiliar = getCharacterFamiliar(context, rowIndex);

        if (familiarId == chosenFamiliar) {
            return true;
        }
        return false;
    }

    public static void changeFamiliarSelection(Context context, long rowIndex, long familiarId) {
        ContentValues values = getInProgressRow(context, rowIndex);
        setCharacterFamiliar(values, familiarId);
        updateInProgressTable(context, values, rowIndex);
    }

    public static int getTotalFamiliarToSelect(ContentValues inProgressCharacter) {
        int classId = getCharacterClass(inProgressCharacter);
        if (classId == CLASS_WIZARD) {
            return 1;
        }
        return 0;
    }

    public static int getNumberFamiliarsSelected(Context context, long rowIndex) {
        ContentValues values = getInProgressRow(context, rowIndex);
        long familiarId = InProgressCharacterUtil.getCharacterFamiliar(values);
        if (familiarId != -1) return 1;
        return 0;
    }


    /*
     *      ABILITY util functions
     */

    private static int getAbilityScoreModifier(Context context, ContentValues values, int abilityChoice) {
        int modifier = 0;

        int raceId = InProgressCharacterUtil.getCharacterRace(values);
        if (raceId != 0) {
            ContentValues raceValues = getRaceStats(context, raceId);

            switch(abilityChoice) {
                case ABILITYSTR:
                    modifier = RulesRacesUtils.getRaceStrMod(raceValues);
                    break;
                case ABILITYDEX:
                    modifier = RulesRacesUtils.getRaceDexMod(raceValues);
                    break;
                case ABILITYCON:
                    modifier = RulesRacesUtils.getRaceConMod(raceValues);
                    break;
                case ABILITYINT:
                    modifier = RulesRacesUtils.getRaceIntMod(raceValues);
                    break;
                case ABILITYWIS:
                    modifier = RulesRacesUtils.getRaceWisMod(raceValues);
                    break;
                case ABILITYCHA:
                    modifier = RulesRacesUtils.getRaceChaMod(raceValues);
                    break;
            }
        }

        return modifier;
    }

    public static int getTotalStrengthScore(Context context, ContentValues values) {
        int score = InProgressCharacterUtil.getCharacterStr(values);
        if (score == -1) return score;
        score += getAbilityScoreModifier(context, values, ABILITYSTR);
        return score;
    }

    public static int getTotalDexterityScore(Context context, ContentValues values) {
        int score = InProgressCharacterUtil.getCharacterDex(values);
        if (score == -1) return score;
        score += getAbilityScoreModifier(context, values, ABILITYDEX);
        return score;
    }

    public static int getTotalConstitutionScore(Context context, ContentValues values) {
        int score = InProgressCharacterUtil.getCharacterCon(values);
        if (score == -1) return score;
        score += getAbilityScoreModifier(context, values, ABILITYCON);
        return score;
    }

    public static int getTotalIntelligenceScore(Context context, ContentValues values) {
        int score = InProgressCharacterUtil.getCharacterInt(values);
        if (score == -1) return score;
        score += getAbilityScoreModifier(context, values, ABILITYINT);
        return score;
    }

    public static int getTotalWisdomScore(Context context, ContentValues values) {
        int score = InProgressCharacterUtil.getCharacterWis(values);
        if (score == -1) return score;
        score += getAbilityScoreModifier(context, values, ABILITYWIS);
        return score;
    }

    public static int getTotalCharismaScore(Context context, ContentValues values) {
        int score = InProgressCharacterUtil.getCharacterCha(values);
        if (score == -1) return score;
        score += getAbilityScoreModifier(context, values, ABILITYCHA);
        return score;
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
