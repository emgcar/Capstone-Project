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

public class RulesDomainsUtils {

    private static final int TRUE = 1;
    private static final int FALSE = 0;

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

    private static final String tableName = RulesContract.ClericDomainsEntry.TABLE_NAME;

    private static ContentValues getDomainByAlignment(Context context, int alignment) {
        String query = "SELECT * FROM " + tableName + " WHERE " + CLERIC_DOMAIN_COLUMNS[alignment] + " = ?";
        return getStats(context, query, TRUE);
    }

    public static ContentValues getDomain(Context context, long domainId) {
        String query = "SELECT * FROM " + tableName + " WHERE " + CLERIC_DOMAIN_COLUMNS[COL_DOMAIN_ID] + " = ?";
        return getStats(context, query, domainId);
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
