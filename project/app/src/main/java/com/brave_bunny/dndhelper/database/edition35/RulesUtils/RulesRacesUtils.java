package com.brave_bunny.dndhelper.database.edition35.RulesUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;

/**
 * Created by Jemma on 2/3/2017.
 */

public class RulesRacesUtils {

    public final static int RACE_HUMAN = 1;
    public final static int RACE_DWARF = 2;
    public final static int RACE_ELF = 3;

    private static final String[] RACE_COLUMNS = {
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
