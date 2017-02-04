package com.brave_bunny.dndhelper.database.edition35.RulesUtils;

/**
 * Created by Jemma on 1/11/2017.
 */

public class RulesCharacterUtils {

    public static int scoreToModifier(int score) {
        return (score - 10)/2;
    }

    public static long scoreToModifier(long score) {
        return (score - 10)/2;
    }
}
