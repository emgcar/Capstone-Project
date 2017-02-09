package com.brave_bunny.dndhelper.database.character.CharacterUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.character.CharacterDbHelper;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSkillsUtil;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;

/**
 * Created by Jemma on 2/3/2017.
 */

public class CharacterSpellsUtil {

    private static final String[] CHARACTER_SPELL_COLUMNS = {
            CharacterContract.CharacterSpells.TABLE_NAME + "." + CharacterContract.CharacterSpells._ID,
            CharacterContract.CharacterSpells.COLUMN_CHARACTER_ID,
            CharacterContract.CharacterSpells.COLUMN_SPELL_ID
    };

    private static final int COL_CHARACTER_SPELL_ITEM = 0;
    private static final int COL_CHARACTER_SPELL_CHARACTER_ID = 1;
    private static final int COL_CHARACTER_SPELL_SPELL_ID = 2;

    private static final String tableName = CharacterContract.CharacterSpells.TABLE_NAME;

    public static void transferSpells(Context context, long inProgressIndex, long characterIndex) {

        ContentValues[] allSpells = InProgressSpellsUtil.getAllSpellsForCharacter(context, inProgressIndex);
        int numSpells = allSpells.length;

        for (int i = 0; i < numSpells; i++) {
            ContentValues newValue = new ContentValues();
            newValue.put(CHARACTER_SPELL_COLUMNS[COL_CHARACTER_SPELL_CHARACTER_ID], characterIndex);

            long spellIndex = InProgressSpellsUtil.getSpellId(allSpells[i]);
            newValue.put(CHARACTER_SPELL_COLUMNS[COL_CHARACTER_SPELL_SPELL_ID], spellIndex);

            insertSpellIntoCharacterTable(context, newValue);
        }
    }

    public static void insertSpellIntoCharacterTable(Context context, ContentValues values) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(tableName, null, values);
        } finally {
            db.close();
        }
    }

    public static String getTableName() {
        return tableName;
    }

    public static long getCharacterId(ContentValues values) {
        return values.getAsLong(CHARACTER_SPELL_COLUMNS[COL_CHARACTER_SPELL_CHARACTER_ID]);
    }

    public static void setCharacterId(ContentValues values, long charId) {
        values.put(CHARACTER_SPELL_COLUMNS[COL_CHARACTER_SPELL_CHARACTER_ID], charId);
    }

    public static long getSpellId(Cursor cursor) {
        return cursor.getLong(COL_CHARACTER_SPELL_SPELL_ID);
    }

    public static long getSpellId(ContentValues values) {
        return values.getAsLong(CHARACTER_SPELL_COLUMNS[COL_CHARACTER_SPELL_SPELL_ID]);
    }

    public static void setSpellId(ContentValues values, long spellId) {
        values.put(CHARACTER_SPELL_COLUMNS[COL_CHARACTER_SPELL_SPELL_ID], spellId);
    }
}
