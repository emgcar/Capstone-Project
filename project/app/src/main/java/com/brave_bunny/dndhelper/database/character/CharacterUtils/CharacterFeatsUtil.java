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
        ContentValues[] allSpells = InProgressFeatsUtil.getAllFeatsForCharacter(context, inProgressIndex);
        int numSpells = allSpells.length;

        for (int i = 0; i < numSpells; i++) {
            ContentValues newValue = new ContentValues();
            newValue.put(FEAT_COLUMNS[COL_CHARACTER_FEAT_CHARACTER_ID], characterIndex);

            long spellIndex = InProgressFeatsUtil.getFeatId(allSpells[i]);
            newValue.put(FEAT_COLUMNS[COL_CHARACTER_FEAT_FEAT_ID], spellIndex);

            insertFeatIntoCharacterTable(context, newValue);
        }
    }

    public static void insertFeatIntoCharacterTable(Context context, ContentValues values) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(tableName, null, values);
        } finally {
            db.close();
        }
    }
}
