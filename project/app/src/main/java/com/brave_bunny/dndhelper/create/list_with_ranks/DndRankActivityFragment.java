package com.brave_bunny.dndhelper.create.list_with_ranks;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil;

import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesArmorUtils.getAllArmor;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesItemsUtils.getAllItems;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesSkillsUtils.getClassSkills;
import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesWeaponsUtils.getAllWeapons;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_ARMOR;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_ITEM;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_SKILL;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_WEAPON;

/**
 * A placeholder fragment containing a simple view.
 */
public class DndRankActivityFragment extends Fragment {

    private Cursor mCursor;
    private long rowIndex;
    private int mType;

    public DndRankActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle extras = getActivity().getIntent().getExtras();
        rowIndex = (long) extras.get(DnDRankActivity.indexValue);
        mType = (int) extras.get(DnDRankActivity.listType);
        View view = inflater.inflate(R.layout.fragment_dnd_ranks, container, false);

        ContentValues values = InProgressCharacterUtil.getInProgressRow(getContext(), rowIndex);
        int classId = InProgressCharacterUtil.getCharacterClass(values);

        switch (mType) {
            case TYPE_SKILL:
                mCursor = getClassSkills(getContext(), classId);
                break;
            case TYPE_WEAPON:
                mCursor = getAllWeapons(getContext());
                break;
            case TYPE_ARMOR:
                mCursor = getAllArmor(getContext());
                break;
            case TYPE_ITEM:
                mCursor = getAllItems(getContext());
                break;
        }
        ListView listView = (ListView) view.findViewById(R.id.listview_skills);
        TextView pointsText = (TextView) view.findViewById(R.id.points_left);

        DndRankAdapter adapter = new DndRankAdapter(getContext(), mCursor, 0, rowIndex, mType, pointsText);
        listView.setAdapter(adapter);

        return view;
    }
}
