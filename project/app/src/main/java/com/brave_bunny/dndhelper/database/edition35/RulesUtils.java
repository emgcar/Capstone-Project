package com.brave_bunny.dndhelper.database.edition35;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;

/**
 * Created by Jemma on 1/11/2017.
 */

public class RulesUtils {
    public final static int CLASS_CLERIC = 1;
    public final static int CLASS_FIGHTER = 2;
    public final static int CLASS_ROGUE = 3;
    public final static int CLASS_WIZARD = 4;

    private static final String[] CLERIC_STAT_COLUMNS = {
            RulesContract.ClericEntry.TABLE_NAME + "." + RulesContract.ClericEntry._ID,
            RulesContract.ClericEntry.COLUMN_LEVEL,
            RulesContract.ClericEntry.COLUMN_BASE_ATTACK_1,
            RulesContract.ClericEntry.COLUMN_BASE_ATTACK_2,
            RulesContract.ClericEntry.COLUMN_BASE_ATTACK_3,
            RulesContract.ClericEntry.COLUMN_FORT,
            RulesContract.ClericEntry.COLUMN_REF,
            RulesContract.ClericEntry.COLUMN_WILL
    };

    public static final int COL_CLERIC_ID = 0;
    public static final int COL_CLERIC_LEVEL = 1;
    public static final int COL_CLERIC_BASE_1 = 2;
    public static final int COL_CLERIC_BASE_2 = 3;
    public static final int COL_CLERIC_BASE_3 = 4;
    public static final int COL_CLERIC_FORT = 5;
    public static final int COL_CLERIC_REF = 6;
    public static final int COL_CLERIC_WILL = 7;

    public static final int ALIGN_LG = 1;
    public static final int ALIGN_LN = 2;
    public static final int ALIGN_LE = 3;
    public static final int ALIGN_NG = 4;
    public static final int ALIGN_N = 5;
    public static final int ALIGN_NE = 6;
    public static final int ALIGN_CG = 7;
    public static final int ALIGN_CN = 8;
    public static final int ALIGN_CE = 9;

    private static final String[] CLERIC_DOMAIN_COLUMNS = {
            RulesContract.ClericDomainsEntry.TABLE_NAME + "." + RulesContract.ClericEntry._ID,
            RulesContract.ClericDomainsEntry.COLUMN_NAME,
            RulesContract.ClericDomainsEntry.COLUMN_LAWFUL_GOOD,
            RulesContract.ClericDomainsEntry.COLUMN_LAWFUL_NEUTRAL,
            RulesContract.ClericDomainsEntry.COLUMN_LAWFUL_EVIL,
            RulesContract.ClericDomainsEntry.COLUMN_NEUTRAL_GOOD,
            RulesContract.ClericDomainsEntry.COLUMN_NEUTRAL,
            RulesContract.ClericDomainsEntry.COLUMN_NEUTRAL_EVIL,
            RulesContract.ClericDomainsEntry.COLUMN_CHAOTIC_GOOD,
            RulesContract.ClericDomainsEntry.COLUMN_CHAOTIC_NEUTRAL,
            RulesContract.ClericDomainsEntry.COLUMN_CHAOTIC_EVIL
    };

    public static final int COL_DOMAIN_ID = 0;
    public static final int COL_DOMAIN_NAME = 1;
    public static final int COL_DOMAIN_LAWFUL_GOOD = 2;
    public static final int COL_DOMAIN_LAWFUL_NEUTRAL = 3;
    public static final int COL_DOMAIN_LAWFUL_EVIL = 4;
    public static final int COL_DOMAIN_NEUTRAL_GOOD = 5;
    public static final int COL_DOMAIN_NEUTRAL = 6;
    public static final int COL_DOMAIN_NEUTRAL_EVIL = 7;
    public static final int COL_DOMAIN_CHAOTIC_GOOD = 8;
    public static final int COL_DOMAIN_CHAOTIC_NEUTRAL = 9;
    public static final int COL_DOMAIN_CHAOTIC_EVIL = 10;


    public static Cursor getAllSkills(Context context) {
            Cursor cursor;

            RulesDbHelper dbHelper = new RulesDbHelper(context);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            try {
                    String query = "SELECT * FROM " + RulesContract.SkillsEntry.TABLE_NAME;
                    cursor = db.rawQuery(query, null);

                    cursor.moveToFirst();
            } finally {
                    db.close();
            }

            return cursor;
    }

    public static ContentValues getFirstLevelStats(Context context, int selectedClass) {
        String tableName = getTableName(selectedClass);
        if (tableName == "") return null;
        String level = getLevel(selectedClass);

        ContentValues values;

        RulesDbHelper dbHelper = new RulesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String table = RulesContract.ClericEntry.COLUMN_LEVEL;

        try {
            String query = "SELECT * FROM " + tableName + " WHERE " + level + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{"1"});

            cursor.moveToFirst();

            values = Utility.cursorRowToContentValues(cursor);
            cursor.close();
        } finally {
            db.close();
        }

        return values;
    }

    public static String getTableName(int selectedName) {
        switch (selectedName) {
            case CLASS_CLERIC:
                return RulesContract.ClericEntry.TABLE_NAME;
            case CLASS_FIGHTER:
                return RulesContract.FighterEntry.TABLE_NAME;
            case CLASS_ROGUE:
                return RulesContract.RogueEntry.TABLE_NAME;
            case CLASS_WIZARD:
                return RulesContract.WizardEntry.TABLE_NAME;
            default:
                return "";
        }
    }

    public static String getLevel(int selectedName) {
        switch (selectedName) {
            case CLASS_CLERIC:
                return RulesContract.ClericEntry.COLUMN_LEVEL;
            case CLASS_FIGHTER:
                return RulesContract.FighterEntry.COLUMN_LEVEL;
            case CLASS_ROGUE:
                return RulesContract.RogueEntry.COLUMN_LEVEL;
            case CLASS_WIZARD:
                return RulesContract.WizardEntry.COLUMN_LEVEL;
            default:
                return "";
        }
    }

    public static ContentValues getFirstLevelClassStats(Context context, int selectedClass) {
        String tableName = RulesContract.ClassEntry.TABLE_NAME;
        if (tableName == "") return null;

        ContentValues values = null;

        RulesDbHelper dbHelper = new RulesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName + " WHERE " + RulesContract.ClassEntry._ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Integer.toString(selectedClass)});

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
}
