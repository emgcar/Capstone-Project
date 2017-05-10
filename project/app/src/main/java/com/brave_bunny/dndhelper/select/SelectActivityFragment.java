package com.brave_bunny.dndhelper.select;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
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
public class SelectActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public final String LOG_TAG = SelectActivityFragment.class.getSimpleName();

    ListView mListView;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_select, container, false);

        mListView = (ListView) rootView.findViewById(R.id.listview_inprogress);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                ContentValues value = cursorRowToContentValues(cursor);
                String name = CharacterUtil.getCharacterName(value);
                long index = CharacterUtil.getId(value);
                Intent selectActivity;

                if (isCompleted(getContext(), name)) {
                    selectActivity = new Intent(getContext(), DetailActivity.class);
                    selectActivity.putExtra(CastSpellActivityFragment.ROW_INDEX, index);
                } else {
                    selectActivity = new Intent(getContext(), CreateActivity.class);
                    selectActivity.putExtra(CreateActivityFragment.ROW_INDEX, index);
                }
                startActivity(selectActivity);
            }
        });

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CharacterLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        // mAdapter is a CursorAdapter
        CharacterListAdapter inProgress = new CharacterListAdapter(getContext(), cursor, 0);
        mListView.setAdapter(inProgress);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
    }

    private static class CharacterLoader extends CursorLoader {
        private static final String LOG_TAG = "Character Loader";

        public CharacterLoader(Context context) {
            super(context);
        }

        @Override
        public Cursor loadInBackground() {
            // this is just a simple query, could be anything that gets a cursor
            Cursor[] cursors = new Cursor[2];
            cursors[0] = CharacterUtil.getFinishedCharacterList(getContext());
            cursors[1] = InProgressCharacterUtil.getInProgressCharacterList(getContext());

            MergeCursor joiner = new MergeCursor(cursors);
            return joiner;
        }
    }
}
