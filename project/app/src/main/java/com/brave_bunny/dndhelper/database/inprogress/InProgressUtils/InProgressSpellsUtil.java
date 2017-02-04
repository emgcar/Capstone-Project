package com.brave_bunny.dndhelper.database.inprogress.InProgressUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;

import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSpellsUtils.numberSpellsUntilLevelOne;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.COL_CHARACTER_NAME;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.INPROGRESS_COLUMNS;

/**
 * Created by Jemma on 2/3/2017.
 */

public class InProgressSpellsUtil {

    private static final String[] SPELL_COLUMNS = {
            InProgressContract.SpellEntry.TABLE_NAME + "." + InProgressContract.SpellEntry._ID,
            InProgressContract.SpellEntry.COLUMN_CHARACTER_ID,
            InProgressContract.SpellEntry.COLUMN_SPELL_ID
    };

    public static final int COL_INPROGRESS_SPELL_ITEM = 0;
    public static final int COL_INPROGRESS_SPELL_CHARACTER_ID = 1;
    public static final int COL_INPROGRESS_SPELL_SPELL_ID = 2;

    private static final String tableName = InProgressContract.SpellEntry.TABLE_NAME;

    public static void removeAllInProgressSpells(Context context, long rowIndex) {
        String query = SPELL_COLUMNS[COL_INPROGRESS_SPELL_CHARACTER_ID] + " = ?";
        String[] selectionArgs = new String[]{Long.toString(rowIndex)};
        deleteFromTable(context, query, selectionArgs);
    }

    public static void removeSpellSelection(Context context, long rowIndex, int domainId) {
        String query = SPELL_COLUMNS[COL_INPROGRESS_SPELL_CHARACTER_ID] + " = ? AND " +
                SPELL_COLUMNS[COL_INPROGRESS_SPELL_SPELL_ID] + " = ?";
        String[] selectionArgs = new String[]{Long.toString(rowIndex), Long.toString(domainId)};
        deleteFromTable(context, query, selectionArgs);
    }

    public static void deleteFromTable(Context context, String query, String[] selectionArgs) {
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            db.delete(tableName, query, selectionArgs);
        } finally {
            db.close();
        }
    }

    public static void addSpellSelection(Context context, long rowIndex, int spellId) {
        spellId += numberSpellsUntilLevelOne;
        ContentValues values = new ContentValues();
        values.put(SPELL_COLUMNS[COL_INPROGRESS_SPELL_CHARACTER_ID], rowIndex);
        values.put(SPELL_COLUMNS[COL_INPROGRESS_SPELL_SPELL_ID], spellId);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(tableName, null, values);
        } finally {
            db.close();
        }
    }

    public static boolean isSpellSelected(Context context, long rowIndex, long spellId) {
        spellId += numberSpellsUntilLevelOne;
        boolean isSelected = false;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName
                    + " WHERE " + SPELL_COLUMNS[COL_INPROGRESS_SPELL_CHARACTER_ID] + " = ? AND " +
                    SPELL_COLUMNS[COL_INPROGRESS_SPELL_SPELL_ID] + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(spellId)});
            if (cursor.getCount() > 0) {
                isSelected = true;
            }
            cursor.close();
        } finally {
            db.close();
        }
        return isSelected;
    }

    public static int getNumberSpellSelected(Context context, long rowIndex) {
        int numSpells = 0;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName
                    + " WHERE " + SPELL_COLUMNS[COL_INPROGRESS_SPELL_CHARACTER_ID] + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            numSpells = cursor.getCount();
            cursor.close();
        } finally {
            db.close();
        }
        return numSpells;
    }

    public static int numberSpellsSelected(Context context, ContentValues values) {
        long rowIndex = values.getAsLong(INPROGRESS_COLUMNS[COL_CHARACTER_NAME]);
        int numberSpells;

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName
                    + " WHERE " + SPELL_COLUMNS[COL_INPROGRESS_SPELL_CHARACTER_ID] + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            numberSpells = cursor.getCount();
            cursor.close();
        } finally {
            db.close();
        }

        return numberSpells;
    }
}
