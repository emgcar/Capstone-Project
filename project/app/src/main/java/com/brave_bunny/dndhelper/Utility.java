package com.brave_bunny.dndhelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Jemma on 8/14/2016.
 */
public class Utility {

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
}
