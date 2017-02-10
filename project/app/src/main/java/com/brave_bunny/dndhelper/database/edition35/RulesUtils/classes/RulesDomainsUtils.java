package com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;

import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils.ChaoticEvil;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils.ChaoticGood;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils.ChaoticNeutral;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils.LawfulEvil;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils.LawfulGood;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils.LawfulNeutral;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils.Neutral;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils.NeutralEvil;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils.NeutralGood;

/**
 * Handles all of the domain data.
 */

public class RulesDomainsUtils {

    private static final int TRUE = 1;
    private static final int FALSE = 0;

    /* LABELS */

    private static String getTableName() {
        return RulesContract.ClericDomainsEntry.TABLE_NAME;
    }

    private static String domainIdLabel() {
        return RulesContract.ClericDomainsEntry._ID;
    }

    private static String domainNameLabel() {
        return RulesContract.ClericDomainsEntry.COLUMN_NAME;
    }

    private static String domainLawGoodLabel() {
        return RulesContract.ClericDomainsEntry.COLUMN_LAWFUL_GOOD;
    }

    private static String domainLawNeutLabel() {
        return RulesContract.ClericDomainsEntry.COLUMN_LAWFUL_NEUTRAL;
    }

    private static String domainLawEvilLabel() {
        return RulesContract.ClericDomainsEntry.COLUMN_LAWFUL_EVIL;
    }

    private static String domainNeutGoodLabel() {
        return RulesContract.ClericDomainsEntry.COLUMN_NEUTRAL_GOOD;
    }

    private static String domainNeutLabel() {
        return RulesContract.ClericDomainsEntry.COLUMN_NEUTRAL;
    }

    private static String domainNeutEvilLabel() {
        return RulesContract.ClericDomainsEntry.COLUMN_NEUTRAL_EVIL;
    }

    private static String domainChaotGoodLabel() {
        return RulesContract.ClericDomainsEntry.COLUMN_CHAOTIC_GOOD;
    }

    private static String domainChaotNeutLabel() {
        return RulesContract.ClericDomainsEntry.COLUMN_CHAOTIC_NEUTRAL;
    }

    private static String domainChoatEvilLabel() {
        return RulesContract.ClericDomainsEntry.COLUMN_CHAOTIC_EVIL;
    }

    /* PARSE VALUES*/

    public static long getDomainId(ContentValues values) {
        return values.getAsLong(domainIdLabel());
    }

    public static String getDomainName(ContentValues values) {
        return values.getAsString(domainNameLabel());
    }

    /* DATABASE FUNCTIONS */

    private static ContentValues getDomainByAlignment(Context context, int alignment) {
        String alignmentLabel = getAlignmentLabel(alignment);
        if (alignmentLabel.equals("")) return null;

        String query = "SELECT * FROM " + getTableName() + " WHERE " + alignmentLabel + " = ?";
        return getStats(context, query, TRUE);
    }

    private static String getAlignmentLabel(int alignment) {
        switch (alignment) {
            case LawfulGood:
                return domainLawGoodLabel();
            case LawfulNeutral:
                return domainLawNeutLabel();
            case LawfulEvil:
                return domainLawEvilLabel();
            case NeutralGood:
                return domainNeutGoodLabel();
            case Neutral:
                return domainNeutLabel();
            case NeutralEvil:
                return domainNeutEvilLabel();
            case ChaoticGood:
                return domainChaotGoodLabel();
            case ChaoticNeutral:
                return domainChaotNeutLabel();
            case ChaoticEvil:
                return domainChoatEvilLabel();
        }
        return "";
    }

    public static ContentValues getDomain(Context context, long domainId) {
        String query = "SELECT * FROM " + getTableName() + " WHERE " + domainIdLabel() + " = ?";
        return getStats(context, query, domainId);
    }

    private static ContentValues getStats(Context context, String query, long index) {
        ContentValues values = null;

        RulesDbHelper dbHelper = new RulesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(index)});

            cursor.moveToFirst();

            if(cursor.getCount() > 0) {
                values = Utility.cursorRowToContentValues(cursor);
            }
            cursor.close();
        } finally {
            db.close();
        }

        return values;
    }
}
