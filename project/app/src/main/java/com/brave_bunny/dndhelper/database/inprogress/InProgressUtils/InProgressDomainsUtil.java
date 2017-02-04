package com.brave_bunny.dndhelper.database.inprogress.InProgressUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;

import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.COL_CHARACTER_ID;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.INPROGRESS_COLUMNS;

/**
 * Created by Jemma on 2/3/2017.
 */

public class InProgressDomainsUtil {

    private static final String[] DOMAIN_COLUMNS = {
            InProgressContract.ClericDomainEntry.TABLE_NAME + "." + InProgressContract.ClericDomainEntry._ID,
            InProgressContract.ClericDomainEntry.COLUMN_CHARACTER_ID,
            InProgressContract.ClericDomainEntry.COLUMN_DOMAIN_ID
    };

    public static final int COL_INPROGRESS_DOMAIN_ITEM = 0;
    public static final int COL_INPROGRESS_DOMAIN_CHARACTER_ID = 1;
    public static final int COL_INPROGRESS_DOMAIN_DOMAIN_ID = 2;

    private static final String tableName = InProgressContract.ClericDomainEntry.TABLE_NAME;

    public static void removeAllInProgressDomains(Context context, long rowIndex) {
        String query = DOMAIN_COLUMNS[COL_INPROGRESS_DOMAIN_CHARACTER_ID] + " = ?";
        String[] selectionArgs = new String[]{Long.toString(rowIndex)};
        deleteFromTable(context, query, selectionArgs);
    }

    public static void removeDomainSelection(Context context, long rowIndex, int domainId) {
        String query = DOMAIN_COLUMNS[COL_INPROGRESS_DOMAIN_CHARACTER_ID] + " = ? AND " +
                DOMAIN_COLUMNS[COL_INPROGRESS_DOMAIN_DOMAIN_ID] + " = ?";
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

    public static void addDomainSelection(Context context, long rowIndex, int domainId) {
        ContentValues values = new ContentValues();
        values.put(DOMAIN_COLUMNS[COL_INPROGRESS_DOMAIN_CHARACTER_ID], rowIndex);
        values.put(DOMAIN_COLUMNS[COL_INPROGRESS_DOMAIN_DOMAIN_ID], domainId);

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(tableName, null, values);
        } finally {
            db.close();
        }
    }

    public static boolean isDomainSelected(Context context, long rowIndex, long domainId) {
        boolean isSelected = false;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName
                    + " WHERE " + DOMAIN_COLUMNS[COL_INPROGRESS_DOMAIN_CHARACTER_ID] + " = ? AND " +
                    DOMAIN_COLUMNS[COL_INPROGRESS_DOMAIN_DOMAIN_ID] + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(domainId)});
            if (cursor.getCount() > 0) {
                isSelected = true;
            }
            cursor.close();
        } finally {
            db.close();
        }
        return isSelected;
    }

    public static int getNumberDomainsSelected(Context context, long rowIndex) {
        int numDomains = 0;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName
                    + " WHERE " + DOMAIN_COLUMNS[COL_INPROGRESS_DOMAIN_CHARACTER_ID] + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            numDomains = cursor.getCount();
            cursor.close();
        } finally {
            db.close();
        }
        return numDomains;
    }

    public static int numberDomainsSelected(Context context, ContentValues values) {
        long rowIndex = values.getAsLong(INPROGRESS_COLUMNS[COL_CHARACTER_ID]);
        int numberDomains;

        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + tableName
                    + " WHERE " + DOMAIN_COLUMNS[COL_INPROGRESS_DOMAIN_CHARACTER_ID] + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            numberDomains = cursor.getCount();
            cursor.close();
        } finally {
            db.close();
        }

        return numberDomains;
    }
}
