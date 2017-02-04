package com.brave_bunny.dndhelper.database.edition35;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jemma on 1/10/2017.
 */

public class RulesDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "edition35.db";

    private static SQLiteDatabase mDatabase;

    public RulesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        mDatabase = sqLiteDatabase;
        setupSkills();
        setupFeats();

        setupRaceData();

        setupClassData();
        setupClericData();
        setupClericDomains();
        setupFighterData();
        setupRogueData();
        setupWizardData();
        setupSpellData();
        setupFamiliarData();

        setupArmorData();
        setupWeaponData();
        setupItemData();
    }

    public void setupSkills() {
        final String SQL_CREATE_SKILL_TABLE = "CREATE TABLE " +
                RulesContract.SkillsEntry.TABLE_NAME + " (" +
                RulesContract.SkillsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RulesContract.SkillsEntry.COLUMN_NAME + " STRING NOT NULL," +
                RulesContract.SkillsEntry.COLUMN_BASE_SCORE + " INTEGER NOT NULL," +
                RulesContract.SkillsEntry.COLUMN_UNTRAINED + " INTEGER NOT NULL," +

                RulesContract.SkillsEntry.COLUMN_CLERIC + " INTEGER NOT NULL," +
                RulesContract.SkillsEntry.COLUMN_FIGHTER + " INTEGER NOT NULL," +
                RulesContract.SkillsEntry.COLUMN_ROGUE + " INTEGER NOT NULL," +
                RulesContract.SkillsEntry.COLUMN_WIZARD + " INTEGER NOT NULL," +
                RulesContract.SkillsEntry.COLUMN_ARMOR_PENALTY + " INTEGER NOT NULL," +
                RulesContract.SkillsEntry.COLUMN_DOUBLE_ARMOR_PENALTY + " INTEGER NOT NULL )";

        mDatabase.execSQL(SQL_CREATE_SKILL_TABLE);

        //if you change these, need to change familiar table skill reference
        insertInSkillTable("1, 'Appraise', 'INT', 1, 0, 0, 1, 0, 0, 0");
        insertInSkillTable("2, 'Balance', 'DEX', 1, 0, 0, 1, 0, 1, 0");
        insertInSkillTable("3, 'Bluff', 'CHA', 1, 0, 0, 1, 0, 0, 0");
        insertInSkillTable("4, 'Climb', 'STR', 1, 0, 1, 1, 0, 1, 0");
        insertInSkillTable("5, 'Concentrate', 'CON', 1, 1, 0, 0, 1, 0, 0");
        insertInSkillTable("6, 'Decipher Script', 'INT', 0, 0, 0, 1, 1, 0, 0");
        insertInSkillTable("7, 'Diplomacy', 'CHA', 1, 1, 0, 1, 0, 0, 0");
        insertInSkillTable("8, 'Disable Device', 'INT', 0, 0, 0, 1, 0, 0, 0");
        insertInSkillTable("9, 'Disguise', 'CHA', 1, 0, 0, 1, 0, 0, 0");
        insertInSkillTable("10, 'Escape Artist', 'DEX', 1, 0, 0, 1, 0, 1, 0");
        insertInSkillTable("11, 'Forgery', 'INT', 1, 0, 0, 1, 0, 0, 0");
        insertInSkillTable("12, 'Gather Information', 'CHA', 1, 0, 0, 1, 0, 0, 0");
        insertInSkillTable("13, 'Handle Animal', 'CHA', 0, 0, 1, 0, 0, 0, 0");
        insertInSkillTable("14, 'Heal', 'WIS', 1, 1, 0, 0, 0, 0, 0");
        insertInSkillTable("15, 'Hide', 'DEX', 1, 0, 0, 1, 0, 1, 0");
        insertInSkillTable("16, 'Intimidate', 'CHA', 1, 0, 1, 1, 0, 0, 0");
        insertInSkillTable("17, 'Jump', 'STR', 1, 0, 1, 1, 0, 1, 0");
        insertInSkillTable("18, 'Knowledge (arcana)', 'INT', 0, 1, 0, 0, 1, 0, 0");
        insertInSkillTable("19, 'Knowledge (architecture and engineering)', 'INT', 0, 0, 0, 0, 1, 0, 0");
        insertInSkillTable("20, 'Knowledge (dungeoneering)', 'INT', 0, 0, 0, 0, 1, 0, 0");
        insertInSkillTable("21, 'Knowledge (geography)', 'INT', 0, 0, 0, 0, 1, 0, 0");
        insertInSkillTable("22, 'Knowledge (history)', 'INT', 0, 1, 0, 0, 1, 0, 0");
        insertInSkillTable("23, 'Knowledge (local)', 'INT', 0, 0, 0, 1, 1, 0, 0");
        insertInSkillTable("24, 'Knowledge (nature)', 'INT', 0, 0, 0, 0, 1, 0, 0");
        insertInSkillTable("25, 'Knowledge (nobility and royalty)', 'INT', 0, 0, 0, 0, 1, 0, 0");
        insertInSkillTable("26, 'Knowledge (religion)', 'INT', 0, 1, 0, 0, 1, 0, 0");
        insertInSkillTable("27, 'Knowledge (the planes)', 'INT', 0, 1, 0, 0, 1, 0, 0");
        insertInSkillTable("28, 'Listen', 'WIS', 1, 0, 0, 1, 0, 0, 0");
        insertInSkillTable("29, 'Move Silently', 'DEX', 1, 0, 0, 1, 0, 1, 0");
        insertInSkillTable("30, 'Open Lock', 'DEX', 0, 0, 0, 1, 0, 0, 0");
        insertInSkillTable("31, 'Ride', 'DEX', 1, 0, 1, 0, 0, 0, 0");
        insertInSkillTable("32, 'Search', 'INT', 1, 0, 0, 1, 0, 0, 0");
        insertInSkillTable("33, 'Sense Motive', 'WIS', 1, 0, 0, 1, 0, 0, 0");
        insertInSkillTable("34, 'Slight of Hand', 'DEX', 0, 0, 0, 1, 0, 1, 0");
        insertInSkillTable("35, 'Speak Language', 'NONE', 0, 0, 0, 0, 0, 0, 0");
        insertInSkillTable("36, 'Spellcraft', 'INT', 0, 1, 0, 0, 1, 0, 0");
        insertInSkillTable("37, 'Spot', 'WIS', 1, 0, 0, 1, 0, 0, 0");
        insertInSkillTable("38, 'Survival', 'WIS', 1, 0, 0, 0, 0, 0, 0");
        insertInSkillTable("39, 'Swim', 'STR', 1, 0, 1, 1, 0, 0, 1");
        insertInSkillTable("40, 'Tumble', 'DEX', 0, 0, 0, 1, 0, 1, 0");
        insertInSkillTable("41, 'Use Magic Device', 'CHA', 0, 0, 0, 1, 0, 0, 0");
        insertInSkillTable("42, 'Use Rope', 'DEX', 1, 0, 0, 1, 0, 0, 0");
    }

    public void insertInSkillTable(String string) {
        String SQL_CREATE_SKILL = "INSERT INTO " + RulesContract.SkillsEntry.TABLE_NAME +
                " VALUES (" + string + ")";
        mDatabase.execSQL(SQL_CREATE_SKILL);
    }

    public void setupFeats() {
        final String SQL_CREATE_FEAT_TABLE = "CREATE TABLE " +
                RulesContract.FeatEntry.TABLE_NAME + " (" +
                RulesContract.FeatEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RulesContract.ClassEntry.COLUMN_NAME + " STRING NOT NULL )";

        mDatabase.execSQL(SQL_CREATE_FEAT_TABLE);

        insertInFeatTable("1, 'Acrobatic'");
        insertInFeatTable("2, 'Agile'");
        insertInFeatTable("3, 'Alertness'");
        insertInFeatTable("4, 'Animal Affinity'");
        insertInFeatTable("5, 'Armor Proficiency (Light)'");
        insertInFeatTable("6, 'Armor Proficiency (Medium)'");
        insertInFeatTable("7, 'Armor Proficiency (Heavy)'");
        insertInFeatTable("8, 'Athletic'");
        insertInFeatTable("9, 'Augment Summoning'");
    }

    public void insertInFeatTable(String string) {
        String SQL_CREATE_FEATS = "INSERT INTO " + RulesContract.FeatEntry.TABLE_NAME +
                " VALUES (" + string + ")";
        mDatabase.execSQL(SQL_CREATE_FEATS);
    }

    public void setupRaceData() {
        final String SQL_CREATE_CLASS_TABLE = "CREATE TABLE " +
                RulesContract.RaceEntry.TABLE_NAME + " (" +
                RulesContract.RaceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RulesContract.RaceEntry.COLUMN_NAME + " STRING NOT NULL," +
                RulesContract.RaceEntry.COLUMN_STR + " INTEGER NOT NULL," +
                RulesContract.RaceEntry.COLUMN_DEX + " INTEGER NOT NULL," +
                RulesContract.RaceEntry.COLUMN_CON + " INTEGER NOT NULL," +
                RulesContract.RaceEntry.COLUMN_INT + " INTEGER NOT NULL," +
                RulesContract.RaceEntry.COLUMN_WIS + " INTEGER NOT NULL," +
                RulesContract.RaceEntry.COLUMN_CHA + " INTEGER NOT NULL )";

        mDatabase.execSQL(SQL_CREATE_CLASS_TABLE);

        //                             STR DEX CON INT WIS CHA
        insertInRaceTable("1, 'Human',  0,  0,  0,  0,  0,  0");
        insertInRaceTable("2, 'Dwarf',  0,  0,  2,  0,  0, -2");
        insertInRaceTable("3, 'Elf',    0,  2, -2,  0,  0,  0");
    }

    public void insertInRaceTable(String string) {
        String SQL_CREATE_CLASSES = "INSERT INTO " + RulesContract.RaceEntry.TABLE_NAME +
                " VALUES (" + string + ")";
        mDatabase.execSQL(SQL_CREATE_CLASSES);
    }

    public void setupClassData() {
        final String SQL_CREATE_CLASS_TABLE = "CREATE TABLE " +
                RulesContract.ClassEntry.TABLE_NAME + " (" +
                RulesContract.ClassEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RulesContract.ClassEntry.COLUMN_NAME + " STRING NOT NULL," +
                RulesContract.ClassEntry.COLUMN_HIT_DIE + " INTEGER NOT NULL," +
                RulesContract.ClassEntry.COLUMN_STARTING_GOLD + " INTEGER NOT NULL )";

        mDatabase.execSQL(SQL_CREATE_CLASS_TABLE);

        insertInClassTable("1, 'Cleric', 8, 125");
        insertInClassTable("2, 'Fighter', 10, 150");
        insertInClassTable("3, 'Rogue', 6, 125");
        insertInClassTable("4, 'Wizard', 4, 75");
    }

    public void insertInClassTable(String string) {
        String SQL_CREATE_CLASSES = "INSERT INTO " + RulesContract.ClassEntry.TABLE_NAME +
                " VALUES (" + string + ")";
        mDatabase.execSQL(SQL_CREATE_CLASSES);
    }

    public void setupClericData() {
        final String SQL_CREATE_CLERIC_TABLE = "CREATE TABLE " +
                RulesContract.ClericEntry.TABLE_NAME + " (" +
                RulesContract.ClericEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                RulesContract.ClericEntry.COLUMN_BASE_ATTACK_1 + " INTEGER NOT NULL," +
                RulesContract.ClericEntry.COLUMN_BASE_ATTACK_2 + " INTEGER NOT NULL," +
                RulesContract.ClericEntry.COLUMN_BASE_ATTACK_3 + " INTEGER NOT NULL," +
                RulesContract.ClericEntry.COLUMN_FORT + " INTEGER NOT NULL," +
                RulesContract.ClericEntry.COLUMN_REF + " INTEGER NOT NULL," +
                RulesContract.ClericEntry.COLUMN_WILL + " INTEGER NOT NULL," +

                RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L0 + " INTEGER NOT NULL," +
                RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L1 + " INTEGER NOT NULL," +
                RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L2 + " INTEGER NOT NULL," +
                RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L3 + " INTEGER NOT NULL," +
                RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L4 + " INTEGER NOT NULL," +
                RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L5 + " INTEGER NOT NULL," +
                RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L6 + " INTEGER NOT NULL," +
                RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L7 + " INTEGER NOT NULL," +
                RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L8 + " INTEGER NOT NULL," +
                RulesContract.ClericEntry.COLUMN_SPELLS_PER_DAY_L9 + " INTEGER NOT NULL )";

        mDatabase.execSQL(SQL_CREATE_CLERIC_TABLE);

        //note that these are accumulative, so add row when add level
        insertInClericTable("1, 0, 0, 0, 2, 0, 2, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0");
        insertInClericTable("2, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0");
        insertInClericTable("3, 1, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0");
        insertInClericTable("4, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0");
        insertInClericTable("5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0");
        insertInClericTable("6, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0");
        insertInClericTable("7, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 2, 0, 0, 0, 0, 0");
        insertInClericTable("8, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0");
        insertInClericTable("9, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0");
        insertInClericTable("10, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0");
        insertInClericTable("11, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 0, 0, 0");
        insertInClericTable("12, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0");
        insertInClericTable("13, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 0, 0");
        insertInClericTable("14, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0");
        insertInClericTable("15, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 0");
        insertInClericTable("16, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0");
        insertInClericTable("17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2");
        insertInClericTable("18, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1");
        insertInClericTable("19, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1");
        insertInClericTable("20, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1");

    }

    public void insertInClericTable(String string) {
        String SQL_CREATE_CLERIC_DATA = "INSERT INTO " + RulesContract.ClericEntry.TABLE_NAME +
                " VALUES (" + string + ")";
        mDatabase.execSQL(SQL_CREATE_CLERIC_DATA);
    }

    public void setupClericDomains() {
        final String SQL_CREATE_CLERIC_DOMAIN_TABLE = "CREATE TABLE " +
                RulesContract.ClericDomainsEntry.TABLE_NAME + " (" +
                RulesContract.ClericDomainsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RulesContract.ClericDomainsEntry.COLUMN_NAME + " STRING NOT NULL," +

                RulesContract.ClericDomainsEntry.COLUMN_LAWFUL_GOOD + " INTEGER NOT NULL," +
                RulesContract.ClericDomainsEntry.COLUMN_LAWFUL_NEUTRAL + " INTEGER NOT NULL," +
                RulesContract.ClericDomainsEntry.COLUMN_LAWFUL_EVIL + " INTEGER NOT NULL," +
                RulesContract.ClericDomainsEntry.COLUMN_NEUTRAL_GOOD + " INTEGER NOT NULL," +
                RulesContract.ClericDomainsEntry.COLUMN_NEUTRAL + " INTEGER NOT NULL," +
                RulesContract.ClericDomainsEntry.COLUMN_NEUTRAL_EVIL + " INTEGER NOT NULL," +
                RulesContract.ClericDomainsEntry.COLUMN_CHAOTIC_GOOD + " INTEGER NOT NULL," +
                RulesContract.ClericDomainsEntry.COLUMN_CHAOTIC_NEUTRAL + " INTEGER NOT NULL," +
                RulesContract.ClericDomainsEntry.COLUMN_CHAOTIC_EVIL + " INTEGER NOT NULL )";

        mDatabase.execSQL(SQL_CREATE_CLERIC_DOMAIN_TABLE);

        insertInClericDomainTable("1, 'Air', 0, 0, 0, 0, 1, 0, 0, 0, 0");
        insertInClericDomainTable("2, 'Animal', 0, 0, 0, 1, 1, 0, 0, 0, 0");
        insertInClericDomainTable("3, 'Chaos', 0, 0, 0, 0, 0, 0, 1, 1, 1");
        insertInClericDomainTable("4, 'Death', 0, 1, 0, 0, 0, 1, 0, 0, 0");
        insertInClericDomainTable("5, 'Desctruction', 0, 1, 1, 0, 0, 0, 0, 0, 0");
        insertInClericDomainTable("6, 'Earth', 1, 0, 0, 0, 1, 0, 0, 0, 0");
        insertInClericDomainTable("7, 'Evil', 0, 0, 1, 0, 0, 1, 0, 0, 1");
        insertInClericDomainTable("8, 'Fire', 0, 0, 0, 0, 1, 0, 0, 0, 0");
        insertInClericDomainTable("9, 'Good', 1, 0, 0, 1, 0, 0, 1, 0, 0");
        insertInClericDomainTable("10, 'Healing', 0, 0, 0, 1, 0, 0, 0, 0, 0");
        insertInClericDomainTable("11, 'Knowledge', 0, 0, 0, 0, 1, 1, 0, 0, 0");
        insertInClericDomainTable("12, 'Law', 1, 1, 1, 0, 0, 0, 0, 0, 0");
        insertInClericDomainTable("13, 'Luck', 0, 0, 0, 0, 0, 0, 1, 1, 0");
        insertInClericDomainTable("14, 'Magic', 0, 1, 0, 0, 1, 1, 0, 0, 0");
        insertInClericDomainTable("15, 'Plant', 0, 0, 0, 1, 1, 0, 0, 0, 0");
        insertInClericDomainTable("16, 'Protection', 1, 1, 0, 1, 1, 0, 1, 0, 0");
        insertInClericDomainTable("17, 'Strength', 0, 1, 0, 1, 0, 0, 1, 0, 1");
        insertInClericDomainTable("18, 'Sun', 0, 0, 0, 1, 0, 0, 0, 0, 0");
        insertInClericDomainTable("19, 'Travel', 0, 0, 0, 0, 1, 0, 0, 0, 0");
        insertInClericDomainTable("20, 'Trickery', 0, 0, 0, 1, 1, 1, 0, 1, 1");
        insertInClericDomainTable("21, 'War', 1, 0, 1, 0, 0, 0, 1, 0, 1");
        insertInClericDomainTable("22, 'Water', 0, 0, 0, 0, 1, 0, 0, 0, 0");

    }

    public void insertInClericDomainTable(String string) {
        String SQL_CREATE_CLERIC_DOMAIN = "INSERT INTO " + RulesContract.ClericDomainsEntry.TABLE_NAME +
                " VALUES (" + string + ")";
        mDatabase.execSQL(SQL_CREATE_CLERIC_DOMAIN);
    }

    public void setupFighterData() {
        final String SQL_CREATE_FIGHTER_TABLE = "CREATE TABLE " +
                RulesContract.FighterEntry.TABLE_NAME + " (" +
                RulesContract.FighterEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                RulesContract.FighterEntry.COLUMN_BASE_ATTACK_1 + " INTEGER NOT NULL," +
                RulesContract.FighterEntry.COLUMN_BASE_ATTACK_2 + " INTEGER NOT NULL," +
                RulesContract.FighterEntry.COLUMN_BASE_ATTACK_3 + " INTEGER NOT NULL," +
                RulesContract.FighterEntry.COLUMN_BASE_ATTACK_4 + " INTEGER NOT NULL," +
                RulesContract.FighterEntry.COLUMN_FORT + " INTEGER NOT NULL," +
                RulesContract.FighterEntry.COLUMN_REF + " INTEGER NOT NULL," +
                RulesContract.FighterEntry.COLUMN_WILL + " INTEGER NOT NULL )";

        mDatabase.execSQL(SQL_CREATE_FIGHTER_TABLE);

        //note that these are accumulative, so add row when add level
        insertInFighterTable("1,   1, 0, 0, 0, 2, 0, 0");
        insertInFighterTable("2,   1, 0, 0, 0, 1, 0, 0");
        insertInFighterTable("3,   1, 0, 0, 0, 0, 1, 1");
        insertInFighterTable("4,   1, 0, 0, 0, 1, 0, 0");
        insertInFighterTable("5,   1, 0, 0, 0, 0, 0, 0");
        insertInFighterTable("6,   1, 1, 0, 0, 1, 1, 1");
        insertInFighterTable("7,   1, 1, 0, 0, 0, 0, 0");
        insertInFighterTable("8,   1, 1, 0, 0, 1, 0, 0");
        insertInFighterTable("9,   1, 1, 0, 0, 0, 1, 1");
        insertInFighterTable("10,  1, 1, 0, 0, 1, 0, 0");
        insertInFighterTable("11,  1, 1, 1, 0, 0, 0, 0");
        insertInFighterTable("12,  1, 1, 1, 0, 1, 1, 1");
        insertInFighterTable("13,  1, 1, 1, 0, 0, 0, 0");
        insertInFighterTable("14,  1, 1, 1, 0, 1, 0, 0");
        insertInFighterTable("15,  1, 1, 1, 0, 0, 1, 1");
        insertInFighterTable("16,  1, 1, 1, 1, 1, 0, 0");
        insertInFighterTable("17,  1, 1, 1, 1, 0, 0, 0");
        insertInFighterTable("18,  1, 1, 1, 1, 1, 1, 1");
        insertInFighterTable("19,  1, 1, 1, 1, 0, 0, 0");
        insertInFighterTable("20,  1, 1, 1, 1, 1, 0, 0");

    }

    public void insertInFighterTable(String string) {
        String SQL_CREATE_FIGHTER_DATA = "INSERT INTO " + RulesContract.FighterEntry.TABLE_NAME +
                " VALUES (" + string + ")";
        mDatabase.execSQL(SQL_CREATE_FIGHTER_DATA);
    }

    public void setupRogueData() {
        final String SQL_CREATE_ROGUE_TABLE = "CREATE TABLE " +
                RulesContract.RogueEntry.TABLE_NAME + " (" +
                RulesContract.RogueEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                RulesContract.RogueEntry.COLUMN_BASE_ATTACK_1 + " INTEGER NOT NULL," +
                RulesContract.RogueEntry.COLUMN_BASE_ATTACK_2 + " INTEGER NOT NULL," +
                RulesContract.RogueEntry.COLUMN_BASE_ATTACK_3 + " INTEGER NOT NULL," +
                RulesContract.RogueEntry.COLUMN_FORT + " INTEGER NOT NULL," +
                RulesContract.RogueEntry.COLUMN_REF + " INTEGER NOT NULL," +
                RulesContract.RogueEntry.COLUMN_WILL + " INTEGER NOT NULL )";

        mDatabase.execSQL(SQL_CREATE_ROGUE_TABLE);

        //note that these are accumulative, so add row when add level
        insertInRogueTable("1,  0, 0, 0, 0, 2, 0");
        insertInRogueTable("2,  1, 0, 0, 0, 1, 0");
        insertInRogueTable("3,  1, 0, 0, 1, 0, 1");
        insertInRogueTable("4,  1, 0, 0, 0, 1, 0");
        insertInRogueTable("5,  0, 0, 0, 0, 0, 0");
        insertInRogueTable("6,  1, 0, 0, 1, 1, 1");
        insertInRogueTable("7,  1, 0, 0, 0, 0, 0");
        insertInRogueTable("8,  1, 1, 0, 0, 1, 0");
        insertInRogueTable("9,  0, 0, 0, 1, 0, 1");
        insertInRogueTable("10, 1, 1, 0, 0, 1, 0");
        insertInRogueTable("11, 1, 1, 0, 0, 0, 0");
        insertInRogueTable("12, 1, 1, 0, 1, 1, 1");
        insertInRogueTable("13, 0, 0, 0, 0, 0, 0");
        insertInRogueTable("14, 1, 1, 0, 0, 1, 0");
        insertInRogueTable("15, 1, 1, 1, 1, 0, 1");
        insertInRogueTable("16, 1, 1, 1, 0, 1, 0");
        insertInRogueTable("17, 0, 0, 0, 0, 0, 0");
        insertInRogueTable("18, 1, 1, 1, 1, 1, 1");
        insertInRogueTable("19, 1, 1, 1, 0, 0, 0");
        insertInRogueTable("20, 1, 1, 1, 0, 1, 0");

    }

    public void insertInRogueTable(String string) {
        String SQL_CREATE_ROGUE_DATA = "INSERT INTO " + RulesContract.RogueEntry.TABLE_NAME +
                " VALUES (" + string + ")";
        mDatabase.execSQL(SQL_CREATE_ROGUE_DATA);
    }

    public void setupWizardData() {
        final String SQL_CREATE_WIZARD_TABLE = "CREATE TABLE " +
                RulesContract.WizardEntry.TABLE_NAME + " (" +
                RulesContract.WizardEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                RulesContract.WizardEntry.COLUMN_BASE_ATTACK_1 + " INTEGER NOT NULL," +
                RulesContract.WizardEntry.COLUMN_BASE_ATTACK_2 + " INTEGER NOT NULL," +
                RulesContract.WizardEntry.COLUMN_FORT + " INTEGER NOT NULL," +
                RulesContract.WizardEntry.COLUMN_REF + " INTEGER NOT NULL," +
                RulesContract.WizardEntry.COLUMN_WILL + " INTEGER NOT NULL," +

                RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L0 + " INTEGER NOT NULL," +
                RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L1 + " INTEGER NOT NULL," +
                RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L2 + " INTEGER NOT NULL," +
                RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L3 + " INTEGER NOT NULL," +
                RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L4 + " INTEGER NOT NULL," +
                RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L5 + " INTEGER NOT NULL," +
                RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L6 + " INTEGER NOT NULL," +
                RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L7 + " INTEGER NOT NULL," +
                RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L8 + " INTEGER NOT NULL," +
                RulesContract.WizardEntry.COLUMN_SPELLS_PER_DAY_L9 + " INTEGER NOT NULL )";

        mDatabase.execSQL(SQL_CREATE_WIZARD_TABLE);

        //note that these are accumulative, so add row when add level
        insertInWizardTable("1,  0, 0, 0, 0, 2, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0");
        insertInWizardTable("2,  1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0");
        insertInWizardTable("3,  0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0");
        insertInWizardTable("4,  1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0");
        insertInWizardTable("5,  0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0");
        insertInWizardTable("6,  1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0");
        insertInWizardTable("7,  0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0");
        insertInWizardTable("8,  1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0");
        insertInWizardTable("9,  0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0");
        insertInWizardTable("10, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0");
        insertInWizardTable("11, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0");
        insertInWizardTable("12, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0");
        insertInWizardTable("13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0");
        insertInWizardTable("14, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0");
        insertInWizardTable("15, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0");
        insertInWizardTable("16, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0");
        insertInWizardTable("17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1");
        insertInWizardTable("18, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1");
        insertInWizardTable("19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1");
        insertInWizardTable("20, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1");

    }

    public void insertInWizardTable(String string) {
        String SQL_CREATE_CLERIC_DATA = "INSERT INTO " + RulesContract.WizardEntry.TABLE_NAME +
                " VALUES (" + string + ")";
        mDatabase.execSQL(SQL_CREATE_CLERIC_DATA);
    }

    public void setupSpellData() {
        final String SQL_CREATE_SPELL_TABLE = "CREATE TABLE " +
                RulesContract.SpellsEntry.TABLE_NAME + " (" +
                RulesContract.SpellsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RulesContract.SpellsEntry.COLUMN_NAME + " STRING NOT NULL," +
                RulesContract.SpellsEntry.COLUMN_LEVEL + " INTEGER NOT NULL )";

        mDatabase.execSQL(SQL_CREATE_SPELL_TABLE);

        // level 0 spells
        insertInSpellTable("1, 'Resistance', 0");
        insertInSpellTable("2, 'Acid Splash', 0");
        insertInSpellTable("3, 'Detect Poison', 0");
        insertInSpellTable("4, 'Detect Magic', 0");
        insertInSpellTable("5, 'Read Magic', 0");
        insertInSpellTable("6, 'Daze', 0");
        insertInSpellTable("7, 'Dancing Lights', 0");
        insertInSpellTable("8, 'Flare', 0");
        insertInSpellTable("9, 'Light', 0");
        insertInSpellTable("10, 'Ray of Frost', 0");
        insertInSpellTable("11, 'Ghost Sound', 0");
        insertInSpellTable("12, 'Disrupt Undead', 0");
        insertInSpellTable("13, 'Touch of Fatigue', 0");
        insertInSpellTable("14, 'Mage Hand', 0");
        insertInSpellTable("15, 'Mending', 0");
        insertInSpellTable("16, 'Message', 0");
        insertInSpellTable("17, 'Open/Close', 0");
        insertInSpellTable("18, 'Arcane Mark', 0");
        insertInSpellTable("19, 'Prestidigitation', 0");

        // level 1 spells
        insertInSpellTable("20, 'Alarm', 1");
        insertInSpellTable("21, 'Endure Elements', 1");
        insertInSpellTable("22, 'Hold Portal', 1");
        insertInSpellTable("23, 'Protection from Chaos/Evil/Good/Law', 1");
        insertInSpellTable("24, 'Shield', 1");
        insertInSpellTable("25, 'Grease', 1");
        insertInSpellTable("26, 'Mage Armor', 1");
        insertInSpellTable("27, 'Mount', 1");
        insertInSpellTable("28, 'Obscuring Mist', 1");
        insertInSpellTable("29, 'Summon Monster I', 1");
        insertInSpellTable("30, 'Unseen Servant', 1");
        insertInSpellTable("31, 'Comprehend Languages', 1");
        insertInSpellTable("32, 'Detect Secret Doors', 1");
        insertInSpellTable("33, 'Detect Undead', 1");
        insertInSpellTable("34, 'Identify', 1");
        insertInSpellTable("35, 'True Strike', 1");
        insertInSpellTable("36, 'Charm Person', 1");
        insertInSpellTable("37, 'Hypnotism', 1");
        insertInSpellTable("38, 'Sleep', 1");
        insertInSpellTable("39, 'Burning Hands', 1");
        insertInSpellTable("40, 'Magic Missile', 1");
        insertInSpellTable("41, 'Shocking Grasp', 1");
        insertInSpellTable("42, 'Tensers Floating Disk', 1");
        insertInSpellTable("43, 'Color Spray', 1");
        insertInSpellTable("44, 'Disguise Self', 1");
        insertInSpellTable("45, 'Nystuls Magic Aura', 1");
        insertInSpellTable("46, 'Silent Image', 1");
        insertInSpellTable("47, 'Ventriloquism', 1");
        insertInSpellTable("48, 'Cause Fear', 1");
        insertInSpellTable("49, 'Chill Touch', 1");
        insertInSpellTable("50, 'Ray of Enfeeblement', 1");
        insertInSpellTable("51, 'Animate Rope', 1");
        insertInSpellTable("52, 'Enlarge Person', 1");
        insertInSpellTable("53, 'Erase', 1");
        insertInSpellTable("54, 'Expeditious Retreat', 1");
        insertInSpellTable("55, 'Feather Fall', 1");
        insertInSpellTable("56, 'Jump', 1");
        insertInSpellTable("57, 'Magic Weapon', 1");
        insertInSpellTable("58, 'Reduce Person', 1");

    }

    public void insertInSpellTable(String string) {
        String SQL_CREATE_SPELLS = "INSERT INTO " + RulesContract.SpellsEntry.TABLE_NAME +
                " VALUES (" + string + ")";
        mDatabase.execSQL(SQL_CREATE_SPELLS);
    }

    public void setupFamiliarData() {
        final String SQL_CREATE_FAMILIAR_TABLE = "CREATE TABLE " +
                RulesContract.FamiliarEntry.TABLE_NAME + " (" +
                RulesContract.FamiliarEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RulesContract.FamiliarEntry.COLUMN_NAME + " STRING NOT NULL," +
                RulesContract.FamiliarEntry.COLUMN_SKILL_ID + " INTEGER NOT NULL," +
                RulesContract.FamiliarEntry.COLUMN_SKILL_BONUS + " INTEGER NOT NULL )";

        mDatabase.execSQL(SQL_CREATE_FAMILIAR_TABLE);

        insertInFamiliarTable("1, 'Bat', 28, 3");
        insertInFamiliarTable("2, 'Cat', 29, 3");
        insertInFamiliarTable("3, 'Hawk', 37, 3");
        insertInFamiliarTable("4, 'Lizard', 4, 3");
        insertInFamiliarTable("5, 'Owl', 37, 3");
        insertInFamiliarTable("6, 'Raven', 1, 3");
        insertInFamiliarTable("7, 'Snake', 3, 3");
    }

    public void insertInFamiliarTable(String string) {
        String SQL_CREATE_FAMILIARS = "INSERT INTO " + RulesContract.FamiliarEntry.TABLE_NAME +
                " VALUES (" + string + ")";
        mDatabase.execSQL(SQL_CREATE_FAMILIARS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RulesContract.SkillsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RulesContract.ClericEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RulesContract.ClericDomainsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RulesContract.FighterEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RulesContract.RogueEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RulesContract.WizardEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RulesContract.SpellsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RulesContract.FamiliarEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void setupArmorData() {
        final String SQL_CREATE_ARMOR_TABLE = "CREATE TABLE " +
                RulesContract.ArmorEntry.TABLE_NAME + " (" +
                RulesContract.ArmorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RulesContract.ArmorEntry.COLUMN_NAME + " STRING NOT NULL," +
                RulesContract.ArmorEntry.COLUMN_ARMOR_WEIGHT + " REAL NOT NULL," +
                RulesContract.ArmorEntry.COLUMN_ARMOR_COST + " REAL NOT NULL )";

        mDatabase.execSQL(SQL_CREATE_ARMOR_TABLE);

        // LIGHT ARMOR
        insertInArmorTable("1, 'Padded Armor', 10, 5");
        insertInArmorTable("2, 'Leather Armor', 15, 10");
        insertInArmorTable("3, 'Studded Leather Armor', 20, 25");
        insertInArmorTable("4, 'Chain Shirt', 25, 100");

        // MEDIUM ARMOR
        insertInArmorTable("5, 'Hide Armor', 25, 15");
        insertInArmorTable("6, 'Scale Mail Armor', 30, 50");
        insertInArmorTable("7, 'Chainmail Armor', 40, 150");
        insertInArmorTable("8, 'Breastplace Armor', 30, 200");

        //HEAVY ARMOR
        insertInArmorTable("9, 'Splint Mail Armor', 45, 200");
        insertInArmorTable("10, 'Banded Mail Armor', 35, 250");
        insertInArmorTable("11, 'Half-Plate Armor', 50, 600");
        insertInArmorTable("12, 'Full Plate Armor', 50, 1500");

    }

    public void insertInArmorTable(String string) {
        String SQL_CREATE_ARMOR = "INSERT INTO " + RulesContract.ArmorEntry.TABLE_NAME +
                " VALUES (" + string + ")";
        mDatabase.execSQL(SQL_CREATE_ARMOR);
    }

    public void setupWeaponData() {
        final String SQL_CREATE_WEAPON_TABLE = "CREATE TABLE " +
                RulesContract.WeaponEntry.TABLE_NAME + " (" +
                RulesContract.WeaponEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RulesContract.WeaponEntry.COLUMN_NAME + " STRING NOT NULL," +
                RulesContract.WeaponEntry.COLUMN_WEAPON_WEIGHT + " REAL NOT NULL," +
                RulesContract.WeaponEntry.COLUMN_WEAPON_COST + " REAL NOT NULL )";

        mDatabase.execSQL(SQL_CREATE_WEAPON_TABLE);

        // SIMPLE WEAPONS
        // UNARMED ATTACKS
        insertInWeaponTable("1, 'Gauntlet', 1, 2");
        insertInWeaponTable("2, 'Unarmed Strike', 0, 0");
        // LIGHT MELEE WEAPONS
        insertInWeaponTable("3, 'Dagger', 1, 2");
        insertInWeaponTable("4, 'Light Mace', 4, 5");
        insertInWeaponTable("5, 'Sickle', 2, 6");
        // ONE-HANDED MELEE WEAPONS
        insertInWeaponTable("6, 'Club', 3, 0");
        insertInWeaponTable("7, 'Heavy Mace', 8, 12");
        insertInWeaponTable("8, 'Morningstar', 6, 8");
        insertInWeaponTable("9, 'Shortspear', 3, 1");
        // TWO-HANDED MELEE WEAPONS
        insertInWeaponTable("10, 'Longspear', 9, 5");
        insertInWeaponTable("11, 'Quarterstaff', 4, 0");
        insertInWeaponTable("12, 'Spear', 6, 2");
        // RANGED WEAPONS
        insertInWeaponTable("13, 'Heavy Crossbow', 8, 50");
        insertInWeaponTable("14, 'Light Crossbow', 4, 35");
        insertInWeaponTable("15, 'Dart', 0.5, 0.5");
        insertInWeaponTable("16, 'Javelin', 2, 1");
        insertInWeaponTable("17, 'Sling', 0, 0");
    }

    public void insertInWeaponTable(String string) {
        String SQL_CREATE_WEAPON = "INSERT INTO " + RulesContract.WeaponEntry.TABLE_NAME +
                " VALUES (" + string + ")";
        mDatabase.execSQL(SQL_CREATE_WEAPON);
    }

    public void setupItemData() {
        final String SQL_CREATE_ITEM_TABLE = "CREATE TABLE " +
                RulesContract.ItemEntry.TABLE_NAME + " (" +
                RulesContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RulesContract.ItemEntry.COLUMN_NAME + " STRING NOT NULL," +
                RulesContract.ItemEntry.COLUMN_ITEM_WEIGHT + " REAL NOT NULL," +
                RulesContract.ItemEntry.COLUMN_ITEM_COST + " REAL NOT NULL )";

        mDatabase.execSQL(SQL_CREATE_ITEM_TABLE);

        // ADVENTURING GEAR
        insertInItemTable("1, 'Backpack (empty)', 2, 2");
        insertInItemTable("2, 'Bedroll', 5, 0.1");
        insertInItemTable("3, 'Candle', 0, 0.01");
        insertInItemTable("4, 'Chain (10 ft)', 2, 30");
        insertInItemTable("5, 'Crowbar', 5, 2");
        insertInItemTable("6, 'Flint and Steel', 0, 1");
        insertInItemTable("7, 'Grappling Hook', 4, 1");
        insertInItemTable("8, 'Ladder (10 foot)', 20, 0.05");
        insertInItemTable("9, 'Common Lamp', 1, 0.1");
        insertInItemTable("10, 'Bullseye Lantern', 3, 12");
        insertInItemTable("11, 'Hooded Lantern', 2, 7");
        insertInItemTable("12, 'Belt Pouch (empty)', 0.5, 1");
        insertInItemTable("13, 'Trail Rations', 1, 0.5");
        insertInItemTable("14, 'Hempen Rope (50 ft)', 10, 1");
        insertInItemTable("15, 'Silk Rope (50 ft)', 5, 10");
        insertInItemTable("16, 'Sack (empty)', 0.5, 0.1");
        insertInItemTable("17, 'Shovel', 8, 2");
        insertInItemTable("18, 'Tent', 20, 10");
        insertInItemTable("19, 'Torch', 1, 0.01");
        insertInItemTable("20, 'Waterskin', 4, 1");
    }

    public void insertInItemTable(String string) {
        String SQL_CREATE_ITEM = "INSERT INTO " + RulesContract.ItemEntry.TABLE_NAME +
                " VALUES (" + string + ")";
        mDatabase.execSQL(SQL_CREATE_ITEM);
    }
}
