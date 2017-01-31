package com.brave_bunny.dndhelper.database.inprogress;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jemma on 1/11/2017.
 */

public class InProgressDbHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "inprogress.db";

    public InProgressDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        addCharacterTable(sqLiteDatabase);
        addClericDomainTable(sqLiteDatabase);
        addSpellTable(sqLiteDatabase);
        addSkillTable(sqLiteDatabase);
        addFeatTable(sqLiteDatabase);
    }

    private void addCharacterTable(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_INPROGRESS_TABLE = "CREATE TABLE " +
                InProgressContract.CharacterEntry.TABLE_NAME + " (" +
                InProgressContract.CharacterEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                InProgressContract.CharacterEntry.COLUMN_NAME + " TEXT," +
                InProgressContract.CharacterEntry.COLUMN_GENDER + " INTEGER," +
                InProgressContract.CharacterEntry.COLUMN_RACE_ID + " INTEGER," +
                InProgressContract.CharacterEntry.COLUMN_CLASS_ID + " INTEGER," +
                InProgressContract.CharacterEntry.COLUMN_AGE + " STRING," +
                InProgressContract.CharacterEntry.COLUMN_WEIGHT + " STRING," +
                InProgressContract.CharacterEntry.COLUMN_HEIGHT + " STRING," +
                InProgressContract.CharacterEntry.COLUMN_RELIGION_ID + " INTEGER," +
                InProgressContract.CharacterEntry.COLUMN_ALIGN + " INTEGER," +

                InProgressContract.CharacterEntry.COLUMN_STR + " INTEGER NOT NULL," +
                InProgressContract.CharacterEntry.COLUMN_DEX + " INTEGER NOT NULL," +
                InProgressContract.CharacterEntry.COLUMN_CON + " INTEGER NOT NULL," +
                InProgressContract.CharacterEntry.COLUMN_INT + " INTEGER NOT NULL," +
                InProgressContract.CharacterEntry.COLUMN_WIS + " INTEGER NOT NULL," +
                InProgressContract.CharacterEntry.COLUMN_CHA + " INTEGER NOT NULL," +
                InProgressContract.CharacterEntry.COLUMN_ABILITY_1 + " INTEGER NOT NULL," +
                InProgressContract.CharacterEntry.COLUMN_ABILITY_2 + " INTEGER NOT NULL," +
                InProgressContract.CharacterEntry.COLUMN_ABILITY_3 + " INTEGER NOT NULL," +
                InProgressContract.CharacterEntry.COLUMN_ABILITY_4 + " INTEGER NOT NULL," +
                InProgressContract.CharacterEntry.COLUMN_ABILITY_5 + " INTEGER NOT NULL," +
                InProgressContract.CharacterEntry.COLUMN_ABILITY_6 + " INTEGER NOT NULL," +

                InProgressContract.CharacterEntry.COLUMN_MONEY + " INTEGER," +
                InProgressContract.CharacterEntry.COLUMN_LIGHT_LOAD + " REAL," +
                InProgressContract.CharacterEntry.COLUMN_MED_LOAD + " REAL," +
                InProgressContract.CharacterEntry.COLUMN_HEAVY_LOAD + " REAL," +

                InProgressContract.CharacterEntry.COLUMN_AC + " INTEGER," +
                InProgressContract.CharacterEntry.COLUMN_HP + " INTEGER," +

                InProgressContract.CharacterEntry.COLUMN_FAMILIAR_ID + " INTEGER )";

        sqLiteDatabase.execSQL(SQL_CREATE_INPROGRESS_TABLE);
    }

    public void addClericDomainTable(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_INPROGRESS_CLERIC_DOMAIN_TABLE = "CREATE TABLE " +
                InProgressContract.ClericDomainEntry.TABLE_NAME + " (" +
                InProgressContract.ClericDomainEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                InProgressContract.ClericDomainEntry.COLUMN_CHARACTER_ID + " INTEGER NOT NULL," +
                InProgressContract.ClericDomainEntry.COLUMN_DOMAIN_ID + " INTEGER NOT NULL )";

        sqLiteDatabase.execSQL(SQL_CREATE_INPROGRESS_CLERIC_DOMAIN_TABLE);
    }

    public void addSpellTable(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_INPROGRESS_SPELL_TABLE = "CREATE TABLE " +
                InProgressContract.SpellEntry.TABLE_NAME + " (" +
                InProgressContract.SpellEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                InProgressContract.SpellEntry.COLUMN_CHARACTER_ID + " INTEGER NOT NULL," +
                InProgressContract.SpellEntry.COLUMN_SPELL_ID + " INTEGER NOT NULL )";

        sqLiteDatabase.execSQL(SQL_CREATE_INPROGRESS_SPELL_TABLE);
    }

    public void addSkillTable(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_INPROGRESS_SKILL_TABLE = "CREATE TABLE " +
                InProgressContract.SkillEntry.TABLE_NAME + " (" +
                InProgressContract.SkillEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                InProgressContract.SkillEntry.COLUMN_CHARACTER_ID + " INTEGER NOT NULL," +
                InProgressContract.SkillEntry.COLUMN_SKILL_ID + " INTEGER NOT NULL," +
                InProgressContract.SkillEntry.COLUMN_RANKS + " INTEGER NOT NULL )";

        sqLiteDatabase.execSQL(SQL_CREATE_INPROGRESS_SKILL_TABLE);
    }

    public void addFeatTable(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_INPROGRESS_FEAT_TABLE = "CREATE TABLE " +
                InProgressContract.FeatEntry.TABLE_NAME + " (" +
                InProgressContract.FeatEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                InProgressContract.FeatEntry.COLUMN_CHARACTER_ID + " INTEGER NOT NULL," +
                InProgressContract.FeatEntry.COLUMN_FEAT_ID + " INTEGER NOT NULL )";

        sqLiteDatabase.execSQL(SQL_CREATE_INPROGRESS_FEAT_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + InProgressContract.CharacterEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + InProgressContract.ClericDomainEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + InProgressContract.SpellEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + InProgressContract.SkillEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + InProgressContract.FeatEntry.TABLE_NAME);
    }
}
