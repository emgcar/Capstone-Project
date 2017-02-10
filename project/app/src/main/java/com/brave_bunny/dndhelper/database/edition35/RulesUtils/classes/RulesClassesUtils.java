package com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;

import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClericUtils.getClericStatsByLevel;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesFighterUtils.getFighterStatsByLevel;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesRogueUtils.getRogueStatsByLevel;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesWizardUtils.getWizardStatsByLevel;

/**
 *  Handles all of the general class data.
 */

public class RulesClassesUtils {
    public final static int CLASS_CLERIC = 1;
    public final static int CLASS_FIGHTER = 2;
    public final static int CLASS_ROGUE = 3;
    public final static int CLASS_WIZARD = 4;

    /* LABELS */

    private static String getTableName() {
        return RulesContract.ClassEntry.TABLE_NAME;
    }

    private static String classIdLabel() {
        return RulesContract.ClassEntry._ID;
    }

    private static String classNameLabel() {
        return RulesContract.ClassEntry.COLUMN_NAME;
    }

    private static String classHitDieLabel() {
        return RulesContract.ClassEntry.COLUMN_HIT_DIE;
    }

    private static String classStartingGoldLabel() {
        return RulesContract.ClassEntry.COLUMN_STARTING_GOLD;
    }

    /* PARSE VALUES*/

    public static long getClassId(ContentValues values) {
        return values.getAsLong(classIdLabel());
    }

    public static String getClassName(ContentValues values) {
        return values.getAsString(classNameLabel());
    }

    public static int getClassHitDie(ContentValues values) {
        return values.getAsInteger(classHitDieLabel());
    }

    public static float getClassStartingGold(ContentValues values) {
        return values.getAsFloat(classStartingGoldLabel());
    }

    /* DATABASE FUNCTIONS */


    public static ContentValues getClassStats(Context context, long selectedClass) {
        String query = "SELECT * FROM " + getTableName() + " WHERE " +
                classIdLabel() + " = ?";
        return getStats(context, query, selectedClass);
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
