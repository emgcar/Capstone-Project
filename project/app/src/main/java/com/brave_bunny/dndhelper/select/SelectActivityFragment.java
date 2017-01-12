package com.brave_bunny.dndhelper.select;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.create.CreateActivity;
import com.brave_bunny.dndhelper.database.CharacterContract;
import com.brave_bunny.dndhelper.database.CharacterDbHelper;
import com.brave_bunny.dndhelper.database.CharacterUtil;
import com.brave_bunny.dndhelper.play.DetailActivity;
import com.brave_bunny.dndhelper.play.battle.BattleActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class SelectActivityFragment extends Fragment {

    private static final int TRUE = 1;
    private static final int FALSE = 0;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_select, container, false);

        CharacterDbHelper dbHelper = new CharacterDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM (SELECT " + CharacterContract.CharacterEntry._ID
                    + ", " + CharacterContract.CharacterEntry.COLUMN_NAME + ", 1 AS FILTER FROM "
                    + CharacterContract.CharacterEntry.TABLE_NAME
                    + " UNION ALL SELECT " + CharacterContract.InProgressEntry._ID + ", "
                    + CharacterContract.InProgressEntry.COLUMN_NAME + ", 2 AS FILTER FROM "
                    + CharacterContract.InProgressEntry.TABLE_NAME
                    + " ORDER BY " + CharacterContract.CharacterEntry.COLUMN_NAME + " ASC, "
                    + CharacterContract.InProgressEntry.COLUMN_NAME + " ASC"
                    + ") ORDER BY FILTER";
            Cursor cursor = db.rawQuery(query, null);
            final CharacterAdapter adapter = new CharacterAdapter(getContext(), cursor, 0);

            ListView listView = (ListView) rootView.findViewById(R.id.listview_characters);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // CursorAdapter returns a cursor at the correct position for getItem(), or null
                    // if it cannot seek to that position.
                    Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                    long index = cursor.getLong(Utility.COL_CHARACTER_ID);
                    Intent selectActivity;

                    if (cursor != null) {
                        if (isFinished(adapter, position)) {
                            if (isInBattle(index)) {
                                selectActivity = new Intent(getContext(), BattleActivity.class);
                            } else {
                                selectActivity = new Intent(getContext(), DetailActivity.class);
                            }
                        } else {
                            selectActivity = new Intent(getContext(), CreateActivity.class);
                        }
                        selectActivity.putExtra(CreateActivity.CreateActivityFragment.ROW_INDEX, index);
                        startActivity(selectActivity);
                    }
                }
            });

        } finally {
            db.close();
        }

        return rootView;
    }

    private boolean isFinished(CharacterAdapter adapter, int position) {
        long numberOfFinishedCharacters = adapter.getNumberOfFinishedCharacters();
        return (position <= numberOfFinishedCharacters);
    }

    private boolean isInBattle(long rowIndex) {
        CharacterUtil characterUtil = new CharacterUtil();
        int colIndex = CharacterUtil.COL_CHARACTER_IN_BATTLE;
        return (characterUtil.getCharacterValue(getContext(), rowIndex, colIndex) == TRUE);
    }
}
