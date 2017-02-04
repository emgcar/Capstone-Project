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

public class RulesFeatsUtils {

    private static final int TRUE = 1;
    private static final int FALSE = 0;

    private static final String[] FEATS_COLUMNS = {
            RulesContract.FeatEntry.TABLE_NAME + "." + RulesContract.FeatEntry._ID,
            RulesContract.FeatEntry.COLUMN_NAME
    };

    public static final int COL_FEAT_ID = 0;
    public static final int COL_FEAT_NAME = 1;

    private static final String tableName = RulesContract.FeatEntry.TABLE_NAME;

    private static ContentValues getFeat(Context context, int index) {
        String query = "SELECT * FROM " + tableName + " WHERE " + FEATS_COLUMNS[COL_FEAT_ID] + " = ?";
        return getStats(context, query, index);
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
