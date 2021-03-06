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
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesItemsUtils;
import com.brave_bunny.dndhelper.play.UseAbilityListAdapter;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;
import static com.brave_bunny.dndhelper.Utility.getViewByPosition;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_ITEM;

/**
 * Handles all of the selected items for created characters.
 */

public class CharacterItemsUtil {

    /* LABELS */

    private static String getTableName() {
        return CharacterContract.CharacterItems.TABLE_NAME;
    }

    private static String idLabel() {
        return CharacterContract.CharacterItems._ID;
    }

    private static String characterIdLabel() {
        return CharacterContract.CharacterItems.COLUMN_CHARACTER_ID;
    }

    private static String itemIdLabel() {
        return CharacterContract.CharacterItems.COLUMN_ITEM_ID;
    }

    private static String itemCountLabel() {
        return CharacterContract.CharacterItems.COLUMN_COUNT;
    }


    /* PARSE VALUES*/

    public static long getId(ContentValues values) {
        return values.getAsLong(idLabel());
    }

    public static long getCharacterId(ContentValues values) {
        return values.getAsLong(characterIdLabel());
    }

    public static void setCharacterId(ContentValues values, long charId) {
        values.put(characterIdLabel(), charId);
    }

    public static long getItemId(ContentValues values) {
        return values.getAsLong(itemIdLabel());
    }

    public static void setItemId(ContentValues values, long armorId) {
        values.put(itemIdLabel(), armorId);
    }

    public static int getItemCount(ContentValues values) {
        return values.getAsInteger(itemCountLabel());
    }

    public static int getItemCount(Context context, long rowIndex, long itemId) {
        ContentValues values = getStats(context, rowIndex, itemId);
        if (values == null) return 0;
        return getItemCount(values);
    }

    public static void setItemCount(ContentValues values, long itemCount) {
        values.put(itemCountLabel(), itemCount);
    }

    /* DATABASE FUNCTIONS */

    public static void removeAllCharacterItems(Context context, long rowIndex) {
        String query = characterIdLabel() + " = ?";
        String[] selectionArgs = new String[]{Long.toString(rowIndex)};
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

    public static boolean isItemListed(Context context, long rowIndex, long itemId) {
        boolean isSelected = false;
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    itemIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(itemId)});
            if (cursor.getCount() > 0) {
                isSelected = true;
            }
            cursor.close();
        } finally {
            db.close();
        }
        return isSelected;
    }

    public static void addItemSelection(Context context, long rowIndex, long itemId, int count) {
        ContentValues values = new ContentValues();
        values.put(characterIdLabel(), rowIndex);
        values.put(itemIdLabel(), itemId);
        values.put(itemCountLabel(), count);

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(getTableName(), null, values);
        } finally {
            db.close();
        }
    }

    public static void updateItemSelection(Context context, long rowIndex, long itemId, int count) {
        ContentValues values = new ContentValues();
        values.put(characterIdLabel(), rowIndex);
        values.put(itemIdLabel(), itemId);
        values.put(itemCountLabel(), count);

        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    itemIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(itemId)});
            try {
                cursor.moveToFirst();
                db.update(getTableName(), values, characterIdLabel() + " = ? AND "
                                + itemIdLabel() + " = ?",
                        new String[]{Long.toString(rowIndex), Long.toString(itemId)});
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }
    }

    public static void addOrUpdateItemSelection(Context context, long rowIndex, long itemId, int count) {
        if (isItemListed(context, rowIndex, itemId)) {
            updateItemSelection(context, rowIndex, itemId, count);
        } else {
            addItemSelection(context, rowIndex, itemId, count);
        }
    }

    public static ContentValues getStats(Context context, long rowIndex, long armorId) {
        ContentValues values = null;
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + getTableName()
                    + " WHERE " + characterIdLabel() + " = ? AND " +
                    itemIdLabel() + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex), Long.toString(armorId)});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                values = cursorRowToContentValues(cursor);
            }
            cursor.close();
        } finally {
            db.close();
        }
        return values;
    }

    public static void insertItemsIntoCharacterTable(Context context, ContentValues values) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.insert(getTableName(), null, values);
        } finally {
            db.close();
        }
    }

    public static void deleteItemsFromCharacterTable(Context context, long characterId) {
        String query = characterIdLabel() + " = ?";
        String[] selectionArgs = new String[]{Long.toString(characterId)};
        deleteFromTable(context, query, selectionArgs);
    }

    public static void setItemList(Context context, View view, long rowIndex) {
        CharacterDbHelper dbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + CharacterContract.CharacterItems.TABLE_NAME
                    + " WHERE " + CharacterContract.CharacterItems.COLUMN_CHARACTER_ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Long.toString(rowIndex)});

            final UseAbilityListAdapter adapter = new UseAbilityListAdapter(context, cursor,
                    0, TYPE_ITEM, rowIndex);
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
                        long itemId = RulesItemsUtils.getItemId(spellItem);
                    }
                }
            });
        } finally {
            db.close();
        }
    }
}
