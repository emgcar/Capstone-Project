package com.brave_bunny.dndhelper.database.edition35.RulesUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;

/**
 *  This file provides functions to help with retrieving data from the 3.5 Edition rules,
 *  specifically the general class data, the cleric data, the fighter data, the rogue data,
 *  and the wizard data.
 */

public class RulesClassesUtils {
    public final static int CLASS_CLERIC = 1;
    public final static int CLASS_FIGHTER = 2;
    public final static int CLASS_ROGUE = 3;
    public final static int CLASS_WIZARD = 4;

    public static final String[] CLASS_COLUMNS = {
            RulesContract.ClassEntry.TABLE_NAME + "." + RulesContract.ClassEntry._ID,
            RulesContract.ClassEntry.COLUMN_NAME,
            RulesContract.ClassEntry.COLUMN_HIT_DIE,
            RulesContract.ClassEntry.COLUMN_STARTING_GOLD
    };

    public static final int COL_CLASS_ID = 0;
    public static final int COL_CLASS_NAME = 1;
    public static final int COL_CLASS_HIT_DIE = 2;
    public static final int COL_CLASS_STARTING_GOLD = 3;


    private static final String[] CLERIC_STAT_COLUMNS = {
            RulesContract.ClericEntry.TABLE_NAME + "." + RulesContract.ClericEntry._ID,
            RulesContract.ClericEntry.COLUMN_BASE_ATTACK_1,
            RulesContract.ClericEntry.COLUMN_BASE_ATTACK_2,
            RulesContract.ClericEntry.COLUMN_BASE_ATTACK_3,
            RulesContract.ClericEntry.COLUMN_FORT,
            RulesContract.ClericEntry.COLUMN_REF,
            RulesContract.ClericEntry.COLUMN_WILL,
            RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L0,
            RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L1,
            RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L2,
            RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L3,
            RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L4,
            RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L5,
            RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L6,
            RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L7,
            RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L8,
            RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L9
    };

    public static final int COL_CLERIC_ID = 0;
    public static final int COL_CLERIC_BASE_1 = 1;
    public static final int COL_CLERIC_BASE_2 = 2;
    public static final int COL_CLERIC_BASE_3 = 3;
    public static final int COL_CLERIC_FORT = 4;
    public static final int COL_CLERIC_REF = 5;
    public static final int COL_CLERIC_WILL = 6;
    public static final int COL_CLERIC_SPELL_L0 = 7;
    public static final int COL_CLERIC_SPELL_L1 = 8;
    public static final int COL_CLERIC_SPELL_L2 = 9;
    public static final int COL_CLERIC_SPELL_L3 = 10;
    public static final int COL_CLERIC_SPELL_L4 = 11;
    public static final int COL_CLERIC_SPELL_L5 = 12;
    public static final int COL_CLERIC_SPELL_L6 = 13;
    public static final int COL_CLERIC_SPELL_L7 = 14;
    public static final int COL_CLERIC_SPELL_L8 = 15;
    public static final int COL_CLERIC_SPELL_L9 = 16;

    private static final String[] FIGHTER_STAT_COLUMNS = {
            RulesContract.FighterEntry.TABLE_NAME + "." + RulesContract.FighterEntry._ID,
            RulesContract.FighterEntry.COLUMN_BASE_ATTACK_1,
            RulesContract.FighterEntry.COLUMN_BASE_ATTACK_2,
            RulesContract.FighterEntry.COLUMN_BASE_ATTACK_3,
            RulesContract.FighterEntry.COLUMN_BASE_ATTACK_4,
            RulesContract.FighterEntry.COLUMN_FORT,
            RulesContract.FighterEntry.COLUMN_REF,
            RulesContract.FighterEntry.COLUMN_WILL
    };

    public static final int COL_FIGHTER_ID = 0;
    public static final int COL_FIGHTER_BASE_1 = 1;
    public static final int COL_FIGHTER_BASE_2 = 2;
    public static final int COL_FIGHTER_BASE_3 = 3;
    public static final int COL_FIGHTER_BASE_4 = 4;
    public static final int COL_FIGHTER_FORT = 5;
    public static final int COL_FIGHTER_REF = 6;
    public static final int COL_FIGHTER_WILL = 7;

    private static final String[] ROGUE_STAT_COLUMNS = {
            RulesContract.RogueEntry.TABLE_NAME + "." + RulesContract.RogueEntry._ID,
            RulesContract.RogueEntry.COLUMN_BASE_ATTACK_1,
            RulesContract.RogueEntry.COLUMN_BASE_ATTACK_2,
            RulesContract.RogueEntry.COLUMN_BASE_ATTACK_3,
            RulesContract.RogueEntry.COLUMN_FORT,
            RulesContract.RogueEntry.COLUMN_REF,
            RulesContract.RogueEntry.COLUMN_WILL
    };

    public static final int COL_ROGUE_ID = 0;
    public static final int COL_ROGUE_BASE_1 = 1;
    public static final int COL_ROGUE_BASE_2 = 2;
    public static final int COL_ROGUE_BASE_3 = 3;
    public static final int COL_ROGUE_FORT = 4;
    public static final int COL_ROGUE_REF = 5;
    public static final int COL_ROGUE_WILL = 6;

    private static final String[] WIZARD_STAT_COLUMNS = {
            RulesContract.WizardEntry.TABLE_NAME + "." + RulesContract.WizardEntry._ID,
            RulesContract.WizardEntry.COLUMN_BASE_ATTACK_1,
            RulesContract.WizardEntry.COLUMN_BASE_ATTACK_2,
            RulesContract.WizardEntry.COLUMN_FORT,
            RulesContract.WizardEntry.COLUMN_REF,
            RulesContract.WizardEntry.COLUMN_WILL,
            RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L0,
            RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L1,
            RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L2,
            RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L3,
            RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L4,
            RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L5,
            RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L6,
            RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L7,
            RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L8,
            RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L9
    };

    public static final int COL_WIZARD_ID = 0;
    public static final int COL_WIZARD_BASE_1 = 1;
    public static final int COL_WIZARD_BASE_2 = 2;
    public static final int COL_WIZARD_BASE_3 = 3;
    public static final int COL_WIZARD_FORT = 4;
    public static final int COL_WIZARD_REF = 5;
    public static final int COL_WIZARD_WILL = 6;
    public static final int COL_WIZARD_SPELL_L0 = 7;
    public static final int COL_WIZARD_SPELL_L1 = 8;
    public static final int COL_WIZARD_SPELL_L2 = 9;
    public static final int COL_WIZARD_SPELL_L3 = 10;
    public static final int COL_WIZARD_SPELL_L4 = 11;
    public static final int COL_WIZARD_SPELL_L5 = 12;
    public static final int COL_WIZARD_SPELL_L6 = 13;
    public static final int COL_WIZARD_SPELL_L7 = 14;
    public static final int COL_WIZARD_SPELL_L8 = 15;
    public static final int COL_WIZARD_SPELL_L9 = 16;

    private static final String classTable = RulesContract.ClassEntry.TABLE_NAME;
    private static final String clericTable = RulesContract.ClericEntry.TABLE_NAME;
    private static final String fighterTable = RulesContract.FighterEntry.TABLE_NAME;
    private static final String rogueTable = RulesContract.RogueEntry.TABLE_NAME;
    private static final String wizardTable = RulesContract.WizardEntry.TABLE_NAME;


    public static ContentValues getClassStats(Context context, long selectedClass) {
        String query = "SELECT * FROM " + classTable + " WHERE " +
                CLASS_COLUMNS[COL_CLASS_ID] + " = ?";
        return getStats(context, query, selectedClass);
    }

    private static ContentValues getClericStatsByLevel(Context context, int level) {
        String query = "SELECT * FROM " + clericTable + " WHERE " +
                CLERIC_STAT_COLUMNS[COL_CLERIC_ID] + " = ?";
        return getStats(context, query, level);
    }

    private static ContentValues getFighterStatsByLevel(Context context, int level) {
        String query = "SELECT * FROM " + fighterTable + " WHERE " +
                FIGHTER_STAT_COLUMNS[COL_FIGHTER_ID] + " = ?";
        return getStats(context, query, level);
    }

    private static ContentValues getRogueStatsByLevel(Context context, int level) {
        String query = "SELECT * FROM " + rogueTable + " WHERE " +
                ROGUE_STAT_COLUMNS[COL_ROGUE_ID] + " = ?";
        return getStats(context, query, level);
    }

    private static ContentValues getWizardStatsByLevel(Context context, int level) {
        String query = "SELECT * FROM " + wizardTable + " WHERE " +
                WIZARD_STAT_COLUMNS[COL_WIZARD_ID] + " = ?";
        return getStats(context, query, level);
    }

    public static ContentValues getFirstLevelStats(Context context, int selectedClass) {
        switch (selectedClass) {
            case CLASS_CLERIC:
                return getClericStatsByLevel(context, 1);
            case CLASS_FIGHTER:
                return getFighterStatsByLevel(context, 1);
            case CLASS_ROGUE:
                return getRogueStatsByLevel(context, 1);
            case CLASS_WIZARD:
                return getWizardStatsByLevel(context, 1);
        }
        return null;
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
}
