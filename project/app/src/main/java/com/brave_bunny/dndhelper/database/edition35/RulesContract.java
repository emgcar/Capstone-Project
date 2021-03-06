package com.brave_bunny.dndhelper.database.edition35;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Creates the database following the rules of DnD edition 3.5.
 */

public class RulesContract {

    static final String CONTENT_AUTHORITY = "com.brave_bunny.dndhelper.3-5_rules";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    static final String PATH_SKILLS = "skills";
    static final String PATH_FEATS = "feats";
    static final String PATH_RACES = "races";
    static final String PATH_CLASSES = "classes";
    static final String PATH_CLERIC = "cleric";
    static final String PATH_CLERIC_DOMAINS = "cleric_domains";
    static final String PATH_FIGHTER = "fighter";
    static final String PATH_ROGUE = "rogue";
    static final String PATH_WIZARD = "wizard";
    static final String PATH_SPELLS = "spells";
    static final String PATH_FAMILIARS = "familiars";
    static final String PATH_ARMOR = "armor";
    static final String PATH_WEAPONS = "weapons";
    static final String PATH_ITEMS = "items";

    public static final class SkillsEntry implements BaseColumns {
        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SKILLS).build();

        public static final String TABLE_NAME = "skills";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_BASE_SCORE = "base_score";
        public static final String COLUMN_UNTRAINED = "untrained";
        public static final String COLUMN_CLERIC = "cleric";
        public static final String COLUMN_FIGHTER = "fighter";
        public static final String COLUMN_ROGUE = "rogue";
        public static final String COLUMN_WIZARD = "wizard";
        public static final String COLUMN_ARMOR_PENALTY = "armor_penalty";
        public static final String COLUMN_DOUBLE_ARMOR_PENALTY = "double_armor_penalty";

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SKILLS;

        public static Uri buildSkillUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class FeatEntry implements BaseColumns {
        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FEATS).build();

        public static final String TABLE_NAME = "feats";

        public static final String COLUMN_NAME = "name";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FEATS;

        public static Uri buildFeatUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class RaceEntry implements BaseColumns {
        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RACES).build();

        public static final String TABLE_NAME = "races";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_STR = "strength_mod";
        public static final String COLUMN_DEX = "dexterity_mod";
        public static final String COLUMN_CON = "constitution_mod";
        public static final String COLUMN_INT = "intelligence_mod";
        public static final String COLUMN_WIS = "wisdom_mod";
        public static final String COLUMN_CHA = "charisma_mod";

        public static final String COLUMN_SIZE = "size";
        public static final String COLUMN_SPEED = "speed";

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RACES;

        public static Uri buildRaceUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // on change, update RulesUtils/RulesClassesUtils.java
    public static final class ClassEntry implements BaseColumns {
        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CLASSES).build();

        public static final String TABLE_NAME = "classes";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_HIT_DIE = "hit_die";
        public static final String COLUMN_STARTING_GOLD = "starting_gold";

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CLASSES;

        public static Uri buildClassUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // on change, update RulesUtils/RulesClassesUtils.java
    public static final class ClericEntry implements BaseColumns {
        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CLERIC).build();

        public static final String TABLE_NAME = "cleric";

        public static final String COLUMN_BASE_ATTACK_1 = "base_attack_first";
        public static final String COLUMN_BASE_ATTACK_2 = "base_attack_second";
        public static final String COLUMN_BASE_ATTACK_3 = "base_attack_third";
        public static final String COLUMN_FORT = "fortitude_save";
        public static final String COLUMN_REF = "reflex_save";
        public static final String COLUMN_WILL = "will_save";
        public static final String COLUMN_SPELLS_PER_DAY_L0 = "spells_per_day_0";
        public static final String COLUMN_SPELLS_PER_DAY_L1 = "spells_per_day_1";
        public static final String COLUMN_SPELLS_PER_DAY_L2 = "spells_per_day_2";
        public static final String COLUMN_SPELLS_PER_DAY_L3 = "spells_per_day_3";
        public static final String COLUMN_SPELLS_PER_DAY_L4 = "spells_per_day_4";
        public static final String COLUMN_SPELLS_PER_DAY_L5 = "spells_per_day_5";
        public static final String COLUMN_SPELLS_PER_DAY_L6 = "spells_per_day_6";
        public static final String COLUMN_SPELLS_PER_DAY_L7 = "spells_per_day_7";
        public static final String COLUMN_SPELLS_PER_DAY_L8 = "spells_per_day_8";
        public static final String COLUMN_SPELLS_PER_DAY_L9 = "spells_per_day_9";

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CLERIC;

        public static Uri buildClericUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class ClericDomainsEntry implements BaseColumns {
        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CLERIC_DOMAINS).build();

        public static final String TABLE_NAME = "cleric_domains";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LAWFUL_GOOD = "lawful_good";
        public static final String COLUMN_LAWFUL_NEUTRAL = "lawful_neutral";
        public static final String COLUMN_LAWFUL_EVIL = "lawful_evil";
        public static final String COLUMN_NEUTRAL_GOOD = "neutral_good";
        public static final String COLUMN_NEUTRAL = "neutral";
        public static final String COLUMN_NEUTRAL_EVIL = "neutral_evil";
        public static final String COLUMN_CHAOTIC_GOOD = "chaotic_good";
        public static final String COLUMN_CHAOTIC_NEUTRAL = "chatic_neutral";
        public static final String COLUMN_CHAOTIC_EVIL = "chaotic_evil";

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CLERIC_DOMAINS;

        public static Uri buildDomainUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // on change, update RulesUtils/RulesClassesUtils.java
    public static final class FighterEntry implements BaseColumns {
        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FIGHTER).build();

        public static final String TABLE_NAME = "fighter";

        public static final String COLUMN_BASE_ATTACK_1 = "base_attack_first";
        public static final String COLUMN_BASE_ATTACK_2 = "base_attack_second";
        public static final String COLUMN_BASE_ATTACK_3 = "base_attack_third";
        public static final String COLUMN_BASE_ATTACK_4 = "base_attack_fourth";
        public static final String COLUMN_FORT = "fortitude_save";
        public static final String COLUMN_REF = "reflex_save";
        public static final String COLUMN_WILL = "will_save";

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FIGHTER;

        public static Uri buildFighterUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // on change, update RulesUtils/RulesClassesUtils.java
    public static final class RogueEntry implements BaseColumns {
        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ROGUE).build();

        public static final String TABLE_NAME = "rogue";

        public static final String COLUMN_BASE_ATTACK_1 = "base_attack_first";
        public static final String COLUMN_BASE_ATTACK_2 = "base_attack_second";
        public static final String COLUMN_BASE_ATTACK_3 = "base_attack_third";
        public static final String COLUMN_FORT = "fortitude_save";
        public static final String COLUMN_REF = "reflex_save";
        public static final String COLUMN_WILL = "will_save";

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ROGUE;

        public static Uri buildRogueUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // on change, update RulesUtils/RulesClassesUtils.java
    public static final class WizardEntry implements BaseColumns {
        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_WIZARD).build();

        public static final String TABLE_NAME = "wizard";

        public static final String COLUMN_BASE_ATTACK_1 = "base_attack_first";
        public static final String COLUMN_BASE_ATTACK_2 = "base_attack_second";
        public static final String COLUMN_FORT = "fortitude_save";
        public static final String COLUMN_REF = "reflex_save";
        public static final String COLUMN_WILL = "will_save";
        public static final String COLUMN_SPELLS_PER_DAY_L0 = "spells_per_day_0";
        public static final String COLUMN_SPELLS_PER_DAY_L1 = "spells_per_day_1";
        public static final String COLUMN_SPELLS_PER_DAY_L2 = "spells_per_day_2";
        public static final String COLUMN_SPELLS_PER_DAY_L3 = "spells_per_day_3";
        public static final String COLUMN_SPELLS_PER_DAY_L4 = "spells_per_day_4";
        public static final String COLUMN_SPELLS_PER_DAY_L5 = "spells_per_day_5";
        public static final String COLUMN_SPELLS_PER_DAY_L6 = "spells_per_day_6";
        public static final String COLUMN_SPELLS_PER_DAY_L7 = "spells_per_day_7";
        public static final String COLUMN_SPELLS_PER_DAY_L8 = "spells_per_day_8";
        public static final String COLUMN_SPELLS_PER_DAY_L9 = "spells_per_day_9";

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WIZARD;

        public static Uri buildWizardUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class SpellsEntry implements BaseColumns {
        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SPELLS).build();

        public static final String TABLE_NAME = "spells";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LEVEL = "level";

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SPELLS;

        public static Uri buildSpellsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class FamiliarEntry implements BaseColumns {
        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAMILIARS).build();

        public static final String TABLE_NAME = "familiars";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SKILL_ID = "skill_id";
        public static final String COLUMN_SKILL_BONUS = "skill_bonus_amount";

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAMILIARS;

        public static Uri buildFamiliarUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // on change, update RulesUtils/RulesArmorUtils.java
    public static final class ArmorEntry implements BaseColumns {
        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARMOR).build();

        public static final String TABLE_NAME = "armor";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ARMOR_WEIGHT = "armor_weight";
        public static final String COLUMN_ARMOR_COST = "armor_cost";

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARMOR;

        public static Uri buildArmorUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class WeaponEntry implements BaseColumns {
        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_WEAPONS).build();

        public static final String TABLE_NAME = "weapons";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_WEAPON_WEIGHT = "weapon_weight";
        public static final String COLUMN_WEAPON_COST = "weapon_cost";

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WEAPONS;

        public static Uri buildWeaponUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class ItemEntry implements BaseColumns {
        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ITEMS).build();

        public static final String TABLE_NAME = "items";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ITEM_WEIGHT = "item_weight";
        public static final String COLUMN_ITEM_COST = "item_cost";

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        public static Uri buildItemUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
