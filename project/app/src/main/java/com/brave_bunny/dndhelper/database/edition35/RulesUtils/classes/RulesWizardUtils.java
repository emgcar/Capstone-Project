package com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;

/**
 * Handles all of the wizard data.
 */

public class RulesWizardUtils {

    /* LABELS */

    private static String getTableName() {
        return RulesContract.WizardEntry.TABLE_NAME;
    }

    private static String wizardLevelLabel() {
        return RulesContract.WizardEntry._ID;
    }

    private static String wizardBaseAttack1Label() {
        return RulesContract.WizardEntry.COLUMN_BASE_ATTACK_1;
    }

    private static String wizardBaseAttack2Label() {
        return RulesContract.WizardEntry.COLUMN_BASE_ATTACK_2;
    }

    private static String wizardFortLabel() {
        return RulesContract.WizardEntry.COLUMN_FORT;
    }

    private static String wizardRefLabel() {
        return RulesContract.WizardEntry.COLUMN_REF;
    }

    private static String wizardWillLabel() {
        return RulesContract.WizardEntry.COLUMN_WILL;
    }

    private static String wizardSpellPerDayLevel0Label() {
        return RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L0;
    }

    private static String wizardSpellPerDayLevel1Label() {
        return RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L1;
    }

    private static String wizardSpellPerDayLevel2Label() {
        return RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L2;
    }

    private static String wizardSpellPerDayLevel3Label() {
        return RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L3;
    }

    private static String wizardSpellPerDayLevel4Label() {
        return RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L4;
    }

    private static String wizardSpellPerDayLevel5Label() {
        return RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L5;
    }

    private static String wizardSpellPerDayLevel6Label() {
        return RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L6;
    }

    private static String wizardSpellPerDayLevel7Label() {
        return RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L7;
    }

    private static String wizardSpellPerDayLevel8Label() {
        return RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L8;
    }

    private static String wizardSpellPerDayLevel9Label() {
        return RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L9;
    }

    /* PARSE VALUES*/

    public static long getWizardLevel(ContentValues values) {
        return values.getAsLong(wizardLevelLabel());
    }

    public static long getWizardBaseAttack1(ContentValues values) {
        return values.getAsLong(wizardBaseAttack1Label());
    }

    public static long getWizardBaseAttack2(ContentValues values) {
        return values.getAsLong(wizardBaseAttack2Label());
    }

    public static long getWizardFort(ContentValues values) {
        return values.getAsLong(wizardFortLabel());
    }

    public static long getWizardRef(ContentValues values) {
        return values.getAsLong(wizardRefLabel());
    }

    public static long getWizardWill(ContentValues values) {
        return values.getAsLong(wizardWillLabel());
    }

    public static long getWizardSpellPerDayLevel0(ContentValues values) {
        return values.getAsLong(wizardSpellPerDayLevel0Label());
    }

    public static long getWizardSpellPerDayLevel1(ContentValues values) {
        return values.getAsLong(wizardSpellPerDayLevel1Label());
    }

    public static long getWizardSpellPerDayLevel2(ContentValues values) {
        return values.getAsLong(wizardSpellPerDayLevel2Label());
    }

    public static long getWizardSpellPerDayLevel3(ContentValues values) {
        return values.getAsLong(wizardSpellPerDayLevel3Label());
    }

    public static long getWizardSpellPerDayLevel4(ContentValues values) {
        return values.getAsLong(wizardSpellPerDayLevel4Label());
    }

    public static long getWizardSpellPerDayLevel5(ContentValues values) {
        return values.getAsLong(wizardSpellPerDayLevel5Label());
    }

    public static long getWizardSpellPerDayLevel6(ContentValues values) {
        return values.getAsLong(wizardSpellPerDayLevel6Label());
    }

    public static long getWizardSpellPerDayLevel7(ContentValues values) {
        return values.getAsLong(wizardSpellPerDayLevel7Label());
    }

    public static long getWizardSpellPerDayLevel8(ContentValues values) {
        return values.getAsLong(wizardSpellPerDayLevel8Label());
    }

    public static long getWizardSpellPerDayLevel9(ContentValues values) {
        return values.getAsLong(wizardSpellPerDayLevel9Label());
    }

    /* DATABASE FUNCTIONS */

    public static ContentValues getWizardStatsByLevel(Context context, int level) {
        String query = "SELECT * FROM " + getTableName() + " WHERE " +
                wizardLevelLabel() + " = ?";
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
