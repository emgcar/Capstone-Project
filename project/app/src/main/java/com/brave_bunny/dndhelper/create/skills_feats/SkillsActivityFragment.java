package com.brave_bunny.dndhelper.create.skills_feats;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.create.classes.DeityActivity;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class SkillsActivityFragment extends Fragment {

    private Cursor mCursor;
    private long rowIndex;

    public SkillsActivityFragment() {
    }

    //TODO: populate previous choices
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle extras = getActivity().getIntent().getExtras();
        rowIndex = (long) extras.get(SkillsActivity.indexValue);
        View view = inflater.inflate(R.layout.fragment_skills, container, false);

        //TODO implement options for viewing all and cross-class also
        int classId = InProgressUtil.getInProgressValue(getContext(), rowIndex,
                InProgressUtil.COL_CHARACTER_CLASS_ID);
        mCursor = RulesUtils.getClassSkills(getContext(), classId);
        ListView listView = (ListView) view.findViewById(R.id.listview_skills);

        SkillAdapter adapter = new SkillAdapter(getContext(), mCursor, 0);
        listView.setAdapter(adapter);


        return view;
    }
}
