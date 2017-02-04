package com.brave_bunny.dndhelper.create.inventory;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.create.skills_feats.SkillAdapter;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesCharacterUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtil;

import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesItemsUtils.getAllItems;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItemActivityFragment extends Fragment {

    private Cursor mCursor;
    private long rowIndex;

    public ItemActivityFragment() {
    }

    //TODO: populate previous choices
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle extras = getActivity().getIntent().getExtras();
        rowIndex = (long) extras.get(ItemActivity.indexValue);
        View view = inflater.inflate(R.layout.fragment_skills, container, false);

        //TODO implement options for viewing all and cross-class also
        int moneyAmount = InProgressUtil.getInProgressValue(getContext(), rowIndex,
                InProgressUtil.COL_CHARACTER_MONEY);
        mCursor = getAllItems(getContext());
        ListView listView = (ListView) view.findViewById(R.id.listview_skills);

        SkillAdapter adapter = new SkillAdapter(getContext(), mCursor, 0, rowIndex, SkillAdapter.TYPE_ITEMS);
        listView.setAdapter(adapter);


        return view;
    }
}
