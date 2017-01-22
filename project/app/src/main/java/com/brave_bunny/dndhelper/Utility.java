package com.brave_bunny.dndhelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Jemma on 8/14/2016.
 */
public class Utility {

    public static TextView findTextView(View view, int id) {
        return (TextView) view.findViewById(id);
    }

    /* START code from http://stackoverflow.com/questions/7932420/android-sqlite-cursor-contentvalues */
    public static ContentValues cursorRowToContentValues(Cursor cursor) {
        ContentValues values = new ContentValues();
        String[] columns = cursor.getColumnNames();
        int length = columns.length;
        for (int i = 0; i < length; i++) {
            switch (cursor.getType(i)) {
                case Cursor.FIELD_TYPE_NULL:
                    values.putNull(columns[i]);
                    break;
                case Cursor.FIELD_TYPE_INTEGER:
                    values.put(columns[i], cursor.getLong(i));
                    break;
                case Cursor.FIELD_TYPE_FLOAT:
                    values.put(columns[i], cursor.getDouble(i));
                    break;
                case Cursor.FIELD_TYPE_STRING:
                    values.put(columns[i], cursor.getString(i));
                    break;
                case Cursor.FIELD_TYPE_BLOB:
                    values.put(columns[i], cursor.getBlob(i));
                    break;
            }
        }
        return values;
    }
    /* END code from http://stackoverflow.com/questions/7932420/android-sqlite-cursor-contentvalues */

    public static String getStringFromCursor(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.getString(index);
    }

    public static int getIntFromCursor(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.getInt(index);
    }

    public static float getFloatFromCursor(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.getFloat(index);
    }

    public static void putInContentValue(ContentValues values,
                                         String columnName, int val) {

        values.put(columnName, val);
    }

    public static void putInContentValue(ContentValues values,
                                         String columnName, float val) {

        values.put(columnName, val);
    }

    public static int scoreToModifier(int score) {
        return (score - 10)/2;
    }
}
