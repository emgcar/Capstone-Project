package com.brave_bunny.dndhelper.database.edition35;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.brave_bunny.dndhelper.database.CharacterContract;

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

        setupClericData();
        setupClericDomains();
        setupFighterData();
        setupRogueData();
        setupWizardData();
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

    public void setupClericData() {
        final String SQL_CREATE_CLERIC_TABLE = "CREATE TABLE " +
                RulesContract.ClericEntry.TABLE_NAME + " (" +
                RulesContract.ClericEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RulesContract.ClericEntry.COLUMN_LEVEL + " STRING NOT NULL," +

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
        insertInClericTable("1, '1', 0, 0, 0, 2, 0, 2, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0");
        insertInClericTable("2, '2', 1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0");
        insertInClericTable("3, '3', 1, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0");
        insertInClericTable("4, '4', 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0");
        insertInClericTable("5, '5', 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0");
        insertInClericTable("6, '6', 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0");
        insertInClericTable("7, '7', 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 2, 0, 0, 0, 0, 0");
        insertInClericTable("8, '8', 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0");
        insertInClericTable("9, '9', 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0");
        insertInClericTable("10, '10', 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0");
        insertInClericTable("11, '11', 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 0, 0, 0");
        insertInClericTable("12, '12', 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0");
        insertInClericTable("13, '13', 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 0, 0");
        insertInClericTable("14, '14', 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0");
        insertInClericTable("15, '15', 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 0");
        insertInClericTable("16, '16', 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0");
        insertInClericTable("17, '17', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2");
        insertInClericTable("18, '18', 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1");
        insertInClericTable("19, '19', 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1");
        insertInClericTable("20, '20', 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1");

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
                RulesContract.ClericDomainsEntry.COLUMN_LAWFUL_EVIL + " INTEGER NOT NULL )";

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
                RulesContract.FighterEntry.COLUMN_LEVEL + " STRING NOT NULL," +

                RulesContract.FighterEntry.COLUMN_BASE_ATTACK_1 + " INTEGER NOT NULL," +
                RulesContract.FighterEntry.COLUMN_BASE_ATTACK_2 + " INTEGER NOT NULL," +
                RulesContract.FighterEntry.COLUMN_BASE_ATTACK_3 + " INTEGER NOT NULL," +
                RulesContract.FighterEntry.COLUMN_BASE_ATTACK_4 + " INTEGER NOT NULL," +
                RulesContract.FighterEntry.COLUMN_FORT + " INTEGER NOT NULL," +
                RulesContract.FighterEntry.COLUMN_REF + " INTEGER NOT NULL," +
                RulesContract.FighterEntry.COLUMN_WILL + " INTEGER NOT NULL )";

        mDatabase.execSQL(SQL_CREATE_FIGHTER_TABLE);

        //note that these are accumulative, so add row when add level
        insertInFighterTable("1, '1',   1, 0, 0, 0, 2, 0, 0");
        insertInFighterTable("2, '2',   1, 0, 0, 0, 1, 0, 0");
        insertInFighterTable("3, '3',   1, 0, 0, 0, 0, 1, 1");
        insertInFighterTable("4, '4',   1, 0, 0, 0, 1, 0, 0");
        insertInFighterTable("5, '5',   1, 0, 0, 0, 0, 0, 0");
        insertInFighterTable("6, '6',   1, 1, 0, 0, 1, 1, 1");
        insertInFighterTable("7, '7',   1, 1, 0, 0, 0, 0, 0");
        insertInFighterTable("8, '8',   1, 1, 0, 0, 1, 0, 0");
        insertInFighterTable("9, '9',   1, 1, 0, 0, 0, 1, 1");
        insertInFighterTable("10, '10', 1, 1, 0, 0, 1, 0, 0");
        insertInFighterTable("11, '11', 1, 1, 1, 0, 0, 0, 0");
        insertInFighterTable("12, '12', 1, 1, 1, 0, 1, 1, 1");
        insertInFighterTable("13, '13', 1, 1, 1, 0, 0, 0, 0");
        insertInFighterTable("14, '14', 1, 1, 1, 0, 1, 0, 0");
        insertInFighterTable("15, '15', 1, 1, 1, 0, 0, 1, 1");
        insertInFighterTable("16, '16', 1, 1, 1, 1, 1, 0, 0");
        insertInFighterTable("17, '17', 1, 1, 1, 1, 0, 0, 0");
        insertInFighterTable("18, '18', 1, 1, 1, 1, 1, 1, 1");
        insertInFighterTable("19, '19', 1, 1, 1, 1, 0, 0, 0");
        insertInFighterTable("20, '20', 1, 1, 1, 1, 1, 0, 0");

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
                RulesContract.RogueEntry.COLUMN_LEVEL + " STRING NOT NULL," +

                RulesContract.RogueEntry.COLUMN_BASE_ATTACK_1 + " INTEGER NOT NULL," +
                RulesContract.RogueEntry.COLUMN_BASE_ATTACK_2 + " INTEGER NOT NULL," +
                RulesContract.RogueEntry.COLUMN_BASE_ATTACK_3 + " INTEGER NOT NULL," +
                RulesContract.RogueEntry.COLUMN_FORT + " INTEGER NOT NULL," +
                RulesContract.RogueEntry.COLUMN_REF + " INTEGER NOT NULL," +
                RulesContract.RogueEntry.COLUMN_WILL + " INTEGER NOT NULL )";

        mDatabase.execSQL(SQL_CREATE_ROGUE_TABLE);

        //note that these are accumulative, so add row when add level
        insertInRogueTable("1, '1',   0, 0, 0, 0, 2, 0");
        insertInRogueTable("2, '2',   1, 0, 0, 0, 1, 0");
        insertInRogueTable("3, '3',   1, 0, 0, 1, 0, 1");
        insertInRogueTable("4, '4',   1, 0, 0, 0, 1, 0");
        insertInRogueTable("5, '5',   0, 0, 0, 0, 0, 0");
        insertInRogueTable("6, '6',   1, 0, 0, 1, 1, 1");
        insertInRogueTable("7, '7',   1, 0, 0, 0, 0, 0");
        insertInRogueTable("8, '8',   1, 1, 0, 0, 1, 0");
        insertInRogueTable("9, '9',   0, 0, 0, 1, 0, 1");
        insertInRogueTable("10, '10', 1, 1, 0, 0, 1, 0");
        insertInRogueTable("11, '11', 1, 1, 0, 0, 0, 0");
        insertInRogueTable("12, '12', 1, 1, 0, 1, 1, 1");
        insertInRogueTable("13, '13', 0, 0, 0, 0, 0, 0");
        insertInRogueTable("14, '14', 1, 1, 0, 0, 1, 0");
        insertInRogueTable("15, '15', 1, 1, 1, 1, 0, 1");
        insertInRogueTable("16, '16', 1, 1, 1, 0, 1, 0");
        insertInRogueTable("17, '17', 0, 0, 0, 0, 0, 0");
        insertInRogueTable("18, '18', 1, 1, 1, 1, 1, 1");
        insertInRogueTable("19, '19', 1, 1, 1, 0, 0, 0");
        insertInRogueTable("20, '20', 1, 1, 1, 0, 1, 0");

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
                RulesContract.WizardEntry.COLUMN_LEVEL + " STRING NOT NULL," +

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
        insertInWizardTable("1, '1',   0, 0, 0, 0, 2, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0");
        insertInWizardTable("2, '2',   1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0");
        insertInWizardTable("3, '3',   0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0");
        insertInWizardTable("4, '4',   1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0");
        insertInWizardTable("5, '5',   0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0");
        insertInWizardTable("6, '6',   1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0");
        insertInWizardTable("7, '7',   0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0");
        insertInWizardTable("8, '8',   1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0");
        insertInWizardTable("9, '9',   0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0");
        insertInWizardTable("10, '10', 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0");
        insertInWizardTable("11, '11', 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0");
        insertInWizardTable("12, '12', 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0");
        insertInWizardTable("13, '13', 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0");
        insertInWizardTable("14, '14', 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0");
        insertInWizardTable("15, '15', 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0");
        insertInWizardTable("16, '16', 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0");
        insertInWizardTable("17, '17', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1");
        insertInWizardTable("18, '18', 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1");
        insertInWizardTable("19, '19', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1");
        insertInWizardTable("20, '20', 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1");

    }

    public void insertInWizardTable(String string) {
        String SQL_CREATE_CLERIC_DATA = "INSERT INTO " + RulesContract.ClericEntry.TABLE_NAME +
                " VALUES (" + string + ")";
        mDatabase.execSQL(SQL_CREATE_CLERIC_DATA);
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
        onCreate(sqLiteDatabase);
    }
}
