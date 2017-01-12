package com.brave_bunny.dndhelper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.brave_bunny.dndhelper.database.CharacterContract;

/**
 * Created by Jemma on 8/7/2016.
 */
public class CharacterDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "characters.db";

    public CharacterDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_CHARACTER_TABLE = "CREATE TABLE " +
                CharacterContract.CharacterEntry.TABLE_NAME + " (" +
                CharacterContract.CharacterEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                CharacterContract.CharacterEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL," +
                CharacterContract.CharacterEntry.COLUMN_GENDER + " INTEGER NOT NULL," +
                CharacterContract.CharacterEntry.COLUMN_RACE_ID + " INTEGER NOT NULL," +
                CharacterContract.CharacterEntry.COLUMN_AGE + " INTEGER," +
                CharacterContract.CharacterEntry.COLUMN_WEIGHT + " REAL," +
                CharacterContract.CharacterEntry.COLUMN_HEIGHT + " REAL," +
                CharacterContract.CharacterEntry.COLUMN_RELIGION_ID + " INTEGER," +
                CharacterContract.CharacterEntry.COLUMN_ALIGN + " INTEGER NOT NULL," +

                CharacterContract.CharacterEntry.COLUMN_STR + " INTEGER NOT NULL," +
                CharacterContract.CharacterEntry.COLUMN_DEX + " INTEGER NOT NULL," +
                CharacterContract.CharacterEntry.COLUMN_CON + " INTEGER NOT NULL," +
                CharacterContract.CharacterEntry.COLUMN_INT + " INTEGER NOT NULL," +
                CharacterContract.CharacterEntry.COLUMN_WIS + " INTEGER NOT NULL," +
                CharacterContract.CharacterEntry.COLUMN_CHA + " INTEGER NOT NULL," +

                CharacterContract.CharacterEntry.COLUMN_BASE_ATTACK + " INTEGER NOT NULL," +
                CharacterContract.CharacterEntry.COLUMN_FORT + " INTEGER NOT NULL," +
                CharacterContract.CharacterEntry.COLUMN_REF + " INTEGER NOT NULL," +
                CharacterContract.CharacterEntry.COLUMN_WILL + " INTEGER NOT NULL," +

                CharacterContract.CharacterEntry.COLUMN_MONEY + " INTEGER NOT NULL," +
                CharacterContract.CharacterEntry.COLUMN_LIGHT_LOAD + " REAL NOT NULL," +
                CharacterContract.CharacterEntry.COLUMN_MED_LOAD + " REAL NOT NULL," +
                CharacterContract.CharacterEntry.COLUMN_HEAVY_LOAD + " REAL NOT NULL," +

                CharacterContract.CharacterEntry.COLUMN_AC + " INTEGER NOT NULL," +
                CharacterContract.CharacterEntry.COLUMN_HP_MAX + " INTEGER NOT NULL," +
                CharacterContract.CharacterEntry.COLUMN_HP_CURR + " INTEGER NOT NULL," +
                CharacterContract.CharacterEntry.COLUMN_IN_BATTLE + " INTEGER NOT NULL )";

        sqLiteDatabase.execSQL(SQL_CREATE_CHARACTER_TABLE);

        createDummyData(sqLiteDatabase);

        final String SQL_CREATE_INPROGRESS_TABLE = "CREATE TABLE " +
                CharacterContract.InProgressEntry.TABLE_NAME + " (" +
                CharacterContract.InProgressEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                CharacterContract.InProgressEntry.COLUMN_NAME + " TEXT," +
                CharacterContract.InProgressEntry.COLUMN_GENDER + " INTEGER," +
                CharacterContract.InProgressEntry.COLUMN_RACE_ID + " INTEGER," +
                CharacterContract.InProgressEntry.COLUMN_CLASS_ID + " INTEGER," +
                CharacterContract.InProgressEntry.COLUMN_AGE + " INTEGER," +
                CharacterContract.InProgressEntry.COLUMN_WEIGHT + " REAL," +
                CharacterContract.InProgressEntry.COLUMN_HEIGHT + " REAL," +
                CharacterContract.InProgressEntry.COLUMN_RELIGION_ID + " INTEGER," +
                CharacterContract.InProgressEntry.COLUMN_ALIGN + " INTEGER," +

                CharacterContract.InProgressEntry.COLUMN_STR + " INTEGER NOT NULL," +
                CharacterContract.InProgressEntry.COLUMN_DEX + " INTEGER NOT NULL," +
                CharacterContract.InProgressEntry.COLUMN_CON + " INTEGER NOT NULL," +
                CharacterContract.InProgressEntry.COLUMN_INT + " INTEGER NOT NULL," +
                CharacterContract.InProgressEntry.COLUMN_WIS + " INTEGER NOT NULL," +
                CharacterContract.InProgressEntry.COLUMN_CHA + " INTEGER NOT NULL," +
                CharacterContract.InProgressEntry.COLUMN_ABILITY_1 + " INTEGER NOT NULL," +
                CharacterContract.InProgressEntry.COLUMN_ABILITY_2 + " INTEGER NOT NULL," +
                CharacterContract.InProgressEntry.COLUMN_ABILITY_3 + " INTEGER NOT NULL," +
                CharacterContract.InProgressEntry.COLUMN_ABILITY_4 + " INTEGER NOT NULL," +
                CharacterContract.InProgressEntry.COLUMN_ABILITY_5 + " INTEGER NOT NULL," +
                CharacterContract.InProgressEntry.COLUMN_ABILITY_6 + " INTEGER NOT NULL," +

                CharacterContract.InProgressEntry.COLUMN_MONEY + " INTEGER," +
                CharacterContract.InProgressEntry.COLUMN_LIGHT_LOAD + " REAL," +
                CharacterContract.InProgressEntry.COLUMN_MED_LOAD + " REAL," +
                CharacterContract.InProgressEntry.COLUMN_HEAVY_LOAD + " REAL," +

                CharacterContract.InProgressEntry.COLUMN_AC + " INTEGER," +
                CharacterContract.InProgressEntry.COLUMN_HP + " INTEGER )";

        sqLiteDatabase.execSQL(SQL_CREATE_INPROGRESS_TABLE);

        final String SQL_CREATE_CHARACTER_CLASSES_TABLE = "CREATE TABLE " +
                CharacterContract.CharacterClasses.TABLE_NAME + " (" +
                CharacterContract.CharacterClasses._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                CharacterContract.CharacterClasses.COLUMN_CHARACTER_ID + " INTEGER NOT NULL," +
                CharacterContract.CharacterClasses.COLUMN_CLASS_ID + " INTEGER NOT NULL," +
                CharacterContract.CharacterClasses.COLUMN_LEVEL + " INTEGER NOT NULL )";

        sqLiteDatabase.execSQL(SQL_CREATE_CHARACTER_CLASSES_TABLE);

        final String SQL_CREATE_CHARACTER_SPELLS_TABLE = "CREATE TABLE " +
                CharacterContract.CharacterSpells.TABLE_NAME + " (" +
                CharacterContract.CharacterSpells._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                CharacterContract.CharacterSpells.COLUMN_CHARACTER_ID + " INTEGER NOT NULL," +
                CharacterContract.CharacterSpells.COLUMN_SPELL_ID + " INTEGER NOT NULL )";

        sqLiteDatabase.execSQL(SQL_CREATE_CHARACTER_SPELLS_TABLE);

        final String SQL_CREATE_CHARACTER_SKILLS_TABLE = "CREATE TABLE " +
                CharacterContract.CharacterSkills.TABLE_NAME + " (" +
                CharacterContract.CharacterSkills._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                CharacterContract.CharacterSkills.COLUMN_CHARACTER_ID + " INTEGER NOT NULL," +
                CharacterContract.CharacterSkills.COLUMN_SKILL_ID + " INTEGER NOT NULL," +
                CharacterContract.CharacterSkills.COLUMN_SKILL_TOTAL_MOD + " INTEGER NOT NULL," +
                CharacterContract.CharacterSkills.COLUMN_SKILL_CLASS + " INTEGER NOT NULL," +
                CharacterContract.CharacterSkills.COLUMN_SKILL_RANKS + " INTEGER NOT NULL," +
                CharacterContract.CharacterSkills.COLUMN_SKILL_ABIL_MOD + " INTEGER NOT NULL," +
                CharacterContract.CharacterSkills.COLUMN_SKILL_MISC_MOD + " INTEGER NOT NULL )";

        sqLiteDatabase.execSQL(SQL_CREATE_CHARACTER_SKILLS_TABLE);

        final String SQL_CREATE_CHARACTER_FEATS_TABLE = "CREATE TABLE " +
                CharacterContract.CharacterFeats.TABLE_NAME + " (" +
                CharacterContract.CharacterFeats._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                CharacterContract.CharacterFeats.COLUMN_CHARACTER_ID + " INTEGER NOT NULL," +
                CharacterContract.CharacterFeats.COLUMN_FEAT_ID + " INTEGER NOT NULL )";

        sqLiteDatabase.execSQL(SQL_CREATE_CHARACTER_FEATS_TABLE);

        final String SQL_CREATE_CHARACTER_ITEMS_TABLE = "CREATE TABLE " +
                CharacterContract.CharacterItems.TABLE_NAME + " (" +
                CharacterContract.CharacterItems._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                CharacterContract.CharacterItems.COLUMN_CHARACTER_ID + " INTEGER NOT NULL," +
                CharacterContract.CharacterItems.COLUMN_ITEM_ID + " INTEGER NOT NULL )";

        sqLiteDatabase.execSQL(SQL_CREATE_CHARACTER_ITEMS_TABLE);

        final String SQL_CREATE_CHARACTER_ARMOR_TABLE = "CREATE TABLE " +
                CharacterContract.CharacterArmor.TABLE_NAME + " (" +
                CharacterContract.CharacterArmor._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                CharacterContract.CharacterArmor.COLUMN_CHARACTER_ID + " INTEGER NOT NULL," +
                CharacterContract.CharacterArmor.COLUMN_ARMOR_ID + " INTEGER NOT NULL," +
                CharacterContract.CharacterArmor.COLUMN_EQUIPPED + " INTEGER NOT NULL )";

        sqLiteDatabase.execSQL(SQL_CREATE_CHARACTER_ARMOR_TABLE);

        final String SQL_CREATE_CHARACTER_WEAPONS_TABLE = "CREATE TABLE " +
                CharacterContract.CharacterWeapons.TABLE_NAME + " (" +
                CharacterContract.CharacterWeapons._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                CharacterContract.CharacterWeapons.COLUMN_CHARACTER_ID + " INTEGER NOT NULL," +
                CharacterContract.CharacterWeapons.COLUMN_WEAPON_ID + " INTEGER NOT NULL," +
                CharacterContract.CharacterWeapons.COLUMN_EQUIPPED + " INTEGER NOT NULL )";

        sqLiteDatabase.execSQL(SQL_CREATE_CHARACTER_WEAPONS_TABLE);
    }

    private void createDummyData(SQLiteDatabase sqLiteDatabase) {
        //createAlice(sqLiteDatabase);

        String SQL_CREATE_CHARACTER = "INSERT INTO " +
                CharacterContract.CharacterEntry.TABLE_NAME +
                " VALUES (" +
                    "2, 'Bob', 0, 2, 145, 121, 6.2, 0, 0," + // id, name, gender, raceID, age, weight, height, religionID, alignment
                    " 10, 11, 12, 13, 14, 15," + //ability scores x6
                    " 0, 0, 2, 0," + //base attack, fort, ref, will
                    " 134, 32, 56, 89," + //money, light load, med load, heavy load
                    " 18, 6, 3, 1" + //ac, max hp, curr hp, in battle
                ");";


        sqLiteDatabase.execSQL(SQL_CREATE_CHARACTER);

        SQL_CREATE_CHARACTER = "INSERT INTO " +
                CharacterContract.CharacterEntry.TABLE_NAME +
                " VALUES (" +
                    "3, 'Charlie', 0, 1, 238, 532, 3.7, 3, 7," + // id, name, gender, raceID, age, weight, height, religionID, alignment
                    " 12, 13, 14, 15, 16, 17," + //ability scores x6
                    " 0, 0, 2, 0," + //base attack, fort, ref, will
                    "134, 32, 56, 89," + //money, light load, med load, heavy load
                    "18, 6, 3, 0" + //ac, max hp, curr hp, in battle
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_CHARACTER);

        SQL_CREATE_CHARACTER = "INSERT INTO " +
                CharacterContract.CharacterEntry.TABLE_NAME +
                " VALUES (" +
                    "4, 'David', 0, 0, 24, 172, 5.11, 2, 5," + // id, name, gender, raceID, age, weight, height, religionID, alignment
                    " 13, 14, 15, 16, 17, 18," + //ability scores x6
                    " 0, 0, 2, 0," + //base attack, fort, ref, will
                    "134, 32, 56, 89," + //money, light load, med load, heavy load
                    "18, 6, 3, 1" + //ac, max hp, curr hp, in battle
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_CHARACTER);
    }

    private void createAlice(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_CHARACTER = "INSERT INTO " +
                CharacterContract.CharacterEntry.TABLE_NAME +
                " VALUES (" +
                "1, 'Alice', 1, 0, 21, 155, 5.3, 1, 5," + // id, name, gender, raceID, age, weight, height, religionID, alignment
                " 10, 11, 12, 13, 14, 15," + //ability scores x6
                " 0, 0, 2, 0," + //base attack, fort, ref, will
                " 134, 32, 56, 89," + //money, light load, med load, heavy load
                " 18, 6, 3" + //ac, max hp, curr hp, in battle
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_CHARACTER);

        String SQL_CREATE_CHARACTER_SKILLS = "INSERT INTO " +
                CharacterContract.CharacterSkills.TABLE_NAME +
                " VALUES (" +
                "0, 6, 5, 0, 4, 1, 0" + // charID, skillID, tot, class, ranks, abMOD, miscMOD
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_CHARACTER_SKILLS);

        SQL_CREATE_CHARACTER_SKILLS = "INSERT INTO " +
                CharacterContract.CharacterSkills.TABLE_NAME +
                " VALUES (" +
                "0, 23, 12, 1, 12, 11, 0" + // charID, skillID, tot, class, ranks, abMOD, miscMOD
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_CHARACTER_SKILLS);

        SQL_CREATE_CHARACTER_SKILLS = "INSERT INTO " +
                CharacterContract.CharacterSkills.TABLE_NAME +
                " VALUES (" +
                "0, 36, 7, 1, 34, 2, 0" + // charID, skillID, tot, class, ranks, abMOD, miscMOD
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_CHARACTER_SKILLS);

        SQL_CREATE_CHARACTER_SKILLS = "INSERT INTO " +
                CharacterContract.CharacterSkills.TABLE_NAME +
                " VALUES (" +
                "0, 40, 10, 1, 15, 25, 0" + // charID, skillID, tot, class, ranks, abMOD, miscMOD
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_CHARACTER_SKILLS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CharacterContract.CharacterEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CharacterContract.InProgressEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CharacterContract.CharacterClasses.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CharacterContract.CharacterSpells.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CharacterContract.CharacterFeats.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CharacterContract.CharacterItems.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CharacterContract.CharacterArmor.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CharacterContract.CharacterWeapons.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
