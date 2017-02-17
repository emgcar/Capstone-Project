package com.brave_bunny.dndhelper.database.edition35.RulesUtils;

import android.content.Context;

import com.brave_bunny.dndhelper.R;

/**
 * Handles all of the general rule data.
 */

public class RulesCharacterUtils {

    public static final int LawfulGood = 1;
    public static final int LawfulNeutral = 2;
    public static final int LawfulEvil = 3;
    public static final int NeutralGood = 4;
    public static final int Neutral = 5;
    public static final int NeutralEvil = 6;
    public static final int ChaoticGood = 7;
    public static final int ChaoticNeutral = 8;
    public static final int ChaoticEvil = 9;

    public static int scoreToModifier(int score) {
        return (score - 10)/2;
    }

    public static long scoreToModifier(long score) {
        return (score - 10)/2;
    }

    public static String getAligmentText(Context context, int alignment) {
        switch (alignment) {
            case LawfulGood:
                return context.getString(R.string.lawful_good);
            case LawfulNeutral:
                return context.getString(R.string.lawful_neutral);
            case LawfulEvil:
                return context.getString(R.string.lawful_evil);
            case NeutralGood:
                return context.getString(R.string.neutral_good);
            case Neutral:
                return context.getString(R.string.neutral);
            case NeutralEvil:
                return context.getString(R.string.neutral_evil);
            case ChaoticGood:
                return context.getString(R.string.chaotic_good);
            case ChaoticNeutral:
                return context.getString(R.string.chaotic_neutral);
            case ChaoticEvil:
                return context.getString(R.string.chaotic_evil);
        }
        return "";
    }
}
