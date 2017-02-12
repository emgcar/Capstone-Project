package com.brave_bunny.dndhelper.create.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.create.DnDListAdapter;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesSpellsUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil.addSpellSelection;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil.isSpellSelected;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil.removeSpellSelection;

/**
 * A placeholder fragment containing a simple view.
 */
public class SpellActivityFragment extends Fragment {

    private View mRootView;
    private int mIntScore;
    static long rowIndex;
    InProgressCharacterUtil mInProgressCharacterUtil;

    public SpellActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_spell, container, false);
        mInProgressCharacterUtil = new InProgressCharacterUtil();

        Bundle extras = getActivity().getIntent().getExtras();
        rowIndex = (long) extras.get(DeityActivity.indexValue);
        ContentValues values = InProgressCharacterUtil.getInProgressRow(getContext(), rowIndex);
        mIntScore = InProgressCharacterUtil.getTotalIntelligenceScore(values);

        getSpells(getContext(), mRootView);

        return mRootView;
    }

    //TODO: populate previous choices
    public void getSpells(Context context, View view) {

        RulesDbHelper dbHelper = new RulesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + RulesContract.SpellsEntry.TABLE_NAME
                    + " WHERE " + RulesContract.SpellsEntry.COLUMN_LEVEL + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{"1"});

            int numberSpells = getNumberSpells();

            final DnDListAdapter adapter = new DnDListAdapter(getContext(), cursor, 0,
                    DnDListAdapter.LIST_TYPE_SPELL, rowIndex, numberSpells);
            final ListView listView = (ListView) view.findViewById(R.id.listview_spells);
            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            //listView.setItemsCanFocus(false);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // CursorAdapter returns a cursor at the correct position for getItem(), or null
                    // if it cannot seek to that position.
                    Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);

                    if (cursor != null) {
                        ContentValues spellData = cursorRowToContentValues(cursor);
                        FrameLayout itemView = (FrameLayout)getViewByPosition(position, listView);
                        long spellId = RulesSpellsUtils.getSpellId(spellData);

                        if(isSpellSelected(getContext(), rowIndex, spellId)) {
                            adapter.decreaseNumberSelected();
                            removeSpellSelection(getContext(), rowIndex, spellId);
                            itemView.setEnabled(false);
                        } else {
                            if (!adapter.atMaxSelected()) {
                                adapter.increaseNumberSelected();
                                addSpellSelection(getContext(), rowIndex, spellId);
                                itemView.setEnabled(true);
                            }
                        }
                        updateNumberSelected(adapter);
                    }
                }
            });
        } finally {
            db.close();
        }
    }

    public int getNumberSpells() {
        int numberSpells = 3;

        if (mIntScore != -1) {
            numberSpells += RulesCharacterUtils.scoreToModifier(mIntScore);
        }
        return numberSpells;
    }

    public void updateNumberSelected(DnDListAdapter adapter) {
        TextView textView = (TextView) mRootView.findViewById(R.id.remaining_spells);
        int numberSelected = adapter.getNumberSelected();
        textView.setText(getString(R.string.selected_spells, numberSelected, getNumberSpells()));
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
