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
 * Handles all of the selected skills for created characters.
 */

public class CharacterSkillsUtil {

    public static final int TRUE = 1;
    public static final int FALSE = 0;

    /* LABELS */

    private static String getTableName() {
        return CharacterContract.CharacterSkills.TABLE_NAME;
    }

    private static String idLabel() {
        return CharacterContract.CharacterSkills._ID;
    }

    private static String characterIdLabel() {
        return CharacterContract.CharacterSkills.COLUMN_CHARACTER_ID;
    }

    private static String skillIdLabel() {
        return CharacterContract.CharacterSkills.COLUMN_SKILL_ID;
    }

    private static String skillTotalModLabel() {
        return CharacterContract.CharacterSkills.COLUMN_SKILL_TOTAL_MOD;
    }

    private static String skillInClassLabel() {
        return CharacterContract.CharacterSkills.COLUMN_SKILL_CLASS;
    }

    private static String skillRanksLabel() {
        return CharacterContract.CharacterSkills.COLUMN_SKILL_RANKS;
    }

    private static String skillAbilModLabel() {
        return CharacterContract.CharacterSkills.COLUMN_SKILL_ABIL_MOD;
    }

    private static String skillMiscModLabel() {
        return CharacterContract.CharacterSkills.COLUMN_SKILL_MISC_MOD;
    }

    /* PARSE VALUES*/

    public static long getId(ContentValues values) {
        return values.getAsLong(idLabel());
    }

    public static long getCharacterId(ContentValues values) {
        return values.getAsLong(characterIdLabel());
    }

    public static void setCharacterId(ContentValues values, long charId) {
        values.put(characterIdLabel(), charId);
    }

    public static long getSkillId(ContentValues values) {
        return values.getAsLong(skillIdLabel());
    }

    public static void setSkillId(ContentValues values, long skillId) {
        values.put(skillIdLabel(), skillId);
    }

    public static int getSkillRanks(ContentValues values) {
        return values.getAsInteger(skillRanksLabel());
    }

    public static int getSkillRanks(Context context, long rowIndex, long skillId) {
        ContentValues values = getStats(context, rowIndex, skillId);
        if (values == null) return 0;
        return getSkillRanks(values);
    }

    public static void setSkillsRanks(ContentValues values, long skillRanks) {
        values.put(skillRanksLabel(), skillRanks);
    }

    public static int getSkillTotalMod(ContentValues values) {
        return values.getAsInteger(skillTotalModLabel());
    }

    public static void setSkillTotalMod(ContentValues values, int totalMod) {
        values.put(skillTotalModLabel(), totalMod);
    }

    public static boolean getSkillInClass(ContentValues values) {
        int inClass = values.getAsInteger(skillInClassLabel());
        return (inClass == TRUE);
    }

    public static void setSkillInClass(ContentValues values, boolean inClass) {
        if (inClass) {
            values.put(skillInClassLabel(), TRUE);
        } else {
            values.put(skillInClassLabel(), FALSE);
        }
    }

    public static int getSkillAbilMod(ContentValues values) {
        return values.getAsInteger(skillAbilModLabel());
    }

    public static void setSkillAbilMod(ContentValues values, int abilMod) {
        values.put(skillAbilModLabel(), abilMod);
    }

    public static int getSkillMiscMod(ContentValues values) {
        return values.getAsInteger(skillMiscModLabel());
    }

    public static void setSkillMiscMod(ContentValues values, int miscMod) {
        values.put(skillMiscModLabel(), miscMod);
    }

    /* DATABASE FUNCTIONS */

    //TODO add all of the extra modifiers
    public static void transferSkills(Context context, long inProgressIndex, long characterIndex) {


        ContentValues[] allSkills = InProgressSkillsUtil.getAllSkillsForCharacter(context, inProgressIndex);
        int numSkills = allSkills.length;

        for (int i = 0; i < numSkills; i++) {
            ContentValues newValue = new ContentValues();
            newValue.put(characterIdLabel(), characterIndex);

            long skillIndex = InProgressSkillsUtil.getSkillId(allSkills[i]);
            newValue.put(skillIdLabel(), skillIndex);

            insertSkillIntoCharacterTable(context, newValue);
        }
    }

    public static void insertSkillIntoCharacterTable(Context context, ContentValues values) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(getTableName(), null, values);
        } finally {
            db.close();
        }
    }

    public static ContentValues getStats(Context context, long rowIndex, long armorId) {
        ContentValues values = null;
        InProgressDbHelper dbHelper = new InProgressDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    skillIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(armorId)});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                values = cursorRowToContentValues(cursor);
            }
            cursor.close();
        } finally {
            db.close();
        }
        return values;
    }

}
