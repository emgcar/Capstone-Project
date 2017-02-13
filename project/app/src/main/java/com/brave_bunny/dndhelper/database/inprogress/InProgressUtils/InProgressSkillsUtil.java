package com.brave_bunny.dndhelper.database.inprogress.InProgressUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSkillsUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesRacesUtils.RACE_HUMAN;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_CLERIC;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_FIGHTER;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_ROGUE;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_WIZARD;

/**
 * Handles all of the selected skills for in-progress characters.
 */

public class InProgressSkillsUtil {

    /* LABELS */

    private static String getTableName() {
        return InProgressContract.SkillEntry.TABLE_NAME;
    }

    private static String idLabel() {
        return InProgressContract.SkillEntry._ID;
    }

    private static String characterIdLabel() {
        return InProgressContract.SkillEntry.COLUMN_CHARACTER_ID;
    }

    private static String skillIdLabel() {
        return InProgressContract.SkillEntry.COLUMN_SKILL_ID;
    }

    private static String skillRanksLabel() {
        return InProgressContract.SkillEntry.COLUMN_RANKS;
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

    public static long getSkillId(ContentValues values) {
        return values.getAsLong(skillIdLabel());
    }

    public static void setSkillId(ContentValues values, long skillId) {
        values.put(skillIdLabel(), skillId);
    }

    public static int getSkillRanks(ContentValues values) {
        return values.getAsInteger(skillRanksLabel());
    }

    public static int getSkillRanks(Context context, long rowIndex, long skillId) {
        ContentValues values = getStats(context, rowIndex, skillId);
        if (values == null) return 0;
        return getSkillRanks(values);
    }

    public static void setSkillsRanks(ContentValues values, long skillRanks) {
        values.put(skillRanksLabel(), skillRanks);
    }

    /* DATABASE FUNCTIONS */

    //TODO
    public static int getTotalSkillPointsToSpend(ContentValues values) {
        int skillPoints = 0;

        // This rule can be found in Player's Handbook on pg 13.
        int race = InProgressCharacterUtil.getCharacterRace(values);
        if (race == RACE_HUMAN) {
            skillPoints += 4;
        }

        // These rules in Player's Handbook in Ch 3.
        int intScore = InProgressCharacterUtil.getCharacterInt(values);
        int intMod = RulesCharacterUtils.scoreToModifier(intScore);
        int classId = InProgressCharacterUtil.getCharacterClass(values);
        switch (classId) {
            case CLASS_CLERIC:
            case CLASS_FIGHTER:
            case CLASS_WIZARD:
                skillPoints += (2 + intMod)*4;
                break;
            case CLASS_ROGUE:
                skillPoints += (8 + intMod)*4;
                break;
        }

        if (skillPoints <= 0) return 1;

        return skillPoints;
    }

    public static void removeAllInProgressSkills(Context context, long rowIndex) {
        String query = characterIdLabel() + " = ?";
        String[] selectionArgs = new String[]{Long.toString(rowIndex)};
        deleteFromTable(context, query, selectionArgs);
    }

    public static void deleteFromTable(Context context, String query, String[] selectionArgs) {
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            db.delete(getTableName(), query, selectionArgs);
        } finally {
            db.close();
        }
    }

    public static boolean isSkillListed(Context context, long rowIndex, long skillId) {
        boolean isSelected = false;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    skillIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(skillId)});
            if (cursor.getCount() > 0) {
                isSelected = true;
            }
            cursor.close();
        } finally {
            db.close();
        }
        return isSelected;
    }

    public static void addSkillSelection(Context context, long rowIndex, long skillId, int rank) {
        ContentValues values = new ContentValues();
        values.put(characterIdLabel(), rowIndex);
        values.put(skillIdLabel(), skillId);
        values.put(skillRanksLabel(), rank);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(getTableName(), null, values);
        } finally {
            db.close();
        }
    }

    public static void updateSkillSelection(Context context, long rowIndex, long skillId, int newRank) {
        ContentValues values = new ContentValues();
        values.put(characterIdLabel(), rowIndex);
        values.put(skillIdLabel(), skillId);
        values.put(skillRanksLabel(), newRank);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    skillIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(skillId)});
            try {
                cursor.moveToFirst();
                db.update(getTableName(), values, characterIdLabel() + " = ? AND "
                        + skillIdLabel() + " = ?",
                        new String[]{Long.toString(rowIndex), Long.toString(skillId)});
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }
    }

    public static void addOrUpdateSkillSelection(Context context, long rowIndex, long skillId, int newRank) {
        if (isSkillListed(context, rowIndex, skillId)) {
            updateSkillSelection(context, rowIndex, skillId, newRank);
        } else {
            addSkillSelection(context, rowIndex, skillId, newRank);
        }
    }

    //TODO need to update for cross class
    public static int numberSkillPointsSpent(Context context, long rowIndex) {
        int skillPointsSpent = 0;

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                ContentValues values = cursorRowToContentValues(cursor);
                skillPointsSpent += getSkillRanks(values);
                cursor.moveToNext();
            }
            cursor.close();
        } finally {
            db.close();
        }

        return skillPointsSpent;
    }

    public static ContentValues getStats(Context context, long rowIndex, long armorId) {
        ContentValues values = null;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    skillIdLabel() + " = ?";
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

    public static ContentValues[] getAllSkillsForCharacter(Context context, long rowIndex) {
        ContentValues[] allSkills;

        InProgressDbHelper inProgressDbHelper = new InProgressDbHelper(context);
        SQLiteDatabase inProgressDb = inProgressDbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName() + " WHERE "
                    + characterIdLabel() + " = ?";
            Cursor cursor = inProgressDb.rawQuery(query, new String[]{Long.toString(rowIndex)});

            int numSkills = cursor.getCount();
            cursor.moveToFirst();
            allSkills = new ContentValues[numSkills];

            for (int i = 0; i < numSkills; i++) {
                allSkills[i] = cursorRowToContentValues(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        } finally {
            inProgressDb.close();
        }
        return allSkills;
    }

    public static int getSkillAbilityMod(ContentValues skillData, ContentValues inProgressData) {
        String baseScore = RulesSkillsUtils.getBaseScore(skillData);

        if (baseScore.equals("STR"))
            return InProgressCharacterUtil.getCharacterStr(inProgressData);
        if (baseScore.equals("DEX"))
            return InProgressCharacterUtil.getCharacterDex(inProgressData);
        if (baseScore.equals("CON"))
            return InProgressCharacterUtil.getCharacterCon(inProgressData);
        if (baseScore.equals("INT"))
            return InProgressCharacterUtil.getCharacterInt(inProgressData);
        if (baseScore.equals("WIS"))
            return InProgressCharacterUtil.getCharacterWis(inProgressData);
        if (baseScore.equals("CHA"))
            return InProgressCharacterUtil.getCharacterCha(inProgressData);
        return 0;
    }
}
