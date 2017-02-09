package com.brave_bunny.dndhelper.database.edition35.RulesUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;

import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesClassesUtils.CLASS_CLERIC;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesClassesUtils.CLASS_FIGHTER;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesClassesUtils.CLASS_ROGUE;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesClassesUtils.CLASS_WIZARD;

/**
 * Created by Jemma on 2/3/2017.
 */

public class RulesSkillsUtils {

    private static final String tableName = RulesContract.SkillsEntry.TABLE_NAME;

    public static final String[] SKILL_COLUMNS = {
            RulesContract.SkillsEntry.TABLE_NAME + "." + RulesContract.SkillsEntry._ID,
            RulesContract.SkillsEntry.COLUMN_NAME,
            RulesContract.SkillsEntry.COLUMN_BASE_SCORE,
            RulesContract.SkillsEntry.COLUMN_UNTRAINED,
            RulesContract.SkillsEntry.COLUMN_CLERIC,
            RulesContract.SkillsEntry.COLUMN_FIGHTER,
            RulesContract.SkillsEntry.COLUMN_ROGUE,
            RulesContract.SkillsEntry.COLUMN_WIZARD,
            RulesContract.SkillsEntry.COLUMN_ARMOR_PENALTY,
            RulesContract.SkillsEntry.COLUMN_DOUBLE_ARMOR_PENALTY
    };

    public static final int COL_SKILL_ID = 0;
    public static final int COL_SKILL_NAME = 1;
    public static final int COL_SKILL_BASE_SCORE = 2;
    public static final int COL_SKILL_UNTRAINED = 3;
    public static final int COL_SKILL_CLERIC = 4;
    public static final int COL_SKILL_FIGHTER = 5;
    public static final int COL_SKILL_ROGUE = 6;
    public static final int COL_SKILL_WIZARD = 7;
    public static final int COL_SKILL_ARMOR_PENALTY = 8;
    public static final int COL_SKILL_DOUBLE_ARMOR_PENALTY = 9;

    private static String idLabel() {
        return RulesContract.SkillsEntry._ID;
    }

    public static long getId(ContentValues values) {
        return values.getAsLong(idLabel());
    }

    public static String getSkillName(ContentValues value) {
        return value.getAsString(SKILL_COLUMNS[COL_SKILL_NAME]);
    }

    public static ContentValues getSkill(Context context, long skillId) {
        String query = "SELECT * FROM " + tableName + " WHERE " + SKILL_COLUMNS[COL_SKILL_ID] + " = ?";
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
                values = Utility.cursorRowToContentValues(cursor);
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
}
