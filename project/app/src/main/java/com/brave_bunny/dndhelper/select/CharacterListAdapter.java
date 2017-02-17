package com.brave_bunny.dndhelper.select;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterUtil;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;
import static com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterUtil.isCompleted;

/**
 * Created by Jemma on 1/9/2017.
 * This adapter creates a list displaying the characters, both finished and in progress
 */

class CharacterListAdapter extends CursorAdapter {

    CharacterListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        int layoutId = R.layout.list_item_character;

        View view = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CharacterListViewHolder viewHolder = new CharacterListViewHolder(view);

        ContentValues value = cursorRowToContentValues(cursor);

        String name = CharacterUtil.getCharacterName(value);
        if (name.equals("")) {
            viewHolder.levelView.setText(R.string.in_progress);
            name = InProgressCharacterUtil.getCharacterName(value);
        } else {
            // TODO implement level display
            viewHolder.levelView.setText("Level 3");
        }

        viewHolder.nameView.setText(name);
    }

    private static class CharacterListViewHolder {
        private final TextView nameView;
        private final TextView levelView;

        private CharacterListViewHolder(View view) {
            nameView = (TextView) view.findViewById(R.id.name_details);
            levelView = (TextView) view.findViewById(R.id.level_details);
        }
    }
}
