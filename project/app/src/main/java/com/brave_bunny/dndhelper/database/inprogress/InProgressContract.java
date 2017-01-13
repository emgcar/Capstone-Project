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

    public static final String CONTENT_AUTHORITY = "com.brave_bunny.dndhelper.inprogress";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_INPROGRESS_STAT = "stat";

    public static final class CharacterEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INPROGRESS_STAT).build();
        public static final String TABLE_NAME = "inprogress_stats";

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
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INPROGRESS_STAT;

        public static Uri buildCharacterUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}