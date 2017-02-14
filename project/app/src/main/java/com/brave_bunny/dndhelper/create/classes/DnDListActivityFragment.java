package com.brave_bunny.dndhelper.create.classes;

import android.content.ContentValues;
import android.database.Cursor;
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
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesFeatsUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesDomainsUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesFamiliarsUtils;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesSpellsUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil;

import static com.brave_bunny.dndhelper.Utility.cursorRowToContentValues;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesFeatsUtils.getAllFeats;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesDomainsUtils.getAllDomains;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesFamiliarsUtils.getAllFamiliars;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.classes.RulesSpellsUtils.getAllSpells;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.changeFamiliarSelection;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.getTotalFamiliarToSelect;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil.isFamiliarSameAsSelected;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressDomainsUtil.addDomainSelection;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressDomainsUtil.getNumberDomainsAllowed;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressDomainsUtil.isDomainSelected;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressDomainsUtil.removeDomainSelection;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressFeatsUtil.addFeatSelection;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressFeatsUtil.getNumberFeatsAllowed;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressFeatsUtil.isFeatSelected;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressFeatsUtil.removeFeatSelection;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil.addSpellSelection;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil.getTotalSpellsToLearn;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil.isSpellSelected;
import static com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressSpellsUtil.removeSpellSelection;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_DOMAIN;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_FAMILIAR;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_FEAT;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_SPELL;

/**
 * A placeholder fragment containing a simple view.
 */
public class DnDListActivityFragment extends Fragment {

    private View mRootView;
    private int mIntScore;
    static long rowIndex;
    private static int mType;
    private static Cursor mCursor;
    private static int numberToSelect;
    private static ContentValues mInProgressCharacter;
    InProgressCharacterUtil mInProgressCharacterUtil;

    public DnDListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_dnd_list, container, false);
        mInProgressCharacterUtil = new InProgressCharacterUtil();

        Bundle extras = getActivity().getIntent().getExtras();
        rowIndex = (long) extras.get(DnDListActivity.indexValue);
        mType = (int) extras.get(DnDListActivity.listType);

        mInProgressCharacter = InProgressCharacterUtil.getInProgressRow(getContext(), rowIndex);
        mIntScore = InProgressCharacterUtil.getTotalIntelligenceScore(getContext(), mInProgressCharacter);

        switch (mType) {
            case TYPE_SPELL:
                mCursor = getAllSpells(getContext());
                numberToSelect = getTotalSpellsToLearn(mInProgressCharacter);
                break;
            case TYPE_FEAT:
                mCursor = getAllFeats(getContext());
                numberToSelect = getNumberFeatsAllowed(mInProgressCharacter);
                break;
            case TYPE_DOMAIN:
                mCursor = getAllDomains(getContext());
                numberToSelect = getNumberDomainsAllowed(mInProgressCharacter);
                break;
            case TYPE_FAMILIAR:
                mCursor = getAllFamiliars(getContext());
                numberToSelect = getTotalFamiliarToSelect(mInProgressCharacter);
                break;

            default:
                return mRootView;
        }

        setListAdapter();

        return mRootView;
    }

    public void setListAdapter() {

            final DnDListAdapter adapter = new DnDListAdapter(getContext(), mCursor, 0,
                    mType, rowIndex, numberToSelect);
            final ListView listView = (ListView) mRootView.findViewById(R.id.listview_spells);
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
                        ContentValues itemData = cursorRowToContentValues(cursor);
                        FrameLayout itemView = (FrameLayout)getViewByPosition(position, listView);
                        long itemId = getItemId(itemData);

                        if(ifIsSelected(itemId)) {
                            adapter.decreaseNumberSelected();
                            removeSelection(itemId);
                            itemView.setEnabled(false);
                        } else {
                            if (!adapter.atMaxSelected()) {
                                adapter.increaseNumberSelected();
                                addSelection(itemId);
                                itemView.setEnabled(true);
                            }
                        }
                        updateNumberSelected(adapter);
                    }
                }
            });
    }

    public long getItemId(ContentValues itemData) {
        switch (mType) {
            case TYPE_SPELL:
                return RulesSpellsUtils.getSpellId(itemData);
            case TYPE_FEAT:
                return RulesFeatsUtils.getFeatId(itemData);
            case TYPE_DOMAIN:
                return RulesDomainsUtils.getDomainId(itemData);
            case TYPE_FAMILIAR:
                return RulesFamiliarsUtils.getFamiliarId(itemData);
        }
        return -1;
    }

    public boolean ifIsSelected(long itemId) {
        switch (mType) {
            case TYPE_SPELL:
                return isSpellSelected(getContext(), rowIndex, itemId);
            case TYPE_FEAT:
                return isFeatSelected(getContext(), rowIndex, itemId);
            case TYPE_DOMAIN:
                return isDomainSelected(getContext(), rowIndex, itemId);
            case TYPE_FAMILIAR:
                return isFamiliarSameAsSelected(getContext(), rowIndex, itemId);
        }
        return false;
    }

    public void removeSelection(long itemId) {
        switch (mType) {
            case TYPE_SPELL:
                removeSpellSelection(getContext(), rowIndex, itemId);
                break;
            case TYPE_FEAT:
                removeFeatSelection(getContext(), rowIndex, itemId);
                break;
            case TYPE_DOMAIN:
                removeDomainSelection(getContext(), rowIndex, itemId);
                break;
            case TYPE_FAMILIAR:
                changeFamiliarSelection(getContext(), rowIndex, -1);
                break;
        }
    }

    public void addSelection(long itemId) {
        switch (mType) {
            case TYPE_SPELL:
                addSpellSelection(getContext(), rowIndex, itemId);
                break;
            case TYPE_FEAT:
                addFeatSelection(getContext(), rowIndex, itemId);
                break;
            case TYPE_DOMAIN:
                addDomainSelection(getContext(), rowIndex, itemId);
                break;
            case TYPE_FAMILIAR:
                changeFamiliarSelection(getContext(), rowIndex, itemId);
                break;
        }
    }

    public void updateNumberSelected(DnDListAdapter adapter) {
        TextView textView = (TextView) mRootView.findViewById(R.id.remaining_spells);
        int numberSelected = adapter.getNumberSelected();
        textView.setText(getString(R.string.selected_spells, numberSelected, numberToSelect));
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
