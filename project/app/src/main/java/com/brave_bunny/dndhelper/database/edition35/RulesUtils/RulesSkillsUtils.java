package com.brave_bunny.dndhelper.database.edition35.RulesUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_CLERIC;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_FIGHTER;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_ROGUE;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_WIZARD;

/**
 * Handles all of the skill data.
 */

public class RulesSkillsUtils {

    public static final int TRUE = 1;
    public static final int FALSE = 0;

    /* LABELS */

    private static String getTableName() {
        return RulesContract.SkillsEntry.TABLE_NAME;
    }

    private static String skillIdLabel() {
        return RulesContract.SkillsEntry._ID;
    }

    private static String skillNameLabel() {
        return RulesContract.SkillsEntry.COLUMN_NAME;
    }

    private static String skillBaseScoreLabel() {
        return RulesContract.SkillsEntry.COLUMN_BASE_SCORE;
    }

    private static String skillUntrainedLabel() {
        return RulesContract.SkillsEntry.COLUMN_UNTRAINED;
    }

    /* PARSE VALUES*/

    public static long getSkillId(ContentValues values) {
        return values.getAsLong(skillIdLabel());
    }

    public static String getSkillName(ContentValues values) {
        return values.getAsString(skillNameLabel());
    }

    public static String getSkillBaseScore(ContentValues skillData) {
        return skillData.getAsString(skillBaseScoreLabel());
    }

    public static boolean canBeUntrained(ContentValues value) {
        return (value.getAsInteger(skillUntrainedLabel())==TRUE);
    }

    /* DATABASE FUNCTIONS */

    public static ContentValues getSkill(Context context, long skillId) {
        String query = "SELECT * FROM " + getTableName() + " WHERE " + skillIdLabel() + " = ?";
        return getStats(context, query, skillId);
    }

    private static ContentValues getStats(Context context, String query, long index) {
        ContentValues values = null;

        RulesDbHelper dbHelper = new RulesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(index)});

            cursor.moveToFirst();

            if(cursor.getCount() > 0) {
                values = cursorRowToContentValues(cursor);
            }
            cursor.close();
        } finally {
            db.close();
        }

        return values;
    }

    public static Cursor getAllSkills(Context context, int classIndex) {
        String classColumn = getSkillClassColumn(classIndex);
        if (classColumn.equals("")) return null;

        String query = "SELECT * FROM " + RulesContract.SkillsEntry.TABLE_NAME;
        return getSkillList(context, query, null);
    }

    public static ContentValues[] getAllSkills(Context context) {
        ContentValues[] values = null;

        RulesDbHelper dbHelper = new RulesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + RulesContract.SkillsEntry.TABLE_NAME;
            Cursor cursor = db.rawQuery(query, null);
            int numSkills = cursor.getCount();
            values = new ContentValues[numSkills];

            cursor.moveToFirst();
            for (int i = 0; i < numSkills; i++) {
                values[i] = cursorRowToContentValues(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        } finally {
            db.close();
        }

        return values;
    }

    public static Cursor getCrossClassSkills(Context context, int classIndex) {
        String classColumn = getSkillClassColumn(classIndex);
        if (classColumn.equals("")) return null;

        String query = "SELECT * FROM " + RulesContract.SkillsEntry.TABLE_NAME +
                " WHERE " + classColumn + " = ?";
        String[] selectionArgs = new String[]{"0"};
        return getSkillList(context, query, selectionArgs);
    }

    public static Cursor getClassSkills(Context context, int classIndex) {
        String classColumn = getSkillClassColumn(classIndex);
        if (classColumn.equals("")) return null;

        String query = "SELECT * FROM " + RulesContract.SkillsEntry.TABLE_NAME +
                " WHERE " + classColumn + " = ?";
        String[] selectionArgs = new String[]{"1"};
        return getSkillList(context, query, selectionArgs);
    }

    public static Cursor getSkillList(Context context, String query, String[] selectionArgs) {
        Cursor cursor;

        RulesDbHelper dbHelper = new RulesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            cursor = db.rawQuery(query, selectionArgs);

            cursor.moveToFirst();
        } finally {
            db.close();
        }

        return cursor;
    }

    private static String getSkillClassColumn(int classIndex) {
        String classColumn = "";
        switch (classIndex) {
            case CLASS_CLERIC:
                classColumn = RulesContract.SkillsEntry.COLUMN_CLERIC;
                break;
            case CLASS_FIGHTER:
                classColumn = RulesContract.SkillsEntry.COLUMN_FIGHTER;
                break;
            case CLASS_ROGUE:
                classColumn = RulesContract.SkillsEntry.COLUMN_ROGUE;
                break;
            case CLASS_WIZARD:
                classColumn = RulesContract.SkillsEntry.COLUMN_WIZARD;
                break;
        }
        return classColumn;
    }

    public static int maxRanksPerLevel(int level) {
        return level + 3;
    }
}
