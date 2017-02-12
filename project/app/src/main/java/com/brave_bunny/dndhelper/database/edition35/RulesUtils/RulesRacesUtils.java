package com.brave_bunny.dndhelper.database.edition35.RulesUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;

/**
 * Handles all of the race data.
 */

public class RulesRacesUtils {

    public final static int RACE_HUMAN = 1;
    public final static int RACE_DWARF = 2;
    public final static int RACE_ELF = 3;

    public static final String[] RACE_COLUMNS = {
            RulesContract.RaceEntry.TABLE_NAME + "." + RulesContract.RaceEntry._ID,
            RulesContract.RaceEntry.COLUMN_NAME,
            RulesContract.RaceEntry.COLUMN_STR,
            RulesContract.RaceEntry.COLUMN_DEX,
            RulesContract.RaceEntry.COLUMN_CON,
            RulesContract.RaceEntry.COLUMN_INT,
            RulesContract.RaceEntry.COLUMN_WIS,
            RulesContract.RaceEntry.COLUMN_CHA
    };

    public static final int COL_RACE_ID = 0;
    public static final int COL_RACE_NAME = 1;
    public static final int COL_RACE_STR = 2;
    public static final int COL_RACE_DEX = 3;
    public static final int COL_RACE_CON = 4;
    public static final int COL_RACE_INT = 5;
    public static final int COL_RACE_WIS = 6;
    public static final int COL_RACE_CHA = 7;

    private static final String tableName = RulesContract.RaceEntry.TABLE_NAME;

    /* LABELS */

    private static String getTableName() {
        return RulesContract.RaceEntry.TABLE_NAME;
    }

    private static String raceIdLabel() {
        return RulesContract.RaceEntry._ID;
    }

    private static String raceNameLabel() {
        return RulesContract.RaceEntry.COLUMN_NAME;
    }

    private static String raceStrLabel() {
        return RulesContract.RaceEntry.COLUMN_STR;
    }

    private static String raceDexLabel() {
        return RulesContract.RaceEntry.COLUMN_DEX;
    }

    private static String raceConLabel() {
        return RulesContract.RaceEntry.COLUMN_CON;
    }

    private static String raceIntLabel() {
        return RulesContract.RaceEntry.COLUMN_INT;
    }

    private static String raceWisLabel() {
        return RulesContract.RaceEntry.COLUMN_WIS;
    }

    private static String raceChaLabel() {
        return RulesContract.RaceEntry.COLUMN_CHA;
    }

    /* PARSE VALUES*/

    public static long getRaceId(ContentValues values) {
        return values.getAsLong(raceIdLabel());
    }

    public static String getRaceName(ContentValues values) {
        return values.getAsString(raceNameLabel());
    }

    public static int getRaceStrMod(ContentValues values) {
        return values.getAsInteger(raceStrLabel());
    }

    public static int getRaceDexMod(ContentValues values) {
        return values.getAsInteger(raceDexLabel());
    }

    public static int getRaceConMod(ContentValues values) {
        return values.getAsInteger(raceConLabel());
    }

    public static int getRaceIntMod(ContentValues values) {
        return values.getAsInteger(raceIntLabel());
    }

    public static int getRaceWisMod(ContentValues values) {
        return values.getAsInteger(raceWisLabel());
    }

    public static int getRaceChaMod(ContentValues values) {
        return values.getAsInteger(raceChaLabel());
    }

    /* DATABASE FUNCTIONS */

    public static ContentValues getRaceStats(Context context, int raceId) {
        String query = "SELECT * FROM " + tableName + " WHERE " + RACE_COLUMNS[COL_RACE_ID] + " = ?";
        return getStats(context, query, raceId);
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
