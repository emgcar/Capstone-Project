package com.brave_bunny.dndhelper.database.inprogress;

/**
 * Created by Jemma on 1/11/2017.
 */

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Jemma on 8/7/2016.
 */
public class InProgressContract {

    public static final String CONTENT_AUTHORITY = "com.brave_bunny.dndhelper";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CHARACTERS = "characters";
    public static final String PATH_INPROGRESS = "inprogress";
    public static final String PATH_CHARACTER_CLASS = "character_class";
    public static final String PATH_CHARACTER_SPELLS = "character_spells";
    public static final String PATH_CHARACTER_SKILLS = "character_feats";
    public static final String PATH_CHARACTER_FEATS = "character_feats";
    public static final String PATH_CHARACTER_ITEMS = "character_items";
    public static final String PATH_CHARACTER_ARMOR = "character_armor";
    public static final String PATH_CHARACTER_WEAPONS = "character_weapons";

    public static final int GENDER_MALE = 0;
    public static final int GENDER_FEMALE = 1;

    public static final int ALIGN_LG = 0;
    public static final int ALIGN_LN = 1;
    public static final int ALIGN_LE = 2;
    public static final int ALIGN_NG = 3;
    public static final int ALIGN_N = 4;
    public static final int ALIGN_NE = 5;
    public static final int ALIGN_CG = 6;
    public static final int ALIGN_CN = 7;
    public static final int ALIGN_CE = 8;

    public static final int EQUIPPED_FALSE = 0;
    public static final int EQUIPPED_TRUE = 1;

    public static final class CharacterEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHARACTERS).build();

        public static final String TABLE_NAME = "characters";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_RACE_ID = "race_id";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_RELIGION_ID = "religion";
        public static final String COLUMN_ALIGN = "alignment";

        public static final String COLUMN_STR = "ability_strength";
        public static final String COLUMN_DEX = "ability_dexterity";
        public static final String COLUMN_CON = "ability_constitution";
        public static final String COLUMN_INT = "ability_intelligence";
        public static final String COLUMN_WIS = "ability_wisdom";
        public static final String COLUMN_CHA = "ability_charisma";

        public static final String COLUMN_BASE_ATTACK = "base_attack_bonus";
        public static final String COLUMN_FORT = "fortitude_save";
        public static final String COLUMN_REF = "reflex_save";
        public static final String COLUMN_WILL = "will_save";

        public static final String COLUMN_MONEY = "money";
        public static final String COLUMN_LIGHT_LOAD = "light_load_weight";
        public static final String COLUMN_MED_LOAD = "medium_load_weight";
        public static final String COLUMN_HEAVY_LOAD = "heavy_load_weight";

        public static final String COLUMN_AC = "armor_class";
        public static final String COLUMN_HP_MAX = "hit_points_maximum";
        public static final String COLUMN_HP_CURR = "hit_points_current";


        // put all skills here

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CHARACTERS;

        public static Uri buildCharacterUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class InProgressEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INPROGRESS).build();

        public static final String TABLE_NAME = "inprogress";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_RACE_ID = "race";
        public static final String COLUMN_CLASS_ID = "class";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_RELIGION_ID = "religion";
        public static final String COLUMN_ALIGN = "alignment";

        public static final String COLUMN_STR = "ability_strength";
        public static final String COLUMN_DEX = "ability_dexterity";
        public static final String COLUMN_CON = "ability_constitution";
        public static final String COLUMN_INT = "ability_intelligence";
        public static final String COLUMN_WIS = "ability_wisdom";
        public static final String COLUMN_CHA = "ability_charisma";

        public static final String COLUMN_ABILITY_1 = "ability_choice_1";
        public static final String COLUMN_ABILITY_2 = "ability_choice_2";
        public static final String COLUMN_ABILITY_3 = "ability_choice_3";
        public static final String COLUMN_ABILITY_4 = "ability_choice_4";
        public static final String COLUMN_ABILITY_5 = "ability_choice_5";
        public static final String COLUMN_ABILITY_6 = "ability_choice_6";

        public static final String COLUMN_MONEY = "money";
        public static final String COLUMN_LIGHT_LOAD = "light_load_weight";
        public static final String COLUMN_MED_LOAD = "medium_load_weight";
        public static final String COLUMN_HEAVY_LOAD = "heavy_load_weight";

        public static final String COLUMN_AC = "armor_class";
        public static final String COLUMN_HP = "hit_points";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INPROGRESS;

        public static Uri buildCharacterUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class CharacterClasses implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHARACTER_CLASS).build();

        public static final String TABLE_NAME = "character_classes";

        public static final String COLUMN_CHARACTER_ID = "character";
        public static final String COLUMN_CLASS_ID = "class";
        public static final String COLUMN_LEVEL = "level";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CHARACTER_CLASS;

        public static Uri buildCharacterUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class CharacterSpells implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHARACTER_SPELLS).build();

        public static final String TABLE_NAME = "character_spells";

        public static final String COLUMN_CHARACTER_ID = "character";
        public static final String COLUMN_SPELL_ID = "spell";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CHARACTER_SPELLS;

        public static Uri buildCharacterUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class CharacterSkills implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHARACTER_SKILLS).build();

        public static final String TABLE_NAME = "character_skills";

        public static final String COLUMN_CHARACTER_ID = "character";
        public static final String COLUMN_SKILL_ID = "skill";
        public static final String COLUMN_SKILL_TOTAL_MOD = "total_modifier";
        public static final String COLUMN_SKILL_CLASS = "skill_in_character_class";
        public static final String COLUMN_SKILL_RANKS = "ranks";
        public static final String COLUMN_SKILL_ABIL_MOD = "ability_modifier";
        public static final String COLUMN_SKILL_MISC_MOD = "misc_mod";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CHARACTER_SKILLS;

        public static Uri buildCharacterUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class CharacterFeats implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHARACTER_FEATS).build();

        public static final String TABLE_NAME = "character_feats";

        public static final String COLUMN_CHARACTER_ID = "character";
        public static final String COLUMN_FEAT_ID = "feat";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CHARACTER_FEATS;

        public static Uri buildCharacterUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class CharacterItems implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHARACTER_ITEMS).build();

        public static final String TABLE_NAME = "character_items";

        public static final String COLUMN_CHARACTER_ID = "character";
        public static final String COLUMN_ITEM_ID = "item";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CHARACTER_ITEMS;

        public static Uri buildCharacterUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class CharacterArmor implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHARACTER_ARMOR).build();

        public static final String TABLE_NAME = "character_armor";

        public static final String COLUMN_CHARACTER_ID = "character";
        public static final String COLUMN_ARMOR_ID = "armor";
        public static final String COLUMN_EQUIPPED = "equipped";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CHARACTER_ARMOR;

        public static Uri buildCharacterUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class CharacterWeapons implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHARACTER_WEAPONS).build();

        public static final String TABLE_NAME = "character_weapons";

        public static final String COLUMN_CHARACTER_ID = "character";
        public static final String COLUMN_WEAPON_ID = "weapon";
        public static final String COLUMN_EQUIPPED = "equipped";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CHARACTER_WEAPONS;

        public static Uri buildCharacterUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}