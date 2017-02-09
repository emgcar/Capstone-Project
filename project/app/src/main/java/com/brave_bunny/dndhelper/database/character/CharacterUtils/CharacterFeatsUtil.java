package com.brave_bunny.dndhelper.database.character.CharacterUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.character.CharacterDbHelper;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressFeatsUtil;

/**
 * Handles all of the selected feats for created characters.
 */

public class CharacterFeatsUtil {

    /* LABELS */

    private static String getTableName() {
        return CharacterContract.CharacterFeats.TABLE_NAME;
    }

    private static String characterIdLabel() {
        return CharacterContract.CharacterFeats.COLUMN_CHARACTER_ID;
    }

    private static String featIdLabel() {
        return CharacterContract.CharacterFeats.COLUMN_FEAT_ID;
    }

    /* PARSE VALUES*/

    public static long getCharacterId(ContentValues values) {
        return values.getAsLong(characterIdLabel());
    }

    public static void setCharacterId(ContentValues values, long charId) {
        values.put(characterIdLabel(), charId);
    }

    public static long getFeatId(ContentValues values) {
        return values.getAsLong(featIdLabel());
    }

    public static void setFeatId(ContentValues values, long featId) {
        values.put(featIdLabel(), featId);
    }

    /* DATABASE FUNCTIONS */

    public static void transferFeats(Context context, long inProgressIndex, long characterIndex) {
        ContentValues[] allFeats = InProgressFeatsUtil.getAllFeatsForCharacter(context, inProgressIndex);
        int numSpells = allFeats.length;

        for (int i = 0; i < numSpells; i++) {
            ContentValues newValue = new ContentValues();
            newValue.put(characterIdLabel(), characterIndex);

            long spellIndex = InProgressFeatsUtil.getFeatId(allFeats[i]);
            newValue.put(featIdLabel(), spellIndex);

            insertFeatIntoCharacterTable(context, newValue);
        }
    }

    public static void insertFeatIntoCharacterTable(Context context, ContentValues values) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(getTableName(), null, values);
        } finally {
            db.close();
        }
    }
}
