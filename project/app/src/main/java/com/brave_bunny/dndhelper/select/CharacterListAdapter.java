package com.brave_bunny.dndhelper.select;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterUtil;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil;

import static com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterUtil.isCompleted;

/**
 * Created by Jemma on 1/9/2017.
 * This adapter creates a list displaying the characters, both finished and in progress
 */

class CharacterListAdapter extends CursorAdapter {

    private final static int VIEW_TYPE_CHARACTER = 0;
    private final static int VIEW_TYPE_INPROGRESS = 1;

    private Context mContext;
    private Cursor mCursor;
    private CharacterListViewHolder mViewHolder;

    CharacterListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext = context;
        mCursor = c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        int layoutId = R.layout.list_item_character;

        View view = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        mViewHolder = new CharacterListViewHolder(view);

        return view;
    }

    // TODO some names take over the list for some reason
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String name = cursor.getString(CharacterUtil.COL_CHARACTER_NAME);
        if (!isCompleted(context, name)) {
            mViewHolder.levelView.setText(R.string.in_progress);
            name = cursor.getString(InProgressCharacterUtil.COL_CHARACTER_NAME);
        } else {
            // TODO implement level display
            mViewHolder.levelView.setText("Level 3");
        }

        mViewHolder.nameView.setText(name);
    }

    /**
     * Cache of the children views for a forecast list item.
     */
    private static class CharacterListViewHolder {
        private final TextView nameView;
        private final TextView levelView;

        private CharacterListViewHolder(View view) {
            nameView = (TextView) view.findViewById(R.id.name_details);
            levelView = (TextView) view.findViewById(R.id.level_details);
        }
    }
}
