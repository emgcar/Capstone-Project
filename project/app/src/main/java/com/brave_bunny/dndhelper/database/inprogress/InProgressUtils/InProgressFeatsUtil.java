package com.brave_bunny.dndhelper.database.inprogress.InProgressUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;

/**
 * Created by Jemma on 2/3/2017.
 */

public class InProgressFeatsUtil {

    public static final String[] FEAT_COLUMNS = {
            InProgressContract.FeatEntry.TABLE_NAME + "." + InProgressContract.FeatEntry._ID,
            InProgressContract.FeatEntry.COLUMN_CHARACTER_ID,
            InProgressContract.FeatEntry.COLUMN_FEAT_ID
    };

    public static final int COL_INPROGRESS_FEAT_ITEM = 0;
    public static final int COL_INPROGRESS_FEAT_CHARACTER_ID = 1;
    public static final int COL_INPROGRESS_FEAT_FEAT_ID = 2;

    public static final String tableName = InProgressContract.FeatEntry.TABLE_NAME;

    public static void removeAllInProgressFeats(Context context, long rowIndex) {
        String query = FEAT_COLUMNS[COL_INPROGRESS_FEAT_CHARACTER_ID] + " = ?";
        String[] selectionArgs = new String[]{Long.toString(rowIndex)};
        deleteFromTable(context, query, selectionArgs);
    }

    public static void removeFeatSelection(Context context, long rowIndex, int domainId) {
        String query = FEAT_COLUMNS[COL_INPROGRESS_FEAT_CHARACTER_ID] + " = ? AND " +
                FEAT_COLUMNS[COL_INPROGRESS_FEAT_FEAT_ID] + " = ?";
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

    public static int getNumberFeatsSelected(Context context, long rowIndex) {
        int numDomains = 0;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName
                    + " WHERE " + FEAT_COLUMNS[COL_INPROGRESS_FEAT_CHARACTER_ID] + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            numDomains = cursor.getCount();
            cursor.close();
        } finally {
            db.close();
        }
        return numDomains;
    }

    public static boolean isFeatSelected(Context context, long rowIndex, long featId) {
        boolean isSelected = false;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName
                    + " WHERE " + FEAT_COLUMNS[COL_INPROGRESS_FEAT_CHARACTER_ID] + " = ? AND " +
                    FEAT_COLUMNS[COL_INPROGRESS_FEAT_FEAT_ID] + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(featId)});
            if (cursor.getCount() > 0) {
                isSelected = true;
            }
            cursor.close();
        } finally {
            db.close();
        }
        return isSelected;
    }

    public static void addFeatSelection(Context context, long rowIndex, int featId) {
        ContentValues values = new ContentValues();
        values.put(FEAT_COLUMNS[COL_INPROGRESS_FEAT_CHARACTER_ID], rowIndex);
        values.put(FEAT_COLUMNS[COL_INPROGRESS_FEAT_FEAT_ID], featId);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(tableName, null, values);
        } finally {
            db.close();
        }
    }
}
