package com.brave_bunny.dndhelper.select;

import android.content.Intent;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.create.CreateActivity;
import com.brave_bunny.dndhelper.create.CreateActivityFragment;
import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.character.CharacterDbHelper;
import com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterUtil;
import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;
import com.brave_bunny.dndhelper.play.battle.CastSpellActivityFragment;
import com.brave_bunny.dndhelper.play.DetailActivity;

import static com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterUtil.isCompleted;

/**
 * A placeholder fragment containing a simple view.
 */
public class SelectActivityFragment extends Fragment {

    private static final int TRUE = 1;
    private static final int FALSE = 0;

    //TODO allow for swipe for delete action, but with confirmation menu

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
        SQLiteDatabase characterDb = dbHelper.getReadableDatabase();

        InProgressDbHelper inprogressHelper = new InProgressDbHelper(getContext());
        SQLiteDatabase inprogressDb = inprogressHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + CharacterContract.CharacterEntry.TABLE_NAME;
            String inProgressQuery = "SELECT * FROM " + InProgressContract.CharacterEntry.TABLE_NAME;

            Cursor[] cursors = new Cursor[2];
            cursors[0] = characterDb.rawQuery(query, null);
            cursors[1] = inprogressDb.rawQuery(inProgressQuery, null);

            MergeCursor joiner = new MergeCursor(cursors);

            final CharacterListAdapter inProgress = new CharacterListAdapter(getContext(), joiner, 0);
            ListView listView = (ListView) rootView.findViewById(R.id.listview_inprogress);
            listView.setAdapter(inProgress);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // CursorAdapter returns a cursor at the correct position for getItem(), or null
                    // if it cannot seek to that position.
                    Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                    String name = cursor.getString(CharacterUtil.COL_CHARACTER_NAME);
                    long index = cursor.getLong(CharacterUtil.COL_CHARACTER_ID);
                    Intent selectActivity;

                    if (isCompleted(getContext(), name)) {
                        //TODO: go to battle screen when in battle
                        //if (isInBattle(getContext(), index)) {
                        //    selectActivity = new Intent(getContext(), BattleActivity.class);
                        //} else {
                            selectActivity = new Intent(getContext(), DetailActivity.class);
                            selectActivity.putExtra(CastSpellActivityFragment.ROW_INDEX, index);
                        //}
                    } else {
                        selectActivity = new Intent(getContext(), CreateActivity.class);
                        selectActivity.putExtra(CreateActivityFragment.ROW_INDEX, index);
                    }
                    startActivity(selectActivity);
                }
            });
        } finally {
            characterDb.close();
            inprogressDb.close();
        }

        return rootView;
    }
}
