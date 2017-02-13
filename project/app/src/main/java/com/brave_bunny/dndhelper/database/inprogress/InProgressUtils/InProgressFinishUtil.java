package com.brave_bunny.dndhelper.database.inprogress.InProgressUtils;

import android.content.ContentValues;
import android.content.Context;

import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils;

import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_CLERIC;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_FIGHTER;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_ROGUE;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_WIZARD;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressArmorUtil.removeAllInProgressArmor;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.getInProgressRow;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.removeAllInProgressStats;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressDomainsUtil.numberDomainsSelected;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressDomainsUtil.removeAllInProgressDomains;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressFeatsUtil.removeAllInProgressFeats;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressItemsUtil.removeAllInProgressItems;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSkillsUtil.removeAllInProgressSkills;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil.numberSpellsSelected;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil.removeAllInProgressSpells;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressWeaponsUtil.removeAllInProgressWeapons;

/**
 * Created by Jemma on 2/13/2017.
 */

public class InProgressFinishUtil {

    public static final int STATE_EMPTY = 0;
    public static final int STATE_PARTIAL = 1;
    public static final int STATE_COMPLETE = 2;

    //TODO for all final tables
    public static void removeAllCharacterData(Context context, long rowIndex) {
        removeAllInProgressStats(context, rowIndex);
        removeAllInProgressDomains(context, rowIndex);
        removeAllInProgressSpells(context, rowIndex);
        removeAllInProgressSkills(context, rowIndex);
        removeAllInProgressFeats(context, rowIndex);
        removeAllInProgressArmor(context, rowIndex);
        removeAllInProgressWeapons(context, rowIndex);
        removeAllInProgressItems(context, rowIndex);
    }

    /*
     *      Checking state of in progress character
     */

    public static int checkStateOfCharacterChoices(Context context, long index) {
        ContentValues inProgressCharacterData = getInProgressRow(context, index);

        if (isCompletelyFilled(context, inProgressCharacterData)) {
            return STATE_COMPLETE;
        } else if (isAtLeastPartiallyFilled(context, inProgressCharacterData)) {
            return STATE_PARTIAL;
        } else {
            return STATE_EMPTY;
        }
    }

    private static boolean isCompletelyFilled(Context context, ContentValues values) {
        boolean isCompletelyFilled = areDetailsFilled(values);
        isCompletelyFilled &= areAbilitiesFilled(values);
        isCompletelyFilled &= areClassSpecificsFilled(context, values);
        isCompletelyFilled &= areSkillsAndFeatsFilled(context, values);
        return isCompletelyFilled;
    }

    private static boolean isAtLeastPartiallyFilled(Context context, ContentValues inProgressCharacterData) {
        boolean isPartiallyFilled = areDetailsPartiallyFilled(inProgressCharacterData);
        isPartiallyFilled |= areAbilitiesPartiallyFilled(inProgressCharacterData);
        isPartiallyFilled |= areClassSpecificsPartiallyFilled(context, inProgressCharacterData);
        isPartiallyFilled |= areSkillsAndFeatsPartiallyFilled(context, inProgressCharacterData);
        return isPartiallyFilled;
    }

    /*
     *      Checking state of in progress character details
     */

    private static boolean areDetailsFilled(ContentValues inProgressCharacterData) {
        String name = InProgressCharacterUtil.getCharacterName(inProgressCharacterData);
        if (name.equals("")) return false;

        int classId = InProgressCharacterUtil.getCharacterClass(inProgressCharacterData);
        return (classId != -1);
    }

    private static boolean areDetailsPartiallyFilled(ContentValues inProgressCharacterData) {
        String name = InProgressCharacterUtil.getCharacterName(inProgressCharacterData);
        if (!name.equals("")) return true;

        int classId = InProgressCharacterUtil.getCharacterClass(inProgressCharacterData);
        return (classId != -1);
    }

    /*
     *      Checking state of in progress character abilities
     */

    private static boolean areAbilitiesFilled(ContentValues values) {
        int strScore = InProgressCharacterUtil.getCharacterStr(values);
        int dexScore = InProgressCharacterUtil.getCharacterDex(values);
        int conScore = InProgressCharacterUtil.getCharacterCon(values);
        int intScore = InProgressCharacterUtil.getCharacterInt(values);
        int wisScore = InProgressCharacterUtil.getCharacterWis(values);
        int chaScore = InProgressCharacterUtil.getCharacterCha(values);

        if (strScore == -1) return false;
        if (dexScore == -1) return false;
        if (conScore == -1) return false;
        if (intScore == -1) return false;
        if (wisScore == -1) return false;
        return (chaScore != -1);
    }

    private static boolean areAbilitiesPartiallyFilled(ContentValues values) {
        int strScore = InProgressCharacterUtil.getCharacterStr(values);
        int dexScore = InProgressCharacterUtil.getCharacterDex(values);
        int conScore = InProgressCharacterUtil.getCharacterCon(values);
        int intScore = InProgressCharacterUtil.getCharacterInt(values);
        int wisScore = InProgressCharacterUtil.getCharacterWis(values);
        int chaScore = InProgressCharacterUtil.getCharacterCha(values);

        if (strScore != -1) return true;
        if (dexScore != -1) return true;
        if (conScore != -1) return true;
        if (intScore != -1) return true;
        if (wisScore != -1) return true;
        return (chaScore != -1);
    }

    /*
     *      Checking state of in progress character class
     */

    private static boolean areClassSpecificsFilled(Context context, ContentValues values) {
        int classId = InProgressCharacterUtil.getCharacterClass(values);
        if (classId == 0) return false;

        switch (classId) {
            case CLASS_CLERIC:
                int numberDomain = numberDomainsSelected(context, values);
                if (numberDomain != 2) return false;
                break;
            case CLASS_FIGHTER:
                break;
            case CLASS_ROGUE:
                break;
            case CLASS_WIZARD:
                long familiarId = InProgressCharacterUtil.getCharacterFamiliar(values);
                if (familiarId == -1) return false;

                int numberSpells = numberSpellsSelected(context, values);
                int maximumSpells = InProgressSpellsUtil.getTotalSpellsToLearn(values);
                if(numberSpells != maximumSpells) return false;
                break;
        }
        return true;
    }

    private static boolean areClassSpecificsPartiallyFilled(Context context, ContentValues values) {
        int classId = InProgressCharacterUtil.getCharacterClass(values);
        if (classId != 0) return true;

        int numberDomain = numberDomainsSelected(context, values);
        if (numberDomain > 0) return true;

        long familiarId = InProgressCharacterUtil.getCharacterFamiliar(values);
        if (familiarId != -1) return true;

        int numberSpells = numberSpellsSelected(context, values);
        if(numberSpells > 0) return true;

        return false;
    }

    /*
     *      Checking state of in progress character skills and feats
     */

    private static boolean areSkillsAndFeatsFilled(Context context, ContentValues inProgressCharacterData) {

        long rowIndex = InProgressCharacterUtil.getId(inProgressCharacterData);
        int skillPointsSpent = InProgressSkillsUtil.numberSkillPointsSpent(context, rowIndex);
        int maximumPointsSpent = InProgressSkillsUtil.getTotalSkillPointsToSpend(inProgressCharacterData);

        int featsSelected = InProgressFeatsUtil.getNumberFeatsSelected(context, rowIndex);
        int maximumFeatsSelected = InProgressFeatsUtil.getNumberFeatsAllowed(inProgressCharacterData);

        return (featsSelected == maximumFeatsSelected) && (skillPointsSpent == maximumPointsSpent);
    }

    private static boolean areSkillsAndFeatsPartiallyFilled(Context context, ContentValues inProgressCharacterData) {
        long rowIndex = InProgressCharacterUtil.getId(inProgressCharacterData);
        int skillPointsSpent = InProgressSkillsUtil.numberSkillPointsSpent(context, rowIndex);
        int featsSelected = InProgressFeatsUtil.getNumberFeatsSelected(context, rowIndex);

        if (skillPointsSpent + featsSelected > 0) {
            return true;
        }
        return false;
    }

}
