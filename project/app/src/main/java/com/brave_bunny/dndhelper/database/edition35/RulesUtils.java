package com.brave_bunny.dndhelper.database.edition35;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Jemma on 1/11/2017.
 */

public class RulesUtils {
        public final static int CLASS_CLERIC = 1;
        public final static int CLASS_FIGHTER = 2;
        public final static int CLASS_ROGUE = 3;
        public final static int CLASS_WIZARD = 4;

        public static Cursor getAllSkills(Context context) {
                Cursor cursor;

                RulesDbHelper dbHelper = new RulesDbHelper(context);
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                try {
                        String query = "SELECT * FROM " + RulesContract.SkillsEntry.TABLE_NAME;
                        cursor = db.rawQuery(query, null);

                        cursor.moveToFirst();
                } finally {
                        db.close();
                }

                return cursor;
        }
}
