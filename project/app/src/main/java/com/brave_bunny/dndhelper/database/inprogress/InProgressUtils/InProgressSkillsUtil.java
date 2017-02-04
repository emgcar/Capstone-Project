package com.brave_bunny.dndhelper.database.inprogress.InProgressUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;

/**
 * Created by Jemma on 2/3/2017.
 */

public class InProgressSkillsUtil {

    private static final String[] SKILL_COLUMNS = {
            InProgressContract.SkillEntry.TABLE_NAME + "." + InProgressContract.SkillEntry._ID,
            InProgressContract.SkillEntry.COLUMN_CHARACTER_ID,
            InProgressContract.SkillEntry.COLUMN_SKILL_ID,
            InProgressContract.SkillEntry.COLUMN_RANKS
    };

    public static final int COL_SKILL_INPUT_ID = 0;
    public static final int COL_SKILL_CHARACTER_ID = 1;
    public static final int COL_SKILL_SKILL_ID = 2;
    public static final int COL_SKILL_RANKS = 3;

    private static final String tableName = InProgressContract.SkillEntry.TABLE_NAME;

    public static void removeAllInProgressSkills(Context context, long rowIndex) {
        String query = SKILL_COLUMNS[COL_SKILL_CHARACTER_ID] + " = ?";
        String[] selectionArgs = new String[]{Long.toString(rowIndex)};
        deleteFromTable(context, query, selectionArgs);
    }

    public static void deleteFromTable(Context context, String query, String[] selectionArgs) {
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            db.delete(tableName, query, selectionArgs);
        } finally {
            db.close();
        }
    }

    public static boolean isSkillListed(Context context, long rowIndex, long skillId) {
        boolean isSelected = false;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName
                    + " WHERE " + SKILL_COLUMNS[COL_SKILL_CHARACTER_ID] + " = ? AND " +
                    SKILL_COLUMNS[COL_SKILL_SKILL_ID] + " = ?";
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

    public static int getSkillRanks(Context context, long rowIndex, long skillId) {
        int ranks = 0;
        if (isSkillListed(context, rowIndex, skillId)) {
            InProgressDbHelper dbHelper = new InProgressDbHelper(context);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            try {
                String query = "SELECT * FROM " + tableName
                        + " WHERE " + SKILL_COLUMNS[COL_SKILL_CHARACTER_ID] + " = ? AND " +
                        SKILL_COLUMNS[COL_SKILL_SKILL_ID] + " = ?";
                Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(skillId)});
                cursor.moveToFirst();
                ranks = cursor.getInt(COL_SKILL_RANKS);
                cursor.close();
            } finally {
                db.close();
            }
        }
        return ranks;
    }

    public static void addSkillSelection(Context context, long rowIndex, long skillId, int rank) {
        ContentValues values = new ContentValues();
        values.put(SKILL_COLUMNS[COL_SKILL_CHARACTER_ID], rowIndex);
        values.put(SKILL_COLUMNS[COL_SKILL_SKILL_ID], skillId);
        values.put(SKILL_COLUMNS[COL_SKILL_RANKS], rank);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(tableName, null, values);
        } finally {
            db.close();
        }
    }

    public static void updateSkillSelection(Context context, long rowIndex, long skillId, int newRank) {
        ContentValues values = new ContentValues();
        values.put(SKILL_COLUMNS[COL_SKILL_CHARACTER_ID], rowIndex);
        values.put(SKILL_COLUMNS[COL_SKILL_SKILL_ID], skillId);
        values.put(SKILL_COLUMNS[COL_SKILL_RANKS], newRank);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String charIndex = SKILL_COLUMNS[COL_SKILL_CHARACTER_ID];

            String query = "SELECT * FROM " + tableName
                    + " WHERE " + SKILL_COLUMNS[COL_SKILL_CHARACTER_ID] + " = ? AND " +
                    SKILL_COLUMNS[COL_SKILL_SKILL_ID] + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(skillId)});
            try {
                cursor.moveToFirst();
                db.update(tableName, values, SKILL_COLUMNS[COL_SKILL_CHARACTER_ID] + " = ? AND "
                        + SKILL_COLUMNS[COL_SKILL_SKILL_ID] + " = ?",
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
            String query = "SELECT * FROM " + tableName
                    + " WHERE " + SKILL_COLUMNS[COL_SKILL_CHARACTER_ID] + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                skillPointsSpent += cursor.getInt(COL_SKILL_RANKS);
                cursor.moveToNext();
            }
            cursor.close();
        } finally {
            db.close();
        }

        return skillPointsSpent;
    }
}
