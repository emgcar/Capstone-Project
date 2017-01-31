package com.brave_bunny.dndhelper.create.skills_feats;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.database.edition35.RulesContract;
import com.brave_bunny.dndhelper.database.edition35.RulesUtils;
import com.brave_bunny.dndhelper.database.inprogress.InProgressUtil;

/**
 * Created by Jemma on 1/30/2017.
 */

public class SkillAdapter extends CursorAdapter {

    private Context mContext;
    private View mRootView;
    private static int maximumSkillPoints;
    private static int skillRanksSpent;
    private static int maxRanks;
    private long mRowIndex;

    public SkillAdapter(Context context, Cursor c, int flags, long rowIndex) {
        super(context, c, flags);
        mContext = context;
        mRowIndex = rowIndex;

        //TODO find currently selected skills
        skillRanksSpent = InProgressUtil.numberSkillPointsSpent(context, rowIndex);

        //TODO find maximum skill ranks to spend
        maximumSkillPoints = 13;

        //TODO change for cross-class
        maxRanks = 4;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.list_item_skill, viewGroup, false);
        return mRootView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final View rootView = view;

        TextView tvBody = (TextView) view.findViewById(R.id.skill_name);
        String body = cursor.getString(cursor.getColumnIndexOrThrow(RulesContract.SkillsEntry.COLUMN_NAME));
        tvBody.setText(body);
        long skillId = cursor.getLong(cursor.getColumnIndexOrThrow(RulesContract.SkillsEntry._ID));
        tvBody.setTag(R.string.skills, skillId);

        Button minusButton = (Button) view.findViewById(R.id.minus_button);
        minusButton.setEnabled(false);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrementRanks(rootView);
            }
        });

        TextView rankText = (TextView) view.findViewById(R.id.skill_ranks);
        //TODO set saved skill values
        int ranks = InProgressUtil.getSkillRanks(context, mRowIndex, skillId);
        rankText.setText(Integer.toString(ranks));

        Button plusButton = (Button) view.findViewById(R.id.plus_button);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementRanks(rootView);
            }
        });
    }

    private void decrementRanks(View rootView) {
        TextView tvBody = (TextView) rootView.findViewById(R.id.skill_name);
        TextView rankText = (TextView) rootView.findViewById(R.id.skill_ranks);
        Button minusButton = (Button) rootView.findViewById(R.id.minus_button);
        Button plusButton = (Button) rootView.findViewById(R.id.plus_button);

        int ranks = Integer.parseInt((String)rankText.getText());
        if (ranks == 0) return;

        ranks--;
        skillRanksSpent--;

        long skillId = (long)tvBody.getTag(R.string.skills);
        InProgressUtil.addOrUpdateSkillSelecetion(mContext, mRowIndex, skillId, ranks);

        if (ranks == 0) {
            minusButton.setEnabled(false);
        }
        plusButton.setEnabled(true);
        rankText.setText(Integer.toString(ranks));
    }

    private void incrementRanks(View rootView) {
        TextView tvBody = (TextView) rootView.findViewById(R.id.skill_name);
        TextView rankText = (TextView) rootView.findViewById(R.id.skill_ranks);
        Button minusButton = (Button) rootView.findViewById(R.id.minus_button);
        Button plusButton = (Button) rootView.findViewById(R.id.plus_button);

        int ranks = Integer.parseInt((String)rankText.getText());
        if (skillRanksSpent == maximumSkillPoints) return;
        if (ranks == maxRanks) return;

        ranks++;
        skillRanksSpent++;

        long skillId = (long)tvBody.getTag(R.string.skills);
        InProgressUtil.addOrUpdateSkillSelecetion(mContext, mRowIndex, skillId, ranks);

        if (skillRanksSpent == maximumSkillPoints) {
            //TODO set all buttons disabled when no more skill points
            //plusButton.setEnabled(false);
        }
        if (ranks == maxRanks) {
            plusButton.setEnabled(false);
        }
        minusButton.setEnabled(true);
        rankText.setText(Integer.toString(ranks));
    }

    //TODO
    private void updateSkillPointText() {
    }
}
