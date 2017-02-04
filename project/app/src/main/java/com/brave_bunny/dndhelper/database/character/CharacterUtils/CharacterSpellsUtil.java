package com.brave_bunny.dndhelper.database.character.CharacterUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.character.CharacterDbHelper;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil.COL_INPROGRESS_SPELL_CHARACTER_ID;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil.COL_INPROGRESS_SPELL_SPELL_ID;

/**
 * Created by Jemma on 2/3/2017.
 */

public class CharacterSpellsUtil {

    private static final String[] SPELL_COLUMNS = {
            CharacterContract.CharacterSpells.TABLE_NAME + "." + CharacterContract.CharacterSpells._ID,
            CharacterContract.CharacterSpells.COLUMN_CHARACTER_ID,
            CharacterContract.CharacterSpells.COLUMN_SPELL_ID
    };

    public static final int COL_CHARACTER_SPELL_ITEM = 0;
    public static final int COL_CHARACTER_SPELL_CHARACTER_ID = 1;
    public static final int COL_CHARACTER_SPELL_SPELL_ID = 2;

    public static final String tableName = CharacterContract.CharacterSpells.TABLE_NAME;

    public static void transferSpells(Context context, long inProgressIndex, long characterIndex) {
        InProgressDbHelper inProgressDbHelper = new InProgressDbHelper(context);
        SQLiteDatabase inProgressDb = inProgressDbHelper.getReadableDatabase();

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + InProgressSpellsUtil.tableName + " WHERE "
                    + InProgressSpellsUtil.SPELL_COLUMNS[COL_INPROGRESS_SPELL_CHARACTER_ID] + " = ?";
            Cursor cursor = inProgressDb.rawQuery(query, new String[]{Long.toString(inProgressIndex)});

            int numSpells = cursor.getCount();
            cursor.moveToFirst();

            ContentValues newValues = new ContentValues();
            newValues.put(SPELL_COLUMNS[COL_CHARACTER_SPELL_CHARACTER_ID], characterIndex);

            for (int i = 0; i < numSpells; i++) {
                ContentValues values = cursorRowToContentValues(cursor);
                long spellIndex = values.getAsLong(InProgressSpellsUtil.SPELL_COLUMNS[COL_INPROGRESS_SPELL_SPELL_ID]);
                newValues.put(SPELL_COLUMNS[COL_INPROGRESS_SPELL_SPELL_ID], spellIndex);
                insertSpellIntoCharacterTable(context, newValues, characterIndex);
                cursor.moveToNext();
            }
            cursor.close();
        } finally {
            inProgressDb.close();
        }
    }

    public static void insertSpellIntoCharacterTable(Context context, ContentValues values, long index) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(tableName, null, values);
        } finally {
            db.close();
        }
    }
}
