package com.brave_bunny.dndhelper.select;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.CharacterContract;
import com.brave_bunny.dndhelper.database.CharacterDbHelper;

/**
 * Created by Jemma on 1/9/2017.
 */

public class CharacterAdapter extends CursorAdapter {

    public final int NUMBER_VIEWS = 2;

    public final int VIEW_TYPE_CHARACTER = 0;
    public final int VIEW_TYPE_INPROGRESS = 1;

    private long numberOfFinishedCharacters;

    private Context mContext;

    public CharacterAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext = context;

        findNumberOfFinishedCharacters();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = R.layout.list_item_character;

        View view = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        if (viewType == VIEW_TYPE_INPROGRESS)
            viewHolder.levelView.setText(R.string.in_progress);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String name = cursor.getString(Utility.COL_CHARACTER_NAME);
        viewHolder.nameView.setText(name);
    }


    @Override
    public int getViewTypeCount() {
        return NUMBER_VIEWS;
    }

    @Override
    public int getItemViewType(int position) {
        long numberOfCharacters = getNumberOfFinishedCharacters();

        if (position <= numberOfCharacters) {
            return VIEW_TYPE_CHARACTER;
        } else {
            return VIEW_TYPE_INPROGRESS;
        }
    }

    private void findNumberOfFinishedCharacters() {
        CharacterDbHelper dbHelper = new CharacterDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            numberOfFinishedCharacters = DatabaseUtils.queryNumEntries(
                    db, CharacterContract.CharacterEntry.TABLE_NAME);
        } finally {
            db.close();
        }
    }

    public long getNumberOfFinishedCharacters() {
        return numberOfFinishedCharacters;
    }

    /**
     * Cache of the children views for a forecast list item.
     */
    public static class ViewHolder {
        public final TextView nameView;
        public final TextView levelView;

        public ViewHolder(View view) {
            nameView = (TextView) view.findViewById(R.id.name_details);
            levelView = (TextView) view.findViewById(R.id.level_details);
        }
    }
}
