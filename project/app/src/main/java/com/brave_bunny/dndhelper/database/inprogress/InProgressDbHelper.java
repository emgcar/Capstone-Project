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
                InProgressContract.CharacterEntry.COLUMN_HP + " INTEGER )";

        sqLiteDatabase.execSQL(SQL_CREATE_INPROGRESS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + InProgressContract.CharacterEntry.TABLE_NAME);
    }
}
