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

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesFamiliarsUtils;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.changeFamiliarSelection;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.isFamiliarSameAsSelected;

/**
 * A placeholder fragment containing a simple view.
 */
public class FamiliarActivityFragment extends Fragment {

    private View mRootView;
    private ContentValues mValues;
    static long rowIndex;
    private Context mContext;

    public FamiliarActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_familiar, container, false);

        Bundle extras = getActivity().getIntent().getExtras();
        mValues = (ContentValues) extras.get(FamiliarActivity.inprogressValues);
        rowIndex = (long) extras.get(FamiliarActivity.indexValue);

        getFamiliars(getContext(), mRootView);
        mContext = getContext();

        return mRootView;
    }

    public void getFamiliars(Context context, View view) {
        ContentValues[] allValues;

        RulesDbHelper dbHelper = new RulesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + RulesContract.FamiliarEntry.TABLE_NAME;
            Cursor cursor = db.rawQuery(query, null);

            final DnDListAdapter adapter = new DnDListAdapter(getContext(), cursor,
                    0, DnDListAdapter.LIST_TYPE_FAMILIAR, rowIndex, 1);
            final ListView listView = (ListView) view.findViewById(R.id.listview_familiars);
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
                        ContentValues familiarItem = cursorRowToContentValues(cursor);
                        FrameLayout itemView = (FrameLayout)getViewByPosition(position, listView);
                        long familiarId = RulesFamiliarsUtils.getFamiliarId(familiarItem);

                        if(isFamiliarSameAsSelected(getContext(), rowIndex, familiarId)) {
                            changeFamiliarSelection(getContext(), rowIndex, -1);
                            itemView.setEnabled(false);
                            adapter.notifyDataSetChanged();
                        } else {
                            changeFamiliarSelection(getContext(), rowIndex, familiarId);
                            itemView.setEnabled(true);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        } finally {
            db.close();
        }
    }

    public void deselectAll(ListView listView) {
        int numViews = listView.getCount();

        for (int i = 0; i < numViews; i++) {
            FrameLayout itemView = (FrameLayout)getViewByPosition(i, listView);
            itemView.setEnabled(false);
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
