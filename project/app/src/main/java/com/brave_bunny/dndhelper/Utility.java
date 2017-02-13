package com.brave_bunny.dndhelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Jemma on 8/14/2016.
 */
public class Utility {

    /* START code from http://stackoverflow.com/questions/7932420/android-sqlite-cursor-contentvalues */
    public static ContentValues cursorRowToContentValues(Cursor cursor) {
        if (cursor.getCount() == 0) return null;
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



    /* START http://stackoverflow.com/questions/24811536/android-listview-get-item-view-by-position */
    public static View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
    /* END http://stackoverflow.com/questions/24811536/android-listview-get-item-view-by-position */
}
