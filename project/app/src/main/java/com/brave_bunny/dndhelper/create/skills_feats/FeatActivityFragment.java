package com.brave_bunny.dndhelper.create.skills_feats;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.create.classes.DnDListAdapter;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtil;

/**
 * Created by Jemma on 1/22/2017.
 */

public class FeatActivityFragment extends Fragment {

    private View mRootView;
    private ContentValues mValues;
    InProgressUtil mInProgressUtil;
    static long rowIndex;

    public FeatActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_feat, container, false);
        mInProgressUtil = new InProgressUtil();

        Bundle extras = getActivity().getIntent().getExtras();
        mValues = (ContentValues) extras.get(FeatActivity.inprogressValues);
        rowIndex = (long) extras.get(FeatActivity.indexValue);

        getFeats(getContext(), mRootView);

        return mRootView;
    }

    //TODO: populate previous choices
    public void getFeats(Context context, View view) {

        RulesDbHelper dbHelper = new RulesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + RulesContract.FeatEntry.TABLE_NAME;
            Cursor cursor = db.rawQuery(query, null);

            int numberFeats = getNumberFeats();

            final DnDListAdapter adapter = new DnDListAdapter(getContext(), cursor, 0,
                    DnDListAdapter.LIST_TYPE_DEITY, rowIndex, numberFeats);
            final ListView listView = (ListView) view.findViewById(R.id.listview_feat);
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
                        FrameLayout itemView = (FrameLayout)getViewByPosition(position, listView);

                        //not working accurately
                        if (itemView.isEnabled()) {
                            adapter.decreaseNumberSelected();
                            itemView.setEnabled(false);
                        } else {
                            if (!adapter.atMaxSelected()) {
                                adapter.increaseNumberSelected();
                                itemView.setEnabled(true);
                            } else {
                                itemView.setEnabled(false);
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

    public int getNumberFeats() {
        //TODO find number feats
        return 2;
    }

    public void updateNumberSelected(DnDListAdapter adapter) {
        TextView textView = (TextView) mRootView.findViewById(R.id.remaining_spells);
        int numberSelected = adapter.getNumberSelected();
        textView.setText(getString(R.string.selected_spells, numberSelected, getNumberFeats()));
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
