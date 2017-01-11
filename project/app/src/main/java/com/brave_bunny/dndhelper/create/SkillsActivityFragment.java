package com.brave_bunny.dndhelper.create;

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
import com.brave_bunny.dndhelper.Utility;
import com.udacity.brave_bunny.dnd_3_5_library.TrialContract;
import com.udacity.brave_bunny.dnd_3_5_library.Utils;

/**
 * A placeholder fragment containing a simple view.
 */
public class SkillsActivityFragment extends Fragment {

    private Cursor mCursor;

    public SkillsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skills, container, false);

        mCursor = Utility.getAllSkills(getContext());
        ListView listView = (ListView) view.findViewById(R.id.listview_skills);

        CursorAdapter adapter = new CursorAdapter(getContext(), mCursor) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                return LayoutInflater.from(context).inflate(R.layout.list_item_skill, viewGroup, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView tvBody = (TextView) view.findViewById(R.id.skill_name);
                String body = cursor.getString(cursor.getColumnIndexOrThrow(TrialContract.SkillsEntry.COLUMN_NAME));
                tvBody.setText(body);
            }
        };
        listView.setAdapter(adapter);


        return view;
    }
}
