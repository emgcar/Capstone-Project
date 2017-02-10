package com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;

/**
 * Handles all of the cleric data.
 */

public class RulesClericUtils {

    /* LABELS */

    private static String getTableName() {
        return RulesContract.ClericEntry.TABLE_NAME;
    }

    private static String clericLevelLabel() {
        return RulesContract.ClericEntry._ID;
    }

    private static String clericBaseAttack1Label() {
        return RulesContract.ClericEntry.COLUMN_BASE_ATTACK_1;
    }

    private static String clericBaseAttack2Label() {
        return RulesContract.ClericEntry.COLUMN_BASE_ATTACK_2;
    }

    private static String clericBaseAttack3Label() {
        return RulesContract.ClericEntry.COLUMN_BASE_ATTACK_3;
    }

    private static String clericFortLabel() {
        return RulesContract.ClericEntry.COLUMN_FORT;
    }

    private static String clericRefLabel() {
        return RulesContract.ClericEntry.COLUMN_REF;
    }

    private static String clericWillLabel() {
        return RulesContract.ClericEntry.COLUMN_WILL;
    }

    private static String clericSpellPerDayLevel0Label() {
        return RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L0;
    }

    private static String clericSpellPerDayLevel1Label() {
        return RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L1;
    }

    private static String clericSpellPerDayLevel2Label() {
        return RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L2;
    }

    private static String clericSpellPerDayLevel3Label() {
        return RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L3;
    }

    private static String clericSpellPerDayLevel4Label() {
        return RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L4;
    }

    private static String clericSpellPerDayLevel5Label() {
        return RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L5;
    }

    private static String clericSpellPerDayLevel6Label() {
        return RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L6;
    }

    private static String clericSpellPerDayLevel7Label() {
        return RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L7;
    }

    private static String clericSpellPerDayLevel8Label() {
        return RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L8;
    }

    private static String clericSpellPerDayLevel9Label() {
        return RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L9;
    }

    /* PARSE VALUES*/

    public static long getClericLevel(ContentValues values) {
        return values.getAsLong(clericLevelLabel());
    }

    public static long getClericBaseAttack1(ContentValues values) {
        return values.getAsLong(clericBaseAttack1Label());
    }

    public static long getClericBaseAttack2(ContentValues values) {
        return values.getAsLong(clericBaseAttack2Label());
    }

    public static long getClericBaseAttack3(ContentValues values) {
        return values.getAsLong(clericBaseAttack3Label());
    }

    public static long getClericFort(ContentValues values) {
        return values.getAsLong(clericFortLabel());
    }

    public static long getClericRef(ContentValues values) {
        return values.getAsLong(clericRefLabel());
    }

    public static long getClericWill(ContentValues values) {
        return values.getAsLong(clericWillLabel());
    }

    public static long getClericSpellPerDayLevel0(ContentValues values) {
        return values.getAsLong(clericSpellPerDayLevel0Label());
    }

    public static long getClericSpellPerDayLevel1(ContentValues values) {
        return values.getAsLong(clericSpellPerDayLevel1Label());
    }

    public static long getClericSpellPerDayLevel2(ContentValues values) {
        return values.getAsLong(clericSpellPerDayLevel2Label());
    }

    public static long getClericSpellPerDayLevel3(ContentValues values) {
        return values.getAsLong(clericSpellPerDayLevel3Label());
    }

    public static long getClericSpellPerDayLevel4(ContentValues values) {
        return values.getAsLong(clericSpellPerDayLevel4Label());
    }

    public static long getClericSpellPerDayLevel5(ContentValues values) {
        return values.getAsLong(clericSpellPerDayLevel5Label());
    }

    public static long getClericSpellPerDayLevel6(ContentValues values) {
        return values.getAsLong(clericSpellPerDayLevel6Label());
    }

    public static long getClericSpellPerDayLevel7(ContentValues values) {
        return values.getAsLong(clericSpellPerDayLevel7Label());
    }

    public static long getClericSpellPerDayLevel8(ContentValues values) {
        return values.getAsLong(clericSpellPerDayLevel8Label());
    }

    public static long getClericSpellPerDayLevel9(ContentValues values) {
        return values.getAsLong(clericSpellPerDayLevel9Label());
    }

    /* DATABASE FUNCTIONS */

    public static ContentValues getClericStatsByLevel(Context context, int level) {
        String query = "SELECT * FROM " + getTableName() + " WHERE " +
                clericLevelLabel() + " = ?";
        return getStats(context, query, level);
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
