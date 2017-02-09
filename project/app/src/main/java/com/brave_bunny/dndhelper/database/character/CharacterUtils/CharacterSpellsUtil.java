package com.brave_bunny.dndhelper.database.character.CharacterUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.character.CharacterDbHelper;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil;

/**
 * Handles all of the selected spells for in-progress characters.
 */

public class CharacterSpellsUtil {

    /* LABELS */

    private static String getTableName() {
        return CharacterContract.CharacterSpells.TABLE_NAME;
    }

    private static String characterIdLabel() {
        return CharacterContract.CharacterSpells.COLUMN_CHARACTER_ID;
    }

    private static String spellIdLabel() {
        return CharacterContract.CharacterSpells.COLUMN_SPELL_ID;
    }

    /* PARSE VALUES*/

    public static long getCharacterId(ContentValues values) {
        return values.getAsLong(characterIdLabel());
    }

    public static void setCharacterId(ContentValues values, long charId) {
        values.put(characterIdLabel(), charId);
    }

    public static long getSpellId(ContentValues values) {
        return values.getAsLong(spellIdLabel());
    }

    public static void setSpellId(ContentValues values, long weaponId) {
        values.put(spellIdLabel(), weaponId);
    }

    /* DATABASE FUNCTIONS */

    public static void transferSpells(Context context, long inProgressIndex, long characterIndex) {

        ContentValues[] allSpells = InProgressSpellsUtil.getAllSpellsForCharacter(context, inProgressIndex);
        int numSpells = allSpells.length;

        for (int i = 0; i < numSpells; i++) {
            ContentValues newValue = new ContentValues();
            newValue.put(characterIdLabel(), characterIndex);

            long spellIndex = InProgressSpellsUtil.getSpellId(allSpells[i]);
            newValue.put(spellIdLabel(), spellIndex);

            insertSpellIntoCharacterTable(context, newValue);
        }
    }

    public static void insertSpellIntoCharacterTable(Context context, ContentValues values) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(getTableName(), null, values);
        } finally {
            db.close();
        }
    }
}
