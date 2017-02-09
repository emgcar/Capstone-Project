package com.brave_bunny.dndhelper.database.character.CharacterUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.character.CharacterDbHelper;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSkillsUtil;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;

/**
 * Created by Jemma on 2/3/2017.
 */

public class CharacterSkillsUtil {

    public static final String[] SKILL_COLUMNS = {
            CharacterContract.CharacterSkills.TABLE_NAME + "." + CharacterContract.CharacterSkills._ID,
            CharacterContract.CharacterSkills.COLUMN_CHARACTER_ID,
            CharacterContract.CharacterSkills.COLUMN_SKILL_ID
    };

    public static final int COL_CHARACTER_SKILL_ITEM = 0;
    public static final int COL_CHARACTER_SKILL_CHARACTER_ID = 1;
    public static final int COL_CHARACTER_SKILL_SKILL_ID = 2;

    public static final String tableName = CharacterContract.CharacterSkills.TABLE_NAME;

    public static void transferSkills(Context context, long inProgressIndex, long characterIndex) {


        ContentValues[] allSkills = InProgressSkillsUtil.getAllSkillsForCharacter(context, inProgressIndex);
        int numSkills = allSkills.length;

        for (int i = 0; i < numSkills; i++) {
            ContentValues newValue = new ContentValues();
            newValue.put(SKILL_COLUMNS[COL_CHARACTER_SKILL_CHARACTER_ID], characterIndex);

            long skillIndex = InProgressSkillsUtil.getSkillId(allSkills[i]);
            newValue.put(SKILL_COLUMNS[COL_CHARACTER_SKILL_SKILL_ID], skillIndex);

            insertSkillIntoCharacterTable(context, newValue);
        }
    }

    //TODO add all of the extra modifiers
    public static void insertSkillIntoCharacterTable(Context context, ContentValues values) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(tableName, null, values);
        } finally {
            db.close();
        }
    }

}
