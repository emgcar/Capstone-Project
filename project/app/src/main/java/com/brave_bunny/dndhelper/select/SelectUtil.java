package com.brave_bunny.dndhelper.select;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.CharacterContract;
import com.brave_bunny.dndhelper.database.CharacterDbHelper;

/**
 * Created by Jemma on 1/11/2017.
 */

public class SelectUtil {

    public Cursor getCharacterAndInProgressCursor(Context context) {
        Cursor results;

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM (SELECT " + CharacterContract.CharacterEntry._ID
                    + ", " + CharacterContract.CharacterEntry.COLUMN_NAME + ", 1 AS FILTER FROM "
                    + CharacterContract.CharacterEntry.TABLE_NAME
                    + " UNION ALL SELECT " + CharacterContract.InProgressEntry._ID + ", "
                    + CharacterContract.InProgressEntry.COLUMN_NAME + ", 2 AS FILTER FROM "
                    + CharacterContract.InProgressEntry.TABLE_NAME
                    + " ORDER BY " + CharacterContract.CharacterEntry.COLUMN_NAME + " ASC, "
                    + CharacterContract.InProgressEntry.COLUMN_NAME + " ASC"
                    + ") ORDER BY FILTER";
            results = db.rawQuery(query, null);
        } finally {
            db.close();
        }
        return results;
    }
}
