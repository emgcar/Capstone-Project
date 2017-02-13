package com.brave_bunny.dndhelper.database.character.CharacterUtils;

import android.content.ContentValues;

import java.util.Random;

import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesRacesUtils.RACE_HUMAN;

/**
 * Created by Jemma on 2/12/2017.
 */

public class CharacterLevelUpUtil {

    // A lot more of the rules in Player's Handbook pg 58.
    //TODO level up process
    public static void levelUpProcess() {
        //choose class

        //increase base attack bonus

        //increase base save bonuses

        //increase ability mod

        //increase hit points

        //increase skill points

        //increase feats

        //select new spells

        //add class features
    }


    //TODO
    /*
     * Add modifier increase.
     * In players handbook pg 10.
     * In players handbook pg 22.
     */
    public static boolean increaseAbilMod(int level) {
        //note: level is total level
        return (level % 4 == 0);
    }


    //TODO
    public static int getTotalSkillPointsToSpend(ContentValues values) {
        int skillPoints = 0;

        // This rule can be found in Player's Handbook on pg 13.
        /*int race = CharacterUtil.getCharacterRace(values);
        if (race == RACE_HUMAN) {
            skillPoints += 1;
        }

        int intScore = InProgressCharacterUtil.getCharacterInt(values);
        skillPoints += RulesCharacterUtils.scoreToModifier(intScore);*/


        // These rules in Player's Handbook in Ch 3.
        /*int intScore = InProgressCharacterUtil.getCharacterInt(values);
        int intMod = RulesCharacterUtils.scoreToModifier(intScore);
        int classId = InProgressCharacterUtil.getCharacterClass(values);
        switch (classId) {
            case CLASS_CLERIC:
            case CLASS_FIGHTER:
            case CLASS_WIZARD:
                skillPoints += (2 + intMod)*4;
                break;
            case CLASS_ROGUE:
                skillPoints += (8 + intMod)*4;
                break;
        }*/

        if (skillPoints <= 0) return 1;

        return skillPoints;
    }

    //TODO
    public static int getExtraFeatsToChoose(int level) {
        //note: level is total level
        if (level % 3 == 0) {
            return 1;
        }
        return 0;
    }

    /*
     * Get extra hit points.
     * In players handbook pg 23.
     */
    //TODO
    public static int addToHitPoints(int hitDie, int conMod) {
        Random rand = new Random();
        int addedHitPoints = rand.nextInt(hitDie + 1) + conMod;

        if (addedHitPoints <= 0) {
            return 1;
        }
        return addedHitPoints;
    }
}
