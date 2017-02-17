package com.brave_bunny.dndhelper.database.inprogress.InProgressUtils;

import android.content.ContentValues;
import android.content.Context;

import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterArmorUtil;
import com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterDomainsUtil;
import com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterFeatsUtil;
import com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterItemsUtil;
import com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterSkillsUtil;
import com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterSpellsUtil;
import com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterWeaponsUtil;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSkillsUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;

import static com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterArmorUtil.insertArmorIntoCharacterTable;
import static com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterDomainsUtil.insertDomainIntoCharacterTable;
import static com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterFeatsUtil.insertFeatIntoCharacterTable;
import static com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterItemsUtil.insertItemsIntoCharacterTable;
import static com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterSkillsUtil.insertSkillIntoCharacterTable;
import static com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterSpellsUtil.insertSpellIntoCharacterTable;
import static com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterUtil.insertValuesInCharacterTable;
import static com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterWeaponsUtil.insertWeaponsIntoCharacterTable;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_CLERIC;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_FIGHTER;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_ROGUE;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.CLASS_WIZARD;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.getClassStats;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesClassesUtils.getFirstLevelStats;
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

    private static final int TRUE = 1;
    private static final int FALSE = 0;

    public static final int STATE_EMPTY = 0;
    public static final int STATE_PARTIAL = 1;
    public static final int STATE_COMPLETE = 2;

    //TODO for all final tables
    public static void removeAllInProgressCharacterData(Context context, long rowIndex) {
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

    //TODO add all chosen content to all tables (deity, spells, familiar, skills, feats, ...)
    public static long createNewCharacter(Context context, long inProgressIndex) {
        ContentValues inProgressValues = InProgressCharacterUtil.getInProgressRow(context, inProgressIndex);
        ContentValues characterValues = new ContentValues();

        // adding character name
        String nameString = inProgressValues.getAsString(InProgressContract.CharacterEntry.COLUMN_NAME);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_NAME, nameString);

        // adding character gender
        int genderChoice = inProgressValues.getAsInteger(InProgressContract.CharacterEntry.COLUMN_GENDER);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_GENDER, genderChoice);

        // adding character race
        int raceChoice = inProgressValues.getAsInteger(InProgressContract.CharacterEntry.COLUMN_RACE_ID);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_RACE, raceChoice);

        // adding character age
        String ageString = inProgressValues.getAsString(InProgressContract.CharacterEntry.COLUMN_AGE);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_AGE, ageString);

        // adding character weight
        String weightString = inProgressValues.getAsString(InProgressContract.CharacterEntry.COLUMN_WEIGHT);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_WEIGHT, weightString);

        // adding character height
        String heightString = inProgressValues.getAsString(InProgressContract.CharacterEntry.COLUMN_HEIGHT);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_HEIGHT, heightString);

        //TODO save religion?
        // adding character religion
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_RELIGION_ID, 0);

        // adding character alignment
        int alignChoice = inProgressValues.getAsInteger(InProgressContract.CharacterEntry.COLUMN_ALIGN);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_ALIGN, alignChoice);

        // adding character strength
        int strTotal = InProgressCharacterUtil.getTotalStrengthScore(context, inProgressValues);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_STR, strTotal);

        // adding character dexterity
        int dexTotal = InProgressCharacterUtil.getTotalDexterityScore(context, inProgressValues);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_DEX, dexTotal);

        // adding character constitution
        int conTotal = InProgressCharacterUtil.getTotalConstitutionScore(context, inProgressValues);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_CON, conTotal);

        // adding character intelligence
        int intTotal = InProgressCharacterUtil.getTotalIntelligenceScore(context, inProgressValues);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_INT, intTotal);

        // adding character wisdom
        int wisTotal = InProgressCharacterUtil.getTotalWisdomScore(context, inProgressValues);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_WIS, wisTotal);

        // adding character charisma
        int chaTotal = InProgressCharacterUtil.getTotalCharismaScore(context, inProgressValues);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_CHA, chaTotal);

        // adding character base attack bonus, fortitude, reflex, and will
        int classChoice = inProgressValues.getAsInteger(InProgressContract.CharacterEntry.COLUMN_CLASS_ID);
        ContentValues classLevelOneStats = getFirstLevelStats(context, classChoice);

        int baseAttack = 0;
        int fortitude = RulesCharacterUtils.scoreToModifier(conTotal);
        int reflex = RulesCharacterUtils.scoreToModifier(dexTotal);
        int will = RulesCharacterUtils.scoreToModifier(wisTotal);

        switch (classChoice) {
            case CLASS_CLERIC:
                baseAttack = classLevelOneStats.getAsInteger(RulesContract.ClericEntry.COLUMN_BASE_ATTACK_1);
                fortitude += classLevelOneStats.getAsInteger(RulesContract.ClericEntry.COLUMN_FORT);
                reflex += classLevelOneStats.getAsInteger(RulesContract.ClericEntry.COLUMN_REF);
                will += classLevelOneStats.getAsInteger(RulesContract.ClericEntry.COLUMN_WILL);
                break;
            case CLASS_FIGHTER:
                baseAttack = classLevelOneStats.getAsInteger(RulesContract.FighterEntry.COLUMN_BASE_ATTACK_1);
                fortitude += classLevelOneStats.getAsInteger(RulesContract.FighterEntry.COLUMN_FORT);
                reflex += classLevelOneStats.getAsInteger(RulesContract.FighterEntry.COLUMN_REF);
                will += classLevelOneStats.getAsInteger(RulesContract.FighterEntry.COLUMN_WILL);
                break;
            case CLASS_ROGUE:
                baseAttack = classLevelOneStats.getAsInteger(RulesContract.RogueEntry.COLUMN_BASE_ATTACK_1);
                fortitude += classLevelOneStats.getAsInteger(RulesContract.RogueEntry.COLUMN_FORT);
                reflex += classLevelOneStats.getAsInteger(RulesContract.RogueEntry.COLUMN_REF);
                will += classLevelOneStats.getAsInteger(RulesContract.RogueEntry.COLUMN_WILL);
                break;
            case CLASS_WIZARD:
                baseAttack = classLevelOneStats.getAsInteger(RulesContract.WizardEntry.COLUMN_BASE_ATTACK_1);
                fortitude += classLevelOneStats.getAsInteger(RulesContract.WizardEntry.COLUMN_FORT);
                reflex += classLevelOneStats.getAsInteger(RulesContract.WizardEntry.COLUMN_REF);
                will += classLevelOneStats.getAsInteger(RulesContract.WizardEntry.COLUMN_WILL);
                break;
        }
        // adding character base attack bonus
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_BASE_ATTACK, baseAttack);

        // adding character fortitude
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_FORT, fortitude);

        // adding character reflex
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_REF, reflex);

        // adding character will
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_WILL, will);

        // adding character money
        int money = inProgressValues.getAsInteger(InProgressContract.CharacterEntry.COLUMN_MONEY);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_MONEY, money);

        //TODO get  load levels
        // adding character light load
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_LIGHT_LOAD, 0);

        // adding character medium load
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_MED_LOAD, 0);

        // adding character heavy load
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_HEAVY_LOAD, 0);

        //TODO: add armor bonus, shield bonus, and size modifier
        // adding character AC
        int armor_class = 10 + RulesCharacterUtils.scoreToModifier(dexTotal);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_AC, armor_class);

        // adding character HP
        ContentValues classStats = getClassStats(context, classChoice);
        int hpDie = classStats.getAsInteger(RulesContract.ClassEntry.COLUMN_HIT_DIE);
        hpDie += RulesCharacterUtils.scoreToModifier(conTotal);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_HP_CURR, hpDie);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_HP_MAX, hpDie);

        //TODO: check for Improved Initiative feat
        // adding character initiative
        int initiative = RulesCharacterUtils.scoreToModifier(dexTotal);
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_INITIATIVE, initiative);


        // adding character in battle
        characterValues.put(CharacterContract.CharacterEntry.COLUMN_IN_BATTLE, FALSE);

        long characterIndex = insertValuesInCharacterTable(context, characterValues);

        transferDomains(context, inProgressIndex, characterIndex);
        transferSpells(context, inProgressIndex, characterIndex);
        transferSkills(context, inProgressIndex, inProgressValues, characterIndex);
        transferFeats(context, inProgressIndex, characterIndex);
        transferArmor(context, inProgressIndex, characterIndex);
        transferWeapons(context, inProgressIndex, characterIndex);
        transferItems(context, inProgressIndex, characterIndex);

        return characterIndex;
    }

    public static void transferDomains(Context context, long inProgressIndex, long characterIndex) {
        ContentValues[] allDomains = InProgressDomainsUtil.getAllDomainsForCharacter(context, inProgressIndex);
        int numSpells = allDomains.length;

        for (int i = 0; i < numSpells; i++) {
            ContentValues newValue = new ContentValues();
            CharacterDomainsUtil.setCharacterId(newValue, characterIndex);

            long domainId = InProgressDomainsUtil.getDomainId(allDomains[i]);
            CharacterDomainsUtil.setDomainId(newValue, domainId);

            insertDomainIntoCharacterTable(context, newValue);
        }
    }

    public static void transferFeats(Context context, long inProgressIndex, long characterIndex) {
        ContentValues[] allFeats = InProgressFeatsUtil.getAllFeatsForCharacter(context, inProgressIndex);
        int numSpells = allFeats.length;

        for (int i = 0; i < numSpells; i++) {
            ContentValues newValue = new ContentValues();
            CharacterFeatsUtil.setCharacterId(newValue, characterIndex);

            long featId = InProgressFeatsUtil.getFeatId(allFeats[i]);
            CharacterFeatsUtil.setFeatId(newValue, featId);

            insertFeatIntoCharacterTable(context, newValue);
        }
    }

    public static void transferSpells(Context context, long inProgressIndex, long characterIndex) {

        ContentValues[] allSpells = InProgressSpellsUtil.getAllSpellsForCharacter(context, inProgressIndex);
        int numSpells = allSpells.length;

        for (int i = 0; i < numSpells; i++) {
            ContentValues newValue = new ContentValues();
            CharacterSpellsUtil.setCharacterId(newValue, characterIndex);

            long spellIndex = InProgressSpellsUtil.getSpellId(allSpells[i]);
            CharacterSpellsUtil.setSpellId(newValue, spellIndex);

            insertSpellIntoCharacterTable(context, newValue);
        }
    }

    public static void transferSkills(Context context, long inProgressIndex,
                                      ContentValues inProgressValues, long characterIndex) {

        ContentValues[] allSkills = RulesSkillsUtils.getAllSkills(context);
        int numSkills = allSkills.length;

        for (int i = 0; i < numSkills; i++) {
            long skillId = RulesSkillsUtils.getSkillId(allSkills[i]);
            if (RulesSkillsUtils.canBeUntrained(allSkills[i])) {
                transferSkill(context, allSkills[i], characterIndex, inProgressIndex, inProgressValues);
            } else if (InProgressSkillsUtil.isSkillListed(context, inProgressIndex, skillId) ) {
                transferSkill(context, allSkills[i], characterIndex, inProgressIndex, inProgressValues);
            }
        }
    }

    public static void transferSkill(Context context, ContentValues skillData, long characterIndex,
                                     long inProgressIndex, ContentValues inProgressValues) {
        long skillId = RulesSkillsUtils.getSkillId(skillData);

        ContentValues newEntry = new ContentValues();
        CharacterSkillsUtil.setCharacterId(newEntry, characterIndex);
        CharacterSkillsUtil.setSkillId(newEntry, skillId);

        //TODO
        CharacterSkillsUtil.setSkillInClass(newEntry, false);

        int ranks = InProgressSkillsUtil.getSkillRanks(context, inProgressIndex, skillId);
        CharacterSkillsUtil.setSkillsRanks(newEntry, ranks);

        int abilMod = InProgressSkillsUtil.getSkillAbilityMod(skillData, inProgressValues);
        CharacterSkillsUtil.setSkillAbilMod(newEntry, abilMod);

        //TODO
        int miscMod = 0;
        CharacterSkillsUtil.setSkillMiscMod(newEntry, miscMod);

        int totalMod = ranks + abilMod + miscMod;
        CharacterSkillsUtil.setSkillTotalMod(newEntry, totalMod);

        insertSkillIntoCharacterTable(context, newEntry);
    }

    public static void transferItems(Context context, long inProgressIndex, long characterIndex) {

        ContentValues[] allItems = InProgressItemsUtil.getAllItemsForCharacter(context, inProgressIndex);
        int numItems = allItems.length;

        for (int i = 0; i < numItems; i++) {
            ContentValues newValue = new ContentValues();
            CharacterItemsUtil.setCharacterId(newValue, characterIndex);

            long itemId = InProgressItemsUtil.getItemId(allItems[i]);
            CharacterItemsUtil.setItemId(newValue, itemId);

            insertItemsIntoCharacterTable(context, newValue);
        }
    }

    public static void transferArmor(Context context, long inProgressIndex, long characterIndex) {

        ContentValues[] allArmor = InProgressArmorUtil.getAllArmorForCharacter(context, inProgressIndex);
        int numArmor = allArmor.length;

        for (int i = 0; i < numArmor; i++) {
            ContentValues newValue = new ContentValues();
            CharacterArmorUtil.setCharacterId(newValue, characterIndex);

            long armorId = InProgressArmorUtil.getArmorId(allArmor[i]);
            CharacterArmorUtil.setArmorId(newValue, armorId);

            insertArmorIntoCharacterTable(context, newValue);
        }
    }

    public static void transferWeapons(Context context, long inProgressIndex, long characterIndex) {

        ContentValues[] allWeapons = InProgressWeaponsUtil.getAllWeaponsForCharacter(context, inProgressIndex);
        int numWeapons = allWeapons.length;

        for (int i = 0; i < numWeapons; i++) {
            ContentValues newValue = new ContentValues();
            CharacterWeaponsUtil.setCharacterId(newValue, characterIndex);

            long weaponId = InProgressWeaponsUtil.getWeaponId(allWeapons[i]);
            CharacterWeaponsUtil.setWeaponId(newValue, weaponId);

            insertWeaponsIntoCharacterTable(context, newValue);
        }
    }

}
