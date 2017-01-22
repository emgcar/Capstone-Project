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
import com.brave_bunny.dndhelper.database.edition35.RulesUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class DeityActivityFragment extends Fragment {

    ContentValues mValues;
    View mRootView;
    private final int numberDomains = 2;

    public DeityActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_deity, container, false);

        Bundle extras = getActivity().getIntent().getExtras();
        mValues = (ContentValues) extras.get(DeityActivity.inprogressValues);
        int align = mValues.getAsInteger(InProgressContract.CharacterEntry.COLUMN_ALIGN);
        /// put in edge case for error here, in case button doesn't gray out

        String selectedAlignment;
        ContentValues[] values;

        TextView alignView = (TextView) mRootView.findViewById(R.id.chosen_align);
        switch(align) {
            case RulesUtils.ALIGN_LG:
                selectedAlignment = RulesContract.ClericDomainsEntry.COLUMN_LAWFUL_GOOD;
                alignView.setText(getString(R.string.lawful_good));
                break;
            case RulesUtils.ALIGN_LN:
                selectedAlignment = RulesContract.ClericDomainsEntry.COLUMN_LAWFUL_NEUTRAL;
                alignView.setText(getString(R.string.lawful_neutral));
                break;
            case RulesUtils.ALIGN_LE:
                selectedAlignment = RulesContract.ClericDomainsEntry.COLUMN_LAWFUL_EVIL;
                alignView.setText(getString(R.string.lawful_evil));
                break;
            case RulesUtils.ALIGN_NG:
                selectedAlignment = RulesContract.ClericDomainsEntry.COLUMN_NEUTRAL_GOOD;
                alignView.setText(getString(R.string.neutral_good));
                break;
            case RulesUtils.ALIGN_N:
                selectedAlignment = RulesContract.ClericDomainsEntry.COLUMN_NEUTRAL;
                alignView.setText(getString(R.string.neutral));
                break;
            case RulesUtils.ALIGN_NE:
                selectedAlignment = RulesContract.ClericDomainsEntry.COLUMN_NEUTRAL_EVIL;
                alignView.setText(getString(R.string.neutral_evil));
                break;
            case RulesUtils.ALIGN_CG:
                selectedAlignment = RulesContract.ClericDomainsEntry.COLUMN_CHAOTIC_GOOD;
                alignView.setText(getString(R.string.chaotic_good));
                break;
            case RulesUtils.ALIGN_CN:
                selectedAlignment = RulesContract.ClericDomainsEntry.COLUMN_CHAOTIC_NEUTRAL;
                alignView.setText(getString(R.string.chaotic_neutral));
                break;
            case RulesUtils.ALIGN_CE:
                selectedAlignment = RulesContract.ClericDomainsEntry.COLUMN_CHAOTIC_EVIL;
                alignView.setText(getString(R.string.chaotic_evil));
                break;
            default:
                return null;
        }

        getClericDomainsByAlignment(getContext(), selectedAlignment, mRootView);

        return mRootView;
    }

    public void getClericDomainsByAlignment(Context context, String alignment, View view) {
        ContentValues[] allValues;

        RulesDbHelper dbHelper = new RulesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + RulesContract.ClericDomainsEntry.TABLE_NAME
                    + " WHERE " + alignment + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{"1"});

            final SelectionListAdapter adapter = new SelectionListAdapter(getContext(), cursor, 0, numberDomains);
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

    public void updateNumberSelected(SelectionListAdapter adapter) {
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
