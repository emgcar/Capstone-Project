package com.brave_bunny.dndhelper.database.character.CharacterUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.character.CharacterDbHelper;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressFeatsUtil;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressFeatsUtil.COL_INPROGRESS_FEAT_CHARACTER_ID;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressFeatsUtil.COL_INPROGRESS_FEAT_FEAT_ID;

/**
 * Created by Jemma on 2/3/2017.
 */

public class CharacterFeatsUtil {

    public static final String[] FEAT_COLUMNS = {
            CharacterContract.CharacterFeats.TABLE_NAME + "." + CharacterContract.CharacterFeats._ID,
            CharacterContract.CharacterFeats.COLUMN_CHARACTER_ID,
            CharacterContract.CharacterFeats.COLUMN_FEAT_ID
    };

    public static final int COL_CHARACTER_FEAT_ITEM = 0;
    public static final int COL_CHARACTER_FEAT_CHARACTER_ID = 1;
    public static final int COL_CHARACTER_FEAT_FEAT_ID = 2;

    public static final String tableName = CharacterContract.CharacterFeats.TABLE_NAME;

    public static void transferFeats(Context context, long inProgressIndex, long characterIndex) {
        InProgressDbHelper inProgressDbHelper = new InProgressDbHelper(context);
        SQLiteDatabase inProgressDb = inProgressDbHelper.getReadableDatabase();

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + InProgressFeatsUtil.tableName + " WHERE "
                    + InProgressFeatsUtil.FEAT_COLUMNS[COL_INPROGRESS_FEAT_CHARACTER_ID] + " = ?";
            Cursor cursor = inProgressDb.rawQuery(query, new String[]{Long.toString(inProgressIndex)});

            int numFeats = cursor.getCount();
            cursor.moveToFirst();

            ContentValues newValues = new ContentValues();
            newValues.put(FEAT_COLUMNS[COL_CHARACTER_FEAT_CHARACTER_ID], characterIndex);

            for (int i = 0; i < numFeats; i++) {
                ContentValues values = cursorRowToContentValues(cursor);
                long featIndex = values.getAsLong(InProgressFeatsUtil.FEAT_COLUMNS[COL_INPROGRESS_FEAT_FEAT_ID]);
                newValues.put(FEAT_COLUMNS[COL_INPROGRESS_FEAT_FEAT_ID], featIndex);
                insertFeatIntoCharacterTable(context, newValues, characterIndex);
                cursor.moveToNext();
            }
            cursor.close();
        } finally {
            inProgressDb.close();
        }
    }

    public static void insertFeatIntoCharacterTable(Context context, ContentValues values, long index) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(tableName, null, values);
        } finally {
            db.close();
        }
    }
}
