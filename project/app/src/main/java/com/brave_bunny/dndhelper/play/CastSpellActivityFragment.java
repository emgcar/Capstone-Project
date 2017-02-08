package com.brave_bunny.dndhelper.play;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.character.CharacterDbHelper;

import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSpellsUtils.COL_SPELL_ID;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_SPELL;

/**
 * A placeholder fragment containing a simple view.
 */
public class CastSpellActivityFragment extends Fragment {
    public static final String ROW_INDEX = "row_index";

    private View mRootView;
    static long rowIndex;

    public CastSpellActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_spell, container, false);

        Bundle extras = getActivity().getIntent().getExtras();
        rowIndex = (long) extras.get(CastSpellActivity.indexValue);

        getSpells(getContext(), mRootView);

        return mRootView;
    }

    public void getSpells(Context context, View view) {

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
                        FrameLayout itemView = (FrameLayout)getViewByPosition(position, listView);
                        int spellId = cursor.getInt(COL_SPELL_ID);

                        // TODO: cast spell
                    }
                }
            });
        } finally {
            db.close();
        }
    }

    /* START http://stackoverflow.com/questions/24811536/android-listview-get-item-view-by-position */
    public View getViewByPosition(int pos, ListView listView) {
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
