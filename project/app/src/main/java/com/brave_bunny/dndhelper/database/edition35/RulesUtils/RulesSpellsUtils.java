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

public class RulesSpellsUtils {

    public static final int numberSpellsUntilLevelOne = 19;

    private static final String[] SPELL_COLUMNS = {
            RulesContract.SpellsEntry.TABLE_NAME + "." + RulesContract.SpellsEntry._ID,
            RulesContract.SpellsEntry.COLUMN_NAME,
            RulesContract.SpellsEntry.COLUMN_LEVEL
    };

    public static final int COL_SPELL_ID = 0;
    public static final int COL_SPELL_NAME = 1;
    public static final int COL_SPELL_LEVEL = 2;

    private static final String tableName = RulesContract.ClericDomainsEntry.TABLE_NAME;

    private static ContentValues getSpell(Context context, int spellId) {
        String query = "SELECT * FROM " + tableName + " WHERE " + SPELL_COLUMNS[COL_SPELL_ID] + " = ?";
        return getStats(context, query, spellId);
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
