package com.brave_bunny.dndhelper.database.character.CharacterUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.character.CharacterDbHelper;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesSpellsUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil;
import com.brave_bunny.dndhelper.play.UseAbilityListAdapter;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;
import static com.brave_bunny.dndhelper.Utility.getViewByPosition;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_SPELL;

/**
 * Handles all of the selected spells for in-progress characters.
 */

public class CharacterSpellsUtil {

    /* LABELS */

    private static String getTableName() {
        return CharacterContract.CharacterSpells.TABLE_NAME;
    }

    private static String characterIdLabel() {
        return CharacterContract.CharacterSpells.COLUMN_CHARACTER_ID;
    }

    private static String spellIdLabel() {
        return CharacterContract.CharacterSpells.COLUMN_SPELL_ID;
    }

    /* PARSE VALUES*/

    public static long getCharacterId(ContentValues values) {
        return values.getAsLong(characterIdLabel());
    }

    public static void setCharacterId(ContentValues values, long charId) {
        values.put(characterIdLabel(), charId);
    }

    public static long getSpellId(ContentValues values) {
        return values.getAsLong(spellIdLabel());
    }

    public static void setSpellId(ContentValues values, long weaponId) {
        values.put(spellIdLabel(), weaponId);
    }

    /* DATABASE FUNCTIONS */

    public static void insertSpellIntoCharacterTable(Context context, ContentValues values) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(getTableName(), null, values);
        } finally {
            db.close();
        }
    }

    public static void deleteSpellFromCharacterTable(Context context, long characterId) {
        String query = characterIdLabel() + " = ?";
        String[] selectionArgs = new String[]{Long.toString(characterId)};
        deleteFromTable(context, query, selectionArgs);
    }

    public static void deleteFromTable(Context context, String query, String[] selectionArgs) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            db.delete(getTableName(), query, selectionArgs);
        } finally {
            db.close();
        }
    }

    public static void setSpellList(Context context, View view, long rowIndex) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + CharacterContract.CharacterSpells.TABLE_NAME
                    + " WHERE " + CharacterContract.CharacterSpells.COLUMN_CHARACTER_ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            final UseAbilityListAdapter adapter = new UseAbilityListAdapter(context, cursor,
                    0, TYPE_SPELL, rowIndex);
            final ListView listView = (ListView) view.findViewById(R.id.listview_spells);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // CursorAdapter returns a cursor at the correct position for getItem(), or null
                    // if it cannot seek to that position.
                    Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);

                    if (cursor != null) {
                        ContentValues spellItem = cursorRowToContentValues(cursor);
                        FrameLayout itemView = (FrameLayout)getViewByPosition(position, listView);
                        long spellId = RulesSpellsUtils.getSpellId(spellItem);

                        // TODO: cast spell
                    }
                }
            });
        } finally {
            db.close();
        }
    }
}
