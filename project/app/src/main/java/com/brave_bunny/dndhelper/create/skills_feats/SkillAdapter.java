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

    private int mType;
    public final static int TYPE_SKILLS = 0;
    public final static int TYPE_ARMOR = 1;
    public final static int TYPE_WEAPONS = 2;
    public final static int TYPE_ITEMS = 3;

    public SkillAdapter(Context context, Cursor c, int flags, long rowIndex, int type) {
        super(context, c, flags);
        mContext = context;
        mRowIndex = rowIndex;

        //TODO find currently selected skills
        skillRanksSpent = InProgressUtil.numberSkillPointsSpent(context, rowIndex);

        //TODO find maximum skill ranks to spend
        maximumSkillPoints = 13;

        //TODO change for cross-class
        maxRanks = 4;

        mType = type;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.list_item_skill, viewGroup, false);
        return mRootView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final View rootView = view;

        long id;
        int nameIndex;
        int ranks;

        switch (mType) {
            case TYPE_SKILLS:
                id = cursor.getLong(cursor.getColumnIndexOrThrow(RulesContract.SkillsEntry._ID));
                nameIndex = cursor.getColumnIndexOrThrow(RulesContract.SkillsEntry.COLUMN_NAME);
                ranks = InProgressUtil.getSkillRanks(context, mRowIndex, id);
                break;
            case TYPE_ARMOR:
                id = cursor.getLong(cursor.getColumnIndexOrThrow(RulesContract.ArmorEntry._ID));
                nameIndex = cursor.getColumnIndexOrThrow(RulesContract.ArmorEntry.COLUMN_NAME);
                ranks = InProgressUtil.getArmorCount(context, mRowIndex, id);
                break;
            case TYPE_WEAPONS:
                id = cursor.getLong(cursor.getColumnIndexOrThrow(RulesContract.WeaponEntry._ID));
                nameIndex = cursor.getColumnIndexOrThrow(RulesContract.WeaponEntry.COLUMN_NAME);
                ranks = InProgressUtil.getWeaponCount(context, mRowIndex, id);
                break;
            case TYPE_ITEMS:
                id = cursor.getLong(cursor.getColumnIndexOrThrow(RulesContract.ItemEntry._ID));
                nameIndex = cursor.getColumnIndexOrThrow(RulesContract.ItemEntry.COLUMN_NAME);
                ranks = InProgressUtil.getItemCount(context, mRowIndex, id);
                break;
            default:
                return;
        }

        TextView tvBody = (TextView) view.findViewById(R.id.skill_name);
        String body = cursor.getString(nameIndex);
        tvBody.setText(body);
        tvBody.setTag(R.string.skills, id);

        Button minusButton = (Button) view.findViewById(R.id.minus_button);
        minusButton.setEnabled(false);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrementRanks(rootView);
            }
        });

        TextView rankText = (TextView) view.findViewById(R.id.skill_ranks);
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
        updateTable(skillId, ranks);

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
        if (mType == TYPE_SKILLS) {
            if (skillRanksSpent == maximumSkillPoints) return;
            if (ranks == maxRanks) return;
        }

        ranks++;
        skillRanksSpent++;

        long skillId = (long)tvBody.getTag(R.string.skills);
        updateTable(skillId, ranks);

        if (mType == TYPE_SKILLS) {
            if (skillRanksSpent == maximumSkillPoints) {
                //TODO set all buttons disabled when no more skill points
                //plusButton.setEnabled(false);
            }
            if (ranks == maxRanks) {
                plusButton.setEnabled(false);
            }
        }
        minusButton.setEnabled(true);
        rankText.setText(Integer.toString(ranks));
    }

    private void updateTable(long skillId, int count) {
        switch(mType) {
            case TYPE_SKILLS:
                InProgressUtil.addOrUpdateSkillSelection(mContext, mRowIndex, skillId, count);
                break;
            case TYPE_ARMOR:
                InProgressUtil.addOrUpdateArmorSelection(mContext, mRowIndex, skillId, count);
                break;
            case TYPE_WEAPONS:
                InProgressUtil.addOrUpdateWeaponSelection(mContext, mRowIndex, skillId, count);
                break;
            case TYPE_ITEMS:
                InProgressUtil.addOrUpdateItemSelection(mContext, mRowIndex, skillId, count);
                break;
        }
    }

    //TODO
    private void updateSkillPointText() {
    }
}
