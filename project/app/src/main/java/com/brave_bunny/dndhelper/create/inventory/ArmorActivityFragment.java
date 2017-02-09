package com.brave_bunny.dndhelper.create.inventory;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.create.skills_feats.SkillAdapter;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtils.InProgressCharacterUtil;

import static com.brave_bunny.dndhelper.database.edition35.RulesUtils.RulesArmorUtils.getAllArmor;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArmorActivityFragment extends Fragment {

    private Cursor mCursor;
    private long rowIndex;

    public ArmorActivityFragment() {
    }

    //TODO: populate previous choices
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle extras = getActivity().getIntent().getExtras();
        rowIndex = (long) extras.get(ArmorActivity.indexValue);
        View view = inflater.inflate(R.layout.fragment_skills, container, false);

        //TODO implement options for viewing all and cross-class also
        ContentValues values = InProgressCharacterUtil.getInProgressRow(getContext(), rowIndex);
        float moneyAmount = InProgressCharacterUtil.getCharacterMoney(values);
        mCursor = getAllArmor(getContext());
        ListView listView = (ListView) view.findViewById(R.id.listview_skills);

        SkillAdapter adapter = new SkillAdapter(getContext(), mCursor, 0, rowIndex, SkillAdapter.TYPE_ARMOR);
        listView.setAdapter(adapter);


        return view;
    }
}
