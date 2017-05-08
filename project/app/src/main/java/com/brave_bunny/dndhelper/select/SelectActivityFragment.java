package com.brave_bunny.dndhelper.select;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil;
import com.brave_bunny.dndhelper.play.battle.CastSpellActivityFragment;
import com.brave_bunny.dndhelper.play.DetailActivity;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;
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

        ListView listView = (ListView) rootView.findViewById(R.id.listview_inprogress);
        new getCharacters().execute(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // CursorAdapter returns a cursor at the correct position for getItem(), or null
                // if it cannot seek to that position.
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                ContentValues value = cursorRowToContentValues(cursor);
                String name = CharacterUtil.getCharacterName(value);
                long index = CharacterUtil.getId(value);
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

        return rootView;
    }

    private class getCharacters extends AsyncTask<ListView, Void, CharacterListAdapter> {
        ListView mListView;

        protected CharacterListAdapter doInBackground(ListView...listViews) {
            mListView = listViews[0];
            CharacterListAdapter inProgress;

            Cursor[] cursors = new Cursor[2];
            cursors[0] = CharacterUtil.getFinishedCharacterList(getContext());
            cursors[1] = InProgressCharacterUtil.getInProgressCharacterList(getContext());

            MergeCursor joiner = new MergeCursor(cursors);
            inProgress = new CharacterListAdapter(getContext(), joiner, 0);
            return inProgress;
        }

        protected void onPostExecute(CharacterListAdapter inProgress) {
            mListView.setAdapter(inProgress);
        }
    }
}
