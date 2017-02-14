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
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesDomainsUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressDomainsUtil.addDomainSelection;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressDomainsUtil.isDomainSelected;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressDomainsUtil.removeDomainSelection;

/**
 * A placeholder fragment containing a simple view.
 */
public class DeityActivityFragment extends Fragment {

    public static final int TYPE_DOMAIN = 0;
    public static final int TYPE_WEAPON = 1;

    ContentValues mValues;
    static long rowIndex;
    View mRootView;
    private final int numberDomains = 2;
    InProgressCharacterUtil mInProgressCharacterUtil;

    public DeityActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_deity, container, false);
        mInProgressCharacterUtil = new InProgressCharacterUtil();

        Bundle extras = getActivity().getIntent().getExtras();
        mValues = (ContentValues) extras.get(DeityActivity.inprogressValues);
        rowIndex = (long) extras.get(DeityActivity.indexValue);
        int align = mValues.getAsInteger(InProgressContract.CharacterEntry.COLUMN_ALIGN);
        /// put in edge case for error here, in case button doesn't gray out

        ContentValues[] values;

        TextView alignView = (TextView) mRootView.findViewById(R.id.chosen_align);
        String selectedAlignment = RulesCharacterUtils.getAligmentText(getContext(), align);
        alignView.setText(selectedAlignment);

        getClericDomainsByAlignment(getContext(), selectedAlignment, mRootView);

        return mRootView;
    }

    //TODO: populate previous choices
    public void getClericDomainsByAlignment(final Context context, String alignment, View view) {
        ContentValues[] allValues;

        RulesDbHelper dbHelper = new RulesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + RulesContract.ClericDomainsEntry.TABLE_NAME
                    + " WHERE " + alignment + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{"1"});

            final DnDListAdapter adapter = new DnDListAdapter(getContext(), cursor, 0,
                    DnDListAdapter.LIST_TYPE_DEITY, rowIndex, numberDomains);
            final ListView listView = (ListView) view.findViewById(R.id.listview_cleric_domains);
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
                        ContentValues domainData = cursorRowToContentValues(cursor);
                        FrameLayout itemView = (FrameLayout)getViewByPosition(position, listView);
                        long domainId = RulesDomainsUtils.getDomainId(domainData);

                        if(isDomainSelected(getContext(), rowIndex, domainId)) {
                            adapter.decreaseNumberSelected();
                            removeDomainSelection(getContext(), rowIndex, domainId);
                            itemView.setEnabled(false);
                        } else {
                            if (!adapter.atMaxSelected()) {
                                adapter.increaseNumberSelected();
                                addDomainSelection(getContext(), rowIndex, domainId);
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

    public void updateNumberSelected(DnDListAdapter adapter) {
        TextView textView = (TextView) mRootView.findViewById(R.id.remaining_domains);
        int numberSelected = adapter.getNumberSelected();
        textView.setText(getString(R.string.select_domains, numberSelected));
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
